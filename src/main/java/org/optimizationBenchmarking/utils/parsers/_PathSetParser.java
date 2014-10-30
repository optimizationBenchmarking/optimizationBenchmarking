package org.optimizationBenchmarking.utils.parsers;

import java.nio.file.Path;

/** A parser for file sets */
final class _PathSetParser extends SetParser<Path> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** create the parser */
  _PathSetParser() {
    super(PathParser.INSTANCE);
  }

  /**
   * write replace
   * 
   * @return the replacement
   */
  private final Object writeReplace() {
    return SetParser.PATH_SET_PARSER;
  }

  /**
   * read resolve
   * 
   * @return the replacement
   */
  private final Object readResolve() {
    return SetParser.PATH_SET_PARSER;
  }
}
