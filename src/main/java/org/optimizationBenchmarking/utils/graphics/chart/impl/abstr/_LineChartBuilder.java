package org.optimizationBenchmarking.utils.graphics.chart.impl.abstr;

import java.util.ArrayList;

import org.optimizationBenchmarking.utils.graphics.chart.spec.IAxis;
import org.optimizationBenchmarking.utils.graphics.chart.spec.ILine2D;
import org.optimizationBenchmarking.utils.graphics.chart.spec.ILineChart;
import org.optimizationBenchmarking.utils.graphics.graphic.Graphic;
import org.optimizationBenchmarking.utils.hierarchy.FSM;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/** the line chart */
final class _LineChartBuilder extends _TitledElementBuilder implements
    ILineChart {

  /** the x-axis type has been set */
  static final int FLAG_HAS_X_AXIS = (_TitledElementBuilder.FLAG_HAS_TITLE << 1);
  /** the y-axis type has been set */
  static final int FLAG_HAS_Y_AXIS = (_LineChartBuilder.FLAG_HAS_X_AXIS << 1);
  /** the show legend has been set */
  static final int FLAG_HAS_SHOW_LEGEND = (_LineChartBuilder.FLAG_HAS_Y_AXIS << 1);
  /** at least one line has been added */
  static final int FLAG_HAS_LINE = (_LineChartBuilder.FLAG_HAS_SHOW_LEGEND << 1);

  /** the graphic */
  private Graphic m_graphic;

  /** the chart driver */
  private final ChartDriver m_driver;

  /** the lines */
  private ArrayList<Line2D> m_lines;

  /** show the legend */
  private boolean m_showLegend;

  /** the internal x-axis builder */
  private _AxisBuilder m_xAxis;
  /** the internal y-axis builder */
  private _AxisBuilder m_yAxis;

  /**
   * create the line chart
   * 
   * @param graphic
   *          the graphic
   * @param driver
   *          the chart driver
   */
  _LineChartBuilder(final Graphic graphic, final ChartDriver driver) {
    super(null);

    if (graphic == null) {
      throw new IllegalArgumentException("Graphic must not be null."); //$NON-NLS-1$
    }
    if (driver == null) {
      throw new IllegalArgumentException("Driver must not be null."); //$NON-NLS-1$
    }
    this.m_graphic = graphic;
    this.m_driver = driver;

    this.m_lines = new ArrayList<>();
    this.m_showLegend = false;

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
      case FLAG_HAS_SHOW_LEGEND: {
        append.append("showLegendSet");break;} //$NON-NLS-1$
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
  synchronized final void _addLine(final Line2D line) {
    this.fsmStateAssert(_ChartElementBuilder.STATE_ALIVE);
    this.fsmFlagsAssertAndUpdate(
        (_LineChartBuilder.FLAG_HAS_X_AXIS | _LineChartBuilder.FLAG_HAS_Y_AXIS),
        FSM.FLAG_NOTHING, _LineChartBuilder.FLAG_HAS_LINE,
        FSM.FLAG_NOTHING);

    if (line != null) {
      this.m_xAxis._registerData(line.m_data);
      this.m_yAxis._registerData(line.m_data);
      this.m_lines.add(line);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void onClose() {
    Graphic g;
    Axis x, y;
    Line2D[] data;

    this.fsmStateAssertAndSet(_ChartElementBuilder.STATE_ALIVE,
        _ChartElementBuilder.STATE_DEAD);
    this.fsmFlagsAssertTrue(_LineChartBuilder.FLAG_HAS_X_AXIS
        | _LineChartBuilder.FLAG_HAS_Y_AXIS
        | _LineChartBuilder.FLAG_HAS_LINE);

    g = this.m_graphic;
    this.m_graphic = null;
    try {
      x = this.m_xAxis._getAxis();
      this.m_xAxis = null;
      y = this.m_yAxis._getAxis();
      this.m_yAxis = null;
      data = this.m_lines.toArray(new Line2D[this.m_lines.size()]);
      this.m_lines = null;
      this.m_driver._renderLineChart(g, this.m_title, this.m_showLegend,
          x, y, data);
    } finally {
      this.m_lines = null;
      this.m_xAxis = null;
      this.m_yAxis = null;
      data = null;
      x = y = null;
      g = null;
    }

    super.onClose();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final IAxis xAxis() {
    this.fsmStateAssert(_ChartElementBuilder.STATE_ALIVE);
    this.fsmFlagsAssertFalse(_LineChartBuilder.FLAG_HAS_LINE
        | _LineChartBuilder.FLAG_HAS_X_AXIS);
    if (this.m_xAxis != null) {
      throw new IllegalStateException(//
          "X-axis is already under construction."); //$NON-NLS-1$
    }
    return (this.m_xAxis = new _AxisBuilder(this, 0));
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final IAxis yAxis() {
    this.fsmStateAssert(_ChartElementBuilder.STATE_ALIVE);
    this.fsmFlagsAssertFalse(_LineChartBuilder.FLAG_HAS_LINE
        | _LineChartBuilder.FLAG_HAS_Y_AXIS);
    if (this.m_yAxis != null) {
      throw new IllegalStateException(//
          "Y-axis is already under construction."); //$NON-NLS-1$
    }
    return (this.m_yAxis = new _AxisBuilder(this, 1));
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final ILine2D line() {
    this.fsmStateAssert(_ChartElementBuilder.STATE_ALIVE);
    this.fsmFlagsAssertTrue(_LineChartBuilder.FLAG_HAS_X_AXIS
        | _LineChartBuilder.FLAG_HAS_Y_AXIS);
    return new _Line2DBuilder(this);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void setLegendVisible(final boolean showLegend) {
    this.fsmStateAssert(_ChartElementBuilder.STATE_ALIVE);
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        _LineChartBuilder.FLAG_HAS_SHOW_LEGEND,
        _LineChartBuilder.FLAG_HAS_SHOW_LEGEND, FSM.FLAG_NOTHING);
    this.m_showLegend = showLegend;
  }
}
