package org.optimizationBenchmarking.utils.chart.spec;

/** The interface for line charts */
public interface ILineChart2D extends IChart {

  /**
   * define the {@code x}-axis
   *
   * @return the {@code x}-axis builder
   */
  public abstract IAxis xAxis();

  /**
   * define the {@code y}-axis
   *
   * @return the {@code y}-axis builder
   */
  public abstract IAxis yAxis();

  /**
   * Add a line to the chart
   *
   * @return the data series builder for the line
   */
  public abstract ILine2D line();

}
