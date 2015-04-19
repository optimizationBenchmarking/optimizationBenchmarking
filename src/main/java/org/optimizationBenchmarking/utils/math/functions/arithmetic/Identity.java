package org.optimizationBenchmarking.utils.math.functions.arithmetic;

import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;

/**
 * The identity function
 */
public final class Identity extends UnaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance */
  public static final Identity INSTANCE = new Identity();

  /** instantiate */
  private Identity() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final byte computeAsByte(final byte x0) {
    return x0;
  }

  /** {@inheritDoc} */
  @Override
  public final short computeAsShort(final short x0) {
    return x0;
  }

  /** {@inheritDoc} */
  @Override
  public final int computeAsInt(final int x0) {
    return x0;
  }

  /** {@inheritDoc} */
  @Override
  public final long computeAsLong(final long x0) {
    return x0;
  }

  /** {@inheritDoc} */
  @Override
  public final float computeAsFloat(final float x0) {
    return x0;
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x0) {
    return x0;
  }

  /** {@inheritDoc} */
  @Override
  public final Identity invertFor(final int index) {
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isLongArithmeticAccurate() {
    return true;
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
    return Identity.INSTANCE;
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
    return Identity.INSTANCE;
  }
}
