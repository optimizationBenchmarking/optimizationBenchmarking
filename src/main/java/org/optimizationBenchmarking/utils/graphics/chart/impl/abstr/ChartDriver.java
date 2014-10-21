package org.optimizationBenchmarking.utils.graphics.chart.impl.abstr;

import org.optimizationBenchmarking.utils.graphics.chart.spec.IChartDriver;
import org.optimizationBenchmarking.utils.graphics.chart.spec.ILineChart;
import org.optimizationBenchmarking.utils.graphics.graphic.Graphic;

/** the chart driver base class */
public class ChartDriver implements IChartDriver {

  /**
   * the chart driver
   */
  protected ChartDriver() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final ILineChart lineChart(final Graphic graphic) {
    return new _LineChartBuilder(graphic, this);
  }

  /**
   * Draw the {@link #lineChart(Graphic) line chart} after the chart has
   * been closed and all data is set.
   * 
   * @param graphic
   *          the graphic
   * @param chart
   *          the chart to render
   */
  protected void renderLineChart(final Graphic graphic,
      final LineChart chart) {
    throw new UnsupportedOperationException();
  }

}
