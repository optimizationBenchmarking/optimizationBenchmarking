package org.optimizationBenchmarking.utils.math.fitting.spec;

import org.optimizationBenchmarking.utils.comparison.EComparison;

/** a fitting result */
public final class FittingResult implements Comparable<FittingResult> {

  /** the unary function */
  private final ParametricUnaryFunction m_function;

  /** the fitted parameters */
  final double[] m_parameters;

  /** the fitting quality */
  double m_quality;

  /**
   * create the optimization result
   *
   * @param function
   *          the function
   */
  FittingResult(final ParametricUnaryFunction function) {
    super();

    this.m_function = function;
    this.m_parameters = new double[function.getParameterCount()];
    this.m_quality = Double.POSITIVE_INFINITY;
  }

  /**
   * Obtain the fitting quality. The quality values are the better the
   * smaller they are. Quality values coming from the same
   * {@link FunctionFitter} are comparable.
   *
   * @return the fitting quality
   */
  public final double getQuality() {
    return this.m_quality;
  }

  /**
   * Obtain the fitted parameters
   *
   * @return the parameters obtained from the fitting
   */
  public final double[] getFittedParameters() {
    return this.m_parameters;
  }

  /**
   * Get the function whose parameters have been fitted
   *
   * @return the function whose parameters have been fitted
   */
  public final ParametricUnaryFunction getFittedFunction() {
    return this.m_function;
  }

  /** {@inheritDoc} */
  @Override
  public final int compareTo(final FittingResult o) {
    if (o == null) {
      return (-1);
    }
    return EComparison.compareDoubles(this.m_quality, o.m_quality);
  }
}
