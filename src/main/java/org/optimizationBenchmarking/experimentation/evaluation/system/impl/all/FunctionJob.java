package org.optimizationBenchmarking.experimentation.evaluation.system.impl.all;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.experimentation.attributes.clusters.ICluster;
import org.optimizationBenchmarking.experimentation.attributes.clusters.IClustering;
import org.optimizationBenchmarking.experimentation.attributes.clusters.propertyValueGroups.PropertyValueSelector;
import org.optimizationBenchmarking.experimentation.attributes.functions.FunctionAttribute;
import org.optimizationBenchmarking.experimentation.data.spec.Attribute;
import org.optimizationBenchmarking.experimentation.data.spec.IExperiment;
import org.optimizationBenchmarking.experimentation.data.spec.IExperimentSet;
import org.optimizationBenchmarking.experimentation.evaluation.system.impl.abstr.ExperimentSetJob;
import org.optimizationBenchmarking.utils.chart.spec.ELegendMode;
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
import org.optimizationBenchmarking.utils.graphics.style.StyleSet;
import org.optimizationBenchmarking.utils.graphics.style.color.ColorStyle;
import org.optimizationBenchmarking.utils.graphics.style.font.FontStyle;
import org.optimizationBenchmarking.utils.graphics.style.stroke.StrokeStyle;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.FiniteMaximumAggregate;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.FiniteMinimumAggregate;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.ScalarAggregate;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.numbers.InTextNumberAppender;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * A function job is an
 * {@link org.optimizationBenchmarking.experimentation.evaluation.system.impl.abstr.ExperimentSetJob}
 * designed to plot a function.
 */
public abstract class FunctionJob extends ExperimentSetJob {

