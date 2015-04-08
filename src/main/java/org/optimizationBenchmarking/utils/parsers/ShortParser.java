package org.optimizationBenchmarking.utils.parsers;

/**
 * A parser for {@code short}s.
 */
public class ShortParser extends StrictShortParser {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance of the short parser */
  public static final ShortParser INSTANCE = new ShortParser();

  /** create the parser */
  ShortParser() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final short parseShort(final String string) {
    final short retVal;
    final int val;

    val = IntParser.INSTANCE.parseInt(string);
    if ((val < Short.MIN_VALUE) || (val > Short.MAX_VALUE)) {
      throw new IllegalArgumentException(string
          + (((((" represents value " + val) + //$NON-NLS-1$
          " which is outside of the valid range [") + Short.MIN_VALUE) + //$NON-NLS-1$
          ',') + Short.MAX_VALUE) + " for shorts."); //$NON-NLS-1$
    }

    retVal = ((short) val);
    this.validateShort(retVal);
    return retVal;
  }

  /** {@inheritDoc} */
  @Override
  public final Short parseObject(final Object o) {
    final Short retVal;
    final short ret;

    if (o instanceof Number) {
      if (o instanceof Short) {
        retVal = ((Short) o);
        ret = retVal.shortValue();
      } else {
        ret = ((Number) o).shortValue();
        retVal = null;
      }
    } else {
      return this.parseString(String.valueOf(o));
    }

    this.validateShort(ret);
    return ((retVal != null) ? retVal : Short.valueOf(ret));
  }

  /**
   * write replace
   * 
   * @return the replacement
   */
  private final Object writeReplace() {
    return ShortParser.INSTANCE;
  }

  /**
   * read resolve
   * 
   * @return the replacement
   */
  private final Object readResolve() {
    return ShortParser.INSTANCE;
  }
}
