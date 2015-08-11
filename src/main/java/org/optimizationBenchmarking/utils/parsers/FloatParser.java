package org.optimizationBenchmarking.utils.parsers;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.hash.HashUtils;

/**
 * A parser for {@code float}s which can also interpret things such as
 * constants.
 */
public class FloatParser extends StrictFloatParser {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance of the float parser */
  public static final FloatParser INSTANCE = new FloatParser();

  /** create the parser */
  protected FloatParser() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final float parseFloat(final String string) {
    final float retVal;
    final double d;

    d = DoubleParser.INSTANCE.parseDouble(string);
    if (d <= Double.NEGATIVE_INFINITY) {
      retVal = Float.NEGATIVE_INFINITY;
    } else {
      if (d >= Double.POSITIVE_INFINITY) {
        retVal = Float.POSITIVE_INFINITY;
      } else {
        if (Double.isNaN(d)) {
          retVal = Float.NaN;
        } else {
          if (d == 0d) {
            retVal = 0f;
          } else {
            if (d == (-0d)) {
              retVal = (-0f);
            } else {
              if ((d > Float.MAX_VALUE) || (d < (-Float.MAX_VALUE))) {
                throw new IllegalArgumentException(//
                    ((((("The finite double value " + d) + //$NON-NLS-1$
                        " is outside of the valid range [") + (-Float.MAX_VALUE)) + //$NON-NLS-1$
                        ',') + Float.MAX_VALUE)
                        + "] of floats."); //$NON-NLS-1$
              }
              if ((d > (-1d)) && (d < 1d)
                  && ((d > (-Float.MIN_VALUE)) && (d < Float.MIN_VALUE))) {
                throw new IllegalArgumentException(//
                    ((((("The finite double value " + d) + //$NON-NLS-1$
                        " has a too small scale for the valid range [") + (-Float.MIN_VALUE)) + //$NON-NLS-1$
                        ',') + Float.MIN_VALUE)
                        + "] of floats."); //$NON-NLS-1$
              }
              retVal = ((float) d);
            }
          }
        }
      }
    }

    this.validateFloat(retVal);
    return retVal;
  }

  /** {@inheritDoc} */
  @Override
  public final Float parseObject(final Object o) {
    final Float retVal;
    final float ret;

    if (o instanceof Number) {

      if (o instanceof Float) {
        retVal = ((Float) o);
        ret = retVal.floatValue();
      } else {
        ret = ((Number) o).floatValue();
        retVal = null;
      }
    } else {
      return this.parseString(String.valueOf(o));
    }

    this.validateFloat(ret);
    return ((retVal != null) ? retVal : Float.valueOf(ret));
  }

  /**
   * write replace
   *
   * @return the replacement
   */
  private final Object writeReplace() {
    return FloatParser.INSTANCE;
  }

  /**
   * read resolve
   *
   * @return the replacement
   */
  private final Object readResolve() {
    return FloatParser.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return HashUtils.combineHashes(87293,//
        HashUtils.combineHashes(//
            HashUtils.hashCode(this.getLowerBound()),//
            HashUtils.hashCode(this.getUpperBound())));
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object other) {
    final FloatParser parser;
    if (other == this) {
      return true;
    }
    if (other instanceof FloatParser) {
      parser = ((FloatParser) other);
      return (EComparison.EQUAL.compare(parser.getLowerBound(),
          this.getLowerBound()) && //
      EComparison.EQUAL.compare(parser.getUpperBound(),
          this.getUpperBound()));
    }
    return false;
  }
}
