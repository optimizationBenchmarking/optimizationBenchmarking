package org.optimizationBenchmarking.experimentation.evaluation.impl.all.function;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.attributes.OnlySharedInstances;
import org.optimizationBenchmarking.experimentation.attributes.clusters.ClustererLoader;
import org.optimizationBenchmarking.experimentation.attributes.clusters.ICluster;
import org.optimizationBenchmarking.experimentation.attributes.clusters.IClustering;
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
import org.optimizationBenchmarking.utils.document.spec.ELabelType;
import org.optimizationBenchmarking.utils.document.spec.IComplexText;
import org.optimizationBenchmarking.utils.document.spec.IFigure;
import org.optimizationBenchmarking.utils.document.spec.IFigureSeries;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.document.spec.ILabelBuilder;
import org.optimizationBenchmarking.utils.document.spec.ISection;
import org.optimizationBenchmarking.utils.document.spec.ISectionBody;
import org.optimizationBenchmarking.utils.document.spec.ISectionContainer;
import org.optimizationBenchmarking.utils.graphics.style.StyleSet;
import org.optimizationBenchmarking.utils.graphics.style.color.ColorStyle;
import org.optimizationBenchmarking.utils.graphics.style.font.FontStyle;
import org.optimizationBenchmarking.utils.graphics.style.stroke.StrokeStyle;
import org.optimizationBenchmarking.utils.math.BasicNumber;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.matrix.impl.MatrixBuilder;
import org.optimizationBenchmarking.utils.math.matrix.processing.iterator2D.MatrixIterator2D;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.CompoundAggregate;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.FiniteMaximumAggregate;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.FiniteMinimumAggregate;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.IAggregate;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.ScalarAggregate;
import org.optimizationBenchmarking.utils.math.statistics.ranking.RankingStrategy;
import org.optimizationBenchmarking.utils.math.text.DefaultParameterRenderer;
import org.optimizationBenchmarking.utils.parallel.Execute;
import org.optimizationBenchmarking.utils.parsers.AnyNumberParser;
import org.optimizationBenchmarking.utils.text.ESequenceMode;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.numbers.InTextNumberAppender;
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

  /** should we plot the ranking instead of the actual data? */
  public static final String PARAM_RANKING = "rankingPlot";//$NON-NLS-1$
  /**
   * should we assume that the experiments keep their last function value
   * forever or should their ranks end when their function ends?
   */
  public static final String PARAM_RANKING_CONTINUE = "rankingContinueToEnd";//$NON-NLS-1$

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

  /**
   * the minimum value for the x-axis, or {@code null} if undefined
   *
   * @see #PARAM_MIN_X
   */
  private final Number m_minX;
  /**
   * the maximum value for the x-axis, or {@code null} if undefined
   *
   * @see #PARAM_MAX_X
   */
  private final Number m_maxX;
  /**
   * the minimum value for the y-axis, or {@code null} if undefined
   *
   * @see #PARAM_MIN_Y
   */
  private final Number m_minY;
  /**
   * the maximum value for the y-axis, or {@code null} if undefined
   *
   * @see #PARAM_MAX_Y
   */
  private final Number m_maxY;
  /**
   * the ranking strategy, or {@code null} if the data should be printed
   *
   * @see #PARAM_RANKING
   */
  private final RankingStrategy m_ranking;

  /**
   * should we assume that the experiments keep their last function value
   * forever ({@code true}) or should their ranks end when their function
   * ends ({@code false})?
   */
  private final boolean m_rankingContinue;

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
        FunctionJob.PARAM_PRINT_AXIS_TITLES, this.getDefaultShowAxisTitles(
            data, this.m_figureSize, this.m_makeLegendFigure));

    this.m_clusterer = ClustererLoader.configureClustering(data, config);

    this.m_minX = config.get(FunctionJob.PARAM_MIN_X,
        AnyNumberParser.INSTANCE, null);
    this.m_maxX = config.get(FunctionJob.PARAM_MAX_X,
        AnyNumberParser.INSTANCE, null);
    this.m_minY = config.get(FunctionJob.PARAM_MIN_Y,
        AnyNumberParser.INSTANCE, null);
    this.m_maxY = config.get(FunctionJob.PARAM_MAX_Y,
        AnyNumberParser.INSTANCE, null);

    if (config.getBoolean(FunctionJob.PARAM_RANKING, false)) {
      this.m_ranking = RankingStrategy.create(config);
      this.m_rankingContinue = config
          .getBoolean(FunctionJob.PARAM_RANKING_CONTINUE, true);
    } else {
      this.m_ranking = null;
      this.m_rankingContinue = false;
    }
  }

  /**
   * Has a ranking strategy been set?
   *
   * @return {@code true} if a ranking strategy has been set, {@code false}
   *         otherwise
   */
  public final RankingStrategy getRankingStrategy() {
    return this.m_ranking;
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
   * Get the title to be printed at the x-axis
   *
   * @return the title to be printed at the x-axis
   */
  protected String getXAxisTitle() {
    final MemoryTextOutput mto;

    mto = new MemoryTextOutput();
    this.m_function.getXAxisTransformation().mathRender(mto,
        DefaultParameterRenderer.INSTANCE);
    return mto.toString();
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
   * Get the title to be printed at the y-axis
   *
   * @return the title to be printed at the y-axis
   */
  protected String getYAxisTitle() {
    final MemoryTextOutput mto;

    mto = new MemoryTextOutput();
    if (this.m_ranking != null) {
      this.m_ranking.printShortName(mto, ETextCase.IN_SENTENCE);
      mto.append('(');
    }
    this.m_function.getYAxisSemanticComponent().mathRender(mto,
        DefaultParameterRenderer.INSTANCE);
    if (this.m_ranking != null) {
      mto.append(')');
    }
    return mto.toString();
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
    if (this.m_ranking == null) {
      return new FiniteMinimumAggregate();
    }
    // if we rank, we should always start at 0
    return null;
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
  final void _drawChart(final ILineChart2D chart,
      final ExperimentSetFunctions data, final boolean showLineTitles,
      final boolean showAxisTitles, final StyleSet styles) {
    String title;
    StrokeStyle stroke;
    FontStyle font;
    ColorStyle color;
    ScalarAggregate aggregate;
    Number number;

    // initialize the x-axis
    try (final IAxis xAxis = chart.xAxis()) {
      if (showAxisTitles) {
        xAxis.setTitle(this.getXAxisTitle());
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
      if (showAxisTitles) {
        yAxis.setTitle(this.getYAxisTitle());
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
    for (final ExperimentFunction experimentFunction : data.getData()) {

      try (final ILine2D line = chart.line()) {
        line.setData(experimentFunction.getFunction());
        line.setType(this.getExperimentLineType(experimentFunction));

        color = this.getExperimentColor(experimentFunction, styles);
        if (color != null) {
          line.setColor(color);
        } else {
          line.setColor(styles.getColor(
              experimentFunction.getExperiment().getName(), true));
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
   * This method renders the caption of the single figure. The function job
   * will render a singular figure with one diagram in it. {@code data} is
   * the function data which consists of exactly one instance of
   * {@link ExperimentSetFunctions}.
   *
   * @param data
   *          the data
   * @param caption
   *          the complex text to render the caption to
   */
  protected void renderFigureCaption(final FunctionData data,
      final IComplexText caption) {
    final IClustering clustering;
    final ICluster cluster;

    caption.append("The "); //$NON-NLS-1$
    if (this.m_ranking != null) {
      caption.append(" rankings of the ");//$NON-NLS-1$
    }
    this.m_function.printLongName(caption, ETextCase.IN_SENTENCE);

    clustering = data.getClustering();
    if (clustering != null) {
      caption.append(" with data separated according to "); //$NON-NLS-1$
      clustering.printLongName(caption, ETextCase.IN_SENTENCE);
      cluster = data.getData().get(0).getCluster();
      if (cluster != null) {
        caption.append(':');
        caption.append(' ');
        cluster.printLongName(caption, ETextCase.IN_SENTENCE);
      }
    }
    caption.append('.');
  }

  /**
   * This method renders the caption of a series of figures. The function
   * job will render a series of figures. This could have two reasons:
   * Either, and most likely, {@code data} is the function data which
   * consists of multiple instances of {@link ExperimentSetFunctions},
   * i.e., we draw several sub-figures with different diagrams because the
   * input data was clustered. Or we have a single diagram and one legend.
   *
   * @param data
   *          the data
   * @param caption
   *          the complex text to render the caption to
   */
  protected void renderFigureSeriesCaption(final FunctionData data,
      final IComplexText caption) {
    final IClustering clustering;
    final ILabel legend;

    caption.append("The "); //$NON-NLS-1$
    if (this.m_ranking != null) {
      caption.append(" rankings of the ");//$NON-NLS-1$
    }
    this.m_function.printLongName(caption, ETextCase.IN_SENTENCE);

    clustering = data.getClustering();
    if (clustering != null) {
      caption.append(" with data separated according to "); //$NON-NLS-1$
      clustering.printLongName(caption, ETextCase.IN_SENTENCE);
    }
    legend = data.getLegendLabel();
    if (legend != null) {
      caption.append(" (legend: "); //$NON-NLS-1$
      caption.reference(ETextCase.IN_SENTENCE, ESequenceMode.AND, legend);
      caption.append(')');
    }
    caption.append('.');
  }

  /**
   * Render the legend caption.
   *
   * @param data
   *          the data
   * @param caption
   *          the complex text to render the caption to
   */
  protected void renderLegendCaption(final FunctionData data,
      final IComplexText caption) {
    final int size;
    final ArrayListView<ExperimentSetFunctions> functs;
    ILabel other;

    functs = data.getData();
    size = functs.size();

    if (size == 1) {
      other = functs.get(0).getLabel();
      if (other != null) {
        caption.append(//
            "Legend for ");//$NON-NLS-1$
        caption.reference(ETextCase.IN_SENTENCE, ESequenceMode.AND, other);
        caption.append('.');
      } else {
        caption.append(//
            "Legend for this figure.");//$NON-NLS-1$
      }
    } else {
      caption.append("Legend for the ");//$NON-NLS-1$
      InTextNumberAppender.INSTANCE.appendTo(size, ETextCase.IN_SENTENCE,
          caption);
      caption.append(" sub-figures of this figure.");//$NON-NLS-1$
    }
  }

  /**
   * This method renders the caption of a sub-figure of the series of
   * figures. The function job will render a series of figures. This could
   * have two reasons: Either, and most likely, {@code data} is the
   * function data which consists of multiple instances of
   * {@link ExperimentSetFunctions}, i.e., we draw several sub-figures with
   * different diagrams because the input data was clustered. Or we have a
   * single diagram and one legend.
   *
   * @param data
   *          the data
   * @param caption
   *          the complex text to render the caption to
   */
  protected void renderSubFigureCaption(final ExperimentSetFunctions data,
      final IComplexText caption) {
    final ICluster cluster;

    cluster = data.getCluster();

    if (cluster == null) {
      this.m_function.printLongName(caption, ETextCase.IN_SENTENCE);
    } else {
      cluster.printLongName(caption, ETextCase.IN_SENTENCE);
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
   * @return the actual label of the created figure
   */
  @SuppressWarnings("resource")
  protected ILabel renderPlots(final FunctionData data,
      final ISectionBody body, final StyleSet styles,
      final Logger logger) {
    final ILabel ret;
    final String mainPath;
    final IClustering clustering;
    final ArrayListView<ExperimentSetFunctions> allFunctions;
    final __DrawChart[] tasks;
    ExperimentSetFunctions experimentSetFunctions;
    ICluster cluster;
    IFigure curFigure;
    ILineChart2D curChart;
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

      try (final IFigure figure = body.figure(data.getLabel(),
          this.m_figureSize, path)) {
        this.__logFigure(logger, 1, 1);

        try (final IComplexText caption = figure.caption()) {
          this.renderFigureCaption(data, caption);
        }

        try (final ILineChart2D lines = figure.lineChart2D()) {
          lines.setLegendMode(ELegendMode.SHOW_COMPLETE_LEGEND);
          this._drawChart(lines, experimentSetFunctions, true,
              this.m_showAxisTitles, styles);
        }

        ret = figure.getLabel();
      }
    } else {
      try (final IFigureSeries figureSeries = body
          .figureSeries(data.getLabel(), this.m_figureSize, mainPath)) {

        try (final IComplexText caption = figureSeries.caption()) {
          this.renderFigureSeriesCaption(data, caption);
        }

        tasks = new __DrawChart[size];
        index = 0;

        if (this.m_makeLegendFigure) {
          path = (mainPath + "_legend"); //$NON-NLS-1$

          this.__logFigure(logger, (++index), size);

          curFigure = figureSeries.figure(data.getLegendLabel(), path);

          try (final IComplexText caption = curFigure.caption()) {
            this.renderLegendCaption(data, caption);
          }
          curChart = curFigure.lineChart2D();
          curChart.setLegendMode(ELegendMode.CHART_IS_LEGEND);
          tasks[index - 1] = new __DrawChart(curFigure, curChart,
              allFunctions.get(0), true, true, styles);
        }

        for (final ExperimentSetFunctions experimentSetFunctions2 : data
            .getData()) {

          path = mainPath;
          cluster = experimentSetFunctions2.getCluster();
          if (cluster != null) {
            path = (path + '_' + cluster.getPathComponentSuggestion());
          }

          this.__logFigure(logger, (++index), size);
          curFigure = figureSeries
              .figure(experimentSetFunctions2.getLabel(), path);

          try (final IComplexText caption = curFigure.caption()) {
            this.renderSubFigureCaption(experimentSetFunctions2, caption);
          }

          curChart = curFigure.lineChart2D();
          curChart.setLegendMode(this.m_makeLegendFigure//
              ? ELegendMode.HIDE_COMPLETE_LEGEND//
              : ELegendMode.SHOW_COMPLETE_LEGEND);
          tasks[index - 1] = new __DrawChart(curFigure, curChart,
              experimentSetFunctions2, (!(this.m_makeLegendFigure)),
              (!(this.m_makeLegendFigure)), styles);
        }

        Execute.parallelAndWait().execute(null, tasks);

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
  protected void renderTitle(final IComplexText title) {
    this.m_function.printLongName(title, ETextCase.AT_TITLE_START);
  }

  /**
   * Should the sub-figures receive labels?
   *
   * @return {@code true} if the sub-figures are labeled, {@code false}
   *         otherwise
   */
  protected boolean labelSubFigures() {
    return true;
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
   * This method is invoked before the figures are plotted and can render
   * some text.
   *
   * @param data
   *          the data, or {@code null} if no data exists to draw
   *          reasonable diagrams
   * @param body
   *          the section body
   * @param logger
   *          the logger
   */
  protected void beforePlots(final FunctionData data,
      final ISectionBody body, final Logger logger) {
    final IClustering clustering;

    body.append("We analyze "); //$NON-NLS-1$
    this.m_function.printDescription(body, ETextCase.IN_SENTENCE);
    clustering = data.getClustering();
    if (clustering != null) {
      body.append(' ');
      clustering.printDescription(body, ETextCase.AT_SENTENCE_START);
    }

    if (this.m_ranking != null) {
      body.appendLineBreak();
      body.append("Instead of plotting the ");//$NON-NLS-1$
      this.m_function.printShortName(body, ETextCase.IN_SENTENCE);
      body.append(//
          " directly, we rank the experiments according to their ");//$NON-NLS-1$
      this.m_function.printShortName(body, ETextCase.IN_SENTENCE);
      body.append(//
          " value at each time step (bigger values lead to bigger ranks). This may make it easier to see which experiment has larger or smaller ");//$NON-NLS-1$
      this.m_function.printShortName(body, ETextCase.IN_SENTENCE);
      body.append(" values and when. "); //$NON-NLS-1$
      this.m_ranking.printDescription(body, ETextCase.AT_SENTENCE_START);
      body.append(//
          " If the function of an experiment has ended (towards growing ");//$NON-NLS-1$
      this.m_function.getXAxisTransformation().printShortName(body,
          ETextCase.IN_SENTENCE);
      if (this.m_rankingContinue) {
        body.append(//
            ") its last value with be used for ranking afterwards.");//$NON-NLS-1$
      } else {
        body.append(//
            ") it will now longer appear in the ranking.");//$NON-NLS-1$
      }
    }
  }

  /**
   * This method is invoked after the figures are plotted and can render
   * some text.
   *
   * @param data
   *          the data, or {@code null} if no data exists to draw
   *          reasonable diagrams
   * @param body
   *          the section body
   * @param logger
   *          the logger
   */
  protected void afterPlots(final FunctionData data,
      final ISectionBody body, final Logger logger) {
    body.appendLineBreak();
    body.append("The corresponding plot"); //$NON-NLS-1$
    if (data.getData().size() > 1) {
      body.append("s are illustrated in "); //$NON-NLS-1$
    } else {
      body.append(" is illustrated in "); //$NON-NLS-1$
    }
    body.reference(ETextCase.IN_SENTENCE, ESequenceMode.AND,
        data.getLabel());
    body.append('.');
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

    functionData = this.__makeData(data, logger, sectionContainer);

    if ((logger != null) && (logger.isLoggable(Level.FINER))) {
      logger.finer(((("Finished computing data for " + //$NON-NLS-1$
          TextUtils.className(this.getClass())) + ", found ") + //$NON-NLS-1$
          ((functionData != null) ? functionData.getData().size() : 0))
          + " clusters with useful data, which will now be processed.");//$NON-NLS-1$
    }

    if (data != null) {
      try (final ISection section = sectionContainer.section(null)) {
        try (final IComplexText title = section.title()) {
          this.renderTitle(title);
        }

        try (final ISectionBody body = section.body()) {
          this.beforePlots(functionData, body, logger);
          this.renderPlots(functionData, body, section.getStyles(),
              logger);
          this.afterPlots(functionData, body, logger);
        }
      }
    }

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
   * @param labelBuilder
   *          the label builder
   * @return the data
   */
  @SuppressWarnings("unchecked")
  private final FunctionData __makeData(final IExperimentSet set,
      final Logger logger, final ILabelBuilder labelBuilder) {
    final Future<ExperimentSetFunctions>[] tasks;
    final ArrayListView<? extends ICluster> data;
    final IExperimentSet selected;
    final Execute execute;
    IClustering clustering;
    ExperimentSetFunctions[] functions, temp;
    ExperimentSetFunctions experimentSetFunctions;
    ILabel figure, legend;
    int size, i;

    selected = OnlySharedInstances.INSTANCE.get(set, logger);

    computeFunctions: {
      if (this.m_clusterer != null) {

        if ((logger != null) && (logger.isLoggable(Level.FINER))) {
          logger.finer("Now computing clustering of data according to " //$NON-NLS-1$
              + this.m_clusterer);
        }
        clustering = this.m_clusterer.get(selected, logger);
        if ((logger != null) && (logger.isLoggable(Level.FINER))) {
          logger.finer("Finished computing clustering of data " //$NON-NLS-1$
              + this.m_clusterer + " into " + //$NON-NLS-1$
              ((clustering != null) ? clustering.getData().size() : 0)
              + " clusters.");//$NON-NLS-1$
        }
        if (clustering == null) {
          throw new IllegalStateException(//
              "Clustering cannot be null.");//$NON-NLS-1$
        }

        data = clustering.getData();
        size = data.size();
        if (size > 1) {
          tasks = new Future[size];
          execute = Execute.parallel();
          for (i = size; (--i) >= 0;) {
            tasks[i] = execute
                .execute(new __MakeFunctions(data.get(i), logger));
          }
          functions = new ExperimentSetFunctions[size];
          size = Execute.join(tasks, functions, 0, true);
          if (size < functions.length) {
            temp = new ExperimentSetFunctions[size];
            System.arraycopy(functions, 0, temp, 0, size);
            functions = temp;
          }
          break computeFunctions;
        }

        // clustering with size 1 == original data, fallthrough
      }
      clustering = null;
      experimentSetFunctions = this._makeFunctions(selected, null, logger);
      if (experimentSetFunctions == null) {
        return null;
      }
      functions = new ExperimentSetFunctions[] { experimentSetFunctions };
    }

    figure = labelBuilder.createLabel(ELabelType.FIGURE);
    makeSubLabels: {
      if (this.m_makeLegendFigure) {
        legend = labelBuilder.createLabel(ELabelType.SUBFIGURE);
        if (this.labelSubFigures()) {
          for (final ExperimentSetFunctions funcs : functions) {
            funcs._setLabel(//
                labelBuilder.createLabel(ELabelType.SUBFIGURE));
          }
        }
      } else {
        legend = null;
        if (functions.length == 1) {
          functions[0]._setLabel(figure);
          break makeSubLabels;
        }
      }
    }

    return new FunctionData(clustering, functions, figure, legend);
  }

  /**
   * Perform a ranking transform of the given functions: Instead of the
   * function values, we will now have their ranks instead.
   *
   * @param inOut
   *          the in/out data
   */
  private final void __rankingTransform(
      final ArrayList<ExperimentFunction> inOut) {
    final double[] ranks;
    final int size;
    final MatrixIterator2D iterator;
    final IMatrix[] source;
    final MatrixBuilder[] builders;
    IMatrix matrix;
    int index, maxRows;
    BasicNumber x;
    MatrixBuilder builder;

    size = inOut.size();
    source = new IMatrix[size];
    builders = new MatrixBuilder[size];

    ranks = new double[size];

    maxRows = 128;
    for (index = size; (--index) >= 0;) {
      matrix = inOut.get(index).getFunction();

      source[index] = matrix;
      maxRows = Math.max(maxRows, matrix.m());
      builders[index] = builder = new MatrixBuilder(maxRows);
      builder.setN(2);
    }

    iterator = MatrixIterator2D.iterate(0, 1, source,
        (!(this.m_rankingContinue)));
    while (iterator.hasNext()) {
      x = iterator.next();
      this.m_ranking.rankRow(iterator, 0, ranks);

      for (index = iterator.n(); (--index) >= 0;) {
        builder = builders[iterator.getSource(index)];
        if (x.isInteger()) {
          builder.append(x.longValue());
        } else {
          builder.append(x.doubleValue());
        }
        builder.append(ranks[index]);
      }
    }

    for (index = size; (--index) >= 0;) {
      inOut.set(index,
          new ExperimentFunction(//
              inOut.get(index).getExperiment(), //
              builders[index].make()));
      builders[index] = null;
    }
  }

  /**
   * Make the functions over the experiment sets.
   *
   * @param set
   *          the experiment set
   * @param cluster
   *          the cluster
   * @param logger
   *          the logger
   * @return the functions, or {@code null} if none were defined
   */
  @SuppressWarnings("unchecked")
  final ExperimentSetFunctions _makeFunctions(final IExperimentSet set,
      final ICluster cluster, final Logger logger) {
    final Future<IMatrix>[] tasks;
    final Execute execute;
    final ArrayListView<? extends IExperiment> data;
    final FiniteMinimumAggregate minX, minY;
    final FiniteMaximumAggregate maxX, maxY;
    final IAggregate minMaxX, minMaxY;
    final ArrayList<ExperimentFunction> temp;
    int index, size;
    IMatrix function;
    ExperimentSetFunctions retVal;

    execute = Execute.parallel();
    data = set.getData();
    size = data.size();
    tasks = new Future[size];

    for (index = 0; index < size; index++) {
      tasks[index] = execute.execute(//
          this.m_function.getter(data.get(index), logger));
    }

    minX = new FiniteMinimumAggregate();
    minY = new FiniteMinimumAggregate();
    maxX = new FiniteMaximumAggregate();
    maxY = new FiniteMaximumAggregate();
    minMaxX = CompoundAggregate.combine(minX, maxX);
    minMaxY = CompoundAggregate.combine(minY, maxY);
    temp = new ArrayList<>(size);

    for (index = 0; index < size; index++) {
      try {
        function = tasks[index].get();
      } catch (ExecutionException | InterruptedException error) {
        throw new IllegalStateException((//
            "Computation of function " //$NON-NLS-1$
                + this.m_function + " failed for experiment " + //$NON-NLS-1$
                data.get(index).getName()),
            error);
      }
      tasks[index] = null;
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
      temp.add(new ExperimentFunction(data.get(index), function));
    }

    retVal = null;
    size = temp.size();
    if (size > 0) {
      if ((minX.compareTo(maxX) != 0) && (minY.compareTo(maxY) != 0)) {
        if (this.m_ranking != null) {
          this.__rankingTransform(temp);
        }
        retVal = new ExperimentSetFunctions(set, cluster,
            temp.toArray(new ExperimentFunction[size]));
      }
    }
    return retVal;
  }

  /** An internal task for making a set of functions */
  private final class __MakeFunctions
      implements Callable<ExperimentSetFunctions> {

    /** the experiment set data */
    private final ICluster m_data;

    /** the logger */
    private final Logger m_logger;

    /**
     * Create the function computer
     *
     * @param data
     *          the data
     * @param logger
     *          the logger
     */
    __MakeFunctions(final ICluster data, final Logger logger) {
      super();
      this.m_data = data;
      this.m_logger = logger;
    }

    /** {@inheritDoc} */
    @Override
    public final ExperimentSetFunctions call() {
      return FunctionJob.this._makeFunctions(this.m_data, this.m_data,
          this.m_logger);
    }
  }

  /** draw a chart */
  private final class __DrawChart implements Runnable {

    /** the figure */
    private final IFigure m_figure;
    /** the chart */
    private final ILineChart2D m_chart;
    /** the data */
    private final ExperimentSetFunctions m_data;
    /** the line titles */
    private final boolean m_showLineTitles;
    /** the axis */
    private final boolean m_showAxisTitles_;
    /** the styles */
    private final StyleSet m_styles;

    /**
     * draw the line chart
     *
     * @param figure
     *          the figure
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
    __DrawChart(final IFigure figure, final ILineChart2D chart,
        final ExperimentSetFunctions data, final boolean showLineTitles,
        final boolean showAxisTitles, final StyleSet styles) {
      super();
      this.m_figure = figure;
      this.m_chart = chart;
      this.m_data = data;
      this.m_showLineTitles = showLineTitles;
      this.m_showAxisTitles_ = showAxisTitles;
      this.m_styles = styles;
    }

    /** {@inheritDoc} */
    @Override
    public final void run() {
      try (final IFigure figure = this.m_figure) {
        try (final ILineChart2D chart = this.m_chart) {
          FunctionJob.this._drawChart(chart, this.m_data,
              this.m_showLineTitles, this.m_showAxisTitles_,
              this.m_styles);
        }
      }
    }
  }
}
