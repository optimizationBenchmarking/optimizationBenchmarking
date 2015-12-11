package examples.org.optimizationBenchmarking.utils.ml.clustering;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

/**
 * a multi outcome
 *
 * @param <T>
 *          the element type
 */
class _MultiOutcome<T> {

  /** the single outcomes */
  public final ArrayListView<T> outcomes;

  /** the mean error */
  public final Errors mean;

  /** the standard deviation */
  public final Errors stddev;

  /**
   * create
   *
   * @param _outcomes
   *          the outcomes
   * @param _mean
   *          the mean error
   * @param _stddev
   *          the error standard deviation
   */
  _MultiOutcome(final ArrayListView<T> _outcomes, final Errors _mean,
      final Errors _stddev) {
    super();
    this.outcomes = _outcomes;
    this.mean = _mean;
    this.stddev = _stddev;
  }
}
