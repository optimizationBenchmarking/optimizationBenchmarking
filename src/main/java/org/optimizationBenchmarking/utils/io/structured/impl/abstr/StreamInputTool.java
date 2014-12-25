package org.optimizationBenchmarking.utils.io.structured.impl.abstr;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.io.structured.spec.IStreamInputJobBuilder;
import org.optimizationBenchmarking.utils.io.structured.spec.IStreamInputTool;

/**
 * A tool for reading stream input
 * 
 * @param <S>
 *          the destination type
 */
public class StreamInputTool<S> extends FileInputTool<S> implements
    IStreamInputTool<S> {

  /** create */
  protected StreamInputTool() {
    super();
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public IStreamInputJobBuilder<S> use() {
    this.beforeUse();
    return new _StreamInputJobBuilder(this);
  }

  /** {@inheritDoc} */
  @Override
  void _handle(final IOJobLog log, final S data, final _Location location)
      throws Throwable {
    Class<?> clazz;

    if (location.m_location1 instanceof InputStream) {
      if (log.canLog()) {
        log.log("Beginning input from InputStream."); //$NON-NLS-1$
      }
      this.__stream(log, data, ((InputStream) (location.m_location1)),
          location.m_encoding, location.m_zipped);
      if (log.canLog()) {
        log.log("Finished input from InputStream."); //$NON-NLS-1$
      }
      return;
    }

    if (location.m_location1 instanceof URL) {
      if (log.canLog()) {
        log.log("Beginning input from URL " + location.m_location1); //$NON-NLS-1$
      }
      this.__url(log, data, ((URL) (location.m_location1)),
          location.m_encoding, location.m_zipped);
      if (log.canLog()) {
        log.log("Finished input from URL " + location.m_location1); //$NON-NLS-1$
      }
      return;
    }

    if (location.m_location1 instanceof URI) {
      if (log.canLog()) {
        log.log("Beginning input from URI " + location.m_location1); //$NON-NLS-1$
      }
      this.__uri(log, data, ((URI) (location.m_location1)),
          location.m_encoding, location.m_zipped);
      if (log.canLog()) {
        log.log("Finished input from URI " + location.m_location1); //$NON-NLS-1$
      }
      return;
    }

    if ((location.m_location1 instanceof Class)
        && (location.m_location2 instanceof String)) {
      if (log.canLog()) {
        log.log("Beginning input from resource '" + //$NON-NLS-1$
            location.m_location2 + "' of class " + location.m_location1); //$NON-NLS-1$
      }
      this.__resource(log, data, ((Class<?>) (location.m_location1)),
          ((String) (location.m_location2)), location.m_encoding,
          location.m_zipped);
      if (log.canLog()) {
        log.log("Finished input from resource '" + //$NON-NLS-1$
            location.m_location2 + "' of class " + location.m_location1); //$NON-NLS-1$
      }
      return;
    }

    if (location.m_location1 instanceof String) {

      if (location.m_location2 instanceof Class) {
        clazz = ((Class<?>) (location.m_location2));

        if (URL.class.isAssignableFrom(clazz)) {
          if (log.canLog()) {
            log.log("Beginning input from URL specified as String: " + //$NON-NLS-1$
                location.m_location1);
          }
          this.__url(log, data, new URL((String) (location.m_location1)),
              location.m_encoding, location.m_zipped);
          if (log.canLog()) {
            log.log("Finished input from URL specified as String: " + //$NON-NLS-1$
                location.m_location1);
          }
          return;
        }

        if (URI.class.isAssignableFrom(clazz)) {
          if (log.canLog()) {
            log.log("Beginning input from URI specified as String: " + //$NON-NLS-1$
                location.m_location1);
          }
          this.__uri(log, data, new URI((String) (location.m_location1)),
              location.m_encoding, location.m_zipped);
          if (log.canLog()) {
            log.log("Finished input from URI specified as String: " + //$NON-NLS-1$
                location.m_location1);
          }
          return;
        }

        if (String.class.isAssignableFrom(clazz)) {
          if (log.canLog()) {
            log.log("Beginning input from Resource specified as String: " + //$NON-NLS-1$
                location.m_location1 + ':' + location.m_location2);
          }
          this.__resource(log, data,
              Class.forName((String) (location.m_location1)),
              ((String) (location.m_location2)), location.m_encoding,
              location.m_zipped);
          if (log.canLog()) {
            log.log("Finished input from Resource specified as String: " + //$NON-NLS-1$
                location.m_location1 + ':' + location.m_location2);
          }
          return;
        }
      }

    }

    super._handle(log, data, location);
  }

  /**
   * Handle a resource
   * 
   * @param log
   *          the log where logging info can be written
   * @param data
   *          the data to be read
   * @param clazz
   *          the class
   * @param name
   *          the resource name
   * @param encoding
   *          the encoding
   * @param zipped
   *          is the stream ZIP-compressed?
   * @throws Throwable
   *           if it must
   */
  private final void __resource(final IOJobLog log, final S data,
      final Class<?> clazz, final String name,
      final StreamEncoding<?, ?> encoding, final boolean zipped)
      throws Throwable {
    try (final InputStream stream = clazz.getResourceAsStream(name)) {
      this.__stream(log, data, stream, encoding, zipped);
    }
  }

  /**
   * Handle an URL
   * 
   * @param log
   *          the log where logging info can be written
   * @param data
   *          the data to be read
   * @param url
   *          the url
   * @param encoding
   *          the encoding
   * @param zipped
   *          is the stream ZIP-compressed?
   * @throws Throwable
   *           if it must
   */
  private final void __url(final IOJobLog log, final S data,
      final URL url, final StreamEncoding<?, ?> encoding,
      final boolean zipped) throws Throwable {
    try (final InputStream stream = url.openStream()) {
      this.__stream(log, data, stream, encoding, zipped);
    }
  }

  /**
   * Handle an URI
   * 
   * @param log
   *          the log where logging info can be written
   * @param data
   *          the data to be read
   * @param uri
   *          the uri
   * @param encoding
   *          the encoding
   * @param zipped
   *          is the stream ZIP-compressed?
   * @throws Throwable
   *           if it must
   */
  private final void __uri(final IOJobLog log, final S data,
      final URI uri, final StreamEncoding<?, ?> encoding,
      final boolean zipped) throws Throwable {
    URL url;
    Path path;
    Throwable errorA, errorB, ioError;

    errorA = null;
    url = null;
    try {
      url = uri.toURL();
    } catch (final Throwable throwable) {
      errorA = throwable;
      url = null;
    }

    if (url != null) {
      this.__url(log, data, url, encoding, zipped);
      return;
    }

    path = null;
    errorB = null;
    try {
      path = Paths.get(uri);
    } catch (final Throwable throwable) {
      errorB = throwable;
      path = null;
    }

    if (path != null) {
      this.path(log, data, path,
          Files.readAttributes(path, BasicFileAttributes.class), encoding,
          zipped);
      return;
    }

    ioError = new IOException("URI '" + uri + //$NON-NLS-1$
        "' cannot be processed."); //$NON-NLS-1$
    if (errorA != null) {
      ioError.addSuppressed(errorA);
    }
    if (errorB != null) {
      ioError.addSuppressed(errorB);
    }
    throw ioError;
  }

  /**
   * Handle a stream
   * 
   * @param log
   *          the log where logging info can be written
   * @param data
   *          the data to be read
   * @param stream
   *          the stream
   * @param encoding
   *          the encoding
   * @param zipped
   *          is the stream ZIP-compressed?
   * @throws Throwable
   *           if it must
   */
  private final void __stream(final IOJobLog log, final S data,
      final InputStream stream, final StreamEncoding<?, ?> encoding,
      final boolean zipped) throws Throwable {
    final Class<?> clazz;

    if (zipped) {
      this._loadZIP(log, data, stream, encoding);
    } else {
      if ((encoding != null) && (encoding != StreamEncoding.UNKNOWN)
          && (encoding != StreamEncoding.TEXT)
          && (encoding != StreamEncoding.BINARY)
          && ((clazz = encoding.getInputClass()) != null)
          && InputStream.class.isAssignableFrom(clazz)) {
        if (log.canLog(IOTool.FINE_LOG_LEVEL)) {
          log.log(IOTool.FINE_LOG_LEVEL,
              "Using byte stream encoding " + encoding.name()); //$NON-NLS-1$
        }
        try (final InputStream input = ((InputStream) (encoding
            .wrapInputStream(stream)))) {
          this.stream(log, data, stream, StreamEncoding.UNKNOWN);
        }
      } else {
        this.stream(log, data, stream, encoding);
      }
    }
  }

  /**
   * Handle a stream
   * 
   * @param log
   *          the log where logging info can be written
   * @param data
   *          the data to be read
   * @param stream
   *          the stream
   * @param encoding
   *          the encoding
   * @throws Throwable
   *           if it must
   */
  protected void stream(final IOJobLog log, final S data,
      final InputStream stream, final StreamEncoding<?, ?> encoding)
      throws Throwable {
    //
  }

  /** {@inheritDoc} */
  @Override
  protected void file(final IOJobLog log, final S data, final Path path,
      final BasicFileAttributes attributes,
      final StreamEncoding<?, ?> encoding) throws Throwable {
    try (final InputStream input = PathUtils.openInputStream(path)) {
      this.__stream(log, data, input, encoding, false);
    }
  }
}
