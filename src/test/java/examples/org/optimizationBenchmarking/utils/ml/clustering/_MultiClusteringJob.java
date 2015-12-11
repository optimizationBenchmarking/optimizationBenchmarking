package examples.org.optimizationBenchmarking.utils.ml.clustering;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.StandardDeviationAggregate;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IClusterer;
import org.optimizationBenchmarking.utils.parallel.Execute;

/** the multiple clustering job */
final class _MultiClusteringJob
    implements Callable<MultiClusteringOutcome> {

  /** the number of runs */
  private static final int RUNS = 10;

  /** the logger */
  private final Logger m_logger;

  /** the example data set */
  private final ClusteringExampleDataset m_ds;

  /** the clusterer */
  private final IClusterer m_clusterer;

  /**
   * create the job
   *
   * @param logger
   *          the logger
   * @param ds
   *          the data set
   * @param clusterer
   *          the clusterer
   */
  _MultiClusteringJob(final Logger logger,
      final ClusteringExampleDataset ds, final IClusterer clusterer) {
    super();
    this.m_logger = logger;
    this.m_ds = ds;
    this.m_clusterer = clusterer;
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public final MultiClusteringOutcome call() {
    final Future<SingleClusteringOutcome>[] futures;
    final SingleClusteringOutcome[] results;
    final StandardDeviationAggregate rt, df, cl;
    int index;

    index = _MultiClusteringJob.RUNS;
    futures = new Future[index];
    for (; (--index) >= 0;) {
      futures[index] = Execute.parallel(new _SingleClusteringJob(
          this.m_logger, this.m_ds, this.m_clusterer));
    }
    results = new SingleClusteringOutcome[futures.length];
    Execute.join(futures, results, 0, true);

    rt = new StandardDeviationAggregate();
    df = new StandardDeviationAggregate();
    cl = new StandardDeviationAggregate();
    for (final SingleClusteringOutcome oc : results) {
      rt.append(oc.runtime);
      df.append(oc.assignmentError);
      cl.append(oc.clusterNumError);
    }

    return new MultiClusteringOutcome(new ArrayListView<>(results), //
        this.m_ds, //
        new Errors(rt.getArithmeticMean().doubleValue(), //
            df.getArithmeticMean().doubleValue(), //
            cl.getArithmeticMean().doubleValue()), //
        new Errors(rt.doubleValue(), df.doubleValue(), cl.doubleValue()));
  }
}
