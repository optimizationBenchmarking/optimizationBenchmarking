package org.optimizationBenchmarking.utils.math.functions.power;

import org.apache.commons.math3.util.FastMath;
import org.optimizationBenchmarking.utils.math.functions.BinaryFunction;
import org.optimizationBenchmarking.utils.math.functions.MathLibraries;

/**
 * The pow function: raise {@code x1} to the power {@code x2}
 */
public final class Pow extends BinaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance */
  public static final Pow INSTANCE = new Pow();

  /** instantiate */
  private Pow() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final long computeAsLong(final long x1, final long x2) {
    if (x2 <= 1L) {
      if ((x2 <= 0L) && (x1 == 0L)) {
        throw new java.lang.ArithmeticException("0 to a power <= 0"); //$NON-NLS-1$
      }
      if (x2 < 0L) {
        return 0L;
      }
      if (x2 == 1L) {
        return x1;
      }
      return 1L;
    }

    if (x1 == 0L) {
      return 0L;
    }
    if (x1 == 1L) {
      return 1L;
    }

    return Pow.__long(x1, x2);
  }

  /**
   * Compute the result as long
   *
   * @param x1
   *          the first parameter
   * @param x2
   *          the second parameter
   * @return the result
   */
  private static final long __long(final long x1, final long x2) {
    long result, exp, base, nextRes;

    if (x1 == 2L) {
      if (x2 >= 63) {
        return Long.MAX_VALUE;
      }
      return (1L << x2);
    }

    result = 1L;
    exp = x2;
    base = x1;

    for (;;) {
      if ((exp & 1L) != 0L) {
        nextRes = (result * base);
        if ((nextRes / base) != result) {
          if ((x1 < 0L) && ((x2 & 1L) != 0L)) {
            return (java.lang.Long.MIN_VALUE);
          }
          return (java.lang.Long.MAX_VALUE);
        }
        result = nextRes;
      }

      if ((exp >>>= 1L) <= 0L) {
        return result;
      }
      if ((base < (-Sqr.SQRT_LONG_MAX_VALUE))
          || (base > Sqr.SQRT_LONG_MAX_VALUE)) {
        if ((x1 < 0L) && ((x2 & 1L) != 0L)) {
          return (java.lang.Long.MIN_VALUE);
        }
        return (java.lang.Long.MAX_VALUE);
      }
      base *= base;
    }
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x1, final double x2) {
    final long l1, l2, res;

    // try to use integer arithmetic where possible

    if ((x2 > Long.MIN_VALUE) && (x2 <= Long.MAX_VALUE)) {
      l2 = ((long) x2);
      if ((l2 == x2) && (l2 > Long.MIN_VALUE)) {

        if ((x1 >= Long.MIN_VALUE) && (x1 <= Long.MAX_VALUE)) {

          if (l2 == 0L) {
            return 1d;
          }

          l1 = ((long) x1);
          if (l1 == x1) {

            if (l1 <= 0L) {
              if (l1 == 0L) {
                if (l2 <= 0L) {
                  return Double.NaN;
                }
                return 0d;
              }
              if (l1 == (-1L)) {
                return (((l2 & 1L) == 0L) ? 1d : (-1d));
              }
            } else {
              if (l1 == 1L) {
                return 1d;
              }
            }

            if (l2 < 0L) {
              res = Pow.__long(l1, -l2);
            } else {
              res = Pow.__long(l1, l2);
            }

            if ((res > Long.MIN_VALUE) && (res < Long.MAX_VALUE)) {
              if (l2 < 0L) {
                return (1d / res);
              }
              return res;
            }
          }
        }

        if ((l2 >= Integer.MIN_VALUE) && (l2 <= Integer.MAX_VALUE)
            && MathLibraries.HAS_FASTMATH) {
          return Pow.__fastMathPow(x1, ((int) l2));
        }
      }
    }

    // resort to double arithmetic
    if (MathLibraries.HAS_FASTMATH) {
      return Pow.__fastMathPow(x1, x2);
    }
    return (Math.pow(x1, x2));
  }

  /**
   * Compute {@code pow} with
   * {@link org.apache.commons.math3.util.FastMath}
   *
   * @param x1
   *          the base
   * @param x2
   *          the value
   * @return the result
   */
  private static final double __fastMathPow(final double x1,
      final double x2) {
    return FastMath.pow(x1, x2);
  }

  /**
   * Compute {@code pow} with
   * {@link org.apache.commons.math3.util.FastMath} where the second
   * argument is an integer value
   *
   * @param x1
   *          the base
   * @param x2
   *          the value
   * @return the result
   */
  private static final double __fastMathPow(final double x1, final int x2) {
    final double res;

    res = FastMath.pow(x1, x2);
    if ((res > Double.NEGATIVE_INFINITY)
        && (res < Double.POSITIVE_INFINITY)) {
      return res; // only if finite and not NaN
    }
    return FastMath.pow(x1, ((double) x2));
  }

  /** {@inheritDoc} */
  @Override
  public final Log invertFor(final int index) {
    if (index == 1) {
      return Log.INSTANCE;
    }
    return null;
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
    return Pow.INSTANCE;
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
    return Pow.INSTANCE;
  }
}
