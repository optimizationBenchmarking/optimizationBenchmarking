package org.optimizationBenchmarking.utils.parsers;

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
   * @throws Exception
   *           if the parser could not be obtained
   */
  @SuppressWarnings("rawtypes")
  private final NumberParser<Number> __parse(final String parserClass,
      final String lowerBound, final String upperBound) throws Exception {
    Number lower, upper;
    RuntimeException re;
    Class<? extends NumberParser> clazz;

    try {
      lower = StrictLongParser.STRICT_INSTANCE.parseString(lowerBound);
    } catch (final Throwable t1) {
      try {
        lower = StrictDoubleParser.STRICT_INSTANCE.parseString(lowerBound);
      } catch (final Throwable t2) {
        try {
          lower = LongParser.INSTANCE.parseString(lowerBound);
        } catch (final Throwable t3) {
          try {
            lower = DoubleParser.INSTANCE.parseString(lowerBound);
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
      upper = StrictLongParser.STRICT_INSTANCE.parseString(upperBound);
    } catch (final Throwable t1) {
      try {
        upper = StrictDoubleParser.STRICT_INSTANCE.parseString(upperBound);
      } catch (final Throwable t2) {
        try {
          upper = LongParser.INSTANCE.parseString(upperBound);
        } catch (final Throwable t3) {
          try {
            upper = DoubleParser.INSTANCE.parseString(upperBound);
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
      clazz = BoundedDoubleParser.class;
    } else {
      if ("long".equalsIgnoreCase(parserClass)) { //$NON-NLS-1$
        clazz = BoundedLongParser.class;
      } else {
        if ("int".equalsIgnoreCase(parserClass) || //$NON-NLS-1$
            "integer".equalsIgnoreCase(parserClass)) { //$NON-NLS-1$
          clazz = BoundedIntParser.class;
        } else {
          if ("float".equalsIgnoreCase(parserClass)) { //$NON-NLS-1$
            clazz = BoundedFloatParser.class;
          } else {
            if ("short".equalsIgnoreCase(parserClass)) { //$NON-NLS-1$
              clazz = BoundedShortParser.class;
            } else {
              if ("byte".equalsIgnoreCase(parserClass)) { //$NON-NLS-1$
                clazz = BoundedByteParser.class;
              } else {
                clazz = ReflectionUtils.findClass(parserClass,
                    NumberParser.class, NumberParserParser.PREFIXES);
              }
            }
          }
        }
      }
    }

    return clazz.getConstructor(Number.class, Number.class).newInstance(
        lower, upper);
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
   * @throws Exception
   *           if the parser could not be obtained
   */
  public final NumberParser<Number> parse(final String parserClass,
      final String lowerBound, final String upperBound) throws Exception {
    NumberParser<Number> parser;

    parser = this.__parse(parserClass, lowerBound, upperBound);
    this.validate(parser);
    return parser;
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  public final NumberParser<Number> parseString(final String string)
      throws Exception {

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
      return ((NumberParser) (StrictDoubleParser.STRICT_INSTANCE));
    }
    if ("long".equalsIgnoreCase(string)) { //$NON-NLS-1$
      return ((NumberParser) (StrictLongParser.STRICT_INSTANCE));
    }
    if ("int".equalsIgnoreCase(string) || //$NON-NLS-1$
        "integer".equalsIgnoreCase(string)) { //$NON-NLS-1$
      return ((NumberParser) (StrictIntParser.STRICT_INSTANCE));
    }
    if ("float".equalsIgnoreCase(string)) { //$NON-NLS-1$
      return ((NumberParser) (StrictFloatParser.STRICT_INSTANCE));
    }
    if ("short".equalsIgnoreCase(string)) { //$NON-NLS-1$
      return ((NumberParser) (StrictShortParser.STRICT_INSTANCE));
    }
    if ("byte".equalsIgnoreCase(string)) { //$NON-NLS-1$
      return ((NumberParser) (StrictByteParser.STRICT_INSTANCE));
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
