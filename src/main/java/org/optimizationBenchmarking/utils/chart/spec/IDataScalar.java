package org.optimizationBenchmarking.utils.chart.spec;

/** The interface for building scalar data elements */
public interface IDataScalar extends IDataElement {

  /**
   * Set the value of the scalar data as {@code double}
   *
   * @param value
   *          the value
   */
  public abstract void setData(final double value);

  /**
   * Set the value of the scalar data as {@code long}
   *
   * @param value
   *          the value
   */
  public abstract void setData(final long value);

}
