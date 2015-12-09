package examples.org.optimizationBenchmarking.utils.ml.clustering;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;

/** a multi-clustering outcome */
public final class MultiClusteringOutcome {

  /** the single outcomes */
  public final ArrayListView<SingleClusteringOutcome> outcomes;

  /** the total difference */
  public final long totalDifference;

  /** the data set */
  public final ClusteringExampleDataset dataset;

  /** the runtime */
  public final long totalRuntime;

  /**
   * create
   *
   * @param _outcomes
   *          the outcomes
   * @param _totalDifference
   *          the total difference
   * @param _dataset
   *          the dataset
   * @param _totalRuntime
   *          the total runtime
   */
  MultiClusteringOutcome(
      final ArrayListView<SingleClusteringOutcome> _outcomes,
      final long _totalDifference, final ClusteringExampleDataset _dataset,
      final long _totalRuntime) {
    super();
    this.outcomes = _outcomes;
    this.totalDifference = _totalDifference;
    this.dataset = _dataset;
    this.totalRuntime = _totalRuntime;
  }
}
