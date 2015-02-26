package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.chart.impl.abstr.DelegatingLineChart;
import org.optimizationBenchmarking.utils.chart.spec.ILineChart;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;

/** the delegating line chart */
final class _DelegatingLineChart extends DelegatingLineChart {

  /** the graphic */
  private final Graphic m_graphic;

  /**
   * the line chart
   * 
   * @param graphic
   *          the graphic
   * @param chart
   *          the chart
   */
  public _DelegatingLineChart(final Graphic graphic, final ILineChart chart) {
    super(chart);
    this.m_graphic = graphic;
  }

  /** {@inheritDoc} */
  @Override
  protected final void doClose() {
    this.m_graphic.close();
    super.doClose();
  }
}
