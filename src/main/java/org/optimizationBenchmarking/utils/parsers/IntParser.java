package org.optimizationBenchmarking.utils.parsers;

import org.optimizationBenchmarking.utils.hash.HashUtils;

/**
 * A strict {@code int}-parser.
 */
public abstract class IntParser extends NumberParser<Integer> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** create the parser */
  protected IntParser() {
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

  /**
   * Get the lower bound as int
   *
   * @return the lower bound as int
   */
  public int getLowerBound() {
    return Integer.MIN_VALUE;
  }

  /**
   * Get the upper bound as int
   *
   * @return the upper bound as int
   */
  public int getUpperBound() {
    return Integer.MAX_VALUE;
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return HashUtils.combineHashes(44483,//
        HashUtils.combineHashes(//
            HashUtils.hashCode(this.getLowerBound()),//
            HashUtils.hashCode(this.getUpperBound())));
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(final Object other) {
    final IntParser parser;
    if (other == this) {
      return true;
    }
    if (other instanceof IntParser) {
      parser = ((IntParser) other);
      return ((parser.getLowerBound() == this.getLowerBound()) && //
      (parser.getUpperBound() == this.getUpperBound()));
    }
    return false;
  }
}
