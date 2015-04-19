package org.optimizationBenchmarking.utils.math.functions.hyperbolic;

import org.apache.commons.math3.util.FastMath;
import org.optimizationBenchmarking.utils.math.functions.MathLibraries;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Add;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Div;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Mul;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Sub;
import org.optimizationBenchmarking.utils.math.functions.power.Ln;

/** The atanh function */
public final class ATanh extends UnaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance */
  public static final ATanh INSTANCE = new ATanh();

  /** instantiate */
  private ATanh() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x1) {
    final double inv;

    if (MathLibraries.HAS_FASTMATH) {
      return ATanh.__fastMathATanh(x1);
    }

    inv = Div.INSTANCE.computeAsDouble(1d, x1);
    return Mul.INSTANCE.computeAsDouble(0.5d,//
        Sub.INSTANCE.computeAsDouble(//
            Ln.INSTANCE.computeAsDouble(//
                Add.INSTANCE.computeAsDouble(1d, inv)),//
            Ln.INSTANCE.computeAsDouble(//
                Sub.INSTANCE.computeAsDouble(1d, inv))));

  }

  /**
   * Compute {@code atanh} with
   * {@link org.apache.commons.math3.util.FastMath}
   * 
   * @param x1
   *          the parameter
   * @return the result
   */
  private static final double __fastMathATanh(final double x1) {
    return FastMath.atanh(x1);
  }

  /** {@inheritDoc} */
  @Override
  public final Tanh invertFor(final int index) {
    return Tanh.INSTANCE;
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
    return ATanh.INSTANCE;
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
    return ATanh.INSTANCE;
  }
}
