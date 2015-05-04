package org.optimizationBenchmarking.utils.chart.spec;

/** The interface for pie charts */
public interface IPieChart extends IChart {

  /**
   * Add a slice to the pie chart
   *
   * @return the scalar data builder for the line
   */
  public abstract IDataScalar slice();

}
