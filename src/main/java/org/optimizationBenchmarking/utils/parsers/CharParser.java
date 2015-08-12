package org.optimizationBenchmarking.utils.parsers;

/**
 * A {@code char}-parser.
 */
public abstract class CharParser extends Parser<Character> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** create the parser */
  protected CharParser() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final Class<Character> getOutputClass() {
    return Character.class;
  }

  /**
   * Validate the parsing result
   *
   * @param value
   *          the parsing result
   * @throws IllegalArgumentException
   *           if the result is not admissible
   */
  public void validateChar(final char value)
      throws IllegalArgumentException {
    if (!(Character.isDefined(value))) {
      throw new IllegalArgumentException("Character code \\u" + //$NON-NLS-1$
          Integer.toHexString(value) + " is undefined."); //$NON-NLS-1$
    }
  }

  /**
   * Parse a string to a char
   *
   * @param string
   *          the string
   * @return the return type
   */
  public char parseCharacter(final String string) {
    final char b;

    if (string == null) {
      throw new IllegalArgumentException(//
          "Null string is not a valid char."); //$NON-NLS-1$
    }

    if (string.length() != 1) {
      throw new IllegalArgumentException(//
          "String '" + string + //$NON-NLS-1$
              "' must have length 1 to be interpreted as char, but has length " //$NON-NLS-1$
              + string.length());
    }
    b = string.charAt(0);
    this.validateChar(b);
    return b;
  }

  /** {@inheritDoc} */
  @Override
  public final Character parseString(final String string) {
    return Character.valueOf(this.parseCharacter(string));
  }

  /** {@inheritDoc} */
  @Override
  public Character parseObject(final Object o) {
    final Character retVal;
    final char ret;

    if (o instanceof Character) {
      retVal = ((Character) o);
      ret = retVal.charValue();
      this.validateChar(ret);
      return retVal;
    }

    if (o instanceof String) {
      return this.parseString((String) o);
    }

    throw new IllegalArgumentException(//
        o + " is not a valid char."); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final void validate(final Character instance) {
    this.validateChar(instance.charValue());
  }
}
