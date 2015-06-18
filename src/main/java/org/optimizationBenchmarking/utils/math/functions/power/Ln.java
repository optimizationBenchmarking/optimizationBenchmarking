package org.optimizationBenchmarking.utils.math.functions.power;

import org.apache.commons.math3.util.FastMath;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.math.functions.MathLibraries;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.text.IParameterRenderer;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** The ln function */
public final class Ln extends UnaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance */
  public static final Ln INSTANCE = new Ln();

  /** the precedence priority of the ln operator */
  public static final int PRECEDENCE_PRIORITY = //
  Exp.PRECEDENCE_PRIORITY;

  /** instantiate */
  private Ln() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final int getPrecedencePriority() {
    return Ln.PRECEDENCE_PRIORITY;
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x1) {
    if (MathLibraries.HAS_FASTMATH) {
      return Ln.__fastMathLog(x1);
    }
    return Math.log(x1);
  }

  /**
   * Compute {@code log} with
   * {@link org.apache.commons.math3.util.FastMath}
   *
   * @param x1
   *          the parameter
   * @return the result
   */
  private static final double __fastMathLog(final double x1) {
    return FastMath.log(x1);
  }

  /** {@inheritDoc} */
  @Override
  public final Exp invertFor(final int index) {
    return Exp.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(final ITextOutput out,
      final IParameterRenderer renderer) {
    out.append("ln("); //$NON-NLS-1$
    renderer.renderParameter(0, out);
    out.append(')');
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(final IMath out,
      final IParameterRenderer renderer) {
    try (final IMath log = out.ln()) {
      renderer.renderParameter(0, log);
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
    return Ln.INSTANCE;
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
    return Ln.INSTANCE;
  }
}
