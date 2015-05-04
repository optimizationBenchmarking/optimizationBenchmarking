package org.optimizationBenchmarking.utils.math.units;

/** a conversion function based on double multiplication */
final class _ConvertDoubleMul extends _ConversionFunction {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the multiplier */
  final double m_multiplier;

  /**
   * create
   *
   * @param multiplier
   *          the multiplier
   */
  _ConvertDoubleMul(final double multiplier) {
    super();
    this.m_multiplier = multiplier;
  }

  /** {@inheritDoc} */
  @Override
  public final double computeAsDouble(final double x0) {
    return (x0 * this.m_multiplier);
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return ("*" + this.m_multiplier); //$NON-NLS-1$
  }
}
