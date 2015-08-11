package org.optimizationBenchmarking.utils.parsers;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.hash.HashUtils;

/**
 * A strict {@code double}-parser. A strict parser will behave similar to
 * {@link java.lang.Double#parseDouble(String)} and throw exceptions
 * quickly if its input does not comply. Its extension
 * {@link org.optimizationBenchmarking.utils.parsers.DoubleParser} will,
 * instead, behave much more generous and try several heuristics to
 * determine a suitable {@code double} value corresponding to a given
 * {@link java.lang.String string} or {@link java.lang.Object object}.
 */
public class StrictDoubleParser extends NumberParser<Double> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance of the double parser */
  public static final StrictDoubleParser STRICT_INSTANCE = new StrictDoubleParser();

  /** create the parser */
  protected StrictDoubleParser() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final Class<Double> getOutputClass() {
    return Double.class;
  }

  /**
   * Parse the string
   *
   * @param string
   *          the string
   * @return the return type
   */
  public double parseDouble(final String string) {
    final double d;
    boolean hasLong;
    long l;

    d = Double.parseDouble(string);

    hasLong = false;
    l = 0L;
    try {
      l = Long.parseLong(string);
      hasLong = true;
    } catch (final Throwable t) {
      //
    }
    if (hasLong && (l != d)) {
      throw new IllegalArgumentException(//
          "Loss of fidelity when parsing '" + string + //$NON-NLS-1$
              "' to a double (" + d + //$NON-NLS-1$
              ") compared to parsing it to a long (" + //$NON-NLS-1$
              l + ")."); //$NON-NLS-1$
    }

    this.validateDouble(d);

    return d;
  }

  /** {@inheritDoc} */
  @Override
  public final Double parseString(final String string) {
    return Double.valueOf(this.parseDouble(string));
  }

  /** {@inheritDoc} */
  @Override
  public Double parseObject(final Object o) {
    final Double retVal;
    final double ret;

    if (o instanceof Double) {
      retVal = ((Double) o);
      ret = retVal.doubleValue();
      this.validateDouble(ret);
      return retVal;
    }

    if (o instanceof String) {
      return this.parseString((String) o);
    }

    throw new IllegalArgumentException(//
        o + " is not a valid double."); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final void validate(final Double instance) {
    this.validateDouble(instance.doubleValue());
  }

  /**
   * write replace
   *
   * @return the replacement
   */
  private final Object writeReplace() {
    return StrictDoubleParser.STRICT_INSTANCE;
  }

  /**
   * read resolve
   *
   * @return the replacement
   */
  private final Object readResolve() {
    return StrictDoubleParser.STRICT_INSTANCE;
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
    return ((l < d) ? (l + 1L) : l);
  }

  /** {@inheritDoc} */
  @Override
  public final long getUpperBoundLong() {
    final long l;
    final double d;

    d = this.getUpperBoundDouble();
    l = ((long) (Math.min(Long.MAX_VALUE, d)));
    return ((l > d) ? (l - 1L) : l);
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
  public void validateDouble(final double d) {//
  }

  /** {@inheritDoc} */
  @Override
  public final void validateLong(final long l) {
    final double f;

    f = l;
    if (l != f) {
      throw new IllegalArgumentException(l
          + " cannot be converted to a double without loss of fidelity."); //$NON-NLS-1$
    }
    this.validateDouble(f);
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return HashUtils.combineHashes(556725031,//
        HashUtils.combineHashes(//
            HashUtils.hashCode(this.getLowerBoundDouble()),//
            HashUtils.hashCode(this.getUpperBoundDouble())));
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(final Object other) {
    final StrictDoubleParser parser;
    if (other == this) {
      return true;
    }
    if (other instanceof StrictDoubleParser) {
      parser = ((StrictDoubleParser) other);
      return (EComparison.EQUAL.compare(parser.getLowerBoundDouble(),
          this.getLowerBoundDouble()) && //
      EComparison.EQUAL.compare(parser.getUpperBoundDouble(),
          this.getUpperBoundDouble()));
    }
    return false;
  }
}
