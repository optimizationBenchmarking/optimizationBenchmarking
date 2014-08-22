package org.optimizationBenchmarking.utils.parsers;

import org.optimizationBenchmarking.utils.reflection.GetStaticConstantByName;

/** A parser for a given type */
public class LongParser extends StrictLongParser {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;
  /** the globally shared instance of the long parser */
  public static final LongParser INSTANCE = new LongParser();

  /** create the parser */
  LongParser() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final long parseLong(final String string) {
    final _PreparedString prep;
    long retVal;
    String str;
    _TextConst cnst;
    Object number;
    int base;

    checker: {

      try {
        // first we try to cast the string directly to a long
        // this will be the fast execution path for 'correctly' formatted
        // numbers
        retVal = java.lang.Long.parseLong(string);
        break checker;

      } catch (final NumberFormatException origNumberError) {
        // ok, the string does not follow java's default number format
        // so now, we check whether it is hexadecimal/octal/binary
        // formatted, a
        // constant, or even a static member identifier

        prep = new _PreparedString(string);

        outer: while ((str = prep.next()) != null) {
          // we now iterate over the prepared strings: step-by-step, we
          // will
          // try
          // to process the string by, e.g., trimming it; removing +, -, (,
          // ),
          // etc; removing internal white spaces and underscores (to match
          // Java
          // 7's new binary syntax) and so on - until we can either parse
          // it or
          // have to give up

          if (str != string) {
            // try again to parse the string directly
            try {
              retVal = prep.getReturn(java.lang.Long.parseLong(str));
              break checker;
            } catch (final Throwable canBeIgnored) {
              // we ignore this exception, as we will throw the original
              // one
              // on failure
            }
          }

          // let's see if it was a constant in some other base
          if ((str.length() > 2) && (str.charAt(0) == '0')) {
            if ((base = _PreparedString._getBase(str.charAt(1))) != 0) {
              try {
                retVal = prep.getReturn(java.lang.Long.parseLong(
                    str.substring(2), base));
                break checker;
              } catch (final Throwable canBeIgnored) {
                // we ignore this exception, as we will throw the original
                // one
                // on failure
              }
            }
          }

          // does the string correspond to a constant we know?
          cnst = _TextConst.findConst(str);
          if (cnst != null) {
            if (cnst.hasInt()) {
              retVal = prep.getReturn(cnst.m_l);
              break checker;
            }
            break outer;
          }

          // ok, it is no constant, maybe it is a public static final
          // member?
          try {
            number = new GetStaticConstantByName<>(str, Object.class)
                .call();
            if ((number != null) && (number != string) && (number != str)) {
              retVal = prep.getReturn(this.__parseObjectRaw(number));
              break checker;
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

    this.validateLong(retVal);
    return retVal;
  }

  /**
   * The raw parsing method for calling inside {@link #parseString(String)}
   * 
   * @param o
   *          the object
   * @return the return value
   */
  private final long __parseObjectRaw(final Object o) {
    if (o instanceof java.lang.Number) {
      return ((Number) o).longValue();
    }

    return this.parseLong(String.valueOf(o));
  }

  /** {@inheritDoc} */
  @Override
  public final Long parseObject(final Object o) {
    final Long retVal;
    final long ret;

    if (o instanceof Long) {
      retVal = ((Long) o);
      ret = retVal.longValue();
    } else {
      ret = this.__parseObjectRaw(o);
      retVal = null;
    }

    this.validateLong(ret);
    return ((retVal != null) ? retVal : Long.valueOf(ret));
  }

  /**
   * write replace
   * 
   * @return the replacement
   */
  private final Object writeReplace() {
    return LongParser.INSTANCE;
  }

  /**
   * read resolve
   * 
   * @return the replacement
   */
  private final Object readResolve() {
    return LongParser.INSTANCE;
  }
}
