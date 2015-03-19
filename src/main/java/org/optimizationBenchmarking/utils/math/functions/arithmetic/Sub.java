package org.optimizationBenchmarking.utils.math.functions.arithmetic;

import org.optimizationBenchmarking.utils.math.functions.BinaryFunction;

/**
 * The "-" function
 */
public final class Sub extends BinaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance */
  public static final Sub INSTANCE = new Sub();

  /** instantiate */
  private Sub() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final byte computeAsByte(final byte x0, final byte x1) {
    return ((byte) (x0 - x1));
  }

  /** {@inheritDoc} */
  @Override
  public final short computeAsShort(final short x0, final short x1) {
    return ((short) (x0 - x1));
  }

  /** {@inheritDoc} */
  @Override
  public final int computeAsInt(final int x0, final int x1) {
    return (x0 - x1);
  }

  /** {@inheritDoc} */
  @Override
  public final long computeAsLong(final long x0, final long x1) {
    return (x0 - x1);
  }

  /** {@inheritDoc} */
  @Override
  public final float computeAsFloat(final float x0, final float x1) {
    return (x0 - x1);
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x0, final double x1) {
    return (x0 - x1);
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final long x0, final long x1) {
    switch (SaturatingSub.getOverflowType(x0, x1)) {
      case -1:
      case 1: {
        return this.computeAsDouble(((double) x0), ((double) x1));
      }
      default: {
        return (x0 - x1);
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final int x0, final int x1) {
    return (((long) x0) - ((long) x1));
  }

  /** {@inheritDoc} */
  @Override
  public final BinaryFunction invertFor(final int index) {
    if (index == 1) {
      return Sub.INSTANCE;
    }
    return Add.INSTANCE;
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
    return Sub.INSTANCE;
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
    return Sub.INSTANCE;
  }
}
