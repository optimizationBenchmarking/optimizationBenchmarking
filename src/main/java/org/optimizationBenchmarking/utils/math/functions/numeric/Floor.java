package org.optimizationBenchmarking.utils.math.functions.numeric;

import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;

/**
 * The floor function
 */
public final class Floor extends UnaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance */
  public static final Floor INSTANCE = new Floor();

  /** instantiate */
  private Floor() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final byte computeAsByte(final byte x1) {
    return x1;
  }

  /** {@inheritDoc} */
  @Override
  public final short computeAsShort(final short x1) {
    return x1;
  }

  /** {@inheritDoc} */
  @Override
  public final int computeAsInt(final int x1) {
    return x1;
  }

  /** {@inheritDoc} */
  @Override
  public final long computeAsLong(final long x1) {
    return x1;
  }

  /** {@inheritDoc} */
  @Override
  public final float computeAsFloat(final float x1) {
    return ((float) (Math.floor(x1)));
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x1) {
    return (Math.floor(x1));
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
    return Floor.INSTANCE;
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
    return Floor.INSTANCE;
  }
}
