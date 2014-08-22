package org.optimizationBenchmarking.utils.math.functions.analysis.differentiation;

import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;

/**
 * <p>
 * The first derivative of a function.
 * </p>
 */
public final class FirstUnaryDerivative extends BasicUnaryDerivative {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the epsilon */
  private static final double EPSILON = Math.sqrt(2.2e-16);

  /**
   * Create
   * 
   * @param f
   *          the function
   */
  public FirstUnaryDerivative(final UnaryFunction f) {
    super(f);
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return ('(' + this.m_f.toString() + ")'"); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final double compute(final double x1) {
    double h, th, xi;
    final UnaryFunction f;

    h = (FirstUnaryDerivative.EPSILON * x1);
    xi = (x1 + h);
    h = (xi - h);
    f = this.m_f;
    th = (2d * h);

    return (((((-f.compute(x1 + th)) + (8d * f.compute(x1 + h))) - (8d * f
        .compute(x1 - h))) + (f.compute(x1 - th))) / (12d * h));
  }
}
