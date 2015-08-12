package org.optimizationBenchmarking.utils.parsers;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.hash.HashUtils;

/**
 * A strict {@code float}-parser. A strict parser will behave similar to
 * {@link java.lang.Float#parseFloat(String)} and throw exceptions quickly
 * if its input does not comply. Its extension
 * {@link org.optimizationBenchmarking.utils.parsers.LooseFloatParser}
 * will, instead, behave much more generous and try several heuristics to
 * determine a suitable {@code float} value corresponding to a given
 * {@link java.lang.String string} or {@link java.lang.Object object}.
 */
public class StrictFloatParser extends FloatParser {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance of the float parser */
  public static final StrictFloatParser INSTANCE = new StrictFloatParser();

  /** create the parser */
  protected StrictFloatParser() {
    super();
  }

  /**
   * write replace
   *
   * @return the replacement
   */
  private final Object writeReplace() {
    return StrictFloatParser.INSTANCE;
  }

  /**
   * read resolve
   *
   * @return the replacement
   */
  private final Object readResolve() {
    return StrictFloatParser.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return HashUtils.combineHashes(377325253,//
        HashUtils.combineHashes(//
            HashUtils.hashCode(this.getLowerBound()),//
            HashUtils.hashCode(this.getUpperBound())));
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(final Object other) {
    final StrictFloatParser parser;
    if (other == this) {
      return true;
    }
    if (other instanceof StrictFloatParser) {
      parser = ((StrictFloatParser) other);
      return (EComparison.EQUAL.compare(parser.getLowerBound(),
          this.getLowerBound()) && //
      EComparison.EQUAL.compare(parser.getUpperBound(),
          this.getUpperBound()));
    }
    return false;
  }
}
