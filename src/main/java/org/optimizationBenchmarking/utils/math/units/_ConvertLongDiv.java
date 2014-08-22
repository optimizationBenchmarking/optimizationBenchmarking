package org.optimizationBenchmarking.utils.math.units;

/** a conversion function based on integer division */
final class _ConvertLongDiv extends _ConversionFunction {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the divisor */
  private final long m_divisor;

  /**
   * create
   * 
   * @param divisor
   *          the divisor
   */
  _ConvertLongDiv(final long divisor) {
    super();
    this.m_divisor = divisor;
  }

  /** {@inheritDoc} */
  @Override
  public final long compute(final long x0) {
    return (x0 / this.m_divisor);
  }

  /** {@inheritDoc} */
  @Override
  public final double compute(final double x0) {
    long l;
    if ((x0 > Long.MIN_VALUE) && (x0 < Long.MAX_VALUE)) {
      l = ((long) x0);
      if (l == x0) {
        if ((l % this.m_divisor) == 0l) {
          return (l / this.m_divisor);
        }
      }
    }

    return (x0 / this.m_divisor);
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return ("/" + this.m_divisor); //$NON-NLS-1$
  }
}
