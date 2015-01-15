package org.optimizationBenchmarking.utils.math.functions.special;

import org.optimizationBenchmarking.utils.math.MathConstants;
import org.optimizationBenchmarking.utils.math.functions.BinaryFunction;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Absolute;

/**
 * <p>
 * The the regularized Q gamma function.
 * </p>
 */
public final class GammaRegularizedQ extends BinaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance */
  public static final GammaRegularizedQ INSTANCE = new GammaRegularizedQ();

  /** instantiate */
  private GammaRegularizedQ() {
    super();
  }

  /**
   * The regularized gamma Q.
   * 
   * @param a
   *          the a parameter.
   * @param x
   *          the value.
   * @param epsilon
   *          When the absolute value of the nth item in the series is less
   *          than epsilon the approximation ceases to calculate further
   *          elements in the series.
   * @return the regularized gamma function P(a, x)
   */
  public static final double regularizedGammaQ(final double a,
      final double x, final double epsilon) {
    return org.apache.commons.math3.special.Gamma.regularizedGammaQ(a, x,
        epsilon, Integer.MAX_VALUE);
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x1, final double x2) {
    return GammaRegularizedQ.regularizedGammaQ(x1, x2, MathConstants.EPS);
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
