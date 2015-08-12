package org.optimizationBenchmarking.utils.parsers;

import org.optimizationBenchmarking.utils.hash.HashUtils;

/**
 * A strict {@code int}-parser. A strict parser will behave similar to
 * {@link java.lang.Integer#parseInt(String)} and throw exceptions quickly
 * if its input does not comply. Its extension
 * {@link org.optimizationBenchmarking.utils.parsers.LooseIntParser} will,
 * instead, behave much more generous and try several heuristics to
 * determine a suitable {@code int} value corresponding to a given
 * {@link java.lang.String string} or {@link java.lang.Object object}.
 */
public class StrictIntParser extends IntParser {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance of the int parser */
  public static final StrictIntParser INSTANCE = new StrictIntParser();

  /** create the parser */
  protected StrictIntParser() {
    super();
  }

  /**
   * write replace
   *
   * @return the replacement
   */
  private final Object writeReplace() {
    return StrictIntParser.INSTANCE;
  }

  /**
   * read resolve
   *
   * @return the replacement
   */
  private final Object readResolve() {
    return StrictIntParser.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return HashUtils.combineHashes(73557217,//
        HashUtils.combineHashes(//
            HashUtils.hashCode(this.getLowerBound()),//
            HashUtils.hashCode(this.getUpperBound())));
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(final Object other) {
    final StrictIntParser parser;
    if (other == this) {
      return true;
    }
    if (other instanceof StrictIntParser) {
      parser = ((StrictIntParser) other);
      return ((parser.getLowerBound() == this.getLowerBound()) && //
      (parser.getUpperBound() == this.getUpperBound()));
    }
    return false;
  }
}
