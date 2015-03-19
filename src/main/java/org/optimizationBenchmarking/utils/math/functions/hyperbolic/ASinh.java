package org.optimizationBenchmarking.utils.math.functions.hyperbolic;

import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;

/** The asinh function */
public final class ASinh extends UnaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance */
  public static final ASinh INSTANCE = new ASinh();

  /** instantiate */
  private ASinh() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x1) {
    return org.apache.commons.math3.util.FastMath.asin(x1);
  }

  /** {@inheritDoc} */
  @Override
  public final Sinh invertFor(final int index) {
    return Sinh.INSTANCE;
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
    return ASinh.INSTANCE;
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
    return ASinh.INSTANCE;
  }
}
