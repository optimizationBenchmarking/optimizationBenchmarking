package examples.org.optimizationBenchmarking.utils.math.fitting;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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
import org.optimizationBenchmarking.utils.text.textOutput.AbstractTextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

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
    final ITextOutput text;
    int i;
    final Path destFolder, dest;

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

      dest = PathUtils.createPathInside(destFolder,
          fitter + "-summary.txt");//$NON-NLS-1$

      try (final OutputStream os = PathUtils.openOutputStream(dest)) {
        try (final OutputStreamWriter osw = new OutputStreamWriter(os)) {
          try (final BufferedWriter bw = new BufferedWriter(osw)) {
            text = AbstractTextOutput.wrap(bw);

            text.append("fitter:\t");//$NON-NLS-1$
            text.append(fitter);
            text.appendLineBreak();
            text.appendLineBreak();
            text.appendLineBreak();

            text.append(
                "===== medians of error:\tmin\tmedian\tmax\t/\tstddev ===== ");//$NON-NLS-1$
            text.appendLineBreak();
            text.append("quality:\t");//$NON-NLS-1$
            text.append(outcome.minErrors.quality);
            text.append('\t');
            text.append(outcome.medianErrors.quality);
            text.append('\t');
            text.append(outcome.maxErrors.quality);
            text.append('\t');
            text.append('/');
            text.append('\t');
            text.append(outcome.stddevErrors.quality);
            text.appendLineBreak();

            text.append("rootMeanSquareError:\t");//$NON-NLS-1$
            text.append(outcome.minErrors.rootMeanSquareError);
            text.append('\t');
            text.append(outcome.medianErrors.rootMeanSquareError);
            text.append('\t');
            text.append(outcome.maxErrors.rootMeanSquareError);
            text.append('\t');
            text.append('/');
            text.append('\t');
            text.append(outcome.stddevErrors.rootMeanSquareError);
            text.appendLineBreak();

            text.append("median error:\t");//$NON-NLS-1$
            text.append(outcome.minErrors.medianError);
            text.append('\t');
            text.append(outcome.medianErrors.medianError);
            text.append('\t');
            text.append(outcome.maxErrors.medianError);
            text.append('\t');
            text.append('/');
            text.append('\t');
            text.append(outcome.stddevErrors.medianError);
            text.appendLineBreak();

            text.append("runtime:\t");//$NON-NLS-1$
            text.append(outcome.minErrors.runtime);
            text.append('\t');
            text.append(outcome.medianErrors.runtime);
            text.append('\t');
            text.append(outcome.maxErrors.runtime);
            text.append('\t');
            text.append('/');
            text.append('\t');
            text.append(outcome.stddevErrors.runtime);
            text.appendLineBreak();

            text.appendLineBreak();
            text.appendLineBreak();

            text.append(
                "======================= MEDIANS/CASE =======================");//$NON-NLS-1$
            text.appendLineBreak();

            text.append("case: ");//$NON-NLS-1$
            for (final FittingOutcome oc : res) {
              text.append('\t');
              text.append(oc.example.name);
            }
            text.appendLineBreak();

            text.append("quality: ");//$NON-NLS-1$
            for (final FittingOutcome oc : res) {
              text.append('\t');
              text.append(oc.medianErrors.quality);
            }
            text.appendLineBreak();

            text.append("rootMeanSquare: ");//$NON-NLS-1$
            for (final FittingOutcome oc : res) {
              text.append('\t');
              text.append(oc.medianErrors.rootMeanSquareError);
            }
            text.appendLineBreak();

            text.append("medianError: ");//$NON-NLS-1$
            for (final FittingOutcome oc : res) {
              text.append('\t');
              text.append(oc.medianErrors.medianError);
            }
            text.appendLineBreak();
            text.appendLineBreak();
            text.appendLineBreak();

            text.append(
                "======================= MAX/CASE =======================");//$NON-NLS-1$
            text.appendLineBreak();

            text.append("case: ");//$NON-NLS-1$
            for (final FittingOutcome oc : res) {
              text.append('\t');
              text.append(oc.example.name);
            }
            text.appendLineBreak();

            text.append("quality: ");//$NON-NLS-1$
            for (final FittingOutcome oc : res) {
              text.append('\t');
              text.append(oc.maxErrors.quality);
            }
            text.appendLineBreak();

            text.append("rootMeanSquare: ");//$NON-NLS-1$
            for (final FittingOutcome oc : res) {
              text.append('\t');
              text.append(oc.maxErrors.rootMeanSquareError);
            }
            text.appendLineBreak();

            text.append("medianError: ");//$NON-NLS-1$
            for (final FittingOutcome oc : res) {
              text.append('\t');
              text.append(oc.maxErrors.medianError);
            }
            text.appendLineBreak();
            text.appendLineBreak();
            text.appendLineBreak();

            text.append(
                "======================= STDDEV/CASE =======================");//$NON-NLS-1$
            text.appendLineBreak();

            text.append("case: ");//$NON-NLS-1$
            for (final FittingOutcome oc : res) {
              text.append('\t');
              text.append(oc.example.name);
            }
            text.appendLineBreak();

            text.append("quality: ");//$NON-NLS-1$
            for (final FittingOutcome oc : res) {
              text.append('\t');
              text.append(oc.stddevErrors.quality);
            }
            text.appendLineBreak();

            text.append("rootMeanSquare: ");//$NON-NLS-1$
            for (final FittingOutcome oc : res) {
              text.append('\t');
              text.append(oc.stddevErrors.rootMeanSquareError);
            }
            text.appendLineBreak();

            text.append("medianError: ");//$NON-NLS-1$
            for (final FittingOutcome oc : res) {
              text.append('\t');
              text.append(oc.stddevErrors.medianError);
            }
            text.appendLineBreak();
          }
        }
      }

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
