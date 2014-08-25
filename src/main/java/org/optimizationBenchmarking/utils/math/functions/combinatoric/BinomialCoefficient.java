package org.optimizationBenchmarking.utils.math.functions.combinatoric;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.math.functions.BinaryFunction;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Absolute;
import org.optimizationBenchmarking.utils.math.functions.special.Beta;
import org.optimizationBenchmarking.utils.math.functions.special.GammaLn;

/**
 * <p>
 * The binomial coefficient function.
 * </p>
 */
public final class BinomialCoefficient extends BinaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance */
  public static final BinomialCoefficient INSTANCE = new BinomialCoefficient();

  /** instantiate */
  private BinomialCoefficient() {
    super();
  }

  /**
   * compute the binomial coefficient
   *
   * @param n
   *          the n
   * @param k
   *          the k
   * @return the coefficient
   */
  @Override
  public final long compute(final long n, final long k) {
    if ((k < 0L) || (k > n) || (n < 0L)) {
      return 0L;
    }

    if ((k <= 0L) || (k >= n)) {
      return 1L;
    }

    if ((k <= 1L) || (k >= (n - 1L))) {
      return n;
    }

    return BinomialCoefficient.internalBinomial(n, k);
  }

  /**
   * compute the binomial coefficient
   *
   * @param n
   *          the n
   * @param k
   *          the k
   * @return the coefficient, or {@code -1l} on overflow
   */
  private static final long internalBinomial(final long n, final long k) {
    long r, d, v, rn, g;
    final long kk;
    final int ni, ki;

    if (n < Factorial.FACTORIALS.length) {
      ni = ((int) n);
      ki = ((int) k);
      return ((Factorial.FACTORIALS[ni] / Factorial.FACTORIALS[ki]) / //
      Factorial.FACTORIALS[ni - ki]);
    }

    g = (n >>> 1);
    if (k > g) {
      kk = (n - k);
    } else {
      kk = k;
    }

    r = 1L;
    v = n;
    for (d = 1L; d <= kk; d++) {
      rn = ((r * v) / d);

      // overflow handling
      if (rn <= r) {
        g = GCD.INSTANCE.compute(r, d);
        rn = (((r / g) * v) / (d / g));
        if (rn <= r) {
          g = GCD.INSTANCE.compute(v, d);
          rn = ((r * (v / g)) / (d / g));
          if (rn <= r) {
            return (-1L);
          }
        }
      }
      v--;
      r = rn;
    }

    return r;
  }

  /** {@inheritDoc} */
  @Override
  public final double compute(final double x1, final double x2) {
    final double a, b, c, nmk, ra, rb;
    final long ln, lk, lr, nmkl;

    if ((x2 < 0d) || (x2 > x1) || (x1 < 0d)) {
      return 0d;
    }

    if ((x2 <= 0d) || (x2 >= x1)) {
      return 1d;
    }

    if ((x2 <= 1d) || (x2 >= (x1 - 1d))) {
      return x1;
    }

    if ((x2 < Long.MAX_VALUE) && (x1 < Long.MAX_VALUE)) {
      lk = Math.round(x2);
      ln = Math.round(x1);
      lr = BinomialCoefficient.internalBinomial(ln, lk);
      if (lr >= 0L) {
        return lr;
      }

      nmkl = (ln - lk);
      a = (x2 * (Beta.INSTANCE.compute(x2, nmkl + 1L)));
      b = (nmkl * (Beta.INSTANCE.compute(x2 + 1L, nmkl)));
    } else {
      nmk = (x1 - x2);
      a = (x2 * (Beta.INSTANCE.compute(x2, nmk + 1d)));
      b = (nmk * (Beta.INSTANCE.compute(x2 + 1d, nmk)));
    }

    ra = (1d / a);
    rb = (1d / b);
    if (Double.isInfinite(ra) || Double.isNaN(ra)) {
      if (Double.isInfinite(rb) || Double.isNaN(rb)) {
        return Math.exp(GammaLn.INSTANCE.compute(x1 + 1d)
            - GammaLn.INSTANCE.compute(x2 + 1d)
            - GammaLn.INSTANCE.compute((x1 - x2) + 1d));
      }

      return rb;
    }
    if (Double.isInfinite(rb) || Double.isNaN(rb)) {
      return ra;
    }

    if (EComparison.EQUAL.compare(ra, rb)) {
      return ra;
    }

    c = ((0.5d / a) + (0.5d / b));
    if (Double.isInfinite(c) || Double.isNaN(c)) {
      return ra;
    }
    return c;
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
