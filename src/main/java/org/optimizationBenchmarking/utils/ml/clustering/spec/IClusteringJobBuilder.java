package org.optimizationBenchmarking.utils.ml.clustering.spec;

import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.tools.spec.IToolJobBuilder;

/** The basic, common interface for clustering job builders. */
public interface IClusteringJobBuilder extends IToolJobBuilder {

  /** {@inheritDoc} */
  @Override
  public abstract IClusteringJobBuilder setLogger(final Logger logger);

  /**
   * Set the number of clusters. If this number is specified, the algorithm
   * will attempt to divide the data into {@code number} clusters. If this
   * method is not called, the clusterer may either use a reasonable
   * default (such as {@code 2}) or attempt to find the optimal number of
   * clusters according to some metric.
   *
   * @param number
   *          the number of clusters
   * @return the cluster job builder
   */
  public abstract IClusteringJobBuilder setClusterNumber(final int number);

  /** {@inheritDoc} */
  @Override
  public abstract IClusteringJob create();
}
