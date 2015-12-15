package org.optimizationBenchmarking.utils.ml.clustering.impl.abstr;

/** The base class for data clustering jobs. */
public abstract class DataClusteringJob extends ClusteringJob {

  /**
   * create the clustering job
   *
   * @param builder
   *          the job builder
   */
  protected DataClusteringJob(final DataClusteringJobBuilder builder) {
    super(builder, false);
  }
}
