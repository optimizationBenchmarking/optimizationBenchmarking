package org.optimizationBenchmarking.utils.graphics.chart.impl.abstr;

import org.optimizationBenchmarking.utils.graphics.chart.spec.IChartDriver;
import org.optimizationBenchmarking.utils.graphics.graphic.Graphic;

/** the chart driver class */
public class ChartDriver implements IChartDriver {

  /** the chart driver */
  protected ChartDriver() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public LineChart lineChart(final Graphic graphic) {
    return new LineChart(graphic, this);
  }

  /**
   * Create a new 2-dimensional line
   * 
   * @param owner
   *          the owning line chart
   * @return the series
   */
  protected Line2D createLine2D(final LineChart owner) {
    return new Line2D(owner);
  }

  /**
   * Create an axis
   * 
   * @param owner
   *          the owning line chart
   * @param id
   *          the id of the axis
   * @return the axis
   */
  protected Axis createAxis(final LineChart owner, final int id) {
    return new Axis(owner, id);
  }
}
