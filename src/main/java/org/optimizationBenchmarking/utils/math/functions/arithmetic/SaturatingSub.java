package org.optimizationBenchmarking.utils.math.functions.arithmetic;

import org.optimizationBenchmarking.utils.math.functions.BinaryFunction;

/** The saturating {@code "-"} function. */
public final class SaturatingSub extends BinaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance */
  public static final SaturatingSub INSTANCE = new SaturatingSub();

  /** instantiate */
  private SaturatingSub() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final byte computeAsByte(final byte x0, final byte x1) {
    return ((byte) (Math.max(Byte.MIN_VALUE,
        Math.min(Byte.MAX_VALUE, ((x0) - (x1))))));
  }

  /** {@inheritDoc} */
  @Override
  public final short computeAsShort(final short x0, final short x1) {
    return ((short) (Math.max(Short.MIN_VALUE,
        Math.min(Short.MAX_VALUE, ((x0) - (x1))))));
  }

  /** {@inheritDoc} */
  @Override
  public final int computeAsInt(final int x0, final int x1) {
    return ((int) (Math.max(Integer.MIN_VALUE,
        Math.min(Integer.MAX_VALUE, (((long) x0) - ((long) x1))))));
  }

  /** {@inheritDoc} */
  @Override
  public final long computeAsLong(final long x0, final long x1) {
    switch (SaturatingSub.getOverflowType(x0, x1)) {
      case -1: {
        return Long.MIN_VALUE;
      }
      case 1: {
        return Long.MAX_VALUE;
      }
      default: {
        return (x0 - x1);
      }
    }
  }

  /**
   * Get the overflow type when subtracting two {@code long} numbers
   * 
   * @param x0
   *          the first number
   * @param x1
   *          the second number
   * @return {@code -1} if the result would be smaller than
   *         {@link java.lang.Long#MIN_VALUE}, {@code 1} if the result
   *         would be bigger than than {@link java.lang.Long#MAX_VALUE},
   *         {@code 0} if no overflow would occur
   */
  public static final int getOverflowType(final long x0, final long x1) {
    if (x1 <= Long.MIN_VALUE) {
      if (x0 < 0L) {
        return 0;
      }
      return 1;
    }
    return SaturatingAdd.getOverflowType(x0, (-x1));
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final long x0, final long x1) {
    switch (SaturatingSub.getOverflowType(x0, x1)) {
      case 0: {
        return (x0 - x1);
      }
      default: {
        return (((double) x0) - ((double) x1));
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final float computeAsFloat(final float x0, final float x1) {
    return (x0 + x1);
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x0, final double x1) {
    return (x0 + x1);
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
    return SaturatingSub.INSTANCE;
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
    return SaturatingSub.INSTANCE;
  }
}
