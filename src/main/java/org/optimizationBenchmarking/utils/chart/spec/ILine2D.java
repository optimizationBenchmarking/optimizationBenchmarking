package org.optimizationBenchmarking.utils.chart.spec;

/** the 2d line */
public interface ILine2D extends IDataSeries {

  /**
   * Set the type of the line.
   *
   * @param type
   *          the type to set
   */
  public abstract void setType(final ELineType type);

}
