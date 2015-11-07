package org.optimizationBenchmarking.utils.parsers;

import org.optimizationBenchmarking.utils.math.NumericalTypes;
import org.optimizationBenchmarking.utils.reflection.ReflectionUtils;

/**
 * A parser loosely parsing for one number. Passing {@code null} to
 * {@link #parseObject(Object)} will result in return value {@code null}
 * and not throw an exception.
 */
public class AnyNumberParser extends NumberParser<Number> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance of the strict number parser */
  public static final AnyNumberParser INSTANCE = new AnyNumberParser();

  /** create the parser */
  protected AnyNumberParser() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final Class<Number> getOutputClass() {
    return Number.class;
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unused")
  @Override
  public final Number parseString(final String string) {
    final _PreparedString prep;
    Number retVal;
    double d;
    int i;
    long l;
    String str;
    _TextConst cnst;
    Object number;
    int base;

    checker: {

      try {
        // first we try to cast the string directly to a number
        // this will be the fast execution path for 'correctly' formatted
        // numbers
        retVal = Byte.valueOf(string);
        break checker;

      } catch (final NumberFormatException origNumberError) {
        try {
          // we now try shorts
          retVal = Short.valueOf(string);
          break checker;
        } catch (final NumberFormatException error2) {
          origNumberError.addSuppressed(error2);

          try {
            // we now try ints
            retVal = Integer.valueOf(string);
            break checker;
          } catch (final NumberFormatException error3) {
            origNumberError.addSuppressed(error3);

            try {
              // we now try longs
              retVal = Long.valueOf(string);
              break checker;
            } catch (final NumberFormatException error4) {
              origNumberError.addSuppressed(error4);

              try {
                retVal = NumericalTypes
                    .valueOf(Double.parseDouble(string));
                break checker;
              } catch (final NumberFormatException error5) {
                origNumberError.addSuppressed(error5);
              }
            }
          }
        }

        // ok, the string does not follow java's default number format
        // so now, we check whether it is hexadecimal/octal/binary
        // formatted, a constant, or even a static member identifier

        prep = new _PreparedString(string);

        outer: while ((str = prep.next()) != null) {
          // we now iterate over the prepared strings: step-by-step, we
          // will try
          // to process the string by, e.g., trimming it; removing +, -, (,
          // ), etc; removing internal white spaces and underscores (to
          // match
          // Java 7's new binary syntax) and so on - until we can either
          // parse
          // it or have to give up

          if (str != string) {
            // try again to parse the string directly
            try {
              i = Integer.parseInt(str);
              retVal = NumericalTypes.valueOf(prep.getReturn(i));
              break checker;
            } catch (final Throwable canBeIgnored) {
              // we ignore this exception, as we will throw the original
              // one on failure
            }

            try {
              l = Long.parseLong(str);
              retVal = NumericalTypes.valueOf(prep.getReturn(l));
              break checker;
            } catch (final Throwable canBeIgnored) {
              // we ignore this exception, as we will throw the original
              // one on failure
            }

            try {
              d = Double.parseDouble(str);
              retVal = NumericalTypes.valueOf(prep.getReturn(d));
              break checker;
            } catch (final Throwable canBeIgnored) {
              // we ignore this exception, as we will throw the original
              // one on failure
            }
          }

          // let's see if it was a constant in some other base
          if ((str.length() > 2) && (str.charAt(0) == '0')) {
            if ((base = _PreparedString._getBase(str.charAt(1))) != 0) {
              try {
                retVal = NumericalTypes.valueOf(prep.getReturn(Long
                    .parseLong(str.substring(2), base)));
                break checker;
              } catch (final Throwable canBeIgnored) {
                // we ignore this exception, as we will throw the original
                // one on failure
              }
            }
          }

          // does the string correspond to a constant we know?
          cnst = _TextConst.findConst(str);
          if (cnst != null) {
            if (cnst.hasInt()) {
              retVal = NumericalTypes.valueOf(prep.getReturn(cnst.m_l));
              break checker;
            }
            break outer;
          }

          // ok, it is no constant, maybe it is a public static final
          // member?
          try {
            number = ReflectionUtils.getInstanceByName(Object.class, str);
            if ((number != null) && (number != string) && (number != str)) {
              return this.parseObject(number);
            }
          } catch (final Throwable canBeIgnored) {
            // ignore this exception: it will be thrown if no member fits
            // in which case we will throw the original error anyway at the
            // end
          }
        }

        // ok, all our ideas to resolve the number have failed - throw
        // original
        // error again
        throw origNumberError;
      }
    }

    this.validate(retVal);
    return retVal;
  }

  /** {@inheritDoc} */
  @Override
  public Number parseObject(final Object o) {
    final Number retVal;
    final double ret;

    if (o instanceof Number) {
      retVal = ((Number) o);
      this.validate(retVal);
      return retVal;
    }

    if (o instanceof Double) {
      retVal = ((Double) o);
      ret = retVal.doubleValue();
      this.validateDouble(ret);
      return retVal;
    }

    if (o instanceof String) {
      return this.parseString((String) o);
    }

    if (o != null) {
      throw new IllegalArgumentException(//
          o + " is not a valid number."); //$NON-NLS-1$
    }
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public void validate(final Number instance) {
    if (instance == null) {
      throw new IllegalArgumentException("Number must not be null."); //$NON-NLS-1$
    }
  }

  /**
   * write replace
   *
   * @return the replacement
   */
  private final Object writeReplace() {
    return AnyNumberParser.INSTANCE;
  }

  /**
   * read resolve
   *
   * @return the replacement
   */
  private final Object readResolve() {
    return AnyNumberParser.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean areBoundsInteger() {
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final long getLowerBoundLong() {
    return Long.MIN_VALUE;
  }

  /** {@inheritDoc} */
  @Override
  public final long getUpperBoundLong() {
    return Long.MAX_VALUE;
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
  public final void validateDouble(final double d) {//
  }

  /** {@inheritDoc} */
  @Override
  public final void validateLong(final long l) {//
  }
}
