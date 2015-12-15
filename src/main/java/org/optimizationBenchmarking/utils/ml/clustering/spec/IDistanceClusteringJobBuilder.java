package org.optimizationBenchmarking.utils.ml.clustering.spec;

import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.math.matrix.IMatrix;

/** A job builder for the distance-based clustering jobs. */
public interface IDistanceClusteringJobBuilder
    extends IClusteringJobBuilder {

  /** {@inheritDoc} */
  @Override
  public abstract IDistanceClusteringJobBuilder setLogger(
      final Logger logger);

  /**
   * Set the distance matrix describing the distances between the data
   * elements to be clustered: A {@code m*m} matrix, usually with {@code 0}
   * s at the diagonal and the elements in the triangle above being the
   * same as those in the triangle below the diagonal. The element at index
   * {@code (i,j)} describes the dissimilarity (distance) of data element
   * {@code i} and {@code j}.
   *
   * @param matrix
   *          the distance matrix
   * @return this builder
   */
  public abstract IDistanceClusteringJobBuilder setDistanceMatrix(
      final IMatrix matrix);

  /** {@inheritDoc} */
  @Override
  public abstract IDistanceClusteringJobBuilder setClusterNumber(
      final int number);
}
