package org.optimizationBenchmarking.utils.math.functions.special;

import org.optimizationBenchmarking.utils.math.MathConstants;
import org.optimizationBenchmarking.utils.math.functions.TernaryFunction;

/**
 * <p>
 * The regularized Beta function.
 * </p>
 */
public final class BetaRegularized extends TernaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance */
  public static final BetaRegularized INSTANCE = new BetaRegularized();

  /** instantiate */
  private BetaRegularized() {
    super();
  }

  /**
   * Returns the regularized beta function I(x, a, b).
   * 
   * @param x
   *          the value.
   * @param a
   *          the a parameter.
   * @param b
   *          the b parameter.
   * @param epsilon
   *          When the absolute value of the nth item in the series is less
   *          than epsilon the approximation ceases to calculate further
   *          elements in the series.
   * @return the regularized beta function I(x, a, b)
   */
  public static final double regularizedBeta(final double x,
      final double a, final double b, final double epsilon) {
    return org.apache.commons.math3.special.Beta.regularizedBeta(x, a, b,
        epsilon);
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x1, final double x2,
      final double x3) {
    return BetaRegularized.regularizedBeta(x1, x2, x3, MathConstants.EPS);
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
    return BetaRegularized.INSTANCE;
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
    return BetaRegularized.INSTANCE;
  }
}
