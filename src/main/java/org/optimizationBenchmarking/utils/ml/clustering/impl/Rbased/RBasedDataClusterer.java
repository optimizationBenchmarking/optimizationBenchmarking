package org.optimizationBenchmarking.utils.ml.clustering.impl.Rbased;

import org.optimizationBenchmarking.utils.ml.clustering.impl.abstr.ClusteringJob;
import org.optimizationBenchmarking.utils.ml.clustering.impl.abstr.DataClusterer;
import org.optimizationBenchmarking.utils.ml.clustering.impl.abstr.DataClusteringJobBuilder;

/** The {@code R}-based clustering engine. */
public final class RBasedDataClusterer extends DataClusterer {

  /** create */
  RBasedDataClusterer() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected final ClusteringJob create(
      final DataClusteringJobBuilder builder) {
    return new _RBasedDataClusteringJob(builder);
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "R-based Data Clusterer"; //$NON-NLS-1$
  }

  /**
   * Get the globally shared instance of the {@code R}-based clusterer.
   *
   * @return the globally shared instance of the {@code R}-based clusterer.
   */
  public static final RBasedDataClusterer getInstance() {
    return __RBasedClustererHolder.INSTANCE;
  }

  /** the clusterer holder */
  private static final class __RBasedClustererHolder {
    /** the globally shared instance */
    static final RBasedDataClusterer INSTANCE = new RBasedDataClusterer();
  }
}
