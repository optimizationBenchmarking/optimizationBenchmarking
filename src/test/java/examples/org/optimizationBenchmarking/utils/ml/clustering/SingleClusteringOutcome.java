package examples.org.optimizationBenchmarking.utils.ml.clustering;

import org.optimizationBenchmarking.utils.ml.clustering.spec.IClusteringResult;

/** A single clustering outcome */
public final class SingleClusteringOutcome {

  /** the clustering result */
  public final IClusteringResult result;

  /** the differences */
  public final int differences;

  /** the runtime */
  public final long runtime;

  /**
   * Create a single clustering outcome
   *
   * @param _differences
   *          the differences
   * @param _result
   *          the result
   * @param _runtime
   *          the runtime
   */
  SingleClusteringOutcome(final int _differences,
      final IClusteringResult _result, final long _runtime) {
    super();
    this.differences = _differences;
    this.result = _result;
    this.runtime = _runtime;
  }
}
