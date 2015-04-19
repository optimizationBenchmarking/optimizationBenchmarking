package org.optimizationBenchmarking.utils.math.functions.power;

import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;

/**
 * The cube function
 */
public final class Cube extends UnaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the cubic root of {@value java.lang.Long#MAX_VALUE}: {@value} */
  private static final long CBRT_LONG_MAX_VALUE = 2097151L;

  /** the globally shared instance */
  public static final Cube INSTANCE = new Cube();

  /** instantiate */
  private Cube() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final byte computeAsByte(final byte x1) {
    int res;

    res = x1;
    res = (res * res * res);
    if (res >= Byte.MAX_VALUE) {
      return Byte.MAX_VALUE;
    }
    if (res <= Byte.MIN_VALUE) {
      return Byte.MIN_VALUE;
    }
    return ((byte) res);
  }

  /** {@inheritDoc} */
  @Override
  public final short computeAsShort(final short x1) {
    long res;

    res = x1;
    res = (res * res * res);
    if (res >= Short.MAX_VALUE) {
      return Short.MAX_VALUE;
    }
    if (res <= Short.MIN_VALUE) {
      return Short.MIN_VALUE;
    }
    return ((short) res);
  }

  /** {@inheritDoc} */
  @Override
  public final int computeAsInt(final int x1) {
    long res;

    if (x1 == 0) {
      return 0;
    }
    if (x1 < 0) {
      if (x1 >= (-Cube.CBRT_LONG_MAX_VALUE)) {
        res = x1;
        res = (res * res * res);
        if (res > Integer.MIN_VALUE) {
          return ((int) res);
        }
      }
      return Integer.MIN_VALUE;
    }

    if (x1 <= Cube.CBRT_LONG_MAX_VALUE) {
      res = x1;
      res = (res * res * res);
      if (res < Integer.MAX_VALUE) {
        return ((int) res);
      }
    }
    return Integer.MAX_VALUE;
  }

  /** {@inheritDoc} */
  @Override
  public final long computeAsLong(final long x1) {
    if (x1 == 0L) {
      return 0L;
    }
    if (x1 < 0L) {
      if (x1 < (-Cube.CBRT_LONG_MAX_VALUE)) {
        return Long.MIN_VALUE;
      }
    } else {
      if (x1 > Cube.CBRT_LONG_MAX_VALUE) {
        return Long.MAX_VALUE;
      }
    }

    return (x1 * x1 * x1);
  }

  /** {@inheritDoc} */
  @Override
  public final float computeAsFloat(final float x1) {
    final long l1;

    if ((x1 >= (-Cube.CBRT_LONG_MAX_VALUE))
        && (x1 <= Cube.CBRT_LONG_MAX_VALUE)) {
      l1 = ((long) x1);
      if (l1 == x1) {
        return (l1 * l1 * l1);
      }
    }

    return (x1 * x1 * x1);
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x1) {
    final long l1;

    if ((x1 >= (-Cube.CBRT_LONG_MAX_VALUE))
        && (x1 <= Cube.CBRT_LONG_MAX_VALUE)) {
      l1 = ((long) x1);
      if (l1 == x1) {
        return (l1 * l1 * l1);
      }
    }

    return (x1 * x1 * x1);
  }

  /** {@inheritDoc} */
  @Override
  public final Cbrt invertFor(final int index) {
    return Cbrt.INSTANCE;
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
    return Cube.INSTANCE;
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
    return Cube.INSTANCE;
  }
}
