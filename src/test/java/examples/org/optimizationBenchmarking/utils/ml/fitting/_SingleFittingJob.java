package examples.org.optimizationBenchmarking.utils.ml.fitting;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.ArithmeticMeanAggregate;
import org.optimizationBenchmarking.utils.math.statistics.aggregate.QuantileAggregate;
import org.optimizationBenchmarking.utils.ml.fitting.quality.WeightedRootMeanSquareError;
import org.optimizationBenchmarking.utils.ml.fitting.spec.IFittingResult;
import org.optimizationBenchmarking.utils.ml.fitting.spec.IFunctionFitter;
import org.optimizationBenchmarking.utils.ml.fitting.spec.ParametricUnaryFunction;
import org.optimizationBenchmarking.utils.text.textOutput.AbstractTextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** a fitting job */
final class _SingleFittingJob implements Callable<SingleFittingOutcome> {

  /** the fitting example data set */
  private final FittingExampleDataset m_example;

  /** the fitter to apply */
  private final IFunctionFitter m_fitter;

  /** the job index */
  private final int m_index;

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
   * @param index
   *          the index
   * @param root
   *          the root path
   * @param logger
   *          the logger
   */
  _SingleFittingJob(final Logger logger,
      final FittingExampleDataset example, final IFunctionFitter fitter,
      final int index, final Path root) {
    super();
    this.m_example = example;
    this.m_fitter = fitter;
    this.m_index = index;
    this.m_root = root;
    this.m_logger = logger;
  }

  /** {@inheritDoc} */
  @Override
  public final SingleFittingOutcome call() {
    final IFittingResult result;
    final ITextOutput text;
    final String example, fitter;
    final QuantileAggregate median;
    final ArithmeticMeanAggregate mean;
    final IMatrix data;
    final ParametricUnaryFunction model;
    final double[] parameters;
    final int m;
    final SingleFittingOutcome outcome;
    final Errors errors;
    long runtime;
    int i;
    double x, y, z, error;
    Path dest;

    example = this.m_example.name;
    fitter = this.m_fitter.getClass().getSimpleName();

    if ((this.m_logger != null)
        && (this.m_logger.isLoggable(Level.FINEST))) {
      this.m_logger.finest("Beginning run " + this.m_index //$NON-NLS-1$
          + " of fitter " + fitter//$NON-NLS-1$
          + " on problem " + example);//$NON-NLS-1$
    }

    runtime = System.nanoTime();
    result = this.m_fitter.use().setFunctionToFit(this.m_example.model)
        .setPoints(this.m_example.data)
        .setQualityMeasure(//
            new WeightedRootMeanSquareError(this.m_example.data))
        .create().call();
    runtime = (System.nanoTime() - runtime);

    dest = PathUtils.createPathInside(this.m_root,
        ((((fitter + '_') + example) + '_') + this.m_index) + //
            ".results"); //$NON-NLS-1$
    try (final OutputStream os = PathUtils.openOutputStream(dest)) {
      try (final OutputStreamWriter osw = new OutputStreamWriter(os)) {
        try (final BufferedWriter bw = new BufferedWriter(osw)) {
          text = AbstractTextOutput.wrap(bw);

          text.append("# dataset:\t");//$NON-NLS-1$
          text.append(example);
          text.appendLineBreak();

          text.append("# model:\t");//$NON-NLS-1$
          text.append(this.m_example.model.getClass().getSimpleName());
          text.appendLineBreak();

          text.append("# fitter:\t");//$NON-NLS-1$
          text.append(fitter);
          text.appendLineBreak();

          text.append("# quality:\t");//$NON-NLS-1$
          text.append(result.getQuality());
          text.appendLineBreak();

          text.append("# fitted model:\t");//$NON-NLS-1$
          text.append(result.toString());
          text.appendLineBreak();

          parameters = result.getFittedParametersRef();
          text.append("# model parameters:");//$NON-NLS-1$
          for (final double d : parameters) {
            text.append('\t');
            text.append(d);
          }
          text.appendLineBreak();

          text.append("# runtime [ns]:\t");//$NON-NLS-1$
          text.append(runtime);
          text.appendLineBreak();

          text.append('#');
          text.appendLineBreak();
          text.append('#');
          text.appendLineBreak();

          text.append("# X\texpectedY\tmodelY");//$NON-NLS-1$
          text.appendLineBreak();

          data = this.m_example.data;
          m = data.m();
          median = new QuantileAggregate(0.5d);
          mean = new ArithmeticMeanAggregate();
          model = this.m_example.model;
          for (i = 0; i < m; i++) {
            x = data.getDouble(i, 0);
            y = data.getDouble(i, 1);
            z = model.value(x, parameters);
            text.append(x);
            text.append('\t');
            text.append(y);
            text.append('\t');
            text.append(z);
            text.appendLineBreak();

            error = Math.abs(y - z);
            median.append(error);
            mean.append(error * error);
          }

          text.append('#');
          text.appendLineBreak();

          errors = new Errors(result.getQuality(),
              Math.sqrt(mean.doubleValue()), median.doubleValue(),
              runtime);
          outcome = new SingleFittingOutcome(parameters, dest, errors);

          text.append("# rootMeanSquareError\t");//$NON-NLS-1$
          text.append(errors.rootMeanSquareError);
          text.appendLineBreak();

          text.append("# median error\t");//$NON-NLS-1$
          text.append(errors.medianError);
          text.appendLineBreak();
        }
      }
    } catch (final IOException ignore) {
      throw new RuntimeException(ignore);
    }

    if ((this.m_logger != null)
        && (this.m_logger.isLoggable(Level.FINEST))) {
      this.m_logger.finest("Finished run " + this.m_index //$NON-NLS-1$
          + " of fitter " + fitter//$NON-NLS-1$
          + " on problem " + example);//$NON-NLS-1$
    }

    return outcome;
  }
}
