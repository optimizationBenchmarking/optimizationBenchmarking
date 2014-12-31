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
import org.optimizationBenchmarking.utils.io.paths.TempDir;
import org.optimizationBenchmarking.utils.io.structured.spec.IFileInputJobBuilder;
import org.optimizationBenchmarking.utils.io.structured.spec.IFileInputTool;

/**
 * A tool for reading file input
 * 
 * @param <S>
 *          the destination type
 */
public class FileInputTool<S> extends IOTool<S> implements
    IFileInputTool<S> {

  /** create */
  protected FileInputTool() {
    super();
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public IFileInputJobBuilder<S> use() {
    this.beforeUse();
    return new _FileInputJobBuilder(this);
  }

  /** {@inheritDoc} */
  @Override
  void _handle(final IOJob job, final S data, final _Location location)
      throws Throwable {
    Class<?> clazz;

    if (location.m_location1 instanceof InputStream) {
      if (!(location.m_zipped)) {
        this._checkRawStreams();
      }
      if (job.canLog()) {
        job.log("Beginning input from InputStream."); //$NON-NLS-1$
      }
      this._stream(job, data, ((InputStream) (location.m_location1)),
          location.m_encoding, location.m_zipped);
      if (job.canLog()) {
        job.log("Finished input from InputStream."); //$NON-NLS-1$
      }
      return;
    }

    if (location.m_location1 instanceof URL) {
      if (!(location.m_zipped)) {
        this._checkRawStreams();
      }
      if (job.canLog()) {
        job.log("Beginning input from URL " + location.m_location1); //$NON-NLS-1$
      }
      this.__url(job, data, ((URL) (location.m_location1)),
          location.m_encoding, location.m_zipped);
      if (job.canLog()) {
        job.log("Finished input from URL " + location.m_location1); //$NON-NLS-1$
      }
      return;
    }

    if (location.m_location1 instanceof URI) {
      if (!(location.m_zipped)) {
        this._checkRawStreams();
      }
      if (job.canLog()) {
        job.log("Beginning input from URI " + location.m_location1); //$NON-NLS-1$
      }
      this.__uri(job, data, ((URI) (location.m_location1)),
          location.m_encoding, location.m_zipped);
      if (job.canLog()) {
        job.log("Finished input from URI " + location.m_location1); //$NON-NLS-1$
      }
      return;
    }

    if ((location.m_location1 instanceof Class)
        && (location.m_location2 instanceof String)) {
      if (!(location.m_zipped)) {
        this._checkRawStreams();
      }
      if (job.canLog()) {
        job.log("Beginning input from resource '" + //$NON-NLS-1$
            location.m_location2 + "' of class " + location.m_location1); //$NON-NLS-1$
      }
      this.__resource(job, data, ((Class<?>) (location.m_location1)),
          ((String) (location.m_location2)), location.m_encoding,
          location.m_zipped);
      if (job.canLog()) {
        job.log("Finished input from resource '" + //$NON-NLS-1$
            location.m_location2 + "' of class " + location.m_location1); //$NON-NLS-1$
      }
      return;
    }

    if (location.m_location1 instanceof String) {

      if (location.m_location2 instanceof Class) {
        clazz = ((Class<?>) (location.m_location2));

        if (URL.class.isAssignableFrom(clazz)) {
          if (!(location.m_zipped)) {
            this._checkRawStreams();
          }
          if (job.canLog()) {
            job.log("Beginning input from URL specified as String: " + //$NON-NLS-1$
                location.m_location1);
          }
          this.__url(job, data, new URL((String) (location.m_location1)),
              location.m_encoding, location.m_zipped);
          if (job.canLog()) {
            job.log("Finished input from URL specified as String: " + //$NON-NLS-1$
                location.m_location1);
          }
          return;
        }

        if (URI.class.isAssignableFrom(clazz)) {
          if (!(location.m_zipped)) {
            this._checkRawStreams();
          }
          if (job.canLog()) {
            job.log("Beginning input from URI specified as String: " + //$NON-NLS-1$
                location.m_location1);
          }
          this.__uri(job, data, new URI((String) (location.m_location1)),
              location.m_encoding, location.m_zipped);
          if (job.canLog()) {
            job.log("Finished input from URI specified as String: " + //$NON-NLS-1$
                location.m_location1);
          }
          return;
        }

        if (String.class.isAssignableFrom(clazz)) {
          if (!(location.m_zipped)) {
            this._checkRawStreams();
          }
          if (job.canLog()) {
            job.log("Beginning input from Resource specified as String: " + //$NON-NLS-1$
                location.m_location1 + ':' + location.m_location2);
          }
          this.__resource(job, data,
              Class.forName((String) (location.m_location1)),
              ((String) (location.m_location2)), location.m_encoding,
              location.m_zipped);
          if (job.canLog()) {
            job.log("Finished input from Resource specified as String: " + //$NON-NLS-1$
                location.m_location1 + ':' + location.m_location2);
          }
          return;
        }
      }

    }

    super._handle(job, data, location);
  }

  /**
   * Load a ZIP archive
   * 
   * @param job
   *          the job where logging info can be written
   * @param data
   *          the data to be read
   * @param stream
   *          the stream
   * @param encoding
   *          the encoding
   * @throws Throwable
   *           if it must
   */
  private final void __loadZIP(final IOJob job, final S data,
      final InputStream stream, final StreamEncoding<?, ?> encoding)
      throws Throwable {
    final Path path;
    try (final TempDir temp = new TempDir()) {
      path = temp.getPath();
      if (job.canLog(IOJob.FINE_LOG_LEVEL)) {
        job.log(IOJob.FINE_LOG_LEVEL,
            ((("Begin unzipping to temporary folder '" //$NON-NLS-1$
            + path) + '\'') + '.'));
      }
      PathUtils.unzipToFolder(stream, path);
      if (job.canLog(IOJob.FINE_LOG_LEVEL)) {
        job.log(IOJob.FINE_LOG_LEVEL,
            ((("Finished unzipping to temporary folder '" //$NON-NLS-1$
            + path) + '\'') + '.'));
      }
      this.path(job, data, path,
          Files.readAttributes(path, BasicFileAttributes.class), encoding,
          false);
    }
  }

  /**
   * Handle a file which may be compressed
   * 
   * @param job
   *          the job where logging info can be written
   * @param data
   *          the data to be read
   * @param path
   *          the path
   * @param attributes
   *          the attributes
   * @param encoding
   *          the encoding
   * @param zipped
   *          should we ZIP-compress / expect ZIP compression
   * @throws Throwable
   *           if it must
   */
  final void _file(final IOJob job, final S data, final Path path,
      final BasicFileAttributes attributes,
      final StreamEncoding<?, ?> encoding, final boolean zipped)
      throws Throwable {
    if (zipped) {
      if (job.canLog()) {
        job.log("Unzipping path '" + path + '\''); //$NON-NLS-1$
      }
      try (final InputStream stream = PathUtils.openInputStream(path)) {
        this.__loadZIP(job, data, stream, encoding);
      }
    } else {
      this.file(job, data, path, attributes, encoding);
    }
  }

  /**
   * Handle a resource
   * 
   * @param job
   *          the job where logging info can be written
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
  private final void __resource(final IOJob job, final S data,
      final Class<?> clazz, final String name,
      final StreamEncoding<?, ?> encoding, final boolean zipped)
      throws Throwable {
    try (final InputStream stream = clazz.getResourceAsStream(name)) {
      this._stream(job, data, stream, encoding, zipped);
    }
  }

  /**
   * Handle an URL
   * 
   * @param job
   *          the job where logging info can be written
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
  private final void __url(final IOJob job, final S data, final URL url,
      final StreamEncoding<?, ?> encoding, final boolean zipped)
      throws Throwable {
    try (final InputStream stream = url.openStream()) {
      this._stream(job, data, stream, encoding, zipped);
    }
  }

  /**
   * Handle an URI
   * 
   * @param job
   *          the job where logging info can be written
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
  private final void __uri(final IOJob job, final S data, final URI uri,
      final StreamEncoding<?, ?> encoding, final boolean zipped)
      throws Throwable {
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
      this.__url(job, data, url, encoding, zipped);
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
      this.path(job, data, path,
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
   * @param job
   *          the job where logging info can be written
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
  final void _stream(final IOJob job, final S data,
      final InputStream stream, final StreamEncoding<?, ?> encoding,
      final boolean zipped) throws Throwable {
    final Class<?> clazz;

    if (zipped) {
      this.__loadZIP(job, data, stream, encoding);
    } else {
      if ((encoding != null) && (encoding != StreamEncoding.UNKNOWN)
          && (encoding != StreamEncoding.TEXT)
          && (encoding != StreamEncoding.BINARY)
          && ((clazz = encoding.getInputClass()) != null)
          && InputStream.class.isAssignableFrom(clazz)) {
        if (job.canLog(IOJob.FINE_LOG_LEVEL)) {
          job.log(IOJob.FINE_LOG_LEVEL,
              "Using byte stream encoding " + encoding.name()); //$NON-NLS-1$
        }
        try (final InputStream input = ((InputStream) (encoding
            .wrapInputStream(stream)))) {
          this.stream(job, data, stream, StreamEncoding.UNKNOWN);
        }
      } else {
        this.stream(job, data, stream, encoding);
      }
    }
  }

  /**
   * Handle a stream
   * 
   * @param job
   *          the job where logging info can be written
   * @param data
   *          the data to be read
   * @param stream
   *          the stream
   * @param encoding
   *          the encoding
   * @throws Throwable
   *           if it must
   */
  void stream(final IOJob job, final S data, final InputStream stream,
      final StreamEncoding<?, ?> encoding) throws Throwable {
    this._checkRawStreams();
  }

  /**
   * Handle a file
   * 
   * @param job
   *          the job where logging info can be written
   * @param data
   *          the data to be read
   * @param path
   *          the path
   * @param attributes
   *          the attributes
   * @param encoding
   *          the encoding
   * @throws Throwable
   *           if it must
   */
  protected void file(final IOJob job, final S data, final Path path,
      final BasicFileAttributes attributes,
      final StreamEncoding<?, ?> encoding) throws Throwable {
    //
  }

  /**
   * Enter a directory
   * 
   * @param job
   *          the job where logging info can be written
   * @param data
   *          the data to be read
   * @param path
   *          the path
   * @param attributes
   *          the attributes
   * @return {@code true} if the directory should be entered, {@code false}
   *         otherwise
   * @throws Throwable
   *           if it must
   */
  protected boolean enterDirectory(final IOJob job, final S data,
      final Path path, final BasicFileAttributes attributes)
      throws Throwable {
    return true;
  }

  /**
   * Check whether a file in a directory is loadable. This method is only
   * called if the input source does not just identify a single file, in
   * which case loading is enforced.
   * 
   * @param job
   *          the job
   * @param data
   *          the data
   * @param path
   *          the path to the file
   * @param attributes
   *          the attributes of the file
   * @return {@code true} if the file is loadable, {@code false} otherwise
   * @throws Throwable
   *           if it must
   */
  protected boolean isFileInDirectoryLoadable(final IOJob job,
      final S data, final Path path, final BasicFileAttributes attributes)
      throws Throwable {
    return ((attributes != null) && (attributes.isRegularFile()));
  }

  /**
   * Leave a directory
   * 
   * @param job
   *          the job where logging info can be written
   * @param data
   *          the data to be read
   * @param path
   *          the path
   * @throws Throwable
   *           if it must
   */
  protected void leaveDirectory(final IOJob job, final S data,
      final Path path) throws Throwable {
    //
  }

  /** {@inheritDoc} */
  @Override
  final void _path(final IOJob job, final S data, final Path path,
      final BasicFileAttributes attributes,
      final StreamEncoding<?, ?> encoding, final boolean zipped)
      throws Throwable {

    if (attributes == null) {
      throw new IOException("Path '" + path + //$NON-NLS-1$
          "' does not exist.");//$NON-NLS-1$
    }

    if (attributes.isRegularFile()) {
      this._file(job, data, path, attributes, encoding, zipped);
    } else {
      if (attributes.isDirectory()) {
        Files.walkFileTree(path, new _FileWalker<>(job, data, encoding,
            zipped, this));
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void path(final IOJob job, final S data, final Path path,
      final BasicFileAttributes attributes,
      final StreamEncoding<?, ?> encoding, final boolean zipped)
      throws Throwable {
    this._path(job, data, path, attributes, encoding, zipped);
  }
}
