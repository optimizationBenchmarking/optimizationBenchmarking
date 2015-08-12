package org.optimizationBenchmarking.utils.parsers;

/**
 * A strict {@code char}-parser. A strict parser will behave a strictly as
 * possible and throw exceptions quickly if its input does not comply. Its
 * extension
 * {@link org.optimizationBenchmarking.utils.parsers.LooseCharParser} will,
 * instead, behave much more generous and try several heuristics to
 * determine a suitable {@code char} value corresponding to a given
 * {@link java.lang.String string} or {@link java.lang.Object object} .
 */
public class StrictCharParser extends CharParser {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance of the char parser */
  public static final StrictCharParser INSTANCE = new StrictCharParser();

  /** create the parser */
  protected StrictCharParser() {
    super();
  }

  /**
   * write replace
   *
   * @return the replacement
   */
  private final Object writeReplace() {
    return StrictCharParser.INSTANCE;
  }

  /**
   * read resolve
   *
   * @return the replacement
   */
  private final Object readResolve() {
    return StrictCharParser.INSTANCE;
  }
}
