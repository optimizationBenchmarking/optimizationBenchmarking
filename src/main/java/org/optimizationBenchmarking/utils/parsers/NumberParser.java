package org.optimizationBenchmarking.utils.parsers;

import org.optimizationBenchmarking.utils.reflection.EPrimitiveType;

/**
 * A parser for a numerical type.
 *
 * @param <N>
 *          the numerical type
 */
public abstract class NumberParser<N extends Number> extends Parser<N> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;
  /** value */
  protected static final String MIN_MAX = "MinimumAggregate allowed value must be less or equal to maximum allowed value, but value range is ("; //$NON-NLS-1$
  /** value too small */
  protected static final String MBLOET = "Value must be larger or equal to "; //$NON-NLS-1$
  /** value is */
  protected static final String BI = " but is "; //$NON-NLS-1$
  /** value too large */
  protected static final String MBSOET = "Value must be smaller or equal to "; //$NON-NLS-1$

  /** create the parser */
  NumberParser() {
    super();
  }

  /**
   * Are the boundaries of the valid numerical range of this parser integer
   * valued?
   *
   * @return {@code true} if the bounds of the valid numerical range of
   *         this parser are integer values, {@code false} if they are
   *         floating point numbers
   */
  public abstract boolean areBoundsInteger();

  /**
   * Get the lower bound of this parser as {@code long} value. If the
   * bounds of the valid numerical range of this parser are
   * {@link #areBoundsInteger() integer-valued}, this will return the lower
   * bound directly. Otherwise, the lowest boundary value is returned which
   * would pass {@link #validateDouble(double)} and
   * {@link #validateLong(long)}
   *
   * @return the lower bound of the numerical range of this parser as
   *         {@code long}.
   */
  public abstract long getLowerBoundLong();

  /**
   * Get the upper bound of this parser as {@code long} value. If the
   * bounds of the valid numerical range of this parser are
   * {@link #areBoundsInteger() integer-valued}, this will return the upper
   * bound directly. Otherwise, the highest boundary value is returned
   * which would pass {@link #validateDouble(double)} and
   * {@link #validateLong(long)}
   *
   * @return the upper bound of the numerical range of this parser as
   *         {@code long}.
   */
  public abstract long getUpperBoundLong();

  /**
   * Get the lower bound of this parser as {@code double} value. If the
   * bounds of the valid numerical range of this parser are
   * {@link #areBoundsInteger() integer-valued}, this will return the
   * lowest {@code double} value which would pass
   * {@link #validateDouble(double)} and {@link #validateLong(long)}.
   * Otherwise (the bounds are floating point numbers}, the boundary will
   * be returned directly.
   *
   * @return the lower bound of the numerical range of this parser as
   *         {@code double}.
   */
  public abstract double getLowerBoundDouble();

  /**
   * Get the upper bound of this parser as {@code double} value. If the
   * bounds of the valid numerical range of this parser are
   * {@link #areBoundsInteger() integer-valued}, this will return the
   * highest {@code double} value which would pass
   * {@link #validateDouble(double)} and {@link #validateLong(long)}.
   * Otherwise (the bounds are floating point numbers}, the boundary will
   * be returned directly.
   *
   * @return the upper bound of the numerical range of this parser as
   *         {@code double}.
   */
  public abstract double getUpperBoundDouble();

  /**
   * Validate whether the given {@code double} value {@code d} is within
   * the valid numerical range of this parser.
   *
   * @param d
   *          the {@code double} value to check
   * @throws IllegalArgumentException
   *           if {@code d} is outside the valid range
   */
  public abstract void validateDouble(final double d);

  /**
   * Validate whether the given {@code long} value {@code l} is within the
   * valid numerical range of this parser.
   *
   * @param l
   *          the {@code long} value to check
   * @throws IllegalArgumentException
   *           if {@code l} is outside the valid range
   */
  public abstract void validateLong(final long l);

  /**
   * Create a number parser for the given type and bounds
   *
   * @param type
   *          the type
   * @param lowerBound
   *          the lower bound, or {@code null} if not specified
   * @param upperBound
   *          the upper bound, or {@code null} if not specified
   * @return the number parser
   */
  public static final NumberParser<?> createNumberParser(
      final EPrimitiveType type, final Number lowerBound,
      final Number upperBound) {
    long llb, lub;
    double dlb, dub;

    switch (type) {
      case BYTE: {
        llb = ((lowerBound != null) ? lowerBound.longValue()
            : Long.MIN_VALUE);
        lub = ((upperBound != null) ? upperBound.longValue()
            : Long.MAX_VALUE);

        if ((llb <= Byte.MIN_VALUE) && (lub >= Byte.MAX_VALUE)) {
          return ByteParser.INSTANCE;
        }
        return new BoundedByteParser(//
            ((byte) (Math.max(Byte.MIN_VALUE, llb))),//
            ((byte) (Math.min(Byte.MAX_VALUE, lub))));
      }
      case SHORT: {
        llb = ((lowerBound != null) ? lowerBound.longValue()
            : Long.MIN_VALUE);
        lub = ((upperBound != null) ? upperBound.longValue()
            : Long.MAX_VALUE);

        if ((llb <= Short.MIN_VALUE) && (lub >= Short.MAX_VALUE)) {
          return ShortParser.INSTANCE;
        }
        return new BoundedShortParser(//
            ((short) (Math.max(Short.MIN_VALUE, llb))),//
            ((short) (Math.min(Short.MAX_VALUE, lub))));
      }
      case INT: {
        llb = ((lowerBound != null) ? lowerBound.longValue()
            : Long.MIN_VALUE);
        lub = ((upperBound != null) ? upperBound.longValue()
            : Long.MAX_VALUE);

        if ((llb <= Integer.MIN_VALUE) && (lub >= Integer.MAX_VALUE)) {
          return IntParser.INSTANCE;
        }
        return new BoundedIntParser(//
            ((int) (Math.max(Integer.MIN_VALUE, llb))),//
            ((int) (Math.min(Integer.MAX_VALUE, lub))));
      }

      case LONG: {
        llb = ((lowerBound != null) ? lowerBound.longValue()
            : Long.MIN_VALUE);
        lub = ((upperBound != null) ? upperBound.longValue()
            : Long.MAX_VALUE);

        if ((llb <= Long.MIN_VALUE) && (lub >= Long.MAX_VALUE)) {
          return LongParser.INSTANCE;
        }
        return new BoundedLongParser(//
            Math.max(Long.MIN_VALUE, llb),//
            Math.min(Long.MAX_VALUE, lub));
      }

      case FLOAT: {
        dlb = ((lowerBound != null) ? lowerBound.doubleValue()
            : Double.NEGATIVE_INFINITY);
        dub = ((upperBound != null) ? upperBound.doubleValue()
            : Double.POSITIVE_INFINITY);

        if ((dlb <= Double.NEGATIVE_INFINITY)
            && (dub >= Double.POSITIVE_INFINITY)) {
          return FloatParser.INSTANCE;
        }
        return new BoundedFloatParser(//
            ((float) (Math.max(Float.NEGATIVE_INFINITY, dlb))),//
            ((float) (Math.min(Float.POSITIVE_INFINITY, dub))));
      }

      case DOUBLE: {
        dlb = ((lowerBound != null) ? lowerBound.doubleValue()
            : Double.NEGATIVE_INFINITY);
        dub = ((upperBound != null) ? upperBound.doubleValue()
            : Double.POSITIVE_INFINITY);

        if ((dlb <= Double.NEGATIVE_INFINITY)
            && (dub >= Double.POSITIVE_INFINITY)) {
          return DoubleParser.INSTANCE;
        }
        return new BoundedDoubleParser(//
            Math.max(Double.NEGATIVE_INFINITY, dlb),//
            Math.min(Double.POSITIVE_INFINITY, dub));
      }

      default: {
        throw new IllegalArgumentException(//
            "Cannot create a number parser for type " + //$NON-NLS-1$
            type);
      }
    }

  }
}
