package org.optimizationBenchmarking.experimentation.evaluation.impl.all.function;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.attributes.OnlySharedInstanceRuns;
import org.optimizationBenchmarking.experimentation.attributes.clusters.ICluster;
import org.optimizationBenchmarking.experimentation.attributes.clusters.IClustering;
import org.optimizationBenchmarking.experimentation.attributes.clusters.byInstance.ByInstanceGrouping;
import org.optimizationBenchmarking.experimentation.attributes.clusters.propertyValueGroups.PropertyValueSelector;
import org.optimizationBenchmarking.experimentation.attributes.functions.FunctionAttribute;
import org.optimizationBenchmarking.experimentation.data.spec.Attribute;
import org.optimizationBenchmarking.experimentation.data.spec.IExperiment;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.evaluation.impl.abstr.ExperimentSetJob;
import org.optimizationBenchmarking.utils.chart.spec.ELegendMode;
import org.optimizationBenchmarking.utils.chart.spec.ELineType;
import org.optimizationBenchmarking.utils.chart.spec.IAxis;
import org.optimizationBenchmarking.utils.chart.spec.ILine2D;
import org.optimizationBenchmarking.utils.chart.spec.ILineChart2D;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.document.impl.FigureSizeParser;
import org.optimizationBenchmarking.utils.document.spec.EFigureSize;
import org.optimizationBenchmarking.utils.document.spec.IComplexText;
import org.optimizationBenchmarking.utils.document.spec.IFigure;
import org.optimizationBenchmarking.utils.document.spec.IFigureSeries;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.document.spec.ISectionBody;
import org.optimizationBenchmarking.utils.document.spec.ISectionContainer;
import org.optimizationBenchmarking.utils.graphics.style.StyleSet;
import org.optimizationBenchmarking.utils.graphics.style.color.ColorStyle;
import org.optimizationBenchmarking.utils.graphics.style.font.FontStyle;
import org.optimizationBenchmarking.utils.graphics.style.stroke.StrokeStyle;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.CompoundAggregate;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.FiniteMaximumAggregate;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.FiniteMinimumAggregate;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.IAggregate;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.ScalarAggregate;
import org.optimizationBenchmarking.utils.math.text.DefaultParameterRenderer;
import org.optimizationBenchmarking.utils.parsers.AnyNumberParser;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * A function job is a base class
 * {@link org.optimizationBenchmarking.experimentation.evaluation.impl.abstr.ExperimentSetJob}
 * designed to support evaluation approaches which plot functions for
 * different experiments into one diagram. The function job is applied to
 * an
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet}
 * and will compute the function for each
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IExperiment
 * experiment} in that experiment set. Computation is limited to
 * {@link org.optimizationBenchmarking.experimentation.data.spec.IInstanceRuns}
 * for which data exists in all experiments. Diagrams are only printed if
 * they contain functions functions with at least two different {@code x}
 * and {@code y} coordinates, i.e., no empty diagrams will be plotted.
 * Clusters or experiment sets which would lead to empty diagrams are not
 * considered in the computation, experiments which produce empty functions
 * (not containing at least one value, are omitted as well.
 * {@link #getClusteringAlgorithm() Clustering} is supported, i.e., the
 * experiment set can be divided into different sub-sets according to
 * different criteria.
 */
public abstract class FunctionJob extends ExperimentSetJob {

  /**
   * Should there be a sub-figure which only serves as legend, hence
   * allowing us to omit legends in the other figures?
   */
  public static final String PARAM_MAKE_LEGEND_FIGURE = "makeLegendFigure"; //$NON-NLS-1$
  /** Should the figures include axis titles? */
  public static final String PARAM_PRINT_AXIS_TITLES = "showAxisTitles"; //$NON-NLS-1$

  /** the minimum value for the x-axis */
  public static final String PARAM_MIN_X = "minX";//$NON-NLS-1$
  /** the maximum value for the x-axis */
  public static final String PARAM_MAX_X = "maxX";//$NON-NLS-1$
  /** the minimum value for the y-axis */
  public static final String PARAM_MIN_Y = "minY";//$NON-NLS-1$
  /** the maximum value for the y-axis */
  public static final String PARAM_MAX_Y = "maxY";//$NON-NLS-1$

  /** the figure size */
  private final EFigureSize m_figureSize;

  /** the clusterer, or {@code null} if none was requested */
  private final Attribute<? super IExperimentSet, ? extends IClustering> m_clusterer;

  /** the property for computing the function data */
  private final FunctionAttribute<? super IExperiment> m_function;

  /** should we make a legend figure? */
  private final boolean m_makeLegendFigure;

  /** should we print axis titles? */
  private final boolean m_showAxisTitles;

  /** the minimum value for the x-axis, or {@code null} if undefined */
  private final Number m_minX;
  /** the maximum value for the x-axis, or {@code null} if undefined */
  private final Number m_maxX;
  /** the minimum value for the y-axis, or {@code null} if undefined */
  private final Number m_minY;
  /** the maximum value for the y-axis, or {@code null} if undefined */
  private final Number m_maxY;

  /**
   * Create the function job
   *
   * @param data
   *          the data
   * @param attribute
   *          the function attribute to use
   * @param logger
   *          the logger
   * @param config
   *          the configuration
   */
  protected FunctionJob(final IExperimentSet data,
      final FunctionAttribute<? super IExperiment> attribute,
      final Configuration config, final Logger logger) {
    super(data, logger);

    final EFigureSize defau;

    if (attribute == null) {
      throw new IllegalArgumentException("Function cannot be null."); //$NON-NLS-1$
    }

    defau = this.getDefaultFigureSize(data);
    if (defau == null) {
      throw new IllegalArgumentException(//
          "Default figure size cannot be null."); //$NON-NLS-1$
    }

    this.m_function = attribute;
    this.m_figureSize = config.get(FigureSizeParser.PARAM_FIGURE_SIZE,
        FigureSizeParser.INSTANCE, defau);

    this.m_makeLegendFigure = config.getBoolean(
        FunctionJob.PARAM_MAKE_LEGEND_FIGURE,
        this.getDefaultMakeLegendFigure(data, this.m_figureSize));

    this.m_showAxisTitles = config.getBoolean(
        FunctionJob.PARAM_PRINT_AXIS_TITLES, this
            .getDefaultShowAxisTitles(data, this.m_figureSize,
                this.m_makeLegendFigure));

    this.m_clusterer = this.configureClustering(data, config);

    this.m_minX = config.get(FunctionJob.PARAM_MIN_X,
        AnyNumberParser.INSTANCE, null);
    this.m_maxX = config.get(FunctionJob.PARAM_MAX_X,
        AnyNumberParser.INSTANCE, null);
    this.m_minY = config.get(FunctionJob.PARAM_MIN_Y,
        AnyNumberParser.INSTANCE, null);
    this.m_maxY = config.get(FunctionJob.PARAM_MAX_Y,
        AnyNumberParser.INSTANCE, null);
  }

  /**
   * Get the minimum value for the x-axis provided via the configuration.
   * If this value is not {@code null}, {@link #getXAxisMinimumValue()} and
   * {@link #getXAxisMinimumAggregate()} are ignored.
   *
   * @return the configured minimum value for the {@code x} axis, or
   *         {@code null} if none is defined
   */
  public final Number getXAxisConfiguredMin() {
    return this.m_minX;
  }

  /**
   * Get the maximum value for the x-axis provided via the configuration.
   * If this value is not {@code null}, {@link #getXAxisMaximumValue()} and
   * {@link #getXAxisMaximumAggregate()} are ignored.
   *
   * @return the configured maximum value for the {@code x} axis, or
   *         {@code null} if none is defined
   */
  public final Number getXAxisConfiguredMax() {
    return this.m_maxX;
  }

  /**
   * Get the minimum value for the y-axis provided via the configuration.
   * If this value is not {@code null}, {@link #getYAxisMinimumValue()} and
   * {@link #getYAxisMinimumAggregate()} are ignored.
   *
   * @return the configured minimum value for the {@code y} axis, or
   *         {@code null} if none is defined
   */
  public final Number getYAxisConfiguredMin() {
    return this.m_minY;
  }

  /**
   * Get the maximum value for the y-axis provided via the configuration.
   * If this value is not {@code null}, {@link #getYAxisMaximumValue()} and
   * {@link #getYAxisMaximumAggregate()} are ignored.
   *
   * @return the configured maximum value for the {@code y} axis, or
   *         {@code null} if none is defined
   */
  public final Number getYAxisConfiguredMax() {
    return this.m_maxY;
  }

  /**
   * Get the function to be plotted
   *
   * @return the function to be plotted
   */
  public final FunctionAttribute<? super IExperiment> getFunction() {
    return this.m_function;
  }

  /**
   * Get the clustering algorithm to be used, or {@code null} if no
   * clustering should be performed.
   *
   * @return the clustering algorithm to be used, or {@code null} if no
   *         clustering should be performed
   */
  public final Attribute<? super IExperimentSet, ? extends IClustering> getClusteringAlgorithm() {
    return this.m_clusterer;
  }

  /**
   * Get the figure size of this function job.
   *
   * @return the figure size of this function job
   */
  public final EFigureSize getFigureSize() {
    return this.m_figureSize;
  }

  /**
   * Will this job include dedicated legend figures?
   *
   * @return {@code true} if the each figure also contains a dedicated
   *         legend figure, {@code false} if no dedicated legend is painted
   *         (i.e., each figure contains a legend).
   */
  public final boolean hasLegendFigure() {
    return this.m_makeLegendFigure;
  }

  /**
   * Will there be axis titles?
   *
   * @return {@code true} if the each figure contains axis titles,
   *         {@code false} if no axis titles are shown (except for in a
   *         potential {@link #hasLegendFigure() legend figure}
   */
  public final boolean hasAxisTitles() {
    return this.m_showAxisTitles;
  }

  /**
   * Get the default figure size
   *
   * @param data
   *          the experiment set
   * @return the default figure size
   */
  protected EFigureSize getDefaultFigureSize(final IExperimentSet data) {
    final int size;

    size = data.getData().size();
    if (size > 6) {
      if (size > 12) {
        return EFigureSize.PAGE_WIDE;
      }
      return EFigureSize.PAGE_2_PER_ROW;
    }

    return EFigureSize.PAGE_3_PER_ROW;
  }

  /**
   * Get the default value for deciding whether a legend figure should be
   * made.
   *
   * @param data
   *          the experiment set
   * @param figureSize
   *          the figure size
   * @return {@code true} if a legend figure should be made, {@code false}
   *         otherwise
   */
  protected boolean getDefaultMakeLegendFigure(final IExperimentSet data,
      final EFigureSize figureSize) {
    final int size;
    int n;

    n = figureSize.getNX();
    if (!(figureSize.spansAllColumns())) {
      n <<= 1;
    }

    if (n >= 5) {
      return true;
    }

    size = data.getData().size();
    if (size >= 16) {
      return true;
    }

    if ((size >= 8) && (n >= 2)) {
      return true;
    }

    return false;
  }

  /**
   * Get the default value for deciding whether axis titles should be
   * included in the figure.
   *
   * @param data
   *          the experiment set
   * @param figureSize
   *          the figure size
   * @param hasLegendFigure
   *          is there a separate legend figure?
   * @return {@code true} if axis titles should be displayed in each
   *         figure, {@code false} if axis titles are not printed (except
   *         for in a potential legend figure)
   */
  protected boolean getDefaultShowAxisTitles(final IExperimentSet data,
      final EFigureSize figureSize, final boolean hasLegendFigure) {
    int n;

    if (hasLegendFigure) {
      return false;
    }

    n = figureSize.getNX();
    if (!(figureSize.spansAllColumns())) {
      n <<= 1;
    }

    return (n <= 4);
  }

  /**
   * Obtain the attribute used to get the clustering
   *
   * @param data
   *          the data
   * @param config
   *          the configuration
   * @return the clustering
   */
  protected Attribute<? super IExperimentSet, ? extends IClustering> configureClustering(
      final IExperimentSet data, final Configuration config) {

    if (config.getBoolean(ByInstanceGrouping.PARAM_BY_INSTANCE, false)) {
      return ByInstanceGrouping.INSTANCE;
    }

    return PropertyValueSelector.configure(data, config);
  }

  /**
   * Obtain a suggestion for the path to store the sub-figures in
   *
   * @return a suggestion for the path to store the sub-figures in
   */
  protected String getFunctionPathComponentSuggestion() {
    return this.m_function.getPathComponentSuggestion();
  }

  /**
   * Get the title font of the x-axis. Returns {@code null} to use default.
   *
   * @return the title font of the x-axis, or {@code null} for default
   */
  protected FontStyle getXAxisTitleFont() {
    return null;
  }

  /**
   * Get the tick font of the x-axis. Returns {@code null} to use default.
   *
   * @return the tick font of the x-axis, or {@code null} for default
   */
  protected FontStyle getXAxisTickFont() {
    return null;
  }

  /**
   * Get the color of the x-axis. Returns {@code null} to use default.
   *
   * @return the color of the x-axis, or {@code null} for default
   */
  protected ColorStyle getXAxisColor() {
    return null;
  }

  /**
   * Get the stroke of the x-axis. Returns {@code null} to use default.
   *
   * @return the stroke of the x-axis, or {@code null} for default
   */
  protected StrokeStyle getXAxisStroke() {
    return null;
  }

  /**
   * Get an aggregate to be used to determine the minimum value of the
   * x-axis if none was {@link #getXAxisConfiguredMin() configured}. If
   * this method returns {@code null}, the fixed value returned by
   * {@link #getXAxisMinimumValue()} is used as minimum for the x-axis.
   *
   * @return the aggregate
   * @see #getXAxisMinimumValue()
   * @see #getXAxisConfiguredMin()
   */
  protected ScalarAggregate getXAxisMinimumAggregate() {
    return new FiniteMinimumAggregate();
  }

  /**
   * Get a fixed minimum value for the x-axis. This method is only called
   * if {@link #getXAxisMinimumAggregate()} and
   * {@link #getXAxisConfiguredMin()} both return {@code null}.
   *
   * @return a {@code double} value representing the minimum value for the
   *         x-axis
   * @see #getXAxisMinimumAggregate()
   * @see #getXAxisConfiguredMin()
   */
  protected double getXAxisMinimumValue() {
    return 0d;
  }

  /**
   * Get an aggregate to be used to determine the maximum value of the
   * x-axis if none was {@link #getXAxisConfiguredMax() configured} . If
   * this method returns {@code null}, the fixed value returned by
   * {@link #getXAxisMaximumValue()} is used as maximum for the x-axis.
   *
   * @return the aggregate
   * @see #getXAxisMaximumValue()
   * @see #getXAxisConfiguredMax()
   */
  protected ScalarAggregate getXAxisMaximumAggregate() {
    return new FiniteMaximumAggregate();
  }

  /**
   * Get a fixed maximum value for the x-axis. This method is only called
   * if {@link #getXAxisMaximumAggregate()} and
   * {@link #getXAxisConfiguredMax()} both return {@code null}.
   *
   * @return a {@code double} value representing the maximum value for the
   *         x-axis
   * @see #getXAxisMaximumAggregate()
   * @see #getXAxisConfiguredMax()
   */
  protected double getXAxisMaximumValue() {
    return 0d;
  }

  /**
   * Get the title font of the y-axis. Returns {@code null} to use default.
   *
   * @return the title font of the y-axis, or {@code null} for default
   */
  protected FontStyle getYAxisTitleFont() {
    return null;
  }

  /**
   * Get the tick font of the y-axis. Returns {@code null} to use default.
   *
   * @return the tick font of the y-axis, or {@code null} for default
   */
  protected FontStyle getYAxisTickFont() {
    return null;
  }

  /**
   * Get the color of the y-axis. Returns {@code null} to use default.
   *
   * @return the color of the y-axis, or {@code null} for default
   */
  protected ColorStyle getYAxisColor() {
    return null;
  }

  /**
   * Get the stroke of the y-axis. Returns {@code null} to use default.
   *
   * @return the stroke of the y-axis, or {@code null} for default
   */
  protected StrokeStyle getYAxisStroke() {
    return null;
  }

  /**
   * Get an aggregate to be used to determine the minimum value of the
   * y-axis if none was {@link #getYAxisConfiguredMin() configured}. If
   * this method returns {@code null}, the fixed value returned by
   * {@link #getYAxisMinimumValue()} is used as minimum for the y-axis.
   *
   * @return the aggregate
   * @see #getYAxisMinimumValue()
   * @see #getYAxisConfiguredMin()
   */
  protected ScalarAggregate getYAxisMinimumAggregate() {
    return new FiniteMinimumAggregate();
  }

  /**
   * Get a fixed minimum value for the y-axis. This method is only called
   * if {@link #getYAxisMinimumAggregate()} and
   * {@link #getYAxisConfiguredMin()} both return {@code null}.
   *
   * @return a {@code double} value representing the minimum value for the
   *         y-axis
   * @see #getYAxisMinimumAggregate()
   * @see #getYAxisConfiguredMin()
   */
  protected double getYAxisMinimumValue() {
    return 0d;
  }

  /**
   * Get an aggregate to be used to determine the maximum value of the
   * y-axis if none was {@link #getYAxisConfiguredMax() configured} . If
   * this method returns {@code null}, the fixed value returned by
   * {@link #getYAxisMaximumValue()} is used as maximum for the y-axis.
   *
   * @return the aggregate
   * @see #getYAxisMaximumValue()
   * @see #getYAxisConfiguredMax()
   */
  protected ScalarAggregate getYAxisMaximumAggregate() {
    return new FiniteMaximumAggregate();
  }

  /**
   * Get a fixed maximum value for the y-axis. This method is only called
   * if {@link #getYAxisMaximumAggregate()} and
   * {@link #getYAxisConfiguredMax()} both return {@code null}.
   *
   * @return a {@code double} value representing the maximum value for the
   *         y-axis
   * @see #getYAxisMaximumAggregate()
   * @see #getYAxisConfiguredMax()
   */
  protected double getYAxisMaximumValue() {
    return 0d;
  }

  /**
   * Get an optional starting point for a given experiment function.
   *
   * @param function
   *          the experiment function data
   * @param xyDest
   *          an array of length 2 to receive an x and y coordinate of the
   *          starting point (in case {@code true} is returned)
   * @return {@code true} if a starting point was set, {@code false}
   *         otherwise
   */
  protected boolean getExperimentFunctionStart(
      final ExperimentFunction function, final double[] xyDest) {
    return false; // nothing
  }

  /**
   * Get an optional end point for a given experiment function.
   *
   * @param function
   *          the experiment function data
   * @param xyDest
   *          an array of length 2 to receive an x and y coordinate of the
   *          end point (in case {@code true} is returned)
   * @return {@code true} if a end point was set, {@code false} otherwise
   */
  protected boolean getExperimentFunctionEnd(
      final ExperimentFunction function, final double[] xyDest) {
    return false; // nothing
  }

  /**
   * Get the color to use for a given experiment.
   *
   * @param function
   *          the experiment function data
   * @param styles
   *          the style set
   * @return the color to use for the line or {@code null} for default
   */
  protected ColorStyle getExperimentColor(
      final ExperimentFunction function, final StyleSet styles) {
    return null;
  }

  /**
   * Get the stroke to use for a given experiment.
   *
   * @param function
   *          the experiment function data
   * @param styles
   *          the style set
   * @return the stroke to use for the line, or {@code null} for the
   *         default
   */
  protected StrokeStyle getExperimentStroke(
      final ExperimentFunction function, final StyleSet styles) {
    return null;
  }

  /**
   * Get the font to use for a given experiment.
   *
   * @param function
   *          the experiment function data
   * @param styles
   *          the style set
   * @return the font style to use for the line, or {@code null} for the
   *         default
   */
  protected FontStyle getExperimentFont(final ExperimentFunction function,
      final StyleSet styles) {
    return null;
  }

  /**
   * Get the title to use for a given experiment.
   *
   * @param function
   *          the experiment function data
   * @return the title to use for the experiment
   */
  protected String getExperimentTitle(final ExperimentFunction function) {
    return function.getExperiment().getName();
  }

  /**
   * Get the line type to use for a given experiment.
   *
   * @param function
   *          the experiment function data
   * @return the line type to use for the line
   */
  protected ELineType getExperimentLineType(
      final ExperimentFunction function) {
    return ELineType.STAIRS_KEEP_LEFT;
  }

  /**
   * draw the line chart
   *
   * @param chart
   *          the chart
   * @param data
   *          the data to paint
   * @param showAxisTitles
   *          should we show the axis titles?
   * @param showLineTitles
   *          should line titles be shown?
   * @param styles
   *          the style set
   */
  private final void __drawChart(final ILineChart2D chart,
      final ExperimentSetFunctions data, final boolean showLineTitles,
      final boolean showAxisTitles, final StyleSet styles) {
    final MemoryTextOutput mto;
    final double[] xy;
    String title;
    StrokeStyle stroke;
    FontStyle font;
    ColorStyle color;
    ScalarAggregate aggregate;
    Number number;

    if (showAxisTitles) {
      mto = new MemoryTextOutput();
    } else {
      mto = null;
    }

    // initialize the x-axis
    try (final IAxis xAxis = chart.xAxis()) {
      if (mto != null) {
        this.m_function.getXAxisSemanticComponent().mathRender(mto,
            DefaultParameterRenderer.INSTANCE);
        xAxis.setTitle(mto.toString());
        mto.clear();

        font = this.getXAxisTitleFont();
        if (font != null) {
          xAxis.setTitleFont(font.getFont());
        }
      }

      number = this.getXAxisConfiguredMin();
      if (number != null) {
        xAxis.setMinimum(number.doubleValue());
      } else {
        aggregate = this.getXAxisMinimumAggregate();
        if (aggregate != null) {
          xAxis.setMinimumAggregate(aggregate);
        } else {
          xAxis.setMinimum(this.getXAxisMinimumValue());
        }
      }

      number = this.getXAxisConfiguredMax();
      if (number != null) {
        xAxis.setMaximum(number.doubleValue());
      } else {
        aggregate = this.getXAxisMaximumAggregate();
        if (aggregate != null) {
          xAxis.setMaximumAggregate(aggregate);
        } else {
          xAxis.setMaximum(this.getXAxisMaximumValue());
        }
      }

      color = this.getXAxisColor();
      if (color != null) {
        xAxis.setAxisColor(color);
      }

      stroke = this.getXAxisStroke();
      if (stroke != null) {
        xAxis.setAxisStroke(stroke);
      }

      font = this.getXAxisTickFont();
      if (font != null) {
        xAxis.setTickFont(font.getFont());
      }
    }

    // initialize the y-axis

    try (final IAxis yAxis = chart.yAxis()) {
      if (mto != null) {
        this.m_function.getYAxisSemanticComponent().mathRender(mto,
            DefaultParameterRenderer.INSTANCE);
        yAxis.setTitle(mto.toString());
        mto.clear();

        font = this.getYAxisTitleFont();
        if (font != null) {
          yAxis.setTitleFont(font.getFont());
        }
      }

      number = this.getYAxisConfiguredMin();
      if (number != null) {
        yAxis.setMinimum(number.doubleValue());
      } else {
        aggregate = this.getYAxisMinimumAggregate();
        if (aggregate != null) {
          yAxis.setMinimumAggregate(aggregate);
        } else {
          yAxis.setMinimum(this.getYAxisMinimumValue());
        }
      }

      number = this.getYAxisConfiguredMax();
      if (number != null) {
        yAxis.setMaximum(number.doubleValue());
      } else {
        aggregate = this.getYAxisMaximumAggregate();
        if (aggregate != null) {
          yAxis.setMaximumAggregate(aggregate);
        } else {
          yAxis.setMaximum(this.getYAxisMaximumValue());
        }
      }

      color = this.getYAxisColor();
      if (color != null) {
        yAxis.setAxisColor(color);
      }

      stroke = this.getYAxisStroke();
      if (stroke != null) {
        yAxis.setAxisStroke(stroke);
      }

      font = this.getYAxisTickFont();
      if (font != null) {
        yAxis.setTickFont(font.getFont());
      }
    }

    // plot the functions, one for each experiment
    xy = new double[2];
    for (final ExperimentFunction experimentFunction : data.getData()) {

      try (final ILine2D line = chart.line()) {
        line.setData(experimentFunction.getFunction());

        // set values to NaN to provoke fail-fast behavior
        xy[0] = Double.NaN;
        xy[1] = Double.NaN;
        if (this.getExperimentFunctionStart(experimentFunction, xy)) {
          line.setStart(xy[0], xy[1]);
        }

        // set values to NaN to provoke fail-fast behavior
        xy[0] = Double.NaN;
        xy[1] = Double.NaN;
        if (this.getExperimentFunctionEnd(experimentFunction, xy)) {
          line.setStart(xy[0], xy[1]);
        }

        line.setType(this.getExperimentLineType(experimentFunction));

        color = this.getExperimentColor(experimentFunction, styles);
        if (color != null) {
          line.setColor(color);
        } else {
          line.setColor(styles.getColor(experimentFunction.getExperiment()
              .getName(), true));
        }

        stroke = this.getExperimentStroke(experimentFunction, styles);
        if (stroke != null) {
          line.setStroke(stroke);
        } else {
          line.setStroke(styles.getDefaultStroke());
        }

        if (showLineTitles) {
          title = this.getExperimentTitle(experimentFunction);
          if (title != null) {
            line.setTitle(title);
            font = this.getExperimentFont(experimentFunction, styles);
            if (font != null) {
              line.setTitleFont(font.getFont());
            } else {
              line.setTitleFont(styles.getDefaultFont().getFont());
            }
          }
        }
      }
    }
  }

  /**
   * Print the plots into a section body
   *
   * @param data
   *          the data
   * @param body
   *          the section body
   * @param styles
   *          the style set
   * @param logger
   *          the logger
   * @param mainLabel
   *          the main label to be used
   * @return the actual label of the created figure
   */
  protected ILabel makePlots(final FunctionData data,
      final ISectionBody body, final StyleSet styles, final Logger logger,
      final ILabel mainLabel) {
    final ILabel ret;
    final String mainPath;
    final IClustering clustering;
    final ArrayListView<ExperimentSetFunctions> allFunctions;
    ExperimentSetFunctions experimentSetFunctions;
    ICluster cluster;
    String path;
    int size, index;

    if (data == null) {
      throw new IllegalArgumentException(//
          "Function data is null, which means we cannot draw any diagram."); //$NON-NLS-1$
    }

    path = this.getFunctionPathComponentSuggestion();
    clustering = data.getClustering();
    if (clustering != null) {
      path = (path + '_' + clustering.getPathComponentSuggestion());
    }
    if (this.m_minX != null) {
      path += ("_x1=" + this.m_minX); //$NON-NLS-1$
    }
    if (this.m_maxX != null) {
      path += ("_x2=" + this.m_maxX); //$NON-NLS-1$
    }
    if (this.m_minY != null) {
      path += ("_y1=" + this.m_minY); //$NON-NLS-1$
    }
    if (this.m_maxY != null) {
      path += ("_y2=" + this.m_maxY); //$NON-NLS-1$
    }
    mainPath = path;

    allFunctions = data.getData();
    size = allFunctions.size();
    if (size <= 0) {
      throw new IllegalStateException(//
          "List of experiments cannot be empty.");//$NON-NLS-1$
    }

    if (this.m_makeLegendFigure) {
      size++;
    }

    if (size == 1) {
      path = mainPath;
      experimentSetFunctions = allFunctions.get(0);
      cluster = experimentSetFunctions.getCluster();
      if (cluster != null) {
        path = (path + '_' + cluster.getPathComponentSuggestion());
      }

      try (final IFigure figure = body.figure(mainLabel,
          this.m_figureSize, path)) {
        this.__logFigure(logger, 1, 1);

        try (final IComplexText caption = figure.caption()) {

          caption.append("The "); //$NON-NLS-1$
          this.m_function.printLongName(caption, ETextCase.IN_SENTENCE);
          if (clustering != null) {
            caption.append(" with data separated according to "); //$NON-NLS-1$
            clustering.printLongName(caption, ETextCase.IN_SENTENCE);
            if (cluster != null) {
              caption.append(':');
              caption.append(' ');
              cluster.printLongName(caption, ETextCase.IN_SENTENCE);
            }
          }
          caption.append('.');
        }

        try (final ILineChart2D lines = figure.lineChart2D()) {
          lines.setLegendMode(ELegendMode.SHOW_COMPLETE_LEGEND);
          this.__drawChart(lines, experimentSetFunctions, true,
              this.m_showAxisTitles, styles);
        }

        ret = figure.getLabel();
      }
    } else {
      try (final IFigureSeries figureSeries = body.figureSeries(mainLabel,
          this.m_figureSize, mainPath)) {

        try (final IComplexText caption = figureSeries.caption()) {
          caption.append("The "); //$NON-NLS-1$
          this.m_function.printLongName(caption, ETextCase.IN_SENTENCE);
          if (clustering != null) {
            caption.append(" with data separated according to "); //$NON-NLS-1$
            clustering.printLongName(caption, ETextCase.IN_SENTENCE);
          }
          caption.append('.');
        }

        index = 0;

        if (this.m_makeLegendFigure) {
          path = (mainPath + "_legend"); //$NON-NLS-1$

          this.__logFigure(logger, (++index), size);
          try (final IFigure figure = figureSeries.figure(null, path)) {

            try (final IComplexText caption = figure.caption()) {
              caption.append(//
                  "Legend for all sub-figures of this figure.");//$NON-NLS-1$
            }
            try (final ILineChart2D lines = figure.lineChart2D()) {
              lines.setLegendMode(ELegendMode.CHART_IS_LEGEND);
              this.__drawChart(lines, allFunctions.get(0), true, true,
                  styles);
            }
          }
        }

        for (final ExperimentSetFunctions experimentSetFunctions2 : data
            .getData()) {

          path = mainPath;
          cluster = experimentSetFunctions2.getCluster();
          if (cluster != null) {
            path = (path + '_' + cluster.getPathComponentSuggestion());
          }

          this.__logFigure(logger, (++index), size);
          try (final IFigure figure = figureSeries.figure(null, path)) {

            try (final IComplexText caption = figure.caption()) {
              if (cluster == null) {
                this.m_function.printLongName(caption,
                    ETextCase.IN_SENTENCE);
              } else {
                cluster.printLongName(caption, ETextCase.IN_SENTENCE);
              }
            }

            try (final ILineChart2D lines = figure.lineChart2D()) {
              lines.setLegendMode(this.m_makeLegendFigure//
              ? ELegendMode.HIDE_COMPLETE_LEGEND//
                  : ELegendMode.SHOW_COMPLETE_LEGEND);
              this.__drawChart(lines, experimentSetFunctions2,
                  (!(this.m_makeLegendFigure)),
                  (!(this.m_makeLegendFigure)), styles);
            }
          }

        }

        ret = figureSeries.getLabel();
      }
    }

    return ret;
  }

  /**
   * Make the title
   *
   * @param title
   *          the title destination
   */
  protected void makeTitle(final IComplexText title) {
    this.m_function.printLongName(title, ETextCase.AT_TITLE_START);
  }

  /**
   * log that a figure is painted
   *
   * @param logger
   *          the logger
   * @param index
   *          the index of the figure
   * @param size
   *          the size
   */
  private final void __logFigure(final Logger logger, final int index,
      final int size) {
    if ((logger != null) && (logger.isLoggable(Level.FINEST))) {
      logger.finest((((this.getClass().getSimpleName() + //
          " now painting figure ") //$NON-NLS-1$
          + index) + " of ") + size);//$NON-NLS-1$

    }
  }

  /**
   * Perform the main process: Paint the figures, write the text.
   *
   * @param data
   *          the data, or {@code null} if no data exists to draw
   *          reasonable diagrams
   * @param sectionContainer
   *          the section container
   * @param logger
   *          the logger
   */
  protected void process(final FunctionData data,
      final ISectionContainer sectionContainer, final Logger logger) {
    //
  }

  /** {@inheritDoc} */
  @Override
  protected void doMain(final IExperimentSet data,
      final ISectionContainer sectionContainer, final Logger logger) {
    final FunctionData functionData;

    if ((logger != null) && (logger.isLoggable(Level.FINER))) {
      logger.finer("Beginning to compute data for " + //$NON-NLS-1$
          TextUtils.className(this.getClass()));
    }

    functionData = this.__makeData(data, logger);

    if ((logger != null) && (logger.isLoggable(Level.FINER))) {
      logger.finer(((("Finished computing data for " + //$NON-NLS-1$
          TextUtils.className(this.getClass())) + ", found ") + //$NON-NLS-1$
          ((functionData != null) ? functionData.getData().size() : 0))
          + " clusters with useful data, which will now be processed.");//$NON-NLS-1$
    }

    this.process(functionData, sectionContainer, logger);

    if ((logger != null) && (logger.isLoggable(Level.FINER))) {
      logger.finer("Finished processing of data in " + //$NON-NLS-1$
          TextUtils.className(this.getClass()));
    }
  }

  /**
   * Make the data for a given experiment set
   *
   * @param logger
   *          the logger
   * @param set
   *          the experiment set
   * @return the data
   */
  private final FunctionData __makeData(final IExperimentSet set,
      final Logger logger) {
    final IExperimentSet selected;
    final IClustering clustering;
    final int size;
    ArrayList<ExperimentFunction> experimentSetTemp;
    ArrayList<ExperimentSetFunctions> clustersTemp;
    ExperimentSetFunctions experimentSetFunctions;

    selected = OnlySharedInstanceRuns.INSTANCE.get(set);

    experimentSetTemp = new ArrayList<>();

    if (this.m_clusterer != null) {

      if ((logger != null) && (logger.isLoggable(Level.FINER))) {
        logger.finer("Now computing clustering of data according to " //$NON-NLS-1$
            + this.m_clusterer);
      }
      clustering = this.m_clusterer.get(selected);
      if ((logger != null) && (logger.isLoggable(Level.FINER))) {
        logger.finer("Finished computing clustering of data according to " //$NON-NLS-1$
            + this.m_clusterer);
      }
      if (clustering == null) {
        throw new IllegalStateException(//
            "Clustering cannot be null.");//$NON-NLS-1$
      }

      clustersTemp = new ArrayList<>();
      for (final ICluster cluster : clustering.getData()) {
        experimentSetFunctions = this.__makeFunctions(cluster, cluster,
            experimentSetTemp);
        if (experimentSetFunctions != null) {
          clustersTemp.add(experimentSetFunctions);
        }
      }
    } else {
      experimentSetFunctions = this.__makeFunctions(selected, null,
          experimentSetTemp);
      if (experimentSetFunctions == null) {
        return null;
      }
      clustering = null;
      clustersTemp = new ArrayList<>();
      clustersTemp.add(experimentSetFunctions);
    }

    size = clustersTemp.size();
    if (size <= 0) {
      return null;
    }
    return new FunctionData(clustering,
        clustersTemp.toArray(new ExperimentSetFunctions[size]));
  }

  /**
   * Make the functions over the experiment sets.
   *
   * @param set
   *          the experiment set
   * @param cluster
   *          the cluster
   * @param temp
   *          a temporary list to store the results
   * @return the functions, or {@code null} if none were defined
   */
  private final ExperimentSetFunctions __makeFunctions(
      final IExperimentSet set, final ICluster cluster,
      final ArrayList<ExperimentFunction> temp) {
    final FiniteMinimumAggregate minX, minY;
    final FiniteMaximumAggregate maxX, maxY;
    final IAggregate minMaxX, minMaxY;
    final int size;
    IMatrix function;
    ExperimentSetFunctions retVal;

    minX = new FiniteMinimumAggregate();
    minY = new FiniteMinimumAggregate();
    maxX = new FiniteMaximumAggregate();
    maxY = new FiniteMaximumAggregate();
    minMaxX = CompoundAggregate.combine(minX, maxX);
    minMaxY = CompoundAggregate.combine(minY, maxY);

    for (final IExperiment experiment : set.getData()) {
      function = this.m_function.get(experiment);
      if (function == null) {
        continue;
      }
      if (function.m() <= 0) {
        continue;
      }
      if (function.n() < 2) {
        continue;
      }
      function.aggregateColumn(0, minMaxX);
      function.aggregateColumn(1, minMaxY);
      temp.add(new ExperimentFunction(experiment, function));
    }

    retVal = null;
    size = temp.size();
    if (size > 0) {
      if ((minX.compareTo(maxX) != 0) && (minY.compareTo(maxY) != 0)) {
        retVal = new ExperimentSetFunctions(set, cluster,
            temp.toArray(new ExperimentFunction[size]));
      }
    }
    temp.clear();
    return retVal;
  }

}
