package org.optimizationBenchmarking.utils.parsers;

/**
 * A strict {@code float}-parser. A strict parser will behave similar to
 * {@link java.lang.Float#parseFloat(String)} and throw exceptions quickly
 * if its input does not comply. Its extension
 * {@link org.optimizationBenchmarking.utils.parsers.FloatParser} will,
 * instead, behave much more generous and try several heuristics to
 * determine a suitable {@code float} value corresponding to a given
 * {@link java.lang.String string} or {@link java.lang.Object object}.
 */
public class StrictFloatParser extends NumberParser<Float> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance of the float parser */
  public static final StrictFloatParser STRICT_INSTANCE = new StrictFloatParser();

  /** create the parser */
  protected StrictFloatParser() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final Class<Float> getOutputClass() {
    return Float.class;
  }

  /**
   * Validate the parsing result
   *
   * @param value
   *          the parsing result
   * @throws IllegalArgumentException
   *           if the result is not admissible
   */
  public void validateFloat(final float value)
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
  public float parseFloat(final String string) {
    final float f;
    final double d;
    long l;
    boolean hasLong;

    f = Float.parseFloat(string);

    d = Double.parseDouble(string);
    if (d != f) {
      throw new IllegalArgumentException(//
          "Loss of fidelity when parsing '" + string + //$NON-NLS-1$
              "' to a float (" + f + //$NON-NLS-1$
              ") compared to parsing it to a double (" + //$NON-NLS-1$
              d + ")."); //$NON-NLS-1$
    }

    hasLong = false;
    l = 0l;
    try {
      l = Long.parseLong(string);
      hasLong = true;
    } catch (final Throwable t) {
      //
    }
    if (hasLong && (l != f)) {
      throw new IllegalArgumentException(//
          "Loss of fidelity when parsing '" + string + //$NON-NLS-1$
              "' to a float (" + f + //$NON-NLS-1$
              ") compared to parsing it to a long (" + //$NON-NLS-1$
              l + ")."); //$NON-NLS-1$
    }

    this.validateFloat(f);
    return f;
  }

  /** {@inheritDoc} */
  @Override
  public final Float parseString(final String string) {
    return Float.valueOf(this.parseFloat(string));
  }

  /** {@inheritDoc} */
  @Override
  public Float parseObject(final Object o) {
    final Float retVal;
    final float ret;

    if (o instanceof Float) {
      retVal = ((Float) o);
      ret = retVal.floatValue();
      this.validateFloat(ret);
      return retVal;
    }

    if (o instanceof String) {
      return this.parseString((String) o);
    }

    throw new IllegalArgumentException(//
        o + " is not a valid float."); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final void validate(final Float instance) {
    this.validateFloat(instance.floatValue());
  }

  /**
   * write replace
   *
   * @return the replacement
   */
  private final Object writeReplace() {
    return StrictFloatParser.STRICT_INSTANCE;
  }

  /**
   * read resolve
   *
   * @return the replacement
   */
  private final Object readResolve() {
    return StrictFloatParser.STRICT_INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean areBoundsInteger() {
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final long getLowerBoundLong() {
    final long l;
    final double d;

    d = this.getLowerBoundDouble();
    l = ((long) (Math.max(Long.MIN_VALUE, d)));
    return ((l < d) ? (l + 1l) : l);
  }

  /** {@inheritDoc} */
  @Override
  public final long getUpperBoundLong() {
    final long l;
    final double d;

    d = this.getUpperBoundDouble();
    l = ((long) (Math.min(Long.MAX_VALUE, d)));
    return ((l > d) ? (l - 1l) : l);
  }

  /** {@inheritDoc} */
  @Override
  public double getLowerBoundDouble() {
    return Double.NEGATIVE_INFINITY;
  }

  /** {@inheritDoc} */
  @Override
  public double getUpperBoundDouble() {
    return Double.POSITIVE_INFINITY;
  }

  /** {@inheritDoc} */
  @Override
  public final void validateDouble(final double d) {
    if (d <= Double.NEGATIVE_INFINITY) {
      this.validateFloat(Float.NEGATIVE_INFINITY);
    } else {
      if (d >= Double.POSITIVE_INFINITY) {
        this.validateFloat(Float.POSITIVE_INFINITY);
      } else {
        if (d != d) {
          this.validateFloat(Float.NaN);
        } else {
          if (d == 0d) {
            this.validateFloat(0f);
          } else {
            if (d == (-0d)) {
              this.validateFloat(-0f);
            } else {
              if ((d < (-Float.MAX_VALUE)) || (d > (Float.MAX_VALUE))) {
                throw new IllegalArgumentException(
                    ((((d + " is not a valid float value, since it is out of the range [") + //$NON-NLS-1$
                    (-Float.MAX_VALUE)) + ',') + //
                    (Float.MAX_VALUE))
                        + "]."); //$NON-NLS-1$
              }

              if (((d > (0d)) && (d < Float.MIN_VALUE)) || //
                  ((d < (0d)) && (d > (-Float.MIN_VALUE)))) {
                throw new IllegalArgumentException(
                    ((((d + " is not a valid float value, since its scale is too small. Non-zero values must be out of the range (") + //$NON-NLS-1$
                    (-Float.MIN_VALUE)) + ',') + //
                    (Float.MIN_VALUE))
                        + ")."); //$NON-NLS-1$
              }

              this.validateFloat((float) d);
            }
          }
        }
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void validateLong(final long l) {
    final float f;

    f = l;
    if (l != f) {
      throw new IllegalArgumentException(l
          + " cannot be converted to a float without loss of fidelity."); //$NON-NLS-1$
    }
    this.validateFloat(f);
  }
}
