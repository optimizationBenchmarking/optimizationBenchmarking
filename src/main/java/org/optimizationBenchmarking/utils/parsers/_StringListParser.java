package org.optimizationBenchmarking.utils.parsers;

/** A parser for string lists */
final class _StringListParser extends ListParser<String> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** create the parser */
  _StringListParser() {
    super(LooseStringParser.INSTANCE, true, false);
  }

  /**
   * write replace
   *
   * @return the replacement
   */
  private final Object writeReplace() {
    return ListParser.STRING_LIST_PARSER;
  }

  /**
   * read resolve
   *
   * @return the replacement
   */
  private final Object readResolve() {
    return ListParser.STRING_LIST_PARSER;
  }
}
