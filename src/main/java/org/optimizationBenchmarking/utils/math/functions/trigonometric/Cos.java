package org.optimizationBenchmarking.utils.math.functions.trigonometric;

import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;

/** The cos function */
public final class Cos extends UnaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance */
  public static final Cos INSTANCE = new Cos();

  /** instantiate */
  private Cos() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x1) {
    return Math.cos(x1);
  }

  /** {@inheritDoc} */
  @Override
  public final ACos invertFor(final int index) {
    return ACos.INSTANCE;
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
    return Cos.INSTANCE;
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
    return Cos.INSTANCE;
  }
}
