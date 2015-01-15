package org.optimizationBenchmarking.utils.math.functions.stochastic;

import org.optimizationBenchmarking.utils.math.functions.BinaryFunction;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Absolute;

/**
 * The inverted cumulative distribution function (<a
 * href="http://en.wikipedia.org/wiki/Cumulative_distribution_function"
 * >CDF</a>) of the <a
 * href="http://en.wikipedia.org/wiki/Chi-squared_distribution">Chi-squared
 * (&chi;&sup2;) distribution</a>.
 */
public final class ChiSquareInvCDF extends BinaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance */
  public static final ChiSquareInvCDF INSTANCE = new ChiSquareInvCDF();

  /** instantiate */
  private ChiSquareInvCDF() {
    super();
  }

  /**
   * Calculates the chi-square-quantil for the probability d with dof
   * degrees of freedom.
   * 
   * @param d
   *          the probability (often called alpha)
   * @param dof
   *          the degrees of freedom
   * @return the quantile chi^2 (d, dof) TODO: CHECK
   */
  public static final double chiSquareQuantil(final double d, final int dof) {
    return ChiSquareCDF._distribution(dof).inverseCumulativeProbability(d);
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x1, final double x2) {
    return ChiSquareInvCDF.chiSquareQuantil(x1, ((int) (Math.round(x2))));
  }

  /** {@inheritDoc} */
  @Override
  public final ChiSquareCDF invertFor(final int index) {
    return ChiSquareCDF.INSTANCE;
  }

  // default, automatic serialization replacement and resolve routines for
  // singletons
  //
  /**
   * Write replace: the instance this method is invoked on will be replaced
   * with the singleton instance {@link #INSTANCE} for serialization, i.e.,
   * when the instance is written with
   * {@link java.io.ObjectOutputStream#writeObject(Object)}.
   * 
   * @return the replacement instance (always {@link #INSTANCE})
   */
  private final Object writeReplace() {
    return Absolute.INSTANCE;
  }

  /**
   * Read resolve: The instance this method is invoked on will be replaced
   * with the singleton instance {@link #INSTANCE} after serialization,
   * i.e., when the instance is read with
   * {@link java.io.ObjectInputStream#readObject()}.
   * 
   * @return the replacement instance (always {@link #INSTANCE})
   */
  private final Object readResolve() {
    return Absolute.INSTANCE;
  }
}
