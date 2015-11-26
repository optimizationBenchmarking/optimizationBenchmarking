package examples.org.optimizationBenchmarking.utils.math.fitting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.math.fitting.spec.FunctionFitter;
import org.optimizationBenchmarking.utils.parallel.Execute;

/** a fitting job */
final class _FitterJob implements Callable<FitterOutcome> {

  /** the fitter to apply */
  private final FunctionFitter m_fitter;

  /** the data sets */
  private final ArrayListView<FittingExampleDataset> m_datasets;

  /** the root path */
  private final Path m_root;
  /** the logger */
  private final Logger m_logger;

  /**
   * create
   *
   * @param fitter
   *          the fitter
   * @param datasets
   *          the data sets
   * @param root
   *          the root path
   * @param logger
   *          the logger
   */
  _FitterJob(final Logger logger, final FunctionFitter fitter,
      final ArrayListView<FittingExampleDataset> datasets,
      final Path root) {
    super();
    this.m_fitter = fitter;
    this.m_root = root;
    this.m_datasets = datasets;
    this.m_logger = logger;
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public final FitterOutcome call() {
    final String fitter;
    final Future<FittingOutcome>[] jobs;
    final FittingOutcome[] res;
    final FitterOutcome outcome;
    final ArrayListView<FittingExampleDataset> data;
    int i;
    final Path destFolder;

    fitter = this.m_fitter.getClass().getSimpleName();
    if ((this.m_logger != null)
        && (this.m_logger.isLoggable(Level.FINE))) {
      this.m_logger.fine("Beginning to experiment with fitter " + fitter);//$NON-NLS-1$
    }

    destFolder = PathUtils.createPathInside(this.m_root, fitter);
    try {
      Files.createDirectories(destFolder);

      data = this.m_datasets;
      jobs = new Future[data.size()];
      for (i = jobs.length; (--i) >= 0;) {
        jobs[i] = Execute.parallel(new _MultiFittingJob(this.m_logger,
            data.get(i), this.m_fitter, destFolder));
      }
      res = new FittingOutcome[jobs.length];
      Execute.join(jobs, res, 0, false);
      outcome = new FitterOutcome(this.m_fitter, res, destFolder);

    } catch (final IOException ignore) {
      throw new RuntimeException(ignore);
    }

    if ((this.m_logger != null)
        && (this.m_logger.isLoggable(Level.FINE))) {
      this.m_logger.fine("Finished experimenting with fitter " + fitter);//$NON-NLS-1$
    }

    return outcome;
  }
}
