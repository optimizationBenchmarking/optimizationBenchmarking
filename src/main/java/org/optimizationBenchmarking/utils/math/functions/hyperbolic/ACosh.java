package org.optimizationBenchmarking.utils.math.functions.hyperbolic;

import org.apache.commons.math3.util.FastMath;
import org.optimizationBenchmarking.utils.math.functions.MathLibraries;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Add;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Sub;
import org.optimizationBenchmarking.utils.math.functions.power.Ln;
import org.optimizationBenchmarking.utils.math.functions.power.Sqr;
import org.optimizationBenchmarking.utils.math.functions.power.Sqrt;

/** The acosh function */
public final class ACosh extends UnaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance */
  public static final ACosh INSTANCE = new ACosh();

  /** instantiate */
  private ACosh() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x1) {
    if (MathLibraries.HAS_FASTMATH) {
      return ACosh.__fastMathACosh(x1);
    }
    return Ln.INSTANCE.computeAsDouble(//
        Add.INSTANCE.computeAsDouble(x1,//
            Sqrt.INSTANCE.computeAsDouble(//
                Sub.INSTANCE.computeAsDouble(//
                    Sqr.INSTANCE.computeAsDouble(x1), 1d))));
  }

  /**
   * Compute {@code acosh} with
   * {@link org.apache.commons.math3.util.FastMath}
   *
   * @param x1
   *          the parameter
   * @return the result
   */
  private static final double __fastMathACosh(final double x1) {
    return FastMath.acosh(x1);
  }

  /** {@inheritDoc} */
  @Override
  public final Cosh invertFor(final int index) {
    return Cosh.INSTANCE;
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
    return ACosh.INSTANCE;
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
    return ACosh.INSTANCE;
  }
}
