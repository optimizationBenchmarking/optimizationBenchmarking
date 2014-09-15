package org.optimizationBenchmarking.utils.math.units;

/** a conversion function based on integer multiplication */
final class _ConvertLongMul extends _ConversionFunction {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the multiplier */
  private final long m_multiplier;

  /** the maximally allowed value */
  private final long m_max;

  /** the minimally allowed value */
  private final long m_min;

  /**
   * create
   * 
   * @param multiplier
   *          the multiplier
   */
  _ConvertLongMul(final long multiplier) {
    super();
    this.m_multiplier = multiplier;
    if (multiplier > 0L) {
      this.m_max = (Long.MAX_VALUE / multiplier);
      this.m_min = (Long.MIN_VALUE / multiplier);
    } else {
      if (multiplier == 0L) {
        this.m_max = Long.MIN_VALUE;
        this.m_min = Long.MAX_VALUE;
      } else {
        this.m_max = (Long.MIN_VALUE / multiplier);
        this.m_min = (Long.MAX_VALUE / multiplier);
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final long compute(final long x0) {
    if (x0 > this.m_max) {
      if (this.m_multiplier > 0L) {
        return Long.MAX_VALUE;
      }
      return Long.MIN_VALUE;
    }
    if (x0 < this.m_min) {
      if (this.m_multiplier > 0L) {
        return Long.MIN_VALUE;
      }
      return Long.MAX_VALUE;
    }
    return (x0 * this.m_multiplier);
  }

  /** {@inheritDoc} */
  @Override
  public final double compute(final double x0) {
    long l;
    if ((x0 > this.m_min) && (x0 < this.m_max)) {
      l = ((long) x0);
      if (l == x0) {
        l = this.compute(l);
        if ((l > Long.MIN_VALUE) && (l < Long.MAX_VALUE)) {
          return l;
        }
      }
    }
    return (x0 * this.m_multiplier);
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return ("*" + this.m_multiplier); //$NON-NLS-1$
  }
}
