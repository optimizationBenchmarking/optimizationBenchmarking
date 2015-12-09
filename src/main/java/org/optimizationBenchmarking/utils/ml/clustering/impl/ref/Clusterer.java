package org.optimizationBenchmarking.utils.ml.clustering.impl.ref;

import org.optimizationBenchmarking.utils.ml.clustering.spec.IClusterer;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IClusteringJob;
import org.optimizationBenchmarking.utils.tools.impl.abstr.Tool;

/** The base class for clustering tools. */
public abstract class Clusterer extends Tool implements IClusterer {

  /** create */
  protected Clusterer() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public ClusteringJobBuilder use() {
    return new ClusteringJobBuilder(this);
  }

  /**
   * Create the clustering job.
   *
   * @param builder
   *          the job builder
   * @return the job
   */
  protected abstract ClusteringJob create(
      final ClusteringJobBuilder builder);

  /**
   * Create the clustering job.
   *
   * @param builder
   *          the job builder
   * @return the job
   */
  final IClusteringJob _create(final ClusteringJobBuilder builder) {
    _DirectResult job;

    job = ClusteringJob._canSolveDefault(builder.m_matrix,
        builder.m_classes);
    if (job != null) {
      return job;
    }
    return this.create(builder);
  }
}