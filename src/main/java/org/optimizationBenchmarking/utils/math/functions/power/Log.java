package org.optimizationBenchmarking.utils.math.functions.power;

import org.apache.commons.math3.util.FastMath;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.math.functions.BinaryFunction;
import org.optimizationBenchmarking.utils.math.functions.MathLibraries;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Div;
import org.optimizationBenchmarking.utils.math.text.IParameterRenderer;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * The log function computes the logarithm of {@code x2} to the base
 * {@code x1} .
 */
public final class Log extends BinaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the precedence priority of the log operator */
  public static final int PRECEDENCE_PRIORITY = //
  Ln.PRECEDENCE_PRIORITY;

  /** the globally shared instance */
  public static final Log INSTANCE = new Log();

  /** instantiate */
  private Log() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final int getPrecedencePriority() {
    return Log.PRECEDENCE_PRIORITY;
  }

  /** {@inheritDoc} */
  @Override
  public final long computeAsLong(final long x1, final long x2) {
    if (x1 == 10L) {
      return Lg.INSTANCE.computeAsLong(x2);
    }
    return super.computeAsLong(x1, x2);
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x1, final double x2) {
    final double d;

    if (x1 == 10d) {
      return Lg.INSTANCE.computeAsDouble(x2);
    }

    if (x1 == Math.E) {
      return Ln.INSTANCE.computeAsDouble(x2);
    }

    if (MathLibraries.HAS_FASTMATH) {
      return Log.__fastMathLog(x1, x2);
    }

    d = Math.log(x2);
    if (x1 == Math.E) {
      return d;
    }
    return Div.INSTANCE.computeAsDouble(d, Math.log(x1));
  }

  /**
   * Compute {@code log} with
   * {@link org.apache.commons.math3.util.FastMath}
   *
   * @param x1
   *          the base
   * @param x2
   *          the value
   * @return the result
   */
  private static final double __fastMathLog(final double x1,
      final double x2) {
    return FastMath.log(x1, x2);
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(final ITextOutput out,
      final IParameterRenderer renderer) {
    out.append("log("); //$NON-NLS-1$
    renderer.renderParameter(0, out);
    out.append(',');
    renderer.renderParameter(1, out);
    out.append(')');
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(final IMath out,
      final IParameterRenderer renderer) {
    try (final IMath log = out.log()) {
      renderer.renderParameter(0, log);
      renderer.renderParameter(1, log);
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
    return Log.INSTANCE;
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
    return Log.INSTANCE;
  }
}
