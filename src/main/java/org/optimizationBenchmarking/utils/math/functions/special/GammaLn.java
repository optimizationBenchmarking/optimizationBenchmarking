package org.optimizationBenchmarking.utils.math.functions.special;

import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;

/**
 * <p>
 * The logarithm of the gamma function.
 * </p>
 */
public final class GammaLn extends UnaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance */
  public static final GammaLn INSTANCE = new GammaLn();

  /** instantiate */
  private GammaLn() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x1) {
    return org.apache.commons.math3.special.Gamma.logGamma(x1);
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
    return GammaLn.INSTANCE;
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
    return GammaLn.INSTANCE;
  }
}
