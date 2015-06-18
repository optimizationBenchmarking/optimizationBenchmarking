package org.optimizationBenchmarking.utils.math.functions.power;

import org.apache.commons.math3.util.FastMath;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.math.MathConstants;
import org.optimizationBenchmarking.utils.math.functions.MathLibraries;
import org.optimizationBenchmarking.utils.math.functions.UnaryFunction;
import org.optimizationBenchmarking.utils.math.text.IParameterRenderer;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * The logarithmus dualis function, i.e., the logarithm to base 2
 */
public final class Ld extends UnaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the precedence priority of the ld operator */
  public static final int PRECEDENCE_PRIORITY = //
  Ln.PRECEDENCE_PRIORITY;

  /** the globally shared instance */
  public static final Ld INSTANCE = new Ld();

  /** instantiate */
  private Ld() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final int getPrecedencePriority() {
    return Ld.PRECEDENCE_PRIORITY;
  }

  /** {@inheritDoc} */
  @Override
  public final int computeAsInt(final int x1) {
    if (x1 <= 0) {
      if (x1 == 0) {
        return Integer.MIN_VALUE;
      }
      throw new ArithmeticException(//
          "Cannot compute log2 of " + x1); //$NON-NLS-1$
    }
    return (31 - Integer.numberOfLeadingZeros(x1));
  }

  /** {@inheritDoc} */
  @Override
  public final long computeAsLong(final long x1) {
    if (x1 <= 0L) {
      if (x1 == 0L) {
        return Long.MIN_VALUE;
      }
      throw new ArithmeticException(//
          "Cannot compute log2 of " + x1); //$NON-NLS-1$
    }
    return (63 - Long.numberOfLeadingZeros(x1));
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x1) {
    final double x2;
    long l1;
    int res;

    if ((x1 >= 0d) && (x1 <= Long.MAX_VALUE)) {
      l1 = ((long) x1);

      if (l1 == x1) {
        if (l1 == 0L) {
          return Double.NEGATIVE_INFINITY;
        }
        res = (63 - Long.numberOfLeadingZeros(l1));
        if (l1 == (1L << res)) {
          return res;
        }
      } else {

        if (x1 < 1d) {
          x2 = (1d / x1);

          if ((x2 >= 0d) && (x2 <= Long.MAX_VALUE)) {
            l1 = ((long) x2);

            if (l1 == x2) {
              if (l1 == 0L) {
                return Double.NEGATIVE_INFINITY;
              }
              res = (63 - Long.numberOfLeadingZeros(l1));
              if (l1 == (1L << res)) {
                return (-res);
              }
            }
          }
        }
      }
    }

    if (MathLibraries.HAS_FASTMATH) {
      return Ld.__fastMathLog2(x1);
    }
    return (Math.log(x1) / MathConstants.LN_2);
  }

  /**
   * Compute {@code log2} with
   * {@link org.apache.commons.math3.util.FastMath}
   *
   * @param x1
   *          the parameter
   * @return the result
   */
  private static final double __fastMathLog2(final double x1) {
    return FastMath.log(2d, x1);
  }

  /** {@inheritDoc} */
  @Override
  public final Pow2 invertFor(final int index) {
    return Pow2.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(final ITextOutput out,
      final IParameterRenderer renderer) {
    out.append("log(2,"); //$NON-NLS-1$
    renderer.renderParameter(0, out);
    out.append(')');
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(final IMath out,
      final IParameterRenderer renderer) {
    try (final IMath log = out.ld()) {
      renderer.renderParameter(0, log);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "log2"; //$NON-NLS-1$
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
    return Ld.INSTANCE;
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
    return Ld.INSTANCE;
  }
}
