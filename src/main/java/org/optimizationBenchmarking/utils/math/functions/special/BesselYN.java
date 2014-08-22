package org.optimizationBenchmarking.utils.math.functions.special;

import org.optimizationBenchmarking.utils.math.functions.BinaryFunction;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Absolute;

/**
 * <p>
 * Compute the Bessel function of the second kind, of order n of the
 * argument.
 * </p>
 */
public final class BesselYN extends BinaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance */
  public static final BesselYN INSTANCE = new BesselYN();

  /** instantiate */
  private BesselYN() {
    super();
  }

  /**
   * Compute the Bessel function of the second kind, of order n of the
   * argument.
   * 
   * @param n
   *          integer order
   * @param x
   *          a double value
   * @return The Bessel function of the second kind, of order n of the
   *         argument. TODO: CHECK
   */
  public static final double besselYN(final int n, final double x) {
    double by, bym, byp, tox, j;

    if (n == 0) {
      return BesselY0.INSTANCE.compute(x);
    }
    if (n == 1) {
      return BesselY1.INSTANCE.compute(x);
    }

    tox = (2.0d / x);
    by = BesselY1.INSTANCE.compute(x);
    bym = BesselY0.INSTANCE.compute(x);

    for (j = 1; j < n; j++) {
      byp = ((j * tox * by) - bym);
      bym = by;
      by = byp;
    }

    return by;
  }

  /** {@inheritDoc} */
  @Override
  public final double compute(final double x1, final double x2) {
    return BesselYN.besselYN(((int) (Math.round(x1))), x2);
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
