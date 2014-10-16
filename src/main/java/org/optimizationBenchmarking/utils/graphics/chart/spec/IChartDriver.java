package org.optimizationBenchmarking.utils.graphics.chart.spec;

import org.optimizationBenchmarking.utils.graphics.graphic.Graphic;

/** The basic interface for chart drivers */
public interface IChartDriver {

  /**
   * Create a line chart to be painted on the given graphic object. The
   * chart will close the graphic object once its paining has completed.
   * The chart will fill the complete graphic, i.e., set its extent to
   * {@link org.optimizationBenchmarking.utils.graphics.graphic.Graphic#getBounds()}
   * .
   * 
   * @param graphic
   *          the graphic to paint on
   * @return the line chart
   */
  public abstract ILineChart lineChart(final Graphic graphic);

}
