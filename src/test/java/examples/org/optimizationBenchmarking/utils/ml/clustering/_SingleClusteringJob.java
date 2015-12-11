package examples.org.optimizationBenchmarking.utils.ml.clustering;

import java.util.concurrent.Callable;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.ml.clustering.spec.IClusterer;
import org.optimizationBenchmarking.utils.ml.clustering.spec.IClusteringResult;

/** the single clustering job */
final class _SingleClusteringJob
    implements Callable<SingleClusteringOutcome> {

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
  _SingleClusteringJob(final Logger logger,
      final ClusteringExampleDataset ds, final IClusterer clusterer) {
    super();
    this.m_logger = logger;
    this.m_ds = ds;
    this.m_clusterer = clusterer;
  }

  /** {@inheritDoc} */
  @Override
  public final SingleClusteringOutcome call() {
    final IClusteringResult cr;
    int diff, index, max, min;
    final long start, end;

    start = System.nanoTime();
    cr = this.m_clusterer.use().setLogger(this.m_logger)
        .setData(this.m_ds.data).create().call();
    end = System.nanoTime();

    diff = 0;
    index = (-1);
    min = Integer.MAX_VALUE;
    max = Integer.MIN_VALUE;
    for (final int a : cr.getClustersRef()) {
      if (a != this.m_ds.clusters[++index]) {
        diff++;
      }
      if (a < min) {
        min = a;
      }
      if (a > max) {
        max = a;
      }
    }

    return new SingleClusteringOutcome(cr, //
        Math.max(0L, (end - start)), //
        (diff / ((double) (this.m_ds.clusters.length))), //
        ((((max - min) + 1) - this.m_ds.classes)
            / ((double) (this.m_ds.classes))));
  }
}
