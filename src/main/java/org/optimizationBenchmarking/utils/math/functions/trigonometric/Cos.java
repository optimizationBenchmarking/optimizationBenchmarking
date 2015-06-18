package org.optimizationBenchmarking.utils.math.functions.trigonometric;

import org.apache.commons.math3.util.FastMath;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.math.functions.MathLibraries;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.text.IParameterRenderer;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** The cos function */
public final class Cos extends UnaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the precedence priority of the cosine operator */
  public static final int PRECEDENCE_PRIORITY = //
  Sin.PRECEDENCE_PRIORITY;

  /** the globally shared instance */
  public static final Cos INSTANCE = new Cos();

  /** instantiate */
  private Cos() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final int getPrecedencePriority() {
    return Cos.PRECEDENCE_PRIORITY;
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x1) {
    if (MathLibraries.HAS_FASTMATH) {
      return Cos.__fastMathCos(x1);
    }
    return Math.cos(x1);
  }

  /**
   * Compute {@code cos} with
   * {@link org.apache.commons.math3.util.FastMath}
   *
   * @param x1
   *          the parameter
   * @return the result
   */
  private static final double __fastMathCos(final double x1) {
    return FastMath.cos(x1);
  }

  /** {@inheritDoc} */
  @Override
  public final ACos invertFor(final int index) {
    return ACos.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(final ITextOutput out,
      final IParameterRenderer renderer) {
    out.append("cos("); //$NON-NLS-1$
    renderer.renderParameter(0, out);
    out.append(')');
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(final IMath out,
      final IParameterRenderer renderer) {
    try (final IMath trig = out.cos()) {
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
    return Cos.INSTANCE;
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
    return Cos.INSTANCE;
  }
}
