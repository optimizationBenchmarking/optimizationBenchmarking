package org.optimizationBenchmarking.utils.chart.spec;

/** The base interface for charts */
public interface IChart extends ITitledElement {
  /**
   * set the title of the chart
   *
   * @param title
   *          the title
   */
  @Override
  public abstract void setTitle(final String title);

  /**
   * Set the legend mode of the diagram
   *
   * @param legendMode
   *          the legend mode
   */
  public abstract void setLegendMode(final ELegendMode legendMode);
}
