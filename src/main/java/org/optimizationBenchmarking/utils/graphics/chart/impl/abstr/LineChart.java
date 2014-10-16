package org.optimizationBenchmarking.utils.graphics.chart.impl.abstr;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.graphics.chart.spec.ILineChart;
import org.optimizationBenchmarking.utils.graphics.graphic.Graphic;

/** the line chart */
public class LineChart extends TitleElement implements ILineChart {

  /** the graphic */
  private final Graphic m_graphic;

  /** the chart driver */
  private final ChartDriver m_driver;

  /** show the legend */
  private boolean m_showLegend;

  /**
   * create the line chart
   * 
   * @param graphic
   *          the graphic
   * @param driver
   *          the chart driver
   */
  protected LineChart(final Graphic graphic, final ChartDriver driver) {
    super(null);
    this.m_graphic = graphic;
    this.m_driver = driver;
    this.m_showLegend = false;
  }

  /**
   * Get the chart driver
   * 
   * @return the chart driver
   */
  protected ChartDriver getDriver() {
    return this.m_driver;
  }

  /**
   * Draw the diagram after the chart has been closed
   * 
   * @param graphic
   *          the graphic
   * @param title
   *          the title
   * @param showLegend
   *          show the legend?
   */
  protected void draw(final Graphic graphic, final String title,
      final boolean showLegend) {
    //
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void onClose() {
    final Graphic g;
    Throwable error;

    this.fsmStateAssertAndSet(ChartElement.STATE_ALIVE,
        ChartElement.STATE_DEAD);

    error = null;
    g = this.m_graphic;
    try {
      try {
        this.draw(g, this.m_title, this.m_showLegend);
      } catch (final Throwable tt) {
        error = ErrorUtils.aggregateError(error, tt);
      } finally {
        g.close();
      }
    } catch (final Throwable t) {
      error = ErrorUtils.aggregateError(error, t);
    } finally {
      try {
        super.onClose();
      } catch (final Throwable ttt) {
        error = ErrorUtils.aggregateError(error, ttt);
      }
    }

    if (error != null) {
      ErrorUtils.throwAsRuntimeException(error);
    }
  }

  /** {@inheritDoc} */
  @Override
  public synchronized Axis xAxis() {
    this.fsmStateAssert(ChartElement.STATE_ALIVE);
    return this.m_driver.createAxis(this, 0);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized Axis yAxis() {
    this.fsmStateAssert(ChartElement.STATE_ALIVE);
    return this.m_driver.createAxis(this, 1);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized Line2D line() {
    this.fsmStateAssert(ChartElement.STATE_ALIVE);
    return this.m_driver.createLine2D(this);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized void setLegendVisible(final boolean showLegend) {
    this.fsmStateAssert(ChartElement.STATE_ALIVE);
    this.m_showLegend = showLegend;
  }
}
