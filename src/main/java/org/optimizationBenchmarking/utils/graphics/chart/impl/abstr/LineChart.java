package org.optimizationBenchmarking.utils.graphics.chart.impl.abstr;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.graphics.chart.spec.ELegendMode;
import org.optimizationBenchmarking.utils.graphics.chart.spec.IAxis;
import org.optimizationBenchmarking.utils.graphics.chart.spec.ILine2D;
import org.optimizationBenchmarking.utils.graphics.chart.spec.ILineChart;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;
import org.optimizationBenchmarking.utils.graphics.style.StyleSet;
import org.optimizationBenchmarking.utils.hierarchy.FSM;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/** the line chart */
public class LineChart extends Chart implements ILineChart {

  /** the x-axis type has been set */
  static final int FLAG_HAS_X_AXIS = (TitledElement.FLAG_TITLED_ELEMENT_BUILDER_MAX << 1);
  /** the y-axis type has been set */
  static final int FLAG_HAS_Y_AXIS = (LineChart.FLAG_HAS_X_AXIS << 1);
  /** the show legend has been set */
  static final int FLAG_HAS_LEGEND_MODE = (LineChart.FLAG_HAS_Y_AXIS << 1);
  /** at least one line has been added */
  static final int FLAG_HAS_LINE = (LineChart.FLAG_HAS_LEGEND_MODE << 1);

  /** the id counter */
  private volatile int m_idCounter;

  /** the lines */
  private ArrayList<CompiledLine2D> m_lines;

  /** the legend mode */
  private ELegendMode m_legendMode;
  /** the internal x-axis builder */
  private Axis m_xAxis;
  /** the internal y-axis builder */
  private Axis m_yAxis;

  /**
   * create the line chart
   * 
   * @param selector
   *          the selector
   */
  protected LineChart(final ChartSelector selector) {
    this(selector.getGraphic(), selector.getStyleSet(),//
        selector._getLogger(), selector.getChartDriver());
  }

