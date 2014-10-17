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
   * @param title
   *          the title
   * @param showLegend
   *          show the legend?
   * @param xAxis
   *          the x-axis
   * @param yAxis
   *          the y-axis
   * @param lines
   *          the lines
   */
  protected void renderLineChart(final Graphic graphic,
      final String title, final boolean showLegend, final Axis xAxis,
      final Axis yAxis, final Line2D[] lines) {
    throw new UnsupportedOperationException();
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
   * @param xAxis
   *          the x-axis
   * @param yAxis
   *          the y-axis
   * @param lines
   *          the lines
   */
  final void _renderLineChart(final Graphic graphic, final String title,
      final boolean showLegend, final Axis xAxis, final Axis yAxis,
      final Line2D[] lines) {
    if (graphic == null) {
      throw new IllegalArgumentException("Graphic must not be null."); //$NON-NLS-1$
    }
    if (xAxis == null) {
      throw new IllegalArgumentException("X-axis must not be null."); //$NON-NLS-1$
    }
    if (yAxis == null) {
      throw new IllegalArgumentException("Y-axis must not be null."); //$NON-NLS-1$
    }
    if ((lines == null) || (lines.length <= 0)) {
      throw new IllegalArgumentException(
          "Lines must not be null or empty."); //$NON-NLS-1$
    }
    this.renderLineChart(graphic, title, showLegend, xAxis, yAxis, lines);
  }
}
