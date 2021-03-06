package org.optimizationBenchmarking.utils.math.functions.trigonometric;

import org.apache.commons.math3.util.FastMath;
import org.optimizationBenchmarking.utils.math.functions.MathLibraries;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;

/** Convert radians to degrees. */
public final class RadiansToDegrees extends UnaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance */
  public static final RadiansToDegrees INSTANCE = new RadiansToDegrees();

  /** instantiate */
  private RadiansToDegrees() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x1) {
    if (MathLibraries.HAS_FASTMATH) {
      return RadiansToDegrees.__fastMathToDegrees(x1);
    }
    return Math.toDegrees(x1);
  }

  /**
   * Convert to degrees with {@link org.apache.commons.math3.util.FastMath}
   *
   * @param x1
   *          the first value
   * @return the result
   */
  private static final double __fastMathToDegrees(final double x1) {
    return FastMath.toDegrees(x1);
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
    return RadiansToDegrees.INSTANCE;
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
    return RadiansToDegrees.INSTANCE;
  }
}
