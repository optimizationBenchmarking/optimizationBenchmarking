package examples.org.optimizationBenchmarking.utils.ml.clustering;

import org.optimizationBenchmarking.utils.ml.clustering.spec.IClusteringResult;

/** A single clustering outcome */
public final class SingleClusteringOutcome extends Errors {

  /** the clustering result */
  public final IClusteringResult result;

  /**
   * Create a single clustering outcome
   *
   * @param _result
   *          the result
   * @param rt
   *          the runtime
   * @param as
   *          the assignment error
   * @param cl
   *          the cluster count error
   */
  SingleClusteringOutcome(final IClusteringResult _result, final double rt,
      final double as, final double cl) {
    super(rt, as, cl);
    this.result = _result;
  }
}
