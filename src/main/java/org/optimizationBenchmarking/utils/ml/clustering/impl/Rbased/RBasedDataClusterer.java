package org.optimizationBenchmarking.utils.ml.clustering.impl.Rbased;

import org.optimizationBenchmarking.utils.math.mathEngine.impl.R.R;
import org.optimizationBenchmarking.utils.ml.clustering.impl.abstr.ClusteringJob;
import org.optimizationBenchmarking.utils.ml.clustering.impl.abstr.DataClusterer;
import org.optimizationBenchmarking.utils.ml.clustering.impl.abstr.DataClusteringJobBuilder;

/** The {@code R}-based data clustering engine. */
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
  public final boolean canUse() {
    return R.getInstance().canUse();
  }

  /** {@inheritDoc} */
  @Override
  public final void checkCanUse() {
    R.getInstance().checkCanUse();
    super.checkCanUse();
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "R-based Data Clusterer"; //$NON-NLS-1$
  }

  /**
   * Get the globally shared instance of the {@code R}-based data
   * clusterer.
   *
   * @return the globally shared instance of the {@code R}-based data
   *         clusterer.
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
