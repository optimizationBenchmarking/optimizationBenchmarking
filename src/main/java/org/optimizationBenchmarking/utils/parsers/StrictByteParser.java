package org.optimizationBenchmarking.utils.parsers;

/**
 * A strict {@code byte}-parser. A strict parser will behave similar to
 * {@link java.lang.Byte#parseByte(String)} and throw exceptions quickly if
 * its input does not comply. Its extension
 * {@link org.optimizationBenchmarking.utils.parsers.ByteParser} will,
 * instead, behave much more generous and try several heuristics to
 * determine a suitable {@code byte} value corresponding to a given
 * {@link java.lang.String string} or {@link java.lang.Object object}.
 */
public class StrictByteParser extends NumberParser<Byte> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance of the byte parser */
  public static final StrictByteParser STRICT_INSTANCE = new StrictByteParser();

  /** create the parser */
  protected StrictByteParser() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final Class<Byte> getOutputClass() {
    return Byte.class;
  }

  /**
   * Validate the parsing result
   *
   * @param value
   *          the parsing result
   * @throws IllegalArgumentException
   *           if the result is not admissible
   */
  public void validateByte(final byte value)
      throws IllegalArgumentException {
    //
  }

  /**
   * Parse the string
   *
   * @param string
   *          the string
   * @return the return type
   */
  public byte parseByte(final String string) {
    final byte b;

    b = Byte.parseByte(string);
    this.validateByte(b);
    return b;
  }

  /** {@inheritDoc} */
  @Override
  public final Byte parseString(final String string) {
    return Byte.valueOf(this.parseByte(string));
  }

  /** {@inheritDoc} */
  @Override
  public Byte parseObject(final Object o) {
    final Byte retVal;
    final byte ret;

    if (o instanceof Byte) {
      retVal = ((Byte) o);
      ret = retVal.byteValue();
      this.validateByte(ret);
      return retVal;
    }

    if (o instanceof String) {
      return this.parseString((String) o);
    }

    throw new IllegalArgumentException(//
        o + " is not a valid byte."); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final void validate(final Byte instance) {
    this.validateByte(instance.byteValue());
  }

  /**
   * write replace
   *
   * @return the replacement
   */
  private final Object writeReplace() {
    return StrictByteParser.STRICT_INSTANCE;
  }

  /**
   * read resolve
   *
   * @return the replacement
   */
  private final Object readResolve() {
    return StrictByteParser.STRICT_INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean areBoundsInteger() {
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public long getLowerBoundLong() {
    return Byte.MIN_VALUE;
  }

  /** {@inheritDoc} */
  @Override
  public long getUpperBoundLong() {
    return Byte.MAX_VALUE;
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
    final byte b;

    if (d != d) {
      throw new IllegalArgumentException(d
          + " (not-a-number) is not a valid byte value."); //$NON-NLS-1$
    }

    if ((d < Byte.MIN_VALUE) || (d > Byte.MAX_VALUE)) {
      throw new IllegalArgumentException(
          ((((d + " is not a valid byte value, since it is out of the range ") + //$NON-NLS-1$
          Byte.MIN_VALUE) + "..") + //$NON-NLS-1$
          Byte.MAX_VALUE) + '.');
    }

    b = ((byte) d);
    if (b != d) {
      throw new IllegalArgumentException(d
          + " cannot be converted to a byte without loss of fidelity."); //$NON-NLS-1$
    }
    this.validateByte(b);
  }

  /** {@inheritDoc} */
  @Override
  public final void validateLong(final long l) {
    if ((l < Byte.MIN_VALUE) || (l > Byte.MAX_VALUE)) {
      throw new IllegalArgumentException(
          ((((l + " is not a valid byte value, since it is out of the range ") + //$NON-NLS-1$
          Byte.MIN_VALUE) + "..") + //$NON-NLS-1$
          Byte.MAX_VALUE) + '.');
    }
    this.validateByte((byte) l);
  }

}
