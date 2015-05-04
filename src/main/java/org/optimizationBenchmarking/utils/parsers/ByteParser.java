package org.optimizationBenchmarking.utils.parsers;

/** A parser for {@code byte}s */
public class ByteParser extends StrictByteParser {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance of the byte parser */
  public static final ByteParser INSTANCE = new ByteParser();

  /** create the parser */
  protected ByteParser() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final byte parseByte(final String string) {
    final byte retVal;
    final int val;

    val = IntParser.INSTANCE.parseInt(string);
    if ((val < Byte.MIN_VALUE) || (val > Byte.MAX_VALUE)) {
      throw new IllegalArgumentException(string
          + (((((" represents value " + val) + //$NON-NLS-1$
          " which is outside of the valid range [") + Byte.MIN_VALUE) + //$NON-NLS-1$
          ',') + Byte.MAX_VALUE) + " for bytes."); //$NON-NLS-1$
    }

    retVal = ((byte) val);
    this.validateByte(retVal);
    return retVal;
  }

  /** {@inheritDoc} */
  @Override
  public final Byte parseObject(final Object o) {
    final Byte retVal;
    final byte ret;

    if (o instanceof Number) {
      if (o instanceof Byte) {
        retVal = ((Byte) o);
        ret = retVal.byteValue();
      } else {
        ret = ((Number) o).byteValue();
        retVal = null;
      }
    } else {
      return this.parseString(String.valueOf(o));
    }

    this.validateByte(ret);
    return ((retVal != null) ? retVal : Byte.valueOf(ret));
  }

  /**
   * write replace
   *
   * @return the replacement
   */
  private final Object writeReplace() {
    return ByteParser.INSTANCE;
  }

  /**
   * read resolve
   *
   * @return the replacement
   */
  private final Object readResolve() {
    return ByteParser.INSTANCE;
  }
}
