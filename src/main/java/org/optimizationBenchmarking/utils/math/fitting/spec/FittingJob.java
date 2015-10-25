package org.optimizationBenchmarking.utils.math.fitting.spec;

import java.util.concurrent.Callable;

import org.optimizationBenchmarking.utils.math.MathUtils;
import org.optimizationBenchmarking.utils.tools.impl.abstr.ToolJob;

/** The fitting job */
public class FittingJob extends ToolJob implements Callable<FittingResult> {

  /** a function to fit */
  protected final ParametricUnaryFunction m_function;

  /** the fitting result */
  protected final FittingResult m_result;

  /**
   * create the fitting job
   *
   * @param builder
   *          the fitting job builder
   */
  protected FittingJob(final FittingJobBuilder builder) {
    super(builder);

    FittingJobBuilder._validateFunction(//
        this.m_function = builder.getFunction());
    this.m_result = new FittingResult(this.m_function);
  }

  /** Perform the fitting */
  protected void fit() {
    //
  }

  /**
   * Register a solution
   *
   * @param solution
   *          the solution
   * @param quality
   *          the quality
   */
  protected void register(final double[] solution, final double quality) {
    if (quality < this.m_result.m_quality) {
      System.arraycopy(solution, 0, this.m_result.m_parameters, 0,
          this.m_result.m_parameters.length);
      this.m_result.m_quality = quality;
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

      if (MathUtils.isFinite(this.m_result.m_quality)) {
        return this.m_result;
      }
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
