package org.optimizationBenchmarking.utils.ml.clustering.impl.abstr;

import org.optimizationBenchmarking.utils.ml.clustering.spec.IClusteringJob;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IClusteringResult;

/** the basic clustering job */
final class _DirectResult extends ClusteringSolution
    implements IClusteringJob {

  /**
   * create
   *
   * @param _assignment
   *          the assignment
   * @param _quality
   *          the quality
   */
  _DirectResult(final int[] _assignment, final double _quality) {
    super(_assignment, _quality);
  }

  /** {@inheritDoc} */
  @Override
  public final IClusteringResult call() {
    return this;
  }
}
