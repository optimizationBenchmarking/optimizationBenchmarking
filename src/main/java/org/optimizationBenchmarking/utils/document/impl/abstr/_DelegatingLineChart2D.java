package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.chart.impl.abstr.DelegatingLineChart2D;
import org.optimizationBenchmarking.utils.chart.spec.ILineChart2D;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;

/** the delegating 2D line chart */
final class _DelegatingLineChart2D extends DelegatingLineChart2D {

  /** the graphic */
  private final Graphic m_graphic;

  /**
   * create the delegating 2D line chart
   *
   * @param graphic
   *          the graphic
   * @param chart
   *          the chart
   */
  public _DelegatingLineChart2D(final Graphic graphic,
      final ILineChart2D chart) {
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
