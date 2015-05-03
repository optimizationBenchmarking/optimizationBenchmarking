package org.optimizationBenchmarking.utils.math.functions.power;

import org.apache.commons.math3.util.FastMath;
import org.optimizationBenchmarking.utils.math.NumericalTypes;
import org.optimizationBenchmarking.utils.math.functions.MathLibraries;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;

/** The sqrt function */
public final class Sqrt extends UnaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance */
  public static final Sqrt INSTANCE = new Sqrt();

  /** instantiate */
  private Sqrt() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final long computeAsLong(final long x0) {
    if (x0 < 0L) {
      throw new ArithmeticException(//
          "Cannot take square root of " + x0); //$NON-NLS-1$
    }
    return Sqrt.__isqrt(x0);
  }

  /**
   * An integer-based algorithm based on
   * http://en.wikipedia.org/wiki/Methods_of_computing_square_roots
   * 
   * @param num
   *          the number
   * @return the result
   */
  private static final long __isqrt(long num) {
    long res, bit;

    res = 0L;
    bit = (1L << 62L);

    // "bit" starts at the highest power of four <= the argument.
    while (bit > num) {
      bit >>= 2L;
    }

    while (bit != 0L) {
      if (num >= (res + bit)) {
        num -= (res + bit);
        res = ((res >> 1L) + bit);
      } else {
        res >>= 1L;
      }
      bit >>= 2;
    }
    return res;
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x1) {
    final long l0, res;

    if (NumericalTypes.isLong(x1)) {
      l0 = ((long) x1);
      if (l0 > 0L) {
        res = Sqrt.__isqrt(l0);
        if ((res * res) == l0) {
          return res;
        }
      } else {
        if (l0 == 0L) {
          return 0d;
        }
      }
    }

    if (MathLibraries.HAS_FASTMATH) {
      return Sqrt.__fastMathSqrt(x1);
    }
    return Math.sqrt(x1);
  }

  /**
   * Compute {@code sqrt} with
   * {@link org.apache.commons.math3.util.FastMath}
   * 
   * @param x1
   *          the parameter
   * @return the result
   */
  private static final double __fastMathSqrt(final double x1) {
    return FastMath.sqrt(x1);
  }

  /** {@inheritDoc} */
  @Override
  public final Sqr invertFor(final int index) {
    return Sqr.INSTANCE;
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
    return Sqrt.INSTANCE;
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
    return Sqrt.INSTANCE;
  }
}
