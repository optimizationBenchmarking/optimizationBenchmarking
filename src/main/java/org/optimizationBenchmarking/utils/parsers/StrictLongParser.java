package org.optimizationBenchmarking.utils.parsers;

import org.optimizationBenchmarking.utils.hash.HashUtils;

/**
 * A strict {@code long}-parser. A strict parser will behave similar to
 * {@link java.lang.Long#parseLong(String)} and throw exceptions quickly if
 * its input does not comply. Its extension
 * {@link org.optimizationBenchmarking.utils.parsers.LooseLongParser} will,
 * instead, behave much more generous and try several heuristics to
 * determine a suitable {@code long} value corresponding to a given
 * {@link java.lang.String string} or {@link java.lang.Object object}.
 */
public class StrictLongParser extends LongParser {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance of the long parser */
  public static final StrictLongParser INSTANCE = new StrictLongParser();

  /** create the parser */
  protected StrictLongParser() {
    super();
  }

  /**
   * write replace
   *
   * @return the replacement
   */
  private final Object writeReplace() {
    return StrictLongParser.INSTANCE;
  }

  /**
   * read resolve
   *
   * @return the replacement
   */
  private final Object readResolve() {
    return StrictLongParser.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return HashUtils.combineHashes(57329999,//
        HashUtils.combineHashes(//
            HashUtils.hashCode(this.getLowerBoundLong()),//
            HashUtils.hashCode(this.getUpperBoundLong())));
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(final Object other) {
    final StrictLongParser parser;
    if (other == this) {
      return true;
    }
    if (other instanceof StrictLongParser) {
      parser = ((StrictLongParser) other);
      return ((parser.getLowerBoundLong() == this.getLowerBoundLong()) && //
      (parser.getUpperBoundLong() == this.getUpperBoundLong()));
    }
    return false;
  }
}
