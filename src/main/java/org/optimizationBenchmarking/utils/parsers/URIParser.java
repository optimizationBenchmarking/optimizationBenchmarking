package org.optimizationBenchmarking.utils.parsers;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

/** A parser for uris */
public class URIParser extends Parser<URI> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance of the file parser */
  public static final URIParser INSTANCE = new URIParser();

  /** create the parser */
  private URIParser() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final Class<URI> getOutputClass() {
    return URI.class;
  }

  /** {@inheritDoc} */
  @Override
  public final URI parseString(final String string)
      throws URISyntaxException {
    return this.__cleanse(new URI(StringParser.INSTANCE
        .parseString(string)));
  }

  /**
   * cleanse and validate an uri
   * 
   * @param u
   *          the uri
   * @return the cleansed version
   */
  private final URI __cleanse(final URI u) {
    URI res;

    res = u.normalize();

    if (res == null) {
      res = u;
    }

    this.validate(res);
    return res;
  }

  /** {@inheritDoc} */
  @Override
  public void validate(final URI instance) throws IllegalArgumentException {
    if (instance == null) {
      throw new IllegalArgumentException("URI must not be null."); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  public final URI parseObject(final Object o) throws URISyntaxException {
    final URI ret;

    if (o instanceof File) {
      ret = ((File) o).toURI();
    } else {
      if (o instanceof Path) {
        ret = ((Path) o).toUri();
      } else {
        if (o instanceof URL) {
          ret = ((URL) o).toURI();
        } else {
          return this.parseString(String.valueOf(o));
        }
      }
    }

    return this.__cleanse(ret);
  }

  /**
   * write replace
   * 
   * @return the replacement
   */
  private final Object writeReplace() {
    return URIParser.INSTANCE;
  }

  /**
   * read resolve
   * 
   * @return the replacement
   */
  private final Object readResolve() {
    return URIParser.INSTANCE;
  }
}
