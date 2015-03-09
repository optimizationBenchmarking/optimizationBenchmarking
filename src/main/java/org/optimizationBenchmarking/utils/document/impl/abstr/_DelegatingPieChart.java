package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.chart.impl.abstr.DelegatingPieChart;
import org.optimizationBenchmarking.utils.chart.spec.IPieChart;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;

/** the delegating pie chart */
final class _DelegatingPieChart extends DelegatingPieChart {

  /** the graphic */
  private final Graphic m_graphic;

  /**
   * create the delegating pie chart
   * 
   * @param graphic
   *          the graphic
   * @param chart
   *          the chart
   */
  public _DelegatingPieChart(final Graphic graphic, final IPieChart chart) {
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
