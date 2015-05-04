package org.optimizationBenchmarking.utils.math.units;

import org.optimizationBenchmarking.utils.math.Rational;

/**
 * a conversion function based on rational multiplication
 */
final class _ConvertRationalMul extends _ConversionFunction {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the multiplier */
  private final Rational m_multiplier;

  /**
   * create
   *
   * @param multiplier
   *          the multiplier
   */
  _ConvertRationalMul(final Rational multiplier) {
    super();
    this.m_multiplier = multiplier;
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x0) {
    return this.m_multiplier.multiply(x0);
  }

  /** {@inheritDoc} */
  @Override
  public final long computeAsLong(final long x0) {
    final Rational r;

    r = this.m_multiplier.multiply(x0);
    if (r.isReal()) {
      return r.longValue();
    }
    return ((long) (Math.max(Long.MIN_VALUE,
        Math.min(Long.MAX_VALUE, this.computeAsDouble(x0)))));
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return ("*" + this.m_multiplier); //$NON-NLS-1$
  }
}
