package org.optimizationBenchmarking.utils.parsers;

import java.io.File;

/** A parser for file sets */
final class _FileSetParser extends SetParser<File> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** create the parser */
  _FileSetParser() {
    super(FileParser.INSTANCE);
  }

  /**
   * write replace
   * 
   * @return the replacement
   */
  private final Object writeReplace() {
    return SetParser.FILE_SET_PARSER;
  }

  /**
   * read resolve
   * 
   * @return the replacement
   */
  private final Object readResolve() {
    return SetParser.FILE_SET_PARSER;
  }
}
