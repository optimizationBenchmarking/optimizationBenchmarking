package org.optimizationBenchmarking.utils.document.impl;

import org.optimizationBenchmarking.utils.document.spec.EMathComparison;
import org.optimizationBenchmarking.utils.document.spec.IComplexText;
import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.document.spec.IMathName;
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
   * interface. Even if {@code function==null}, a new instance of
   * {@link org.optimizationBenchmarking.utils.document.spec.IMath} will be
   * returned. This instance will simply delegate to {@code math}.
   *
   * @param function
   *          the function
   * @param math
   *          the input math interface
   * @return the new math interface
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
      return new __NeverClosingMath(math);
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

  /** an internal wrapper which never closes */
  private static final class __NeverClosingMath implements IMath {

    /** the original */
    private final IMath m_orig;

    /**
     * create the never closing math
     *
     * @param orig
     *          the original
     */
    __NeverClosingMath(final IMath orig) {
      super();
      this.m_orig = orig;
    }

    /** {@inheritDoc} */
    @Override
    public final void close() {
      // ignore
    }

    /** {@inheritDoc} */
    @Override
    public final IMath inBraces() {
      return this.m_orig.inBraces();
    }

    /** {@inheritDoc} */
    @Override
    public final IMath add() {
      return this.m_orig.add();
    }

    /** {@inheritDoc} */
    @Override
    public final IMath sub() {
      return this.m_orig.sub();
    }

    /** {@inheritDoc} */
    @Override
    public final IMath mul() {
      return this.m_orig.mul();
    }

    /** {@inheritDoc} */
    @Override
    public final IMath div() {
      return this.m_orig.div();
    }

    /** {@inheritDoc} */
    @Override
    public final IMath divInline() {
      return this.m_orig.divInline();
    }

    /** {@inheritDoc} */
    @Override
    public final IMath mod() {
      return this.m_orig.mod();
    }

    /** {@inheritDoc} */
    @Override
    public final IMath log() {
      return this.m_orig.log();
    }

    /** {@inheritDoc} */
    @Override
    public final IMath ln() {
      return this.m_orig.ln();
    }

    /** {@inheritDoc} */
    @Override
    public final IMath ld() {
      return this.m_orig.ld();
    }

    /** {@inheritDoc} */
    @Override
    public final IMath lg() {
      return this.m_orig.lg();
    }

    /** {@inheritDoc} */
    @Override
    public final IMath pow() {
      return this.m_orig.pow();
    }

    /** {@inheritDoc} */
    @Override
    public final IMath root() {
      return this.m_orig.root();
    }

    /** {@inheritDoc} */
    @Override
    public final IMath sqrt() {
      return this.m_orig.sqrt();
    }

    /** {@inheritDoc} */
    @Override
    public final IMath compare(final EMathComparison cmp) {
      return this.m_orig.compare(cmp);
    }

    /** {@inheritDoc} */
    @Override
    public final IMath negate() {
      return this.m_orig.negate();
    }

    /** {@inheritDoc} */
    @Override
    public final IMath abs() {
      return this.m_orig.abs();
    }

    /** {@inheritDoc} */
    @Override
    public final IMath factorial() {
      return this.m_orig.factorial();
    }

    /** {@inheritDoc} */
    @Override
    public final IMath sin() {
      return this.m_orig.sin();
    }

    /** {@inheritDoc} */
    @Override
    public final IMath cos() {
      return this.m_orig.cos();
    }

    /** {@inheritDoc} */
    @Override
    public final IMath tan() {
      return this.m_orig.tan();
    }

    /** {@inheritDoc} */
    @Override
    public final IMath nAryFunction(final String name, final int minArity,
        final int maxArity) {
      return this.m_orig.nAryFunction(name, minArity, maxArity);
    }

    /** {@inheritDoc} */
    @Override
    public final IComplexText text() {
      return this.m_orig.text();
    }

    /** {@inheritDoc} */
    @Override
    public final IMathName name() {
      return this.m_orig.name();
    }

    /** {@inheritDoc} */
    @Override
    public final IText number() {
      return this.m_orig.number();
    }
  }
}
