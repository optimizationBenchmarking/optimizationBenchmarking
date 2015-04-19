package org.optimizationBenchmarking.utils.math.functions.power;

import org.apache.commons.math3.util.FastMath;
import org.optimizationBenchmarking.utils.math.functions.MathLibraries;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;

/** The sqrt function */
public final class Cbrt extends UnaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance */
  public static final Cbrt INSTANCE = new Cbrt();

  /** instantiate */
  private Cbrt() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x1) {
    if (MathLibraries.HAS_FASTMATH) {
      return Cbrt.__fastMathCbrt(x1);
    }
    return Math.cbrt(x1);
  }

  /**
   * Compute {@code cbrt} with
   * {@link org.apache.commons.math3.util.FastMath}
   * 
   * @param x1
   *          the parameter
   * @return the result
   */
  private static final double __fastMathCbrt(final double x1) {
    return FastMath.cbrt(x1);
  }

  /** {@inheritDoc} */
  @Override
  public final Cube invertFor(final int index) {
    return Cube.INSTANCE;
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
    return Cbrt.INSTANCE;
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
    return Cbrt.INSTANCE;
  }
}