  /**
   * Should there be a sub-figure which only serves as legend, hence
   * allowing us to omit legends in the other figures?
   */
  public static final String PARAM_MAKE_LEGEND_FIGURE = "legendFigure"; //$NON-NLS-1$
  /**
   * Should the figures include axis titles?
   */
  public static final String PARAM_PRINT_AXIS_TITLES = "axisTitles"; //$NON-NLS-1$

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
   * Create the function job
   * 
   * @param data
   *          the data
   * @param logger
   *          the logger
   * @param config
   *          the configuration
   */
  protected FunctionJob(final IExperimentSet data,
      final Configuration config, final Logger logger) {
    super(data, logger);

    final EFigureSize defau;

    defau = this.getDefaultFigureSize(data);
    if (defau == null) {
      throw new IllegalArgumentException(//
          "Default figure size cannot be null."); //$NON-NLS-1$
    }

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

    this.m_function = this.configureFunction(data, config);
    if (this.m_function == null) {
      throw new IllegalArgumentException("Function cannot be null."); //$NON-NLS-1$
    }
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
   * Get the function attribute used to obtain the function data
   * 
   * @param data
   *          the data
   * @param config
   *          the configuration
   * @return the attribute
   */
  protected abstract FunctionAttribute<? super IExperiment> configureFunction(
      final IExperimentSet data, final Configuration config);

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
   * Get an aggregate to be used to determine the minimum value of the
   * x-axis. If this method returns {@code null}, the fixed value returned
   * by {@link #getXAxisMinimumValue()} is used as minimum for the x-axis.
   * 
   * @return the aggregate
   * @see #getXAxisMinimumValue()
   */
  protected ScalarAggregate getXAxisMinimumAggregate() {
    return new FiniteMinimumAggregate();
  }

  /**
   * Get a fixed minimum value for the x-axis. This method is only called
   * if {@link #getXAxisMinimumAggregate()} returns {@code null}.
   * 
   * @return a {@code double} value representing the minimum value for the
   *         x-axis
   * @see #getXAxisMinimumAggregate()
   */
  protected double getXAxisMinimumValue() {
    return 0d;
  }

  /**
   * Get an aggregate to be used to determine the maximum value of the
   * x-axis. If this method returns {@code null}, the fixed value returned
   * by {@link #getXAxisMaximumValue()} is used as maximum for the x-axis.
   * 
   * @return the aggregate
   * @see #getXAxisMaximumValue()
   */
  protected ScalarAggregate getXAxisMaximumAggregate() {
    return new FiniteMaximumAggregate();
  }

  /**
   * Get a fixed maximum value for the x-axis. This method is only called
   * if {@link #getXAxisMaximumAggregate()} returns {@code null}.
   * 
   * @return a {@code double} value representing the maximum value for the
   *         x-axis
   * @see #getXAxisMaximumAggregate()
   */
  protected double getXAxisMaximumValue() {
    return 0d;
  }

  /**
   * Get an aggregate to be used to determine the minimum value of the
   * y-axis. If this method returns {@code null}, the fixed value returned
   * by {@link #getYAxisMinimumValue()} is used as minimum for the y-axis.
   * 
   * @return the aggregate
   * @see #getYAxisMinimumValue()
   */
  protected ScalarAggregate getYAxisMinimumAggregate() {
    return new FiniteMinimumAggregate();
  }

  /**
   * Get a fixed minimum value for the y-axis. This method is only called
   * if {@link #getYAxisMinimumAggregate()} returns {@code null}.
   * 
   * @return a {@code double} value representing the minimum value for the
   *         y-axis
   * @see #getYAxisMinimumAggregate()
   */
  protected double getYAxisMinimumValue() {
    return 0d;
  }

  /**
   * Get an aggregate to be used to determine the maximum value of the
   * y-axis. If this method returns {@code null}, the fixed value returned
   * by {@link #getYAxisMaximumValue()} is used as maximum for the y-axis.
   * 
   * @return the aggregate
   * @see #getYAxisMaximumValue()
   */
  protected ScalarAggregate getYAxisMaximumAggregate() {
    return new FiniteMaximumAggregate();
  }

  /**
   * Get a fixed maximum value for the y-axis. This method is only called
   * if {@link #getYAxisMaximumAggregate()} returns {@code null}.
   * 
   * @return a {@code double} value representing the maximum value for the
   *         y-axis
   * @see #getYAxisMaximumAggregate()
   */
  protected double getYAxisMaximumValue() {
    return 0d;
  }

  /**
   * Get an optional starting point for a given experiment function.
   * 
   * @param experiment
   *          the experiment to be represented
   * @param computed
   *          the function representing the experiment
   * @param xyDest
   *          an array of length 2 to receive an x and y coordinate of the
   *          starting point (in case {@code true} is returned)
   * @return {@code true} if a starting point was set, {@code false}
   *         otherwise
   */
  protected boolean getExperimentFunctionStart(
      final IExperiment experiment, final IMatrix computed,
      final double[] xyDest) {
    return false; // nothing
  }

  /**
   * Get an optional end point for a given experiment function.
   * 
   * @param experiment
   *          the experiment to be represented
   * @param computed
   *          the function representing the experiment
   * @param xyDest
   *          an array of length 2 to receive an x and y coordinate of the
   *          end point (in case {@code true} is returned)
   * @return {@code true} if a end point was set, {@code false} otherwise
   */
  protected boolean getExperimentFunctionEnd(final IExperiment experiment,
      final IMatrix computed, final double[] xyDest) {
    return false; // nothing
  }

  /**
   * Get the color to use for a given experiment.
   * 
   * @param experiment
   *          the experiment to be represented
   * @param computed
   *          the function representing the experiment
   * @param styles
   *          the style set
   * @return the color to use for the line
   */
  protected ColorStyle getExperimentColor(final IExperiment experiment,
      final IMatrix computed, final StyleSet styles) {
    return styles.getColor(experiment.getName(), true);
  }

  /**
   * Get the stroke to use for a given experiment.
   * 
   * @param experiment
   *          the experiment to be represented
   * @param computed
   *          the function representing the experiment
   * @param styles
   *          the style set
   * @return the stroke to use for the line, or {@code null} for the
   *         default
   */
  protected StrokeStyle getExperimentStroke(final IExperiment experiment,
      final IMatrix computed, final StyleSet styles) {
    return null;
  }

  /**
   * Get the font to use for a given experiment.
   * 
   * @param experiment
   *          the experiment to be represented
   * @param computed
   *          the function representing the experiment
   * @param styles
   *          the style set
   * @return the font style to use for the line, or {@code null} for the
   *         default
   */
  protected FontStyle getExperimentFont(final IExperiment experiment,
      final IMatrix computed, final StyleSet styles) {
    return null;
  }

  /**
   * Get the title to use for a given experiment.
   * 
   * @param experiment
   *          the experiment to be represented
   * @param computed
   *          the function representing the experiment
   * @return the title to use for the experiment
   */
  protected String getExperimentTitle(final IExperiment experiment,
      final IMatrix computed) {
    return experiment.getName();
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
      final IExperimentSet data, final boolean showLineTitles,
      final boolean showAxisTitles, final StyleSet styles) {
    final MemoryTextOutput mto;
    final double[] xy;
    String title;
    IMatrix computed;
    StrokeStyle stroke;
    FontStyle font;
    ColorStyle color;
    ScalarAggregate aggregate;

    if (showAxisTitles) {
      mto = new MemoryTextOutput();
    } else {
      mto = null;
    }

    // initialize the x-axis
    try (final IAxis xAxis = chart.xAxis()) {
      if (mto != null) {
        this.m_function.appendXAxisTitle(mto);
        xAxis.setTitle(mto.toString());
        mto.clear();
      }

      aggregate = this.getXAxisMinimumAggregate();
      if (aggregate != null) {
        xAxis.setMinimumAggregate(aggregate);
      } else {
        xAxis.setMinimum(this.getXAxisMinimumValue());
      }

      aggregate = this.getXAxisMaximumAggregate();
      if (aggregate != null) {
        xAxis.setMaximumAggregate(aggregate);
      } else {
        xAxis.setMaximum(this.getXAxisMaximumValue());
      }
    }

    // initialize the y-axis

    try (final IAxis yAxis = chart.yAxis()) {
      if (mto != null) {
        this.m_function.appendYAxisTitle(mto);
        yAxis.setTitle(mto.toString());
        mto.clear();
      }

      aggregate = this.getYAxisMinimumAggregate();
      if (aggregate != null) {
        yAxis.setMinimumAggregate(aggregate);
      } else {
        yAxis.setMinimum(this.getYAxisMinimumValue());
      }

      aggregate = this.getYAxisMaximumAggregate();
      if (aggregate != null) {
        yAxis.setMaximumAggregate(aggregate);
      } else {
        yAxis.setMaximum(this.getYAxisMaximumValue());
      }
    }

    // plot the functions, one for each experiment
    xy = new double[2];
    for (final IExperiment experiment : data.getData()) {
      computed = this.m_function.get(experiment);
      if ((computed != null) && (computed.m() > 0)) {

        try (final ILine2D line = chart.line()) {
          line.setData(computed);

          // set values to NaN to provoke fail-fast behavior
          xy[0] = Double.NaN;
          xy[1] = Double.NaN;
          if (this.getExperimentFunctionStart(experiment, computed, xy)) {
            line.setStart(xy[0], xy[1]);
          }

          // set values to NaN to provoke fail-fast behavior
          xy[0] = Double.NaN;
          xy[1] = Double.NaN;
          if (this.getExperimentFunctionEnd(experiment, computed, xy)) {
            line.setStart(xy[0], xy[1]);
          }

          color = this.getExperimentColor(experiment, computed, styles);
          if (color != null) {
            line.setColor(color);
          }

          stroke = this.getExperimentStroke(experiment, computed, styles);
          if (stroke != null) {
            line.setStroke(stroke);
          }

          if (showLineTitles) {
            title = this.getExperimentTitle(experiment, computed);
            if (title != null) {
              font = this.getExperimentFont(experiment, computed, styles);
              if (font != null) {
                line.setTitleFont(font.getFont());
              }
              line.setTitle(title);
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
  protected ILabel makePlots(final IExperimentSet data,
      final ISectionBody body, final StyleSet styles, final Logger logger,
      final ILabel mainLabel) {
    final IClustering clusters;
    final ArrayListView<? extends IExperimentSet> experiments;
    final ILabel ret;
    final String mainPath;
    String path;
    IExperimentSet exps;
    int size, index;

    if (data == null) {
      throw new IllegalStateException(//
          "Experiment set cannot be null.");//$NON-NLS-1$
    }

    if (this.m_clusterer != null) {
      if ((logger != null) && (logger.isLoggable(Level.FINER))) {
        logger.finer("Now computing clustering of data according to " //$NON-NLS-1$
            + this.m_clusterer);
      }
      clusters = this.m_clusterer.get(data);
      if ((logger != null) && (logger.isLoggable(Level.FINER))) {
        logger.finer("Finished computing clustering of data according to " //$NON-NLS-1$
            + this.m_clusterer);
      }
      if (clusters == null) {
        throw new IllegalStateException(//
            "Clustering cannot be null.");//$NON-NLS-1$
      }

      experiments = clusters.getData();
    } else {
      experiments = new ArrayListView<>(new IExperimentSet[] { data });
      clusters = null;
    }

    if (experiments == null) {
      throw new IllegalStateException(//
          "List of experiments cannot be null.");//$NON-NLS-1$
    }

    path = this.getFunctionPathComponentSuggestion();
    if (clusters != null) {
      mainPath = (path + '_' + clusters.getPathComponentSuggestion());
    } else {
      mainPath = path;
    }
    size = experiments.size();
    if (size <= 0) {
      throw new IllegalStateException(//
          "List of experiments cannot be empty.");//$NON-NLS-1$
    }

    if (this.m_makeLegendFigure) {
      size++;
    }

    if (size == 1) {
      path = mainPath;
      if (clusters != null) {
        exps = experiments.get(0);
        if (exps instanceof ICluster) {
          path = (path + '_' + //
          ((ICluster) exps).getPathComponentSuggestion());
        }
      }

      try (final IFigure figure = body.figure(mainLabel,
          this.m_figureSize, path)) {
        this.__logFigure(logger, 1, 1);
        try (final ILineChart2D lines = figure.lineChart2D()) {
          lines.setLegendMode(ELegendMode.SHOW_COMPLETE_LEGEND);
          this.__drawChart(lines, experiments.get(0), true,
              this.m_showAxisTitles, styles);
        }

        ret = figure.getLabel();
      }
    } else {
      try (final IFigureSeries figureSeries = body.figureSeries(mainLabel,
          this.m_figureSize, mainPath)) {

        index = 0;

        if (this.m_makeLegendFigure) {
          path = (mainPath + "_legend"); //$NON-NLS-1$

          this.__logFigure(logger, (++index), size);
          try (final IFigure figure = figureSeries.figure(null, path)) {

            try (final IComplexText caption = figure.caption()) {
              caption.append("Legend for the next ");//$NON-NLS-1$
              if (size > 1) {
                InTextNumberAppender.INSTANCE.appendTo(size,
                    ETextCase.IN_SENTENCE, caption);
                caption.append(" figures.");//$NON-NLS-1$
              } else {
                caption.append(" figure.");//$NON-NLS-1$
              }
              try (final ILineChart2D lines = figure.lineChart2D()) {
                lines.setLegendMode(ELegendMode.CHART_IS_LEGEND);
                this.__drawChart(lines, experiments.get(0), true, true,
                    styles);
              }
            }
          }
        }

        for (final IExperimentSet expSet : experiments) {

          path = mainPath;
          if ((clusters != null) && (expSet instanceof ICluster)) {
            path = (path + '_' + ((ICluster) expSet)
                .getPathComponentSuggestion());
          }

          this.__logFigure(logger, (++index), size);
          try (final IFigure figure = figureSeries.figure(null, path)) {

            try (final ILineChart2D lines = figure.lineChart2D()) {
              lines.setLegendMode(this.m_makeLegendFigure//
              ? ELegendMode.HIDE_COMPLETE_LEGEND//
                  : ELegendMode.SHOW_COMPLETE_LEGEND);
              this.__drawChart(lines, expSet,
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
}
