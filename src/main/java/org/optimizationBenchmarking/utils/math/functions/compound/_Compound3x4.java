package org.optimizationBenchmarking.utils.math.functions.compound;

import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.math.functions.QuaternaryFunction;
import org.optimizationBenchmarking.utils.math.functions.TernaryFunction;
import org.optimizationBenchmarking.utils.math.text.IParameterRenderer;
import org.optimizationBenchmarking.utils.math.text.ParameterRendererBridge;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * This is the automatically generated code for a
 * {@link org.optimizationBenchmarking.utils.math.functions.QuaternaryFunction
 * 4-ary} which is composed of 3 single functions joined with a
 * {@link org.optimizationBenchmarking.utils.math.functions.TernaryFunction
 * 3-ary} function.
 */
final class _Compound3x4 extends QuaternaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * @serial The
   *         {@link org.optimizationBenchmarking.utils.math.functions.TernaryFunction
   *         3-ary} function used to compute this function's result based
   *         on the results of the 3 child
   *         {@link org.optimizationBenchmarking.utils.math.functions.QuaternaryFunction
   *         3-ary} functions.
   */
  final TernaryFunction m_result;

  /**
   * @serial The first child
   *         {@link org.optimizationBenchmarking.utils.math.functions.QuaternaryFunction
   *         4-ary} function which contributes to the result.
   */
  final QuaternaryFunction m_child1;

  /**
   * @serial The second child
   *         {@link org.optimizationBenchmarking.utils.math.functions.QuaternaryFunction
   *         4-ary} function which contributes to the result.
   */
  final QuaternaryFunction m_child2;

  /**
   * @serial The third child
   *         {@link org.optimizationBenchmarking.utils.math.functions.QuaternaryFunction
   *         4-ary} function which contributes to the result.
   */
  final QuaternaryFunction m_child3;

  /**
   * Create the
   * {@link org.optimizationBenchmarking.utils.math.functions.compound._Compound3x4}
   * , a function which combines the result of 3 child
   * {@link org.optimizationBenchmarking.utils.math.functions.QuaternaryFunction
   * 4-ary} functions by using an
   * {@link org.optimizationBenchmarking.utils.math.functions.TernaryFunction
   * 3-ary} function.
   *
   * @param result
   *          The
   *          {@link org.optimizationBenchmarking.utils.math.functions.TernaryFunction
   *          3-ary} function used to compute this function's result based
   *          on the results of the 3 child
   *          {@link org.optimizationBenchmarking.utils.math.functions.QuaternaryFunction
   *          3-ary} functions.
   * @param child1
   *          The first child
   *          {@link org.optimizationBenchmarking.utils.math.functions.QuaternaryFunction
   *          4-ary} function which contributes to the result.
   * @param child2
   *          The second child
   *          {@link org.optimizationBenchmarking.utils.math.functions.QuaternaryFunction
   *          4-ary} function which contributes to the result.
   * @param child3
   *          The third child
   *          {@link org.optimizationBenchmarking.utils.math.functions.QuaternaryFunction
   *          4-ary} function which contributes to the result.
   * @throws IllegalArgumentException
   *           if any of the parameters is {@code null}
   */
  _Compound3x4(final TernaryFunction result,
      final QuaternaryFunction child1, final QuaternaryFunction child2,
      final QuaternaryFunction child3) {
    super();
    if (result == null) {
      throw new IllegalArgumentException( //
          "Result function of {@link org.optimizationBenchmarking.utils.math.functions.compound._Compound3x4}, a function which combines the result of 3 child {@link org.optimizationBenchmarking.utils.math.functions.QuaternaryFunction 4-ary} functions by using an {@link org.optimizationBenchmarking.utils.math.functions.TernaryFunction 3-ary} function, cannot be null."); //$NON-NLS-1$
    }
    this.m_result = result;
    if (child1 == null) {
      throw new IllegalArgumentException( //
          "Child function 1 of {@link org.optimizationBenchmarking.utils.math.functions.compound._Compound3x4}, a function which combines the result of 3 child {@link org.optimizationBenchmarking.utils.math.functions.QuaternaryFunction 4-ary} functions by using an {@link org.optimizationBenchmarking.utils.math.functions.TernaryFunction 3-ary} function, cannot be null."); //$NON-NLS-1$
    }
    this.m_child1 = child1;
    if (child2 == null) {
      throw new IllegalArgumentException( //
          "Child function 2 of {@link org.optimizationBenchmarking.utils.math.functions.compound._Compound3x4}, a function which combines the result of 3 child {@link org.optimizationBenchmarking.utils.math.functions.QuaternaryFunction 4-ary} functions by using an {@link org.optimizationBenchmarking.utils.math.functions.TernaryFunction 3-ary} function, cannot be null."); //$NON-NLS-1$
    }
    this.m_child2 = child2;
    if (child3 == null) {
      throw new IllegalArgumentException( //
          "Child function 3 of {@link org.optimizationBenchmarking.utils.math.functions.compound._Compound3x4}, a function which combines the result of 3 child {@link org.optimizationBenchmarking.utils.math.functions.QuaternaryFunction 4-ary} functions by using an {@link org.optimizationBenchmarking.utils.math.functions.TernaryFunction 3-ary} function, cannot be null."); //$NON-NLS-1$
    }
    this.m_child3 = child3;
  }

  /** {@inheritDoc} */
  @Override
  public final byte computeAsByte(final byte x0, final byte x1,
      final byte x2, final byte x3) {
    return this.m_result.computeAsByte(
        this.m_child1.computeAsByte(x0, x1, x2, x3),
        this.m_child2.computeAsByte(x0, x1, x2, x3),
        this.m_child3.computeAsByte(x0, x1, x2, x3));
  }

  /** {@inheritDoc} */
  @Override
  public final short computeAsShort(final short x0, final short x1,
      final short x2, final short x3) {
    return this.m_result.computeAsShort(
        this.m_child1.computeAsShort(x0, x1, x2, x3),
        this.m_child2.computeAsShort(x0, x1, x2, x3),
        this.m_child3.computeAsShort(x0, x1, x2, x3));
  }

  /** {@inheritDoc} */
  @Override
  public final int computeAsInt(final int x0, final int x1, final int x2,
      final int x3) {
    return this.m_result.computeAsInt(
        this.m_child1.computeAsInt(x0, x1, x2, x3),
        this.m_child2.computeAsInt(x0, x1, x2, x3),
        this.m_child3.computeAsInt(x0, x1, x2, x3));
  }

  /** {@inheritDoc} */
  @Override
  public final long computeAsLong(final long x0, final long x1,
      final long x2, final long x3) {
    return this.m_result.computeAsLong(
        this.m_child1.computeAsLong(x0, x1, x2, x3),
        this.m_child2.computeAsLong(x0, x1, x2, x3),
        this.m_child3.computeAsLong(x0, x1, x2, x3));
  }

  /** {@inheritDoc} */
  @Override
  public final float computeAsFloat(final float x0, final float x1,
      final float x2, final float x3) {
    return this.m_result.computeAsFloat(
        this.m_child1.computeAsFloat(x0, x1, x2, x3),
        this.m_child2.computeAsFloat(x0, x1, x2, x3),
        this.m_child3.computeAsFloat(x0, x1, x2, x3));
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x0, final double x1,
      final double x2, final double x3) {
    return this.m_result.computeAsDouble(
        this.m_child1.computeAsDouble(x0, x1, x2, x3),
        this.m_child2.computeAsDouble(x0, x1, x2, x3),
        this.m_child3.computeAsDouble(x0, x1, x2, x3));
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final int x0, final int x1,
      final int x2, final int x3) {
    return this.m_result.computeAsDouble(
        this.m_child1.computeAsDouble(x0, x1, x2, x3),
        this.m_child2.computeAsDouble(x0, x1, x2, x3),
        this.m_child3.computeAsDouble(x0, x1, x2, x3));
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final long x0, final long x1,
      final long x2, final long x3) {
    return this.m_result.computeAsDouble(
        this.m_child1.computeAsDouble(x0, x1, x2, x3),
        this.m_child2.computeAsDouble(x0, x1, x2, x3),
        this.m_child3.computeAsDouble(x0, x1, x2, x3));
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isLongArithmeticAccurate() {
    return (this.m_result.isLongArithmeticAccurate()
        && this.m_child1.isLongArithmeticAccurate()
        && this.m_child2.isLongArithmeticAccurate() && this.m_child3
        .isLongArithmeticAccurate());
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(final IMath out,
      final IParameterRenderer renderer) {
    this.m_result.mathRender(out, new ParameterRendererBridge(renderer,
        this.m_child1, this.m_child2, this.m_child3));
  }

  /** {@inheritDoc} */
  @Override
  public final void mathRender(final ITextOutput out,
      final IParameterRenderer renderer) {
    this.m_result.mathRender(out, new ParameterRendererBridge(renderer,
        this.m_child1, this.m_child2, this.m_child3));
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return HashUtils.combineHashes(HashUtils.hashCode(this.m_result),
        HashUtils.combineHashes(HashUtils.hashCode(this.m_child1),
            HashUtils.combineHashes(HashUtils.hashCode(this.m_child2),
                HashUtils.hashCode(this.m_child3))));
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    final _Compound3x4 other;
    if (o == this) {
      return true;
    }
    if (o instanceof _Compound3x4) {
      other = ((_Compound3x4) o);
      return (this.m_result.equals(other.m_result)
          && this.m_child1.equals(other.m_child1)
          && this.m_child2.equals(other.m_child2) && this.m_child3
          .equals(other.m_child3));
    }
    return false;
  }
}
