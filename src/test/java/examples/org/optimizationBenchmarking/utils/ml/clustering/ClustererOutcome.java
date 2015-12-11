package examples.org.optimizationBenchmarking.utils.ml.clustering;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IClusterer;

/** an outcome for a clusterer */
public final class ClustererOutcome
    extends _MultiOutcome<MultiClusteringOutcome> {

  /** the clusterer */
  public final IClusterer clusterer;

  /**
   * create
   *
   * @param _outcomes
   *          the outcomes
   * @param _clusterer
   *          the clusterer
   * @param _mean
   *          the mean error
   * @param _stddev
   *          the error standard deviation
   */
  ClustererOutcome(final ArrayListView<MultiClusteringOutcome> _outcomes,
      final IClusterer _clusterer, final Errors _mean,
      final Errors _stddev) {
    super(_outcomes, _mean, _stddev);
    this.clusterer = _clusterer;
  }
}
