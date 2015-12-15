package org.optimizationBenchmarking.utils.ml.clustering.impl.abstr;

import org.optimizationBenchmarking.utils.ml.clustering.spec.IClusterer;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IClusteringJob;
import org.optimizationBenchmarking.utils.tools.impl.abstr.Tool;

/**
 * The base class for clustering tools.
 *
 * @param <R>
 *          the builder type
 */
public abstract class Clusterer<R extends ClusteringJobBuilder<?>>
    extends Tool implements IClusterer {

  /** create */
  protected Clusterer() {
    super();
  }

  /**
   * Create the clustering job.
   *
   * @param builder
   *          the job builder
   * @return the job
   */
  protected abstract ClusteringJob create(final R builder);

  /**
   * Create the clustering job.
   *
   * @param builder
   *          the job builder
   * @return the job
   */
  @SuppressWarnings("unchecked")
  final IClusteringJob _create(final ClusteringJobBuilder<?> builder) {
    _DirectResult job;

    job = ClusteringTools._canClusterTrivially(builder.m_matrix,
        builder.m_classes);
    if (job != null) {
      return job;
    }
    return this.create((R) builder);
  }
}