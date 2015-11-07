package org.optimizationBenchmarking.utils.parsers;

import java.util.concurrent.atomic.AtomicBoolean;

import org.optimizationBenchmarking.utils.reflection.ReflectionUtils;

/** A parser for a given type */
public class LooseBooleanParser extends BooleanParser {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;
  /** the globally shared instance of the boolean parser */
  public static final LooseBooleanParser INSTANCE = new LooseBooleanParser();

  /** create the parser */
  protected LooseBooleanParser() {
    super();
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unused")
  @Override
  public final boolean parseBoolean(final String string) {
    final _PreparedString prep;
    boolean retVal;
    String str;
    _TextConst cnst;
    Object bool;
    double d;

    checker: {

      // first we try to cast the string directly to a boolean
      // this will be the fast execution path for 'correctly' formatted
      // text
      if (_TextConst.TRUE.equalsIgnoreCase(string)) {
        retVal = true;
        break checker;
      }
      if (_TextConst.FALSE.equalsIgnoreCase(string)) {
        retVal = false;
        break checker;
      }

      // ok, the string does not follow java's default number format
      // so now, we check whether it is hexadecimal/octal/binary formatted,
      // a
      // constant, or even a static member identifier

      prep = new _PreparedString(string);

      outer: while ((str = prep.next()) != null) {
        // we now iterate over the prepared strings: step-by-step, we will
        // try
        // to process the string by, e.g., trimming it; removing +, -, (,
        // ),
        // etc; removing internal white spaces and underscores (to match
        // Java
        // 7's new binary syntax) and so on - until we can either parse it
        // or
        // have to give up

        if (str != string) {
          if (_TextConst.TRUE.equalsIgnoreCase(str)) {
            retVal = prep.getReturn(true);
            break checker;
          }
          if (_TextConst.FALSE.equalsIgnoreCase(str)) {
            retVal = prep.getReturn(false);
            break checker;
          }
          try {
            d = Double.parseDouble(str);
            if ((d == 0d) || (d == (-0d))) {
              retVal = prep.getReturn(false);
              break checker;
            }
            if ((d == 1d) || (d == (-1d))) {
              retVal = prep.getReturn(true);
              break checker;
            }
          } catch (final NumberFormatException nfe) {
            // ignore
          }
        }

        // does the string correspond to a constant we know?
        cnst = _TextConst.findConst(str);
        if (cnst != null) {
          if (cnst.hasBoolean()) {
            retVal = prep.getReturn(cnst.m_b);
            break checker;
          }
          break outer;
        }

        // ok, it is no constant, maybe it is a public static final member?
        try {
          bool = ReflectionUtils.getInstanceByName(Object.class, str);
          if ((bool != null) && (bool != string) && (bool != str)) {
            retVal = prep.getReturn(this.__parseObjectRaw(bool));
            break checker;
          }
        } catch (final Throwable canBeIgnored) {
          // ignore this exception: it will be thrown if no member fits
          // in which case we will throw the original error anyway at the
          // end
        }
      }

      throw new IllegalArgumentException('\'' + string + //
          "' is not a valid Boolean value."); //$NON-NLS-1$
    }

    this.validateBoolean(retVal);
    return retVal;
  }

  /**
   * The raw parsing method for calling inside {@link #parseString(String)}
   *
   * @param o
   *          the object
   * @return the return value
   */
  private final boolean __parseObjectRaw(final Object o) {

    if (o instanceof Boolean) {
      return ((Boolean) o).booleanValue();
    }

    if (o instanceof AtomicBoolean) {
      return ((AtomicBoolean) o).get();
    }

    if (o instanceof Number) {
      return ((((Number) o).intValue()) != 0);
    }

    return this.parseBoolean(String.valueOf(o));
  }

  /** {@inheritDoc} */
  @Override
  public final Boolean parseObject(final Object o) {
    final Boolean retVal;
    final boolean ret;

    if (o instanceof Boolean) {
      retVal = ((Boolean) o);
      ret = retVal.booleanValue();
    } else {
      ret = this.__parseObjectRaw(o);
      retVal = null;
    }

    this.validateBoolean(ret);
    return ((retVal != null) ? retVal : Boolean.valueOf(ret));
  }

  /**
   * write replace
   *
   * @return the replacement
   */
  private final Object writeReplace() {
    return LooseBooleanParser.INSTANCE;
  }

  /**
   * read resolve
   *
   * @return the replacement
   */
  private final Object readResolve() {
    return LooseBooleanParser.INSTANCE;
  }
}
