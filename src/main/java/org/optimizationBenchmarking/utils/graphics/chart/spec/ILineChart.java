package org.optimizationBenchmarking.utils.graphics.chart.spec;

/** The interface for line charts */
public interface ILineChart extends ITitledElement {
  /**
   * set the title of the chart
   * 
   * @param title
   *          the title
   */
  @Override
  public abstract void setTitle(final String title);

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

  /**
   * Set the legend mode of the diagram
   * 
   * @param legendMode
   *          the legend mode
   */
  public abstract void setLegendMode(final ELegendMode legendMode);

}
