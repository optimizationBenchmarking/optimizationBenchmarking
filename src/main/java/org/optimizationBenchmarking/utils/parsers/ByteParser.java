package org.optimizationBenchmarking.utils.parsers;

import org.optimizationBenchmarking.utils.hash.HashUtils;

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

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return HashUtils.combineHashes(94624963,//
        HashUtils.combineHashes(//
            HashUtils.hashCode(this.getLowerBound()),//
            HashUtils.hashCode(this.getUpperBound())));
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object other) {
    final ByteParser parser;
    if (other == this) {
      return true;
    }
    if (other instanceof ByteParser) {
      parser = ((ByteParser) other);
      return ((parser.getLowerBound() == this.getLowerBound()) && //
      (parser.getUpperBound() == this.getUpperBound()));
    }
    return false;
  }
}
