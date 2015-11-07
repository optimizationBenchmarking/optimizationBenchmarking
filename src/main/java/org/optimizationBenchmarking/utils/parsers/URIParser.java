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
  @SuppressWarnings("unused")
  @Override
  public final URI parseString(final String string) {
    URI uri;
    try {
      uri = new URI(LooseStringParser.INSTANCE.parseString(string));
    } catch (final URISyntaxException use) {
      throw new IllegalArgumentException("String '" + string + //$NON-NLS-1$
          "' is not a valid URI."); //$NON-NLS-1$
    }
    return this.__cleanse(uri);
  }

  /**
   * cleanse and validate an uri
   *
   * @param u
   *          the uri
   * @return the cleansed version
   */
  private final URI __cleanse(final URI u) {
    final URI res;

    res = u.normalize();

    // if (res == null) {
    // res = u;
    // }

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
  public final URI parseObject(final Object o) {
    final URI ret;

    if (o instanceof File) {
      ret = ((File) o).toURI();
    } else {
      if (o instanceof Path) {
        ret = ((Path) o).toUri();
      } else {
        if (o instanceof URL) {
          try {
            ret = ((URL) o).toURI();
          } catch (final URISyntaxException use) {
            throw new IllegalArgumentException("Object '" + o + //$NON-NLS-1$
                "' cannot be converted from type URL to URI.", use); //$NON-NLS-1$
          }
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
