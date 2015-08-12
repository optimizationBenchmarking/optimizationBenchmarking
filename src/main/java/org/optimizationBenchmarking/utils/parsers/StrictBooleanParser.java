package org.optimizationBenchmarking.utils.parsers;

/**
 * A strict {@code boolean}-parser. A strict parser will behave similar to
 * {@link java.lang.Boolean#parseBoolean(String)} and throw exceptions
 * quickly if its input does not comply. Its extension
 * {@link org.optimizationBenchmarking.utils.parsers.LooseBooleanParser}
 * will, instead, behave much more generous and try several heuristics to
 * determine a suitable {@code boolean} value corresponding to a given
 * {@link java.lang.String string} or {@link java.lang.Object object} .
 */
public class StrictBooleanParser extends BooleanParser {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance of the boolean parser */
  public static final StrictBooleanParser INSTANCE = new StrictBooleanParser();

  /** create the parser */
  protected StrictBooleanParser() {
    super();
  }

  /**
   * write replace
   *
   * @return the replacement
   */
  private final Object writeReplace() {
    return StrictBooleanParser.INSTANCE;
  }

  /**
   * read resolve
   *
   * @return the replacement
   */
  private final Object readResolve() {
    return StrictBooleanParser.INSTANCE;
  }
}
