package org.optimizationBenchmarking.utils.math.functions.power;

import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Absolute;

/**
 * The cube function
 */
public final class Cube extends UnaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance */
  public static final Cube INSTANCE = new Cube();

  /** instantiate */
  private Cube() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final byte compute(final byte x1) {
    return ((byte) (x1 * x1 * x1));
  }

  /** {@inheritDoc} */
  @Override
  public final short compute(final short x1) {
    return ((short) (x1 * x1 * x1));
  }

  /** {@inheritDoc} */
  @Override
  public final int compute(final int x1) {
    return (x1 * x1 * x1);
  }

  /** {@inheritDoc} */
  @Override
  public final long compute(final long x1) {
    return (x1 * x1 * x1);
  }

  /** {@inheritDoc} */
  @Override
  public final float compute(final float x1) {
    return (x1 * x1 * x1);
  }

  /** {@inheritDoc} */
  @Override
  public final double compute(final double x1) {
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
