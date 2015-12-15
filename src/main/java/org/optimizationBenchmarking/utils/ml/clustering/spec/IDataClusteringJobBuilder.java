package org.optimizationBenchmarking.utils.ml.clustering.spec;

import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.math.matrix.IMatrix;

/** A job builder for the data-based clustering jobs. */
public interface IDataClusteringJobBuilder extends IClusteringJobBuilder {

  /** {@inheritDoc} */
  @Override
  public abstract IDataClusteringJobBuilder setLogger(final Logger logger);

  /**
   * Set the data to be clustered: A {@code m*n} matrix. Each of the
   * {@code m} rows of the matrix are a feature vector to be clustered.
   * There are {@code n} columns, each corresponding to one feature. All of
   * them are used for clustering.
   *
   * @param matrix
   *          the data matrix
   * @return this builder
   */
  public abstract IDataClusteringJobBuilder setData(final IMatrix matrix);

  /**
   * Set the number of clusters. If this number is specified, the algorithm
   * will attempt to divide the {@link #setData(IMatrix) data} into
   * {@code number} clusters. If this method is not called, the clusterer
   * may either use a reasonable default (such as {@code 2}) or attempt to
   * find the optimal number of clusters according to some metric.
   *
   * @param number
   *          the number of clusters
   * @return the cluster job builder
   */
  @Override
  public abstract IDataClusteringJobBuilder setClusterNumber(
      final int number);
}
