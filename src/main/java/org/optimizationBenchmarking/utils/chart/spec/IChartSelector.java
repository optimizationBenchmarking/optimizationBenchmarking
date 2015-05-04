package org.optimizationBenchmarking.utils.chart.spec;

import org.optimizationBenchmarking.utils.tools.spec.IToolJob;

/**
 * This interface allows you to select the chart to paint.
 */
public interface IChartSelector extends IToolJob {

  /**
   * Create a two-dimensional line chart to be painted on the given graphic
   * object. The chart will <em>not</em> close the graphic object once its
   * paining has completed. The chart will fill the complete graphic, i.e.,
   * set its extent to
   * {@link org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic#getBounds()}
   *
   * @return the line chart
   */
  public abstract ILineChart2D lineChart2D();

  /**
   * Create a pie chart to be painted on the given graphic object. The
   * chart will <em>not</em> close the graphic object once its paining has
   * completed. The chart will fill the complete graphic, i.e., set its
   * extent to
   * {@link org.optimizationBenchmarking.utils.graphics.graphic.spec.Graphic#getBounds()}
   *
   * @return the pie chart
   */
  public abstract IPieChart pieChart();
}
