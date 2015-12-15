package org.optimizationBenchmarking.utils.ml.clustering.spec;

import org.optimizationBenchmarking.utils.math.matrix.IMatrix;

/** A distance measure which can be used in clustering. */
public interface IDistanceMeasure {

  /**
   * Compute the distance of {@code rowA} of {@code matrixA} to
   * {@code rowB} of {@code matrixB}.
   *
   * @param matrixA
   *          the first matrix
   * @param rowA
   *          the row of the first matrix which should be used
   * @param matrixB
   *          the second matrix
   * @param rowB
   *          the row of the second matrix which should be used
   * @return the distance
   */
  public abstract double compute(final IMatrix matrixA, final int rowA,
      final IMatrix matrixB, final int rowB);

}
