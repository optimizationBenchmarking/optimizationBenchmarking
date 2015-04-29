package org.optimizationBenchmarking.utils.math.functions.power;

import org.apache.commons.math3.util.FastMath;
import org.optimizationBenchmarking.utils.math.functions.MathLibraries;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;

/**
 * The ln function
 */
public final class Lg extends UnaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the lookup table */
  static final long[] TABLE = { //
  1L,//
      10L,//
      100L,//
      1_000L,//
      10_000L,//
      100_000L,//
      1_000_000L,//
      10_000_000L,//
      100_000_000L,//
      1_000_000_000L,//
      10_000_000_000L,//
      100_000_000_000L,//
      1_000_000_000_000L,//
      10_000_000_000_000L,//
      100_000_000_000_000L,//
      1_000_000_000_000_000L,//
      10_000_000_000_000_000L,//
      100_000_000_000_000_000L,//
      1_000_000_000_000_000_000L,//
  };

  /** the globally shared instance */
  public static final Lg INSTANCE = new Lg();

  /** instantiate */
  private Lg() {
    super();
  }

  /**
   * lookup the value in the lookup table
   * 
   * @param l
   *          the long value
   * @return the result
   */
  private static final int __lookup(final long l) {
    int low, high, mid;
    long midVal;

    low = 0;
    high = (Lg.TABLE.length - 1);

    while (low <= high) {
      mid = ((low + high) >>> 1);
      midVal = Lg.TABLE[mid];

      if (midVal < l) {
        low = (mid + 1);
      } else {
        if (midVal > l) {
          high = (mid - 1);
        } else {
          return mid; // key found
        }
      }
    }
    return low;
  }

  /** {@inheritDoc} */
  @Override
  public final int computeAsInt(final int x1) {
    if (x1 <= 0) {
      if (x1 == 0) {
        return Integer.MIN_VALUE;
      }
      throw new ArithmeticException(//
          "Cannot compute log10 of " + x1); //$NON-NLS-1$
    }
    return Lg.__lookup(x1);
  }

  /** {@inheritDoc} */
  @Override
  public final long computeAsLong(final long x1) {
    if (x1 <= 0L) {
      if (x1 == 0L) {
        return Long.MIN_VALUE;
      }
      throw new ArithmeticException(//
          "Cannot compute log10 of " + x1); //$NON-NLS-1$
    }
    return Lg.__lookup(x1);
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x1) {
    final double x2;
    long l1;
    int res;

    if ((x1 >= 0d) && (x1 <= Long.MAX_VALUE)) {
      l1 = ((long) x1);

      if (l1 == x1) {
        if (l1 == 0L) {
          return Double.NEGATIVE_INFINITY;
        }
        res = Lg.__lookup(l1);
        if ((res < Lg.TABLE.length) && (Lg.TABLE[res] == l1)) {
          return res;
        }
      } else {

        if (x1 < 1d) {
          x2 = (1d / x1);

          if ((x2 >= 0d) && (x2 <= Long.MAX_VALUE)) {
            l1 = ((long) x2);

            if (l1 == x2) {
              if (l1 == 0L) {
                return Double.NEGATIVE_INFINITY;
              }
              res = Lg.__lookup(l1);
              if ((res < Lg.TABLE.length) && (Lg.TABLE[res] == l1)) {
                return -res;
              }
            }
          }
        }
      }
    }

    if (MathLibraries.HAS_FASTMATH) {
      return Lg.__fastMathLog10(x1);
    }
    return Math.log10(x1);
  }

  /**
   * Compute {@code log10} with
   * {@link org.apache.commons.math3.util.FastMath}
   * 
   * @param x1
   *          the parameter
   * @return the result
   */
  private static final double __fastMathLog10(final double x1) {
    return FastMath.log10(x1);
  }

  /** {@inheritDoc} */
  @Override
  public final Pow10 invertFor(final int index) {
    return Pow10.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "log10"; //$NON-NLS-1$
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
    return Lg.INSTANCE;
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
    return Lg.INSTANCE;
  }
}
