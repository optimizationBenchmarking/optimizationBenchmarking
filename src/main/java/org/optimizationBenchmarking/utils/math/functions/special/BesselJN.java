package org.optimizationBenchmarking.utils.math.functions.special;

import org.optimizationBenchmarking.utils.math.functions.BinaryFunction;

/**
 * <p>
 * The Bessel function jn.
 * </p>
 */
public final class BesselJN extends BinaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance */
  public static final BesselJN INSTANCE = new BesselJN();

  /** The bessel acc value. */
  private static final double BESSEL_ACC = 40.0d;

  /** The bessel big value. */
  private static final double BESSEL_BIGNO = 1.0e+10d;

  /** The bessek small value. */
  private static final double BESSEL_BIGNI = 1.0e-10d;

  /** instantiate */
  private BesselJN() {
    super();
  }

  /**
   * Compute the Bessel function of order n of the argument.
   *
   * @param n
   *          integer order
   * @param x
   *          a double value
   * @return The Bessel function of order n of the argument. TODO: CHECK
   */
  public static final double besslJN(final int n, final double x) {
    int j, m;
    double ax, bj, bjm, bjp, sum, tox, ans;
    boolean jsum;

    if (n == 0) {
      return BesselJ0.INSTANCE.computeAsDouble(x);
    }
    if (n == 1) {
      return BesselJ1.INSTANCE.computeAsDouble(x);
    }

    ax = Math.abs(x);

    if (ax <= 0.0d) {
      return 0.0d;
    }

    tox = (2.0 / ax);

    if (ax > n) {
      bjm = BesselJ0.INSTANCE.computeAsDouble(ax);
      bj = BesselJ1.INSTANCE.computeAsDouble(ax);

      for (j = 1; j < n; j++) {
        bjp = (j * tox * bj) - bjm;
        bjm = bj;
        bj = bjp;
      }
      ans = bj;
    } else {
      m = (((n + ((int) (Math.sqrt(BesselJN.BESSEL_ACC * n)))) >> 1)) << 1;
      jsum = false;
      bjp = ans = sum = 0.0d;
      bj = 1.0d;

      for (j = m; j > 0; j--) {
        bjm = (j * tox * bj) - bjp;
        bjp = bj;
        bj = bjm;

        if (Math.abs(bj) > BesselJN.BESSEL_BIGNO) {
          bj *= BesselJN.BESSEL_BIGNI;
          bjp *= BesselJN.BESSEL_BIGNI;
          ans *= BesselJN.BESSEL_BIGNI;
          sum *= BesselJN.BESSEL_BIGNI;
        }

        if (jsum) {
          sum += bj;
        }

        jsum = !jsum;
        if (j == n) {
          ans = bjp;
        }
      }

      sum = ((sum + sum) - bj);
      ans /= sum;
    }
    return (((x < 0.0d) && ((n % 2) == 1)) ? (-ans) : ans);
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x1, final double x2) {
    return BesselJN.besslJN(((int) (Math.round(x1))), x2);
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
    return BesselJN.INSTANCE;
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
    return BesselJN.INSTANCE;
  }
}
