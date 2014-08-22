package org.optimizationBenchmarking.utils.parsers;

/** A bounded byte parser */
public class BoundedByteParser extends ByteParser {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the minimum allowed value */
  private final byte m_min;

  /** the maximum allowed value */
  private final byte m_max;

  /**
   * Create the bounded byte parser
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
  public BoundedByteParser(final Number min, final Number max) {
    this(min.byteValue(), max.byteValue());
  }

  /**
   * Create the bounded byte parser
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
  public BoundedByteParser(final byte min, final byte max) {
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
  public void validateByte(final byte value)
      throws IllegalArgumentException {
    super.validateByte(value);

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
