package org.optimizationBenchmarking.utils.ml.clustering.impl.abstr;

/** The base class for distance-based clustering jobs. */
public abstract class DistanceClusteringJob extends ClusteringJob {

  /**
   * create the clustering job
   *
   * @param builder
   *          the job builder
   */
  protected DistanceClusteringJob(
      final DistanceClusteringJobBuilder builder) {
    super(builder, true);
  }
}
