package org.optimizationBenchmarking.utils.math.functions.arithmetic;

import org.optimizationBenchmarking.utils.math.functions.BinaryFunction;

/**
 * The {@code "/"} function
 */
public final class Div extends BinaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance */
  public static final Div INSTANCE = new Div();

  /** instantiate */
  private Div() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final byte computeAsByte(final byte x0, final byte x1) {
    return ((byte) (x0 / x1));
  }

  /** {@inheritDoc} */
  @Override
  public final short computeAsShort(final short x0, final short x1) {
    return ((short) (x0 / x1));
  }

  /** {@inheritDoc} */
  @Override
  public final int computeAsInt(final int x0, final int x1) {
    return (x0 / x1);
  }

  /** {@inheritDoc} */
  @Override
  public final long computeAsLong(final long x0, final long x1) {
    return (x0 / x1);
  }

  /** {@inheritDoc} */
  @Override
  public final float computeAsFloat(final float x0, final float x1) {
    return (x0 / x1);
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x0, final double x1) {
    long a, b, res;

    if ((x0 >= Long.MIN_VALUE) && (x0 <= Long.MAX_VALUE)) {
      a = ((long) x0);
      if (a == x0) {

        if ((x1 >= Long.MIN_VALUE) && (x1 <= Long.MAX_VALUE)) {
          b = ((long) x1);
          if (b == x1) {

            if (b == 0L) {
              if (a < 0L) {
                return Double.NEGATIVE_INFINITY;
              }
              if (a > 0L) {
                return Double.POSITIVE_INFINITY;
              }
              return Double.NaN;
            }

            res = (a / b);
            if ((b * res) == a) {
              return res;
            }

          }
        }
      }
    }

    return (x0 / x1);
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final long x0, final long x1) {
    long res;

    if (x1 == 0L) {
      if (x0 < 0L) {
        return Double.NEGATIVE_INFINITY;
      }
      if (x0 > 0L) {
        return Double.POSITIVE_INFINITY;
      }
      return Double.NaN;
    }

    res = (x0 / x1);
    if ((x1 * res) == x0) {
      return res;
    }

    return (((double) x0) / ((double) x1));
  }

  /** {@inheritDoc} */
  @Override
  public final BinaryFunction invertFor(final int index) {
    if (index == 0) {
      return Mul.INSTANCE;
    }
    return Div.INSTANCE;
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
