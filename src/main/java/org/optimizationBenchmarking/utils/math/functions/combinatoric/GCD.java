package org.optimizationBenchmarking.utils.math.functions.combinatoric;

import org.optimizationBenchmarking.utils.math.functions.BinaryFunction;

/**
 * The gcd function
 */
public final class GCD extends BinaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance */
  public static final GCD INSTANCE = new GCD();

  /** instantiate */
  private GCD() {
    super();
  }

  /**
   * compute the greatest common divisor
   * 
   * @param a
   *          the n
   * @param b
   *          the k
   * @return the coefficient
   */
  @Override
  public final int computeAsInt(final int a, final int b) {
    int u, v, temp;

    u = a;
    v = b;

    while (v != 0) {
      temp = (u % v);
      u = v;
      v = temp;
    }

    return Math.abs(u);
  }

  /**
   * compute the greatest common divisor
   * 
   * @param a
   *          the n
   * @param b
   *          the k
   * @return the coefficient
   */
  @Override
  public final long computeAsLong(final long a, final long b) {
    long u, v, temp;

    u = a;
    v = b;

    while (v != 0L) {
      temp = (u % v);
      u = v;
      v = temp;
    }

    return Math.abs(u);
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x1, final double x2) {
    long a, b;
    double u, v, temp;

    u = x1;
    v = x2;

    for (;;) {
      if ((v >= Long.MIN_VALUE) && (v <= Long.MAX_VALUE)) {
        a = ((long) v);
        if (a == v) {
          if (a == 0L) {
            return Math.abs(u);
          }

          if ((u >= Long.MIN_VALUE) && (u <= Long.MAX_VALUE)) {
            b = ((long) u);
            if (b == u) {
              return this.computeAsLong(b, a);
            }
          }

        }
      } else {
        if (v != v) {
          return Double.NaN;
        }
      }

      if (v == 0d) {
        return Math.abs(u);
      }

      temp = (u % v);
      u = v;
      v = temp;
    }
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
    return GCD.INSTANCE;
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
    return GCD.INSTANCE;
  }
}
