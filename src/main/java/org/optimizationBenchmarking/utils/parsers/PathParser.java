package org.optimizationBenchmarking.utils.parsers;

import java.io.File;
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
  public Path parseString(final String string) {
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
  @SuppressWarnings("unused")
  @Override
  public final Path parseObject(final Object o) {
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
            try {
              uri = ((URL) o).toURI();
            } catch (final URISyntaxException use) {
              throw new IllegalArgumentException(
                  "Cannot convert object '" + //$NON-NLS-1$
                      "' from type URL to URI in order to translate it to a path.", //$NON-NLS-1$
                  use);
            }
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
