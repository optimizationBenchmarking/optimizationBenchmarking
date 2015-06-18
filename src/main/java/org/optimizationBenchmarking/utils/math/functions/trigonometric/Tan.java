package org.optimizationBenchmarking.utils.math.functions.trigonometric;

import org.apache.commons.math3.util.FastMath;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.math.functions.MathLibraries;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.text.IParameterRenderer;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** The tan function */
public final class Tan extends UnaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the precedence priority of the tangent operator */
  public static final int PRECEDENCE_PRIORITY = //
  Sin.PRECEDENCE_PRIORITY;

  /** the globally shared instance */
  public static final Tan INSTANCE = new Tan();

  /** instantiate */
  private Tan() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final int getPrecedencePriority() {
    return Tan.PRECEDENCE_PRIORITY;
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x1) {
    if (MathLibraries.HAS_FASTMATH) {
      return Tan.__fastMathTan(x1);
    }
    return Math.tan(x1);
  }

  /**
   * Compute {@code tan} with
   * {@link org.apache.commons.math3.util.FastMath}
   *
   * @param x1
   *          the parameter
   * @return the result
   */
  private static final double __fastMathTan(final double x1) {
    return FastMath.tan(x1);
  }

  /** {@inheritDoc} */
  @Override
  public final ATan invertFor(final int index) {
    return ATan.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(final ITextOutput out,
      final IParameterRenderer renderer) {
    out.append("tan("); //$NON-NLS-1$
    renderer.renderParameter(0, out);
    out.append(')');
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(final IMath out,
      final IParameterRenderer renderer) {
    try (final IMath trig = out.tan()) {
      renderer.renderParameter(0, trig);
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
    return Tan.INSTANCE;
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
    return Tan.INSTANCE;
  }
}
