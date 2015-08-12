package org.optimizationBenchmarking.utils.parsers;

/** A bounded float parser */
public final class BoundedLooseFloatParser extends LooseFloatParser {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the minimum allowed value */
  private final float m_min;

  /** the maximum allowed value */
  private final float m_max;

  /**
   * Create the bounded float parser
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
  public BoundedLooseFloatParser(final Number min, final Number max) {
    this(min.floatValue(), max.floatValue());
  }

  /**
   * Create the bounded float parser
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
  public BoundedLooseFloatParser(final float min, final float max) {
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
  public void validateFloat(final float value)
      throws IllegalArgumentException {
    super.validateFloat(value);

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
  public final float getLowerBound() {
    return this.m_min;
  }

  /** {@inheritDoc} */
  @Override
  public final float getUpperBound() {
    return this.m_max;
  }
}
