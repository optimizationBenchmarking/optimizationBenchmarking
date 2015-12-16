package org.optimizationBenchmarking.utils.ml.clustering.impl.Rbased;

import org.optimizationBenchmarking.utils.math.mathEngine.impl.R.R;
import org.optimizationBenchmarking.utils.ml.clustering.impl.abstr.ClusteringJob;
import org.optimizationBenchmarking.utils.ml.clustering.impl.abstr.DistanceClusterer;
import org.optimizationBenchmarking.utils.ml.clustering.impl.abstr.DistanceClusteringJobBuilder;

/** The {@code R}-based distance distance clustering engine. */
public final class RBasedDistanceClusterer extends DistanceClusterer {

  /** create */
  RBasedDistanceClusterer() {
    super();
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
  protected final ClusteringJob create(
      final DistanceClusteringJobBuilder builder) {
    return new _RBasedDistanceClusteringJob(builder);
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "R-based Distance Clusterer"; //$NON-NLS-1$
  }

  /**
   * Get the globally shared instance of the {@code R}-based distance
   * clusterer.
   *
   * @return the globally shared instance of the {@code R}-based distance
   *         clusterer.
   */
  public static final RBasedDistanceClusterer getInstance() {
    return __RBasedClustererHolder.INSTANCE;
  }

  /** the clusterer holder */
  private static final class __RBasedClustererHolder {
    /** the globally shared instance */
    static final RBasedDistanceClusterer INSTANCE = new RBasedDistanceClusterer();
  }
}
