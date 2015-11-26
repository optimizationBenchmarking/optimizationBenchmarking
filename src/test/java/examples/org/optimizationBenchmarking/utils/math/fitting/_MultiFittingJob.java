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

import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.math.fitting.spec.FunctionFitter;
import org.optimizationBenchmarking.utils.parallel.Execute;
import org.optimizationBenchmarking.utils.text.textOutput.AbstractTextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** a fitting job */
final class _MultiFittingJob implements Callable<FittingOutcome> {

  /** the number of fitting runs */
  private static final int NUM_RUNS = 10;

  /** the fitting example data set */
  private final FittingExampleDataset m_example;

  /** the fitter to apply */
  private final FunctionFitter m_fitter;

  /** the root path */
  private final Path m_root;
  /** the logger */
  private final Logger m_logger;

  /**
   * create
   *
   * @param example
   *          the example data set
   * @param fitter
   *          the fitter
   * @param root
   *          the root path
   * @param logger
   *          the logger
   */
  _MultiFittingJob(final Logger logger,
      final FittingExampleDataset example, final FunctionFitter fitter,
      final Path root) {
    super();
    this.m_example = example;
    this.m_fitter = fitter;
    this.m_root = root;
    this.m_logger = logger;
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public final FittingOutcome call() {
    final ITextOutput text;
    final String example, fitter;
    final Future<SingleFittingOutcome>[] jobs;
    final SingleFittingOutcome[] res;
    final FittingOutcome outcome;
    int i;
    String name;
    Path destFolder, dest;

    example = this.m_example.name;
    fitter = this.m_fitter.getClass().getSimpleName();

    if ((this.m_logger != null)
        && (this.m_logger.isLoggable(Level.FINER))) {
      this.m_logger.finer("Beginning to apply fitter " + fitter//$NON-NLS-1$
          + " to problem " + example);//$NON-NLS-1$
    }

    try {
      name = ((fitter + '_') + example);
      destFolder = PathUtils.createPathInside(this.m_root, name);
      Files.createDirectories(destFolder);

      jobs = new Future[_MultiFittingJob.NUM_RUNS];
      for (i = jobs.length; (--i) >= 0;) {
        jobs[i] = Execute.parallel(new _SingleFittingJob(this.m_logger,
            this.m_example, this.m_fitter, (i + 1), destFolder));
      }
      res = new SingleFittingOutcome[jobs.length];
      Execute.join(jobs, res, 0, false);
      outcome = new FittingOutcome(this.m_example, res, destFolder);

      dest = PathUtils.createPathInside(destFolder, name + "_summary.txt"); //$NON-NLS-1$

      try (final OutputStream os = PathUtils.openOutputStream(dest)) {
        try (final OutputStreamWriter osw = new OutputStreamWriter(os)) {
          try (final BufferedWriter bw = new BufferedWriter(osw)) {
            text = AbstractTextOutput.wrap(bw);

            text.append("dataset:\t");//$NON-NLS-1$
            text.append(example);
            text.appendLineBreak();

            text.append("model:\t");//$NON-NLS-1$
            text.append(this.m_example.model.getClass().getSimpleName());
            text.appendLineBreak();

            text.append("fitter:\t");//$NON-NLS-1$
            text.append(fitter);
            text.appendLineBreak();
            text.appendLineBreak();
            text.appendLineBreak();

            text.append(
                "===== parameter:\tmin\tmedian\tmax\t/\tstddev ===== ");//$NON-NLS-1$
            for (i = 0; i < outcome.min.length; i++) {
              text.appendLineBreak();
              text.append(i);
              text.append(':');
              text.append('\t');
              text.append(outcome.min[i]);
              text.append('\t');
              text.append(outcome.median[i]);
              text.append('\t');
              text.append(outcome.max[i]);
              text.append('\t');
              text.append('/');
              text.append('\t');
              text.append(outcome.stddev[i]);
            }
            text.appendLineBreak();
            text.appendLineBreak();
            text.appendLineBreak();

            text.append(
                "===== error:\tmin\tmedian\tmax\t/\tstddev ===== ");//$NON-NLS-1$
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

            text.append("weighted rootMeanSquareError:\t");//$NON-NLS-1$
            text.append(outcome.minErrors.weightedRootMeanSquareError);
            text.append('\t');
            text.append(outcome.medianErrors.weightedRootMeanSquareError);
            text.append('\t');
            text.append(outcome.maxErrors.weightedRootMeanSquareError);
            text.append('\t');
            text.append('/');
            text.append('\t');
            text.append(outcome.stddevErrors.weightedRootMeanSquareError);
            text.appendLineBreak();

            text.append("weighted median error:\t");//$NON-NLS-1$
            text.append(outcome.minErrors.weightedMedianError);
            text.append('\t');
            text.append(outcome.medianErrors.weightedMedianError);
            text.append('\t');
            text.append(outcome.maxErrors.weightedMedianError);
            text.append('\t');
            text.append('/');
            text.append('\t');
            text.append(outcome.stddevErrors.weightedMedianError);
          }
        }
      }
    } catch (final IOException ignore) {
      throw new RuntimeException(ignore);
    }

    if ((this.m_logger != null)
        && (this.m_logger.isLoggable(Level.FINER))) {
      this.m_logger.finer("Beginning to applying fitter " + fitter//$NON-NLS-1$
          + " to problem " + example);//$NON-NLS-1$
    }

    return outcome;
  }
}
