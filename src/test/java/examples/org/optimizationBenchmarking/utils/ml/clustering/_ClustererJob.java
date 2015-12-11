package examples.org.optimizationBenchmarking.utils.ml.clustering;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.ArithmeticMeanAggregate;
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
    final ArithmeticMeanAggregate rtm, dfm, clm, rts, dfs, cls;
    int index;

    index = this.m_ds.size();
    futures = new Future[index];
    for (; (--index) >= 0;) {
      futures[index] = Execute.parallel(new _MultiClusteringJob(
          this.m_logger, this.m_ds.get(index), this.m_clusterer));
    }
    results = new MultiClusteringOutcome[futures.length];
    Execute.join(futures, results, 0, true);

    rtm = new ArithmeticMeanAggregate();
    dfm = new ArithmeticMeanAggregate();
    clm = new ArithmeticMeanAggregate();
    rts = new ArithmeticMeanAggregate();
    dfs = new ArithmeticMeanAggregate();
    cls = new ArithmeticMeanAggregate();
    for (final MultiClusteringOutcome oc : results) {
      rtm.append(oc.mean.runtime);
      dfm.append(oc.mean.assignmentError);
      clm.append(oc.mean.clusterNumError);
      rts.append(oc.stddev.runtime);
      dfs.append(oc.stddev.assignmentError);
      cls.append(oc.stddev.clusterNumError);
    }

    return new ClustererOutcome(new ArrayListView<>(results),
        this.m_clusterer, //
        new Errors(rtm.doubleValue(), dfm.doubleValue(),
            clm.doubleValue()), //
        new Errors(rts.doubleValue(), dfs.doubleValue(),
            cls.doubleValue()));
  }
}
