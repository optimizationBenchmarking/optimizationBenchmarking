package org.optimizationBenchmarking.utils.parsers;

import org.optimizationBenchmarking.utils.reflection.ReflectionUtils;
import org.optimizationBenchmarking.utils.text.charset.Char;

/** A parser for a given type */
public class LooseCharParser extends CharParser {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;
  /** the globally shared instance of the char parser */
  public static final LooseCharParser INSTANCE = new LooseCharParser();

  /** create the parser */
  protected LooseCharParser() {
    super();
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unused")
  @Override
  public final char parseCharacter(final String string) {
    char retVal;
    final String str;
    Object var;
    int len;

    if (string == null) {
      throw new NullPointerException();
    }

    len = string.length();
    if (len <= 0) {
      throw new IllegalArgumentException("Empty string."); //$NON-NLS-1$
    }

    checker: {

      // first we try to cast the string directly to a boolean
      // this will be the fast execution path for 'correctly' formatted
      // text
      if (len == 1) {
        retVal = string.charAt(0);
        break checker;
      }

      str = LooseStringParser.INSTANCE.parseString(string);

      len = str.length();
      if (len <= 0) {
        throw new IllegalArgumentException("Empty string."); //$NON-NLS-1$
      }
      if (len == 1) {
        retVal = str.charAt(0);
        break checker;
      }

      // ok, it is no constant, maybe it is a public static final member?
      try {
        var = ReflectionUtils.getInstanceByName(Object.class, str);
        if ((var != null) && (var != string) && (var != str)) {
          retVal = this.__parseObjectRaw(var);
          break checker;
        }
      } catch (final Throwable canBeIgnored) {
        // ignore this exception: it will be thrown if no member fits
        // in which case we will throw the original error anyway at the
        // end
      }

      throw new IllegalArgumentException('\'' + string + //
          "' with length " + string.length() + //$NON-NLS-1$
          " is not a valid character value."); //$NON-NLS-1$
    }

    this.validateChar(retVal);
    return retVal;
  }

  /**
   * The raw parsing method for calling inside {@link #parseString(String)}
   *
   * @param o
   *          the object
   * @return the return value
   */
  private final char __parseObjectRaw(final Object o) {
    if (o instanceof Character) {
      return ((Character) o).charValue();
    }

    if (o instanceof Char) {
      return ((Char) o).getChar();
    }

    if (o instanceof Number) {
      return ((char) (((Number) o).intValue()));
    }

    return this.parseCharacter(String.valueOf(o));
  }

  /** {@inheritDoc} */
  @Override
  public final Character parseObject(final Object o) {
    final Character retVal;
    final char ret;

    if (o instanceof Character) {
      retVal = ((Character) o);
      ret = retVal.charValue();
    } else {
      ret = this.__parseObjectRaw(o);
      retVal = null;
    }

    this.validateChar(ret);
    return ((retVal != null) ? retVal : Character.valueOf(ret));
  }

  /**
   * write replace
   *
   * @return the replacement
   */
  private final Object writeReplace() {
    return LooseCharParser.INSTANCE;
  }

  /**
   * read resolve
   *
   * @return the replacement
   */
  private final Object readResolve() {
    return LooseCharParser.INSTANCE;
  }
}
