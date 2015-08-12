package org.optimizationBenchmarking.utils.parsers;

import org.optimizationBenchmarking.utils.hash.HashUtils;

/**
 * A strict {@code byte}-parser. A strict parser will behave similar to
 * {@link java.lang.Byte#parseByte(String)} and throw exceptions quickly if
 * its input does not comply. Its extension
 * {@link org.optimizationBenchmarking.utils.parsers.LooseByteParser} will,
 * instead, behave much more generous and try several heuristics to
 * determine a suitable {@code byte} value corresponding to a given
 * {@link java.lang.String string} or {@link java.lang.Object object}.
 */
public class StrictByteParser extends ByteParser {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance of the byte parser */
  public static final StrictByteParser INSTANCE = new StrictByteParser();

  /** create the parser */
  protected StrictByteParser() {
    super();
  }

  /**
   * write replace
   *
   * @return the replacement
   */
  private final Object writeReplace() {
    return StrictByteParser.INSTANCE;
  }

  /**
   * read resolve
   *
   * @return the replacement
   */
  private final Object readResolve() {
    return StrictByteParser.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return HashUtils.combineHashes(123456803,//
        HashUtils.combineHashes(//
            HashUtils.hashCode(this.getLowerBound()),//
            HashUtils.hashCode(this.getUpperBound())));
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(final Object other) {
    final StrictByteParser parser;
    if (other == this) {
      return true;
    }
    if (other instanceof StrictByteParser) {
      parser = ((StrictByteParser) other);
      return ((parser.getLowerBound() == this.getLowerBound()) && //
      (parser.getUpperBound() == this.getUpperBound()));
    }
    return false;
  }
}
