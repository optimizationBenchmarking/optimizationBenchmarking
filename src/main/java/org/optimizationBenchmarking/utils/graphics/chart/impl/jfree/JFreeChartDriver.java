package org.optimizationBenchmarking.utils.graphics.chart.impl.jfree;

import org.optimizationBenchmarking.utils.graphics.chart.impl.abstr.ChartDriver;
import org.optimizationBenchmarking.utils.graphics.chart.impl.abstr.LineChart;
import org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic;

/**
 * The driver for JFreeChart-based charts.
 */
public final class JFreeChartDriver extends ChartDriver {

  /** the JFreeChart-based chart driver */
  public static final JFreeChartDriver INSTANCE = new JFreeChartDriver();

  /** create */
  private JFreeChartDriver() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected final void renderLineChart(final Graphic graphic,
      final LineChart chart) {
    new _JFreeChartLineChart2DRenderer(chart)._render(graphic);
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "JFreeChart-based Chart Driver"; //$NON-NLS-1$
  }
}
