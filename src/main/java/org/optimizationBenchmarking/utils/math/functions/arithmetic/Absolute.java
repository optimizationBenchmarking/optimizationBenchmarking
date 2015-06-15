package org.optimizationBenchmarking.utils.math.functions.arithmetic;

import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;

/**
 * The {@code abs} function returns {@code |x|}.
 */
public final class Absolute extends UnaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the precedence priority of the unary absolute operator used as prefix */
  public static final int PRECEDENCE_PRIORITY = //
  (int) ((((long) (Integer.MIN_VALUE)) + ((long) (Add.PRECEDENCE_PRIORITY))) / 2);

  /** the globally shared instance */
  public static final Absolute INSTANCE = new Absolute();

  /** instantiate */
  private Absolute() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final int getPrecedencePriority() {
    return Absolute.PRECEDENCE_PRIORITY;
  }

  /** {@inheritDoc} */
  @Override
  public final byte computeAsByte(final byte x0) {
    if (x0 <= Byte.MIN_VALUE) {
      return Byte.MAX_VALUE;
    }
    return ((byte) (Math.abs(x0)));
  }

  /** {@inheritDoc} */
  @Override
  public final short computeAsShort(final short x0) {
    if (x0 <= Short.MIN_VALUE) {
      return Short.MAX_VALUE;
    }
    return ((short) (Math.abs(x0)));
  }

  /** {@inheritDoc} */
  @Override
  public final int computeAsInt(final int x0) {
    if (x0 <= Integer.MIN_VALUE) {
      return Integer.MAX_VALUE;
    }
    return (Math.abs(x0));
  }

  /** {@inheritDoc} */
  @Override
  public final long computeAsLong(final long x0) {
    if (x0 <= Long.MIN_VALUE) {
      return Long.MAX_VALUE;
    }
    return (Math.abs(x0));
  }

  /** {@inheritDoc} */
  @Override
  public final float computeAsFloat(final float x0) {
    return (Math.abs(x0));
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x0) {
    return (Math.abs(x0));
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final long x0) {
    return ((x0 <= Long.MIN_VALUE) ? (-((double) x0)) : Math.abs(x0));
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final int x0) {
    return ((x0 <= Integer.MIN_VALUE) ? (-((double) x0)) : Math.abs(x0));
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
