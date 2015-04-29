package org.optimizationBenchmarking.utils.document.impl;

import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.document.spec.IText;
import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.math.functions.MathematicalFunction;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Absolute;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Add;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Div;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.ModuloDivisorSign;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Mul;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Negate;
import org.optimizationBenchmarking.utils.math.functions.arithmetic.Sub;
import org.optimizationBenchmarking.utils.math.functions.combinatoric.Factorial;
import org.optimizationBenchmarking.utils.math.functions.power.Cbrt;
import org.optimizationBenchmarking.utils.math.functions.power.Ld;
import org.optimizationBenchmarking.utils.math.functions.power.Lg;
import org.optimizationBenchmarking.utils.math.functions.power.Ln;
import org.optimizationBenchmarking.utils.math.functions.power.Log;
import org.optimizationBenchmarking.utils.math.functions.power.Pow;
import org.optimizationBenchmarking.utils.math.functions.power.Sqrt;
import org.optimizationBenchmarking.utils.math.functions.trigonometric.Cos;
import org.optimizationBenchmarking.utils.math.functions.trigonometric.Sin;
import org.optimizationBenchmarking.utils.math.functions.trigonometric.Tan;

/**
 * <p>
 * This class provides a bridge between the implementations of
 * {@link org.optimizationBenchmarking.utils.math.functions.MathematicalFunction}
 * and the {@link org.optimizationBenchmarking.utils.document.spec.IMath}
 * interface of the Document API.
 * </p>
 * <p>
 * TODO: This class cannot deal yet with
 * {@link org.optimizationBenchmarking.utils.math.functions.power.Sqr} and
 * {@link org.optimizationBenchmarking.utils.math.functions.power.Cube}.
 * </p>
 */
public final class FunctionToMathBridge {

  /** the forbidden constructor */
  private FunctionToMathBridge() {
    ErrorUtils.doNotCall();
  }

  /**
   * Translate an instance of
   * {@link org.optimizationBenchmarking.utils.math.functions.MathematicalFunction}
   * to an invocation of a function via the
   * {@link org.optimizationBenchmarking.utils.document.spec.IMath}
   * interface
   * 
   * @param function
   *          the function
   * @param math
   *          the input math interface
   * @return the new math interface, or {@code math} if
   *         {@code function==null}
   */
  public static final IMath bridge(final MathematicalFunction function,
      final IMath math) {
    IMath temp;

    if (math == null) {
      throw new IllegalArgumentException(//
          "Math interface cannot be null when trying to represent mathematical function "//$NON-NLS-1$
              + function);
    }

    if (function == null) {
      return math;
    }

    if (function instanceof Add) {
      return math.add();
    }

    if (function instanceof Sub) {
      return math.sub();
    }

    if (function instanceof Mul) {
      return math.mul();
    }

    if (function instanceof Div) {
      return math.div();
    }

    if (function instanceof ModuloDivisorSign) {
      return math.mod();
    }

    if (function instanceof Log) {
      return math.log();
    }

    if (function instanceof Ln) {
      return math.ln();
    }

    if (function instanceof Ld) {
      return math.ld();
    }

    if (function instanceof Lg) {
      return math.lg();
    }

    if (function instanceof Pow) {
      return math.pow();
    }

    if (function instanceof Sqrt) {
      return math.sqrt();
    }

    if (function instanceof Cbrt) {
      temp = math.root();
      try (final IText txt = temp.number()) {
        txt.append(3);
      }
      return temp;
    }

    if (function instanceof Negate) {
      return math.negate();
    }

    if (function instanceof Absolute) {
      return math.abs();
    }

    if (function instanceof Factorial) {
      return math.factorial();
    }

    if (function instanceof Sin) {
      return math.sin();
    }

    if (function instanceof Cos) {
      return math.cos();
    }

    if (function instanceof Tan) {
      return math.tan();
    }

    return math.nAryFunction(function.toString(), function.getMinArity(),
        function.getMaxArity());
  }
}
