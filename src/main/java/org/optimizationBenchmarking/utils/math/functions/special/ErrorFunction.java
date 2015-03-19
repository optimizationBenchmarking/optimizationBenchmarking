package org.optimizationBenchmarking.utils.math.functions.special;

import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;

/**
 * <p>
 * The error function.
 * </p>
 */
public final class ErrorFunction extends UnaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance */
  public static final ErrorFunction INSTANCE = new ErrorFunction();

  /** instantiate */
  private ErrorFunction() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x1) {
    return org.apache.commons.math3.special.Erf.erf(x1);
  }

  /** {@inheritDoc} */
  @Override
  public final InverseErrorFunction invertFor(final int index) {
    return InverseErrorFunction.INSTANCE;
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
    return ErrorFunction.INSTANCE;
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
    return ErrorFunction.INSTANCE;
  }
}
