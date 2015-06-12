package org.optimizationBenchmarking.utils.math.functions.compound;

import org.optimizationBenchmarking.utils.document.spec.IMath;
import org.optimizationBenchmarking.utils.document.spec.IText;
import org.optimizationBenchmarking.utils.math.NumericalTypes;
import org.optimizationBenchmarking.utils.math.functions.IParameterRenderer;
import org.optimizationBenchmarking.utils.math.functions.TernaryFunction;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * This is the automatically generated code for a
 * {@link org.optimizationBenchmarking.utils.math.functions.TernaryFunction
 * 3-ary} which returns a constant value.
 */
final class _Const3 extends TernaryFunction {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * @serial the instance of {@link java.lang.Number} holding the constant
   *         value returned by this function
   */
  private final Number m_const;

  /**
   * Create the
   * {@link org.optimizationBenchmarking.utils.math.functions.compound._Const3}
   * , a function which returns a constant value.
   *
   * @param constant
   *          the instance of {@link java.lang.Number} holding the constant
   *          value returned by this function
   * @throws IllegalArgumentException
   *           if {@code constant} is {@code null}
   */
  _Const3(final Number constant) {
    super();
    if (constant == null) {
      throw new IllegalArgumentException( //
          "Constant result of {@link org.optimizationBenchmarking.utils.math.functions.compound._Const3}, a function which returns a constant value, cannot be null."); //$NON-NLS-1$
    }
    this.m_const = constant;
  }

  /** {@inheritDoc} */
  @Override
  public final byte computeAsByte(final byte x0, final byte x1,
      final byte x2) {
    return this.m_const.byteValue();
  }

  /** {@inheritDoc} */
  @Override
  public final short computeAsShort(final short x0, final short x1,
      final short x2) {
    return this.m_const.shortValue();
  }

  /** {@inheritDoc} */
  @Override
  public final int computeAsInt(final int x0, final int x1, final int x2) {
    return this.m_const.intValue();
  }

  /** {@inheritDoc} */
  @Override
  public final long computeAsLong(final long x0, final long x1,
      final long x2) {
    return this.m_const.longValue();
  }

  /** {@inheritDoc} */
  @Override
  public final float computeAsFloat(final float x0, final float x1,
      final float x2) {
    return this.m_const.floatValue();
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x0, final double x1,
      final double x2) {
    return this.m_const.doubleValue();
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final int x0, final int x1,
      final int x2) {
    return this.m_const.doubleValue();
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final long x0, final long x1,
      final long x2) {
    return this.m_const.doubleValue();
  }

  /** {@inheritDoc} */
  @Override
  public final boolean isLongArithmeticAccurate() {
    return ((NumericalTypes.getTypes(this.m_const) & NumericalTypes.IS_LONG) != 0);
  }

  /** {@inheritDoc} */
  @Override
  public final void render(final IMath out,
      final IParameterRenderer renderer) {
    try (final IText number = out.number()) {
      this.render(number, renderer);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void render(final ITextOutput out,
      final IParameterRenderer renderer) {
    if (this.isLongArithmeticAccurate()) {
      out.append(this.m_const.longValue());
    } else {
      out.append(this.m_const.doubleValue());
    }
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return (786547 ^ this.m_const.hashCode());
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    return ((o instanceof _Const3) && (this.m_const
        .equals(((_Const3) o).m_const)));
  }
}
