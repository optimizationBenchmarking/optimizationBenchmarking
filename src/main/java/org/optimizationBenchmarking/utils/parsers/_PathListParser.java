package org.optimizationBenchmarking.utils.parsers;

import java.nio.file.Path;

/** A parser for file sets */
final class _PathListParser extends ListParser<Path> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** create the parser */
  _PathListParser() {
    super(PathParser.INSTANCE, true, true);
  }

  /**
   * write replace
   *
   * @return the replacement
   */
  private final Object writeReplace() {
    return ListParser.PATH_LIST_PARSER;
  }

  /**
   * read resolve
   *
   * @return the replacement
   */
  private final Object readResolve() {
    return ListParser.PATH_LIST_PARSER;
  }
}
