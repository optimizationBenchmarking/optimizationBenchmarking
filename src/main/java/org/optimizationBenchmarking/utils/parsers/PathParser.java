package org.optimizationBenchmarking.utils.parsers;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.optimizationBenchmarking.utils.io.paths.PathUtils;

/** A parser for paths */
public class PathParser extends Parser<Path> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * The default instance of the path parser resolves files aginst the
   * current directory
   */
  public static final PathParser INSTANCE = new PathParser(null);

  /** the base path to resolve non-absolute paths against */
  private final Path m_basePath;

  /**
   * create the parser
   *
   * @param basePath
   *          the base path to resolve non-absolute paths against
   */
  public PathParser(final Path basePath) {
    super();
    this.m_basePath = basePath;
  }

  /**
   * Get the base path to resolve non-absolute paths against
   *
   * @return the base path to resolve non-absolute paths against
   */
  public final Path getBasePath() {
    return this.m_basePath;
  }

  /** {@inheritDoc} */
  @Override
  public final Class<Path> getOutputClass() {
    return Path.class;
  }

  /** {@inheritDoc} */
  @Override
  public Path parseString(final String string) throws IOException,
      SecurityException {
    final Path f;

    f = PathUtils.normalize(string, this.m_basePath);
    this.validate(f);
    return f;
  }

  /** {@inheritDoc} */
  @Override
  public void validate(final Path instance)
      throws IllegalArgumentException {
    if (instance == null) {
      throw new IllegalArgumentException("Path must not be null."); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  public final Path parseObject(final Object o) throws IOException,
      SecurityException, URISyntaxException {
    Path ret;
    URI uri;

    if (o instanceof Path) {
      ret = ((Path) o);
    } else {
      if (o instanceof File) {
        ret = ((File) o).toPath();
      } else {

        if (o instanceof URI) {
          uri = ((URI) o);
        } else {
          if (o instanceof URL) {
            uri = ((URL) o).toURI();
          } else {
            uri = null;
          }
        }

        if (uri != null) {
          try {
            ret = Paths.get(uri);
          } catch (final Throwable t) {
            ret = Paths.get(uri.getPath());
          }
        } else {
          return this.parseString(String.valueOf(o));
        }
      }
    }

    ret = PathUtils.normalize(ret, this.m_basePath);
    this.validate(ret);
    return ret;
  }
}
