package examples.org.optimizationBenchmarking.utils.ml.clustering;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

/** a multi-clustering outcome */
public final class MultiClusteringOutcome
    extends _MultiOutcome<SingleClusteringOutcome> {

  /** the data set */
  public final ClusteringExampleDataset dataset;

  /**
   * create
   *
   * @param _outcomes
   *          the outcomes
   * @param _dataset
   *          the dataset
   * @param _mean
   *          the mean error
   * @param _stddev
   *          the error standard deviation
   */
  MultiClusteringOutcome(
      final ArrayListView<SingleClusteringOutcome> _outcomes,
      final ClusteringExampleDataset _dataset, final Errors _mean,
      final Errors _stddev) {
    super(_outcomes, _mean, _stddev);
    this.dataset = _dataset;
  }
}
