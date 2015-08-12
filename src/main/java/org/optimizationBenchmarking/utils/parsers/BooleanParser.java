package org.optimizationBenchmarking.utils.parsers;

/**
 * A {@code boolean}-parser.
 */
public abstract class BooleanParser extends Parser<Boolean> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** create the parser */
  protected BooleanParser() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final Class<Boolean> getOutputClass() {
    return Boolean.class;
  }

  /**
   * Validate the parsing result
   *
   * @param value
   *          the parsing result
   * @throws IllegalArgumentException
   *           if the result is not admissible
   */
  public void validateBoolean(final boolean value)
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
  public boolean parseBoolean(final String string) {
    final boolean b;

    if ("false".equalsIgnoreCase(string)) { //$NON-NLS-1$
      b = false;
    } else {
      if ("true".equalsIgnoreCase(string)) { //$NON-NLS-1$
        b = true;
      } else {
        throw new IllegalArgumentException(//
            "Not a valid boolean value: " + string); //$NON-NLS-1$
      }
    }
    this.validateBoolean(b);
    return b;
  }

  /** {@inheritDoc} */
  @Override
  public final Boolean parseString(final String string) {
    return Boolean.valueOf(this.parseBoolean(string));
  }

  /** {@inheritDoc} */
  @Override
  public Boolean parseObject(final Object o) {
    final Boolean retVal;
    final boolean ret;

    if (o instanceof Boolean) {
      retVal = ((Boolean) o);
      ret = retVal.booleanValue();
      this.validateBoolean(ret);
      return retVal;
    }

    if (o instanceof String) {
      return this.parseString((String) o);
    }

    throw new IllegalArgumentException(//
        o + " is not a valid boolean."); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final void validate(final Boolean instance) {
    this.validateBoolean(instance.booleanValue());
  }
}
