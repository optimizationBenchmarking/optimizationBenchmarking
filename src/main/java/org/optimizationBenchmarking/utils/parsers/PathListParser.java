package org.optimizationBenchmarking.utils.parsers;

import java.nio.file.Path;

/** A parser for file sets */
public final class PathListParser extends ListParser<Path> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * The default instance of the path list parser resolves paths against
   * the current directory
   */
  public static final PathListParser INSTANCE = new PathListParser(
      PathParser.INSTANCE);

  /**
   * create the parser
   *
   * @param basePath
   *          the base path to resolve non-absolute paths against
   */
  public PathListParser(final Path basePath) {
    this(new PathParser(basePath));
  }

  /**
   * create the parser
   *
   * @param parser
   *          the internal parser
   */
  public PathListParser(final PathParser parser) {
    super(parser, true, true);
  }
}
