package org.optimizationBenchmarking.utils.ml.clustering.impl.Rbased;

import org.optimizationBenchmarking.utils.ml.clustering.impl.ref.Clusterer;
import org.optimizationBenchmarking.utils.ml.clustering.impl.ref.ClusteringJob;
import org.optimizationBenchmarking.utils.ml.clustering.impl.ref.ClusteringJobBuilder;

/** The {@code R}-based clustering engine. */
public final class RBasedClusterer extends Clusterer {

  /** create */
  RBasedClusterer() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected final ClusteringJob create(
      final ClusteringJobBuilder builder) {
    return new _RBasedClusteringJob(builder);
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "R-based Clusterer"; //$NON-NLS-1$
  }

  /**
   * Get the globally shared instance of the {@code R}-based clusterer.
   *
   * @return the globally shared instance of the {@code R}-based clusterer.
   */
  public static final RBasedClusterer getInstance() {
    return __RBasedClustererHolder.INSTANCE;
  }

  /** the clusterer holder */
  private static final class __RBasedClustererHolder {
    /** the globally shared instance */
    static final RBasedClusterer INSTANCE = new RBasedClusterer();
  }
}
