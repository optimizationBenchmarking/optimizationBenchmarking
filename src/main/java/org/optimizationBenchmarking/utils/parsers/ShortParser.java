package org.optimizationBenchmarking.utils.parsers;

import org.optimizationBenchmarking.utils.hash.HashUtils;

/**
 * A strict {@code short}-parser.
 */
public abstract class ShortParser extends NumberParser<Short> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** create the parser */
  protected ShortParser() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final Class<Short> getOutputClass() {
    return Short.class;
  }

  /**
   * Validate the parsing result
   *
   * @param value
   *          the parsing result
   * @throws IllegalArgumentException
   *           if the result is not admissible
   */
  public void validateShort(final short value)
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
  public short parseShort(final String string) {
    final short b;

    b = Short.parseShort(string);
    this.validateShort(b);
    return b;
  }

  /** {@inheritDoc} */
  @Override
  public final Short parseString(final String string) {
    return Short.valueOf(this.parseShort(string));
  }

  /** {@inheritDoc} */
  @Override
  public Short parseObject(final Object o) {
    final Short retVal;
    final short ret;

    if (o instanceof Short) {
      retVal = ((Short) o);
      ret = retVal.shortValue();
      this.validateShort(ret);
      return retVal;
    }

    if (o instanceof String) {
      return this.parseString((String) o);
    }

    throw new IllegalArgumentException(//
        o + " is not a valid short."); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final void validate(final Short instance) {
    this.validateShort(instance.shortValue());
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
    final short b;

    if (d != d) {
      throw new IllegalArgumentException(d
          + " (not-a-number) is not a valid short value."); //$NON-NLS-1$
    }

    if ((d < Short.MIN_VALUE) || (d > Short.MAX_VALUE)) {
      throw new IllegalArgumentException(
          ((((d + " is not a valid short value, since it is out of the range ") + //$NON-NLS-1$
          Short.MIN_VALUE) + "..") + //$NON-NLS-1$
          Short.MAX_VALUE) + '.');
    }

    b = ((short) d);
    if (b != d) {
      throw new IllegalArgumentException(d
          + " cannot be converted to a short without loss of fidelity."); //$NON-NLS-1$
    }
    this.validateShort(b);
  }

  /** {@inheritDoc} */
  @Override
  public final void validateLong(final long l) {
    if ((l < Short.MIN_VALUE) || (l > Short.MAX_VALUE)) {
      throw new IllegalArgumentException(
          ((((l + " is not a valid short value, since it is out of the range ") + //$NON-NLS-1$
          Short.MIN_VALUE) + "..") + //$NON-NLS-1$
          Short.MAX_VALUE) + '.');
    }
    this.validateShort((short) l);
  }

  /**
   * Get the lower bound as short
   *
   * @return the lower bound as short
   */
  public short getLowerBound() {
    return Short.MIN_VALUE;
  }

  /**
   * Get the upper bound as short
   *
   * @return the upper bound as short
   */
  public short getUpperBound() {
    return Short.MAX_VALUE;
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return HashUtils.combineHashes(886741,//
        HashUtils.combineHashes(//
            HashUtils.hashCode(this.getLowerBound()),//
            HashUtils.hashCode(this.getUpperBound())));
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(final Object other) {
    final ShortParser parser;
    if (other == this) {
      return true;
    }
    if (other instanceof ShortParser) {
      parser = ((ShortParser) other);
      return ((parser.getLowerBound() == this.getLowerBound()) && //
      (parser.getUpperBound() == this.getUpperBound()));
    }
    return false;
  }
}
