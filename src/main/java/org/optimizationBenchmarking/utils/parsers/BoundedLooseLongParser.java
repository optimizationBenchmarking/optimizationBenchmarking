package org.optimizationBenchmarking.utils.parsers;

/** A bounded long parser */
public final class BoundedLooseLongParser extends LooseLongParser {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the minimum allowed value */
  private final long m_min;

  /** the maximum allowed value */
  private final long m_max;

  /**
   * Create the bounded long parser
   *
   * @param min
   *          the minimum allowed value, a
   *          {@link java.lang.IllegalArgumentException
   *          IllegalArgumentException} will be thrown if this constraint
   *          is violated
   * @param max
   *          the maximum allowed value, a
   *          {@link java.lang.IllegalArgumentException
   *          IllegalArgumentException} will be thrown if this constraint
   *          is violated
   */
  public BoundedLooseLongParser(final Number min, final Number max) {
    this(min.longValue(), max.longValue());
  }

  /**
   * Create the bounded long parser
   *
   * @param min
   *          the minimum allowed value, a
   *          {@link java.lang.IllegalArgumentException
   *          IllegalArgumentException} will be thrown if this constraint
   *          is violated
   * @param max
   *          the maximum allowed value, a
   *          {@link java.lang.IllegalArgumentException
   *          IllegalArgumentException} will be thrown if this constraint
   *          is violated
   */
  public BoundedLooseLongParser(final long min, final long max) {
    super();

    if (max < min) {
      throw new IllegalArgumentException((((NumberParser.MIN_MAX + //
          min) + ',') + max) + ']');
    }
    this.m_min = min;
    this.m_max = max;
  }

  /** {@inheritDoc} */
  @Override
  public void validateLong(final long value)
      throws IllegalArgumentException {
    super.validateLong(value);

    if (value < this.m_min) {
      throw new IllegalArgumentException((((NumberParser.MBLOET + //
          this.m_min) + NumberParser.BI) + value) + '.'); //
    }

    if (value > this.m_max) {
      throw new IllegalArgumentException((((NumberParser.MBSOET + //
          this.m_max) + NumberParser.BI) + value) + '.'); //
    }
  }

  /** {@inheritDoc} */
  @Override
  public final long getLowerBoundLong() {
    return this.m_min;
  }

  /** {@inheritDoc} */
  @Override
  public final long getUpperBoundLong() {
    return this.m_max;
  }
}
