package org.optimizationBenchmarking.utils.math.functions.hyperbolic;

import org.apache.commons.math3.util.FastMath;
import org.optimizationBenchmarking.utils.math.functions.MathLibraries;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Add;
import org.optimizationBenchmarking.utils.math.functions.power.Ln;
import org.optimizationBenchmarking.utils.math.functions.power.Sqr;
import org.optimizationBenchmarking.utils.math.functions.power.Sqrt;

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
    if (MathLibraries.HAS_FASTMATH) {
      return ASinh.__fastMathASinh(x1);
    }
    return Ln.INSTANCE.computeAsDouble(//
        Add.INSTANCE.computeAsDouble(//
            Sqrt.INSTANCE.computeAsDouble(//
                Add.INSTANCE.computeAsDouble(//
                    Sqr.INSTANCE.computeAsDouble(x1), 1d))));
  }

  /**
   * Compute {@code asinh} with
   * {@link org.apache.commons.math3.util.FastMath}
   * 
   * @param x1
   *          the parameter
   * @return the result
   */
  private static final double __fastMathASinh(final double x1) {
    return FastMath.asinh(x1);
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
