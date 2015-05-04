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

  /** the globally shared instance of the path parser */
  public static final PathParser INSTANCE = new PathParser();

  /** create the parser */
  protected PathParser() {
    super();
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

    f = PathUtils.normalize(Paths.get(string));
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

    ret = PathUtils.normalize(ret);
    this.validate(ret);
    return ret;
  }

  /**
   * write replace
   *
   * @return the replacement
   */
  private final Object writeReplace() {
    return PathParser.INSTANCE;
  }

  /**
   * read resolve
   *
   * @return the replacement
   */
  private final Object readResolve() {
    return PathParser.INSTANCE;
  }
}
