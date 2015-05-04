package org.optimizationBenchmarking.utils.parsers;

/**
 * A strict {@code long}-parser. A strict parser will behave similar to
 * {@link java.lang.Long#parseLong(String)} and throw exceptions quickly if
 * its input does not comply. Its extension
 * {@link org.optimizationBenchmarking.utils.parsers.LongParser} will,
 * instead, behave much more generous and try several heuristics to
 * determine a suitable {@code long} value corresponding to a given
 * {@link java.lang.String string} or {@link java.lang.Object object}.
 */
public class StrictLongParser extends NumberParser<Long> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance of the long parser */
  public static final StrictLongParser STRICT_INSTANCE = new StrictLongParser();

  /** create the parser */
  protected StrictLongParser() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final Class<Long> getOutputClass() {
    return Long.class;
  }

  /**
   * Parse the string
   *
   * @param string
   *          the string
   * @return the return type
   */
  public long parseLong(final String string) {
    final long b;

    b = Long.parseLong(string);
    this.validateLong(b);
    return b;
  }

  /** {@inheritDoc} */
  @Override
  public final Long parseString(final String string) {
    return Long.valueOf(this.parseLong(string));
  }

  /** {@inheritDoc} */
  @Override
  public Long parseObject(final Object o) {
    final Long retVal;
    final long ret;

    if (o instanceof Long) {
      retVal = ((Long) o);
      ret = retVal.longValue();
      this.validateLong(ret);
      return retVal;
    }

    if (o instanceof String) {
      return this.parseString((String) o);
    }

    throw new IllegalArgumentException(//
        o + " is not a valid long."); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final void validate(final Long instance) {
    this.validateLong(instance.longValue());
  }

  /**
   * write replace
   *
   * @return the replacement
   */
  private final Object writeReplace() {
    return StrictLongParser.STRICT_INSTANCE;
  }

  /**
   * read resolve
   *
   * @return the replacement
   */
  private final Object readResolve() {
    return StrictLongParser.STRICT_INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean areBoundsInteger() {
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public long getLowerBoundLong() {
    return Long.MIN_VALUE;
  }

  /** {@inheritDoc} */
  @Override
  public long getUpperBoundLong() {
    return Long.MAX_VALUE;
  }

  /** {@inheritDoc} */
  @Override
  public final double getLowerBoundDouble() {
    return this.getLowerBoundLong();
  }

  /** {@inheritDoc} */
  @Override
  public final double getUpperBoundDouble() {
    return this.getUpperBoundLong();
  }

  /** {@inheritDoc} */
  @Override
  public final void validateDouble(final double d) {
    final long b;

    if (d != d) {
      throw new IllegalArgumentException(d
          + " (not-a-number) is not a valid long value."); //$NON-NLS-1$
    }

    if ((d < Long.MIN_VALUE) || (d > Long.MAX_VALUE)) {
      throw new IllegalArgumentException(
          ((((d + " is not a valid long value, since it is out of the range ") + //$NON-NLS-1$
          Long.MIN_VALUE) + "..") + //$NON-NLS-1$
          Long.MAX_VALUE) + '.');
    }

    b = ((long) d);
    if (b != d) {
      throw new IllegalArgumentException(d
          + " cannot be converted to a long without loss of fidelity."); //$NON-NLS-1$
    }
    this.validateLong(b);
  }

  /** {@inheritDoc} */
  @Override
  public void validateLong(final long l) {//
  }
}
