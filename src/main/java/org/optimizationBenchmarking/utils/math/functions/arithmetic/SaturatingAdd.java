package org.optimizationBenchmarking.utils.math.functions.arithmetic;

import org.optimizationBenchmarking.utils.math.functions.BinaryFunction;

/**
 * The saturating {@code "+"} function. When doing integer addition, the
 * result may overflow. If you add {@code 1} to
 * <code>{@link java.lang.Long#MAX_VALUE}</code>, you get
 * <code>{@link java.lang.Long#MIN_VALUE}</code>. This addition function
 * here saturates, i.e.,
 * <code>{@link java.lang.Long#MAX_VALUE}+1={@link java.lang.Long#MAX_VALUE}</code>
 */
public final class SaturatingAdd extends BinaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance */
  public static final SaturatingAdd INSTANCE = new SaturatingAdd();

  /** instantiate */
  private SaturatingAdd() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final byte compute(final byte x0, final byte x1) {
    return ((byte) (Math.max(Byte.MIN_VALUE,
        Math.min(Byte.MAX_VALUE, ((x0) + (x1))))));
  }

  /** {@inheritDoc} */
  @Override
  public final short compute(final short x0, final short x1) {
    return ((short) (Math.max(Short.MIN_VALUE,
        Math.min(Short.MAX_VALUE, ((x0) + (x1))))));
  }

  /** {@inheritDoc} */
  @Override
  public final int compute(final int x0, final int x1) {
    return ((int) (Math.max(Integer.MIN_VALUE,
        Math.min(Integer.MAX_VALUE, (((long) x0) + ((long) x1))))));
  }

  /** {@inheritDoc} */
  @Override
  public final long compute(final long x0, final long x1) {
    if (x0 > x1) {
      return this.compute(x1, x0);
    }

    if (x0 < 0) {
      if ((x1 < 0) && ((Long.MIN_VALUE - x1) > x0)) {
        return Long.MIN_VALUE;
      }
      return (x0 + x1);
    }

    if (x0 <= (Long.MAX_VALUE - x1)) {
      return (x0 + x1);
    }
    return Long.MAX_VALUE;
  }

  /** {@inheritDoc} */
  @Override
  public final float compute(final float x0, final float x1) {
    return (x0 + x1);
  }

  /** {@inheritDoc} */
  @Override
  public final double compute(final double x0, final double x1) {
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
