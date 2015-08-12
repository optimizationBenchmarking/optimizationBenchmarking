package org.optimizationBenchmarking.utils.parsers;

import org.optimizationBenchmarking.utils.hash.HashUtils;

/**
 * A basic {@code byte}-parser.
 */
public abstract class ByteParser extends NumberParser<Byte> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** create the parser */
  protected ByteParser() {
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

  /** {@inheritDoc} */
  @Override
  public final boolean areBoundsInteger() {
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public final long getLowerBoundLong() {
    return this.getLowerBound();
  }

  /** {@inheritDoc} */
  @Override
  public final long getUpperBoundLong() {
    return this.getUpperBound();
  }

  /** {@inheritDoc} */
  @Override
  public final double getLowerBoundDouble() {
    return this.getLowerBound();
  }

  /** {@inheritDoc} */
  @Override
  public final double getUpperBoundDouble() {
    return this.getUpperBound();
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

  /**
   * Get the lower bound as byte
   *
   * @return the lower bound as byte
   */
  public byte getLowerBound() {
    return Byte.MIN_VALUE;
  }

  /**
   * Get the upper bound as byte
   *
   * @return the upper bound as byte
   */
  public byte getUpperBound() {
    return Byte.MAX_VALUE;
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return HashUtils.combineHashes(34525357,//
        HashUtils.combineHashes(//
            HashUtils.hashCode(this.getLowerBound()),//
            HashUtils.hashCode(this.getUpperBound())));
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(final Object other) {
    final ByteParser parser;
    if (other == this) {
      return true;
    }
    if (other instanceof ByteParser) {
      parser = ((ByteParser) other);
      return ((parser.getLowerBound() == this.getLowerBound()) && //
      (parser.getUpperBound() == this.getUpperBound()));
    }
    return false;
  }
}
