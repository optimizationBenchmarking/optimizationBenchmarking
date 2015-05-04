package org.optimizationBenchmarking.utils.chart.spec;

import org.optimizationBenchmarking.utils.math.matrix.IMatrix;

/** The interface for building data series */
public interface IDataSeries extends IDataElement {

  /**
   * Set the data of the data series. The required dimensions of the matrix
   * depend on the diagram or series type.
   *
   * @param matrix
   *          the matrix to take the data from
   */
  public abstract void setData(final IMatrix matrix);

}
