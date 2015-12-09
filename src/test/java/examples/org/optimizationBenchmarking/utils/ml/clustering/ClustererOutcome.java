package examples.org.optimizationBenchmarking.utils.ml.clustering;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IClusterer;

/** an outcome for a clusterer */
public final class ClustererOutcome {

  /** the single outcomes */
  public final ArrayListView<MultiClusteringOutcome> outcomes;

  /** the total difference */
  public final long totalDifference;

  /** the clusterer */
  public final IClusterer clusterer;

  /** the runtime */
  public final long totalRuntime;

  /**
   * create
   *
   * @param _outcomes
   *          the outcomes
   * @param _totalDifference
   *          the total difference
   * @param _clusterer
   *          the clusterer
   * @param _totalRuntime
   *          the total runtime
   */
  ClustererOutcome(final ArrayListView<MultiClusteringOutcome> _outcomes,
      final long _totalDifference, final IClusterer _clusterer,
      final long _totalRuntime) {
    super();
    this.outcomes = _outcomes;
    this.totalDifference = _totalDifference;
    this.clusterer = _clusterer;
    this.totalRuntime = _totalRuntime;
  }
}
