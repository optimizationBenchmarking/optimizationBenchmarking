package org.optimizationBenchmarking.utils.parsers;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.hash.HashUtils;

/**
 * A strict {@code double}-parser. A strict parser will behave similar to
 * {@link java.lang.Double#parseDouble(String)} and throw exceptions
 * quickly if its input does not comply. Its extension
 * {@link org.optimizationBenchmarking.utils.parsers.LooseDoubleParser}
 * will, instead, behave much more generous and try several heuristics to
 * determine a suitable {@code double} value corresponding to a given
 * {@link java.lang.String string} or {@link java.lang.Object object}.
 */
public class StrictDoubleParser extends DoubleParser {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance of the double parser */
  public static final StrictDoubleParser INSTANCE = new StrictDoubleParser();

  /** create the parser */
  protected StrictDoubleParser() {
    super();
  }

  /**
   * write replace
   *
   * @return the replacement
   */
  private final Object writeReplace() {
    return StrictDoubleParser.INSTANCE;
  }

  /**
   * read resolve
   *
   * @return the replacement
   */
  private final Object readResolve() {
    return StrictDoubleParser.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    return HashUtils.combineHashes(556725031,//
        HashUtils.combineHashes(//
            HashUtils.hashCode(this.getLowerBoundDouble()),//
            HashUtils.hashCode(this.getUpperBoundDouble())));
  }

  /** {@inheritDoc} */
  @Override
  public boolean equals(final Object other) {
    final StrictDoubleParser parser;
    if (other == this) {
      return true;
    }
    if (other instanceof StrictDoubleParser) {
      parser = ((StrictDoubleParser) other);
      return (EComparison.EQUAL.compare(parser.getLowerBoundDouble(),
          this.getLowerBoundDouble()) && //
      EComparison.EQUAL.compare(parser.getUpperBoundDouble(),
          this.getUpperBoundDouble()));
    }
    return false;
  }
}
