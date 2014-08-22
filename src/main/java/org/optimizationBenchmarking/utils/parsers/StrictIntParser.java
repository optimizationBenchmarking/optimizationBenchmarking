package org.optimizationBenchmarking.utils.parsers;

/**
 * A strict {@code int}-parser. A strict parser will behave similar to
 * {@link java.lang.Integer#parseInt(String)} and throw exceptions quickly
 * if its input does not comply. Its extension
 * {@link org.optimizationBenchmarking.utils.parsers.IntParser} will,
 * instead, behave much more generous and try several heuristics to
 * determine a suitable {@code int} value corresponding to a given
 * {@link java.lang.String string} or {@link java.lang.Object object}.
 */
public class StrictIntParser extends NumberParser<Integer> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance of the int parser */
  public static final StrictIntParser STRICT_INSTANCE = new StrictIntParser();

  /** create the parser */
  protected StrictIntParser() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final Class<Integer> getOutputClass() {
    return Integer.class;
  }

  /**
   * Validate the parsing result
   * 
   * @param value
   *          the parsing result
   * @throws IllegalArgumentException
   *           if the result is not admissible
   */
  public void validateInt(final int value) throws IllegalArgumentException {
    //
  }

  /**
   * Parse the string
   * 
   * @param string
   *          the string
   * @return the return type
   */
  public int parseInt(final String string) {
    final int b;

    b = Integer.parseInt(string);
    this.validateInt(b);
    return b;
  }

  /** {@inheritDoc} */
  @Override
  public final Integer parseString(final String string) {
    return Integer.valueOf(this.parseInt(string));
  }

  /** {@inheritDoc} */
  @Override
  public Integer parseObject(final Object o) {
    final Integer retVal;
    final int ret;

    if (o instanceof Integer) {
      retVal = ((Integer) o);
      ret = retVal.intValue();
      this.validateInt(ret);
      return retVal;
    }

    if (o instanceof String) {
      return this.parseString((String) o);
    }

    throw new IllegalArgumentException(//
        o + " is not a valid int."); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final void validate(final Integer instance) {
    this.validateInt(instance.intValue());
  }

  /**
   * write replace
   * 
   * @return the replacement
   */
  private final Object writeReplace() {
    return StrictIntParser.STRICT_INSTANCE;
  }

  /**
   * read resolve
   * 
   * @return the replacement
   */
  private final Object readResolve() {
    return StrictIntParser.STRICT_INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean areBoundsInteger() {
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public long getLowerBoundLong() {
    return Integer.MIN_VALUE;
  }

  /** {@inheritDoc} */
  @Override
  public long getUpperBoundLong() {
    return Integer.MAX_VALUE;
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
    final int b;

    if (d != d) {
      throw new IllegalArgumentException(d
          + " (not-a-number) is not a valid int value."); //$NON-NLS-1$
    }

    if ((d < Integer.MIN_VALUE) || (d > Integer.MAX_VALUE)) {
      throw new IllegalArgumentException(
          ((((d + " is not a valid int value, since it is out of the range ") + //$NON-NLS-1$
          Integer.MIN_VALUE) + "..") + //$NON-NLS-1$
          Integer.MAX_VALUE) + '.');
    }

    b = ((int) d);
    if (b != d) {
      throw new IllegalArgumentException(d
          + " cannot be converted to a int without loss of fidelity."); //$NON-NLS-1$
    }
    this.validateInt(b);
  }

  /** {@inheritDoc} */
  @Override
  public final void validateLong(final long l) {
    if ((l < Integer.MIN_VALUE) || (l > Integer.MAX_VALUE)) {
      throw new IllegalArgumentException(
          ((((l + " is not a valid int value, since it is out of the range ") + //$NON-NLS-1$
          Integer.MIN_VALUE) + "..") + //$NON-NLS-1$
          Integer.MAX_VALUE) + '.');
    }
    this.validateInt((int) l);
  }

}