  /**
   * create the line chart
   * 
   * @param graphic
   *          the graphic
   * @param styles
   *          the style set to use
   * @param logger
   *          the logger to use
   * @param driver
   *          the chart driver
   */
  protected LineChart(final Graphic graphic, final StyleSet styles,
      final Logger logger, final ChartDriver driver) {
    super(graphic, styles, logger, driver);

    this.m_lines = new ArrayList<>();
    this.m_legendMode = ELegendMode.SHOW_COMPLETE_LEGEND;

    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void fsmFlagsAppendName(final int flagValue,
      final int flagIndex, final MemoryTextOutput append) {
    switch (flagValue) {
      case FLAG_HAS_X_AXIS: {
        append.append("xAxisSet");break;} //$NON-NLS-1$      
      case FLAG_HAS_Y_AXIS: {
        append.append("yAxisSet");break;} //$NON-NLS-1$
      case FLAG_HAS_LEGEND_MODE: {
        append.append("legendModeSet");break;} //$NON-NLS-1$
      case FLAG_HAS_LINE: {
        append.append("hasLine");break;} //$NON-NLS-1$
      default: {
        super.fsmFlagsAppendName(flagValue, flagIndex, append);
      }
    }
  }

  /**
   * Add a new line
   * 
   * @param line
   *          the line
   */
  synchronized final void _addLine(final CompiledLine2D line) {
    this.fsmStateAssert(ChartElement.STATE_ALIVE);
    this.fsmFlagsAssertAndUpdate(
        (LineChart.FLAG_HAS_X_AXIS | LineChart.FLAG_HAS_Y_AXIS),
        FSM.FLAG_NOTHING, LineChart.FLAG_HAS_LINE, FSM.FLAG_NOTHING);

    if (line != null) {
      this.m_xAxis._registerData(line.m_data);
      this.m_yAxis._registerData(line.m_data);
      this.m_lines.add(line);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void afterChildClosed(final HierarchicalFSM child) {
    super.afterChildClosed(child);

    if (child == this.m_xAxis) {
      this.fsmFlagsAssertAndUpdate(FSM.STATE_NOTHING,
          LineChart.FLAG_HAS_X_AXIS, LineChart.FLAG_HAS_X_AXIS,
          FSM.FLAG_NOTHING);
      return;
    }
    if (child == this.m_yAxis) {
      this.fsmFlagsAssertAndUpdate(FSM.STATE_NOTHING,
          LineChart.FLAG_HAS_Y_AXIS, LineChart.FLAG_HAS_Y_AXIS,
          FSM.FLAG_NOTHING);
      return;
    }
    if (child instanceof Line2D) {
      return;
    }
    this.throwChildNotAllowed(child);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void onClose() {
    Logger logger;
    Graphic graphic;
    CompiledLineChart chart;

    this.fsmStateAssertAndSet(ChartElement.STATE_ALIVE,
        ChartElement.STATE_DEAD);
    this.fsmFlagsAssertTrue(LineChart.FLAG_HAS_X_AXIS
        | LineChart.FLAG_HAS_Y_AXIS | LineChart.FLAG_HAS_LINE);

    graphic = this.m_graphic;
    logger = this.getLogger();
    try {
      if ((logger != null) && (logger.isLoggable(Level.FINEST))) {
        logger.finest("Now compiling " + this._id()); //$NON-NLS-1$
      }
      chart = new CompiledLineChart(
          this.m_title,
          (((this.m_titleFont != null) || (this.m_title == null)) ? this.m_titleFont
              : this._getChartTitleFont()), this.m_legendMode,
          this.m_xAxis._getAxis(), this.m_yAxis._getAxis(),
          new ArrayListView<>(this.m_lines
              .toArray(new CompiledLine2D[this.m_lines.size()])));
      if ((logger != null) && (logger.isLoggable(Level.FINEST))) {
        logger.finest("Now rendering the compiled " + this._id()); //$NON-NLS-1$
      }
      this.m_xAxis = null;
      this.m_yAxis = null;
      this.m_lines = null;
      this.m_driver.renderLineChart(chart, graphic, logger);
    } catch (final Throwable error) {
      if ((logger != null) && (logger.isLoggable(Level.SEVERE))) {
        logger
            .log(
                Level.SEVERE,
                ("Unrecoverable error during rendering of compiled line chart #" //$NON-NLS-1$
                + this._id()), error);
      }
      ErrorUtils.throwAsRuntimeException(error);
    } finally {
      this.m_lines = null;
      this.m_xAxis = null;
      this.m_yAxis = null;
      chart = null;
      graphic = null;
    }

    super.onClose();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final IAxis xAxis() {
    this.fsmStateAssert(ChartElement.STATE_ALIVE);
    this.fsmFlagsAssertFalse(LineChart.FLAG_HAS_LINE
        | LineChart.FLAG_HAS_X_AXIS);
    if (this.m_xAxis != null) {
      throw new IllegalStateException(//
          "X-axis is already under construction."); //$NON-NLS-1$
    }
    return (this.m_xAxis = new Axis(this, 0));
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final IAxis yAxis() {
    this.fsmStateAssert(ChartElement.STATE_ALIVE);
    this.fsmFlagsAssertFalse(LineChart.FLAG_HAS_LINE
        | LineChart.FLAG_HAS_Y_AXIS);
    if (this.m_yAxis != null) {
      throw new IllegalStateException(//
          "Y-axis is already under construction."); //$NON-NLS-1$
    }
    return (this.m_yAxis = new Axis(this, 1));
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final ILine2D line() {
    this.fsmStateAssert(ChartElement.STATE_ALIVE);
    this.fsmFlagsAssertTrue(LineChart.FLAG_HAS_X_AXIS
        | LineChart.FLAG_HAS_Y_AXIS);
    return new Line2D(this, (++this.m_idCounter));
  }

  /** {@inheritDoc} */
  @Override
  public final void setLegendMode(final ELegendMode legendMode) {
    this.fsmStateAssert(ChartElement.STATE_ALIVE);
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        LineChart.FLAG_HAS_LEGEND_MODE, LineChart.FLAG_HAS_LEGEND_MODE,
        FSM.FLAG_NOTHING);
    if (legendMode == null) {
      throw new IllegalArgumentException(//
          "Cannot set legend mode to null, if you don't want to specify it, don't set it in the first place."); //$NON-NLS-1$
    }
    this.m_legendMode = legendMode;
  }
}
