package examples.org.optimizationBenchmarking.utils.ml.clustering;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IClusterer;
import org.optimizationBenchmarking.utils.parallel.Execute;

/** the multiple clustering job */
final class _ClustererJob implements Callable<ClustererOutcome> {

  /** the logger */
  private final Logger m_logger;

  /** the example data sets */
  private final ArrayListView<ClusteringExampleDataset> m_ds;

  /** the clusterer */
  private final IClusterer m_clusterer;

  /**
   * create the job
   *
   * @param logger
   *          the logger
   * @param ds
   *          the data sets
   * @param clusterer
   *          the clusterer
   */
  _ClustererJob(final Logger logger,
      final ArrayListView<ClusteringExampleDataset> ds,
      final IClusterer clusterer) {
    super();
    this.m_logger = logger;
    this.m_ds = ds;
    this.m_clusterer = clusterer;
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public final ClustererOutcome call() {
    final Future<MultiClusteringOutcome>[] futures;
    final MultiClusteringOutcome[] results;
    int index;
    long total, runtime;

    index = this.m_ds.size();
    futures = new Future[index];
    for (; (--index) >= 0;) {
      futures[index] = Execute.parallel(new _MultiClusteringJob(
          this.m_logger, this.m_ds.get(index), this.m_clusterer));
    }
    results = new MultiClusteringOutcome[futures.length];
    Execute.join(futures, results, 0, true);

    total = runtime = 0L;
    for (final MultiClusteringOutcome oc : results) {
      total += oc.totalDifference;
      runtime += oc.totalRuntime;
    }

    return new ClustererOutcome(new ArrayListView<>(results), total,
        this.m_clusterer, runtime);
  }
}
