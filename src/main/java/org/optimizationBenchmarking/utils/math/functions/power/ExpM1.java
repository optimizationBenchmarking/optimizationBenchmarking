package org.optimizationBenchmarking.utils.math.functions.power;

import org.apache.commons.math3.util.FastMath;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.document.spec.IText;
import org.optimizationBenchmarking.utils.math.functions.MathLibraries;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Sub;
import org.optimizationBenchmarking.utils.math.text.IParameterRenderer;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** The {@code exp(x)-1} function */
public final class ExpM1 extends UnaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the precedence priority of the {@code exp(x)-1} operator */
  public static final int PRECEDENCE_PRIORITY = Sub.PRECEDENCE_PRIORITY;

  /** the globally shared instance */
  public static final ExpM1 INSTANCE = new ExpM1();

  /** instantiate */
  private ExpM1() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final int getPrecedencePriority() {
    return ExpM1.PRECEDENCE_PRIORITY;
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x1) {
    if (MathLibraries.HAS_FASTMATH) {
      return ExpM1.__fastMathExpM1(x1);
    }
    return Math.expm1(x1);
  }

  /**
   * Compute {@code exp(x)-1} with
   * {@link org.apache.commons.math3.util.FastMath}
   *
   * @param x1
   *          the parameter
   * @return the result
   */
  private static final double __fastMathExpM1(final double x1) {
    return FastMath.expm1(x1);
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(final ITextOutput out,
      final IParameterRenderer renderer) {
    out.append("exp("); //$NON-NLS-1$
    renderer.renderParameter(0, out);
    out.append(')');
    out.append('-');
    out.append('1');
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(final IMath out,
      final IParameterRenderer renderer) {
    try (IMath sub = out.sub()) {
      try (final IMath exp = sub.exp()) {
        renderer.renderParameter(0, exp);
      }
      try (final IText num = sub.number()) {
        num.append(1);
      }
    }
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
    return ExpM1.INSTANCE;
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
    return ExpM1.INSTANCE;
  }
}
