package org.optimizationBenchmarking.utils.ml.fitting.impl.abstr;

import org.optimizationBenchmarking.utils.math.MathUtils;
import org.optimizationBenchmarking.utils.math.matrix.IMatrix;
import org.optimizationBenchmarking.utils.ml.fitting.quality.FittingQualityMeasure;
import org.optimizationBenchmarking.utils.ml.fitting.spec.IFittingJob;
import org.optimizationBenchmarking.utils.ml.fitting.spec.IFittingQualityMeasure;
import org.optimizationBenchmarking.utils.ml.fitting.spec.ParametricUnaryFunction;
import org.optimizationBenchmarking.utils.tools.impl.abstr.ToolJob;

/** The fitting job */
public class FittingJob extends ToolJob implements IFittingJob {

  /** a function to fit */
  protected final ParametricUnaryFunction m_function;

  /** the fitting result */
  protected final FittingResult m_result;

  /** the matrix */
  protected final IMatrix m_data;

  /** the fitting quality measure */
  protected final IFittingQualityMeasure m_measure;

  /**
   * create the fitting job
   *
   * @param builder
   *          the fitting job builder
   */
  protected FittingJob(final FittingJobBuilder builder) {
    super(builder);

    FittingJobBuilder.validateFunction(//
        this.m_function = builder.getFunction());
    FittingQualityMeasure.validateData(//
        this.m_data = builder.getPoints());
    FittingJobBuilder.validateMeasure(//
        this.m_measure = builder.getQualityMeasure());

    this.m_result = new FittingResult(this.m_function);
  }

  /** Perform the fitting */
  protected void fit() {
    //
  }

  /**
   * Compute the quality of a given fitting.
   *
   * @param params
   *          the fitting, i.e., the parameters of the function to be
   *          fitted
   * @return the fitting quality, i.e., the weighted RMS sqrt(
   *         ((error*weight)^2) / n )
   */
  protected final double evaluate(final double[] params) {
    final double res;
    res = this.m_measure.evaluate(this.m_function, params);
    this.register(res, params);
    return res;
  }

  /**
   * Register a solution
   *
   * @param quality
   *          the solution quality
   * @param params
   *          the parameters
   */
  public final void register(final double quality, final double[] params) {
    if (quality < this.m_result.quality) {
      System.arraycopy(params, 0, this.m_result.solution, 0,
          this.m_result.solution.length);
      this.m_result.quality = quality;
    }
  }

  /** {@inheritDoc} */
  @Override
  public final FittingResult call() throws IllegalArgumentException {
    Throwable error;
    String message;

    error = null;
    try {
      this.fit();

      if (MathUtils.isFinite(this.m_result.quality)) {
        return this.m_result;
      }
    } catch (final IllegalArgumentException iae) {
      throw iae;
    } catch (final Throwable cause) {
      error = cause;
    }

    message = ((("Could not fit the function " + this.m_function) + //$NON-NLS-1$
        " with method ") //$NON-NLS-1$
        + this + '.');
    if (error != null) {
      throw new IllegalArgumentException(message, error);
    }
    throw new IllegalArgumentException(message);
  }
}
