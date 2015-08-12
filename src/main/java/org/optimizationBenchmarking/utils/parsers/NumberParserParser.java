package org.optimizationBenchmarking.utils.parsers;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashSet;

import org.optimizationBenchmarking.utils.reflection.ReflectionUtils;

/** A parser for number parsers */
public final class NumberParserParser extends
    InstanceParser<NumberParser<Number>> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the package prefixes */
  private static final String[] PREFIXES;

  static {
    final LinkedHashSet<String> paths;

    paths = new LinkedHashSet<>();
    ReflectionUtils.addPackageOfClassToPrefixList(
        NumberParserParser.class, paths);
    PREFIXES = paths.toArray(new String[paths.size()]);
  }

  /** create */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  NumberParserParser() {
    super(((Class) (NumberParser.class)), NumberParserParser.PREFIXES);
  }

  /**
   * Try to obtain the parser via reflection.
   *
   * @param parserClass
   *          the parser class: must take two numbers as parameter
   * @param lowerBound
   *          the lower boundary
   * @param upperBound
   *          the upper boundary
   * @return the parser
   */
  @SuppressWarnings("rawtypes")
  private final NumberParser<Number> __parse(final String parserClass,
      final String lowerBound, final String upperBound) {
    Number lower, upper;
    RuntimeException re;
    Class<? extends NumberParser> clazz;

    try {
      lower = StrictLongParser.INSTANCE.parseString(lowerBound);
    } catch (final Throwable t1) {
      try {
        lower = StrictDoubleParser.INSTANCE.parseString(lowerBound);
      } catch (final Throwable t2) {
        try {
          lower = LooseLongParser.INSTANCE.parseString(lowerBound);
        } catch (final Throwable t3) {
          try {
            lower = LooseDoubleParser.INSTANCE.parseString(lowerBound);
          } catch (final Throwable t4) {
            re = new RuntimeException(//
                "The lower bound string '" + lowerBound + //$NON-NLS-1$
                    "' is not a number."); //$NON-NLS-1$
            re.addSuppressed(t1);
            re.addSuppressed(t2);
            re.addSuppressed(t3);
            re.addSuppressed(t4);
            throw re;
          }
        }
      }
    }

    try {
      upper = StrictLongParser.INSTANCE.parseString(upperBound);
    } catch (final Throwable t1) {
      try {
        upper = StrictDoubleParser.INSTANCE.parseString(upperBound);
      } catch (final Throwable t2) {
        try {
          upper = LooseLongParser.INSTANCE.parseString(upperBound);
        } catch (final Throwable t3) {
          try {
            upper = LooseDoubleParser.INSTANCE.parseString(upperBound);
          } catch (final Throwable t4) {
            re = new RuntimeException(//
                "The upper bound string '" + upperBound + //$NON-NLS-1$
                    "' is not a number."); //$NON-NLS-1$
            re.addSuppressed(t1);
            re.addSuppressed(t2);
            re.addSuppressed(t3);
            re.addSuppressed(t4);
            throw re;
          }
        }
      }
    }

    if ("double".equalsIgnoreCase(parserClass)) { //$NON-NLS-1$
      clazz = BoundedLooseDoubleParser.class;
    } else {
      if ("long".equalsIgnoreCase(parserClass)) { //$NON-NLS-1$
        clazz = BoundedLooseLongParser.class;
      } else {
        if ("int".equalsIgnoreCase(parserClass) || //$NON-NLS-1$
            "integer".equalsIgnoreCase(parserClass)) { //$NON-NLS-1$
          clazz = BoundedLooseIntParser.class;
        } else {
          if ("float".equalsIgnoreCase(parserClass)) { //$NON-NLS-1$
            clazz = BoundedLooseFloatParser.class;
          } else {
            if ("short".equalsIgnoreCase(parserClass)) { //$NON-NLS-1$
              clazz = BoundedLooseShortParser.class;
            } else {
              if ("byte".equalsIgnoreCase(parserClass)) { //$NON-NLS-1$
                clazz = BoundedLooseByteParser.class;
              } else {
                try {
                  clazz = ReflectionUtils.findClass(parserClass,
                      NumberParser.class, NumberParserParser.PREFIXES);
                } catch (LinkageError | ClassNotFoundException
                    | ClassCastException error) {
                  throw new IllegalArgumentException(//
                      "Cannot find class '" + parserClass + //$NON-NLS-1$
                          "' corresponding to '" + parserClass + //$NON-NLS-1$
                          ' ' + lowerBound + ' ' + upperBound, error);
                }
              }
            }
          }
        }
      }
    }

    try {
      return clazz.getConstructor(Number.class, Number.class).newInstance(
          lower, upper);
    } catch (NoSuchMethodException | SecurityException
        | InstantiationException | IllegalAccessException
        | IllegalArgumentException | InvocationTargetException error) {
      throw new IllegalArgumentException(//
          "Cannot find and invoke constructor of class '" + parserClass + //$NON-NLS-1$
              "' corresponding to '" + parserClass + //$NON-NLS-1$
              ' ' + lowerBound + ' ' + upperBound, error);
    }
  }

  /**
   * Try to obtain the parser via reflection.
   *
   * @param parserClass
   *          the parser class: must take two numbers as parameter
   * @param lowerBound
   *          the lower boundary
   * @param upperBound
   *          the upper boundary
   * @return the parser
   */
  public final NumberParser<Number> parse(final String parserClass,
      final String lowerBound, final String upperBound) {
    NumberParser<Number> parser;

    parser = this.__parse(parserClass, lowerBound, upperBound);
    this.validate(parser);
    return parser;
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public final NumberParser<Number> parseString(final String string) {

    final int length, idx1, idx2;

    length = string.length();
    idx1 = string.indexOf(':');
    if ((idx1 > 0) && (idx1 < (length - 2))) {
      idx2 = string.indexOf(':', (idx1 + 1));
      if ((idx2 > 0) && (idx2 < (length - 1))) {
        return this.__parse(//
            string.substring(0, idx1),//
            string.substring(idx1 + 1, idx2),//
            string.substring(idx2 + 1));//
      }
    }

    if ("double".equalsIgnoreCase(string)) { //$NON-NLS-1$
      return ((NumberParser) (StrictDoubleParser.INSTANCE));
    }
    if ("long".equalsIgnoreCase(string)) { //$NON-NLS-1$
      return ((NumberParser) (StrictLongParser.INSTANCE));
    }
    if ("int".equalsIgnoreCase(string) || //$NON-NLS-1$
        "integer".equalsIgnoreCase(string)) { //$NON-NLS-1$
      return ((NumberParser) (StrictIntParser.INSTANCE));
    }
    if ("float".equalsIgnoreCase(string)) { //$NON-NLS-1$
      return ((NumberParser) (StrictFloatParser.INSTANCE));
    }
    if ("short".equalsIgnoreCase(string)) { //$NON-NLS-1$
      return ((NumberParser) (StrictShortParser.INSTANCE));
    }
    if ("byte".equalsIgnoreCase(string)) { //$NON-NLS-1$
      return ((NumberParser) (StrictByteParser.INSTANCE));
    }
    return super.parseString(string);
  }

  /**
   * Get the singleton instance of this parser
   *
   * @return the document driver parser
   */
  public static final NumberParserParser getInstance() {
    return __NumberParserParserLoader.INSTANCE;
  }

  /** the instance loader */
  private static final class __NumberParserParserLoader {
    /** the instance */
    static final NumberParserParser INSTANCE = new NumberParserParser();
  }
}
