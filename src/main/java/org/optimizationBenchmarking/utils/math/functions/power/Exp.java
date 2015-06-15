package org.optimizationBenchmarking.utils.math.functions.power;

import org.apache.commons.math3.util.FastMath;
import org.optimizationBenchmarking.utils.math.functions.MathLibraries;
import org.optimizationBenchmarking.utils.math.functions.MathematicalFunction;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Mul;

/** The exp function */
public final class Exp extends UnaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the precedence priority of the exp operator */
  public static final int PRECEDENCE_PRIORITY = //
  (int) ((((long) (Math.max(Mul.PRECEDENCE_PRIORITY,
      Pow.PRECEDENCE_PRIORITY))) + //
  ((long) (Math.max(Mul.PRECEDENCE_PRIORITY,//
      MathematicalFunction.DEFAULT_PRECEDENCE_PRIORITY)))) / 2);

  /** the globally shared instance */
  public static final Exp INSTANCE = new Exp();

  /** instantiate */
  private Exp() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final int getPrecedencePriority() {
    return Exp.PRECEDENCE_PRIORITY;
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x1) {
    if (MathLibraries.HAS_FASTMATH) {
      return Exp.__fastMathExp(x1);
    }
    return Math.exp(x1);
  }

  /**
   * Compute {@code exp} with
   * {@link org.apache.commons.math3.util.FastMath}
   *
   * @param x1
   *          the parameter
   * @return the result
   */
  private static final double __fastMathExp(final double x1) {
    return FastMath.exp(x1);
  }

  /** {@inheritDoc} */
  @Override
  public final Ln invertFor(final int index) {
    return Ln.INSTANCE;
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
    return Exp.INSTANCE;
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
    return Exp.INSTANCE;
  }
}
