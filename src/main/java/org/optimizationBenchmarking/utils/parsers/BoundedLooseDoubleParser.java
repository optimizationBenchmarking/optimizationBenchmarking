package org.optimizationBenchmarking.utils.parsers;

/** A bounded double parser */
public final class BoundedLooseDoubleParser extends LooseDoubleParser {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the minimum allowed value */
  private final double m_min;

  /** the maximum allowed value */
  private final double m_max;

  /**
   * Create the bounded double parser
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
  public BoundedLooseDoubleParser(final Number min, final Number max) {
    this(min.doubleValue(), max.doubleValue());
  }

  /**
   * Create the bounded double parser
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
  public BoundedLooseDoubleParser(final double min, final double max) {
    super();

    if (max < min) {
      throw new IllegalArgumentException((((NumberParser.MIN_MAX + //
          min) + ',') + max) + ']');
    }
    if ((max != max) || (min != min)) {
      throw new IllegalArgumentException((((NumberParser.NAN + //
          min) + ',') + max) + ']');
    }
    this.m_min = min;
    this.m_max = max;
  }

  /** {@inheritDoc} */
  @Override
  public void validateDouble(final double value)
      throws IllegalArgumentException {
    super.validateDouble(value);

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
  public final double getLowerBoundDouble() {
    return this.m_min;
  }

  /** {@inheritDoc} */
  @Override
  public final double getUpperBoundDouble() {
    return this.m_max;
  }
}
