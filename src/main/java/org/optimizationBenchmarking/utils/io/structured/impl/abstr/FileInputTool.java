package org.optimizationBenchmarking.utils.io.structured.impl.abstr;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.io.EArchiveType;
import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.io.paths.TempDir;
import org.optimizationBenchmarking.utils.io.structured.spec.IFileInputJobBuilder;
import org.optimizationBenchmarking.utils.io.structured.spec.IFileInputTool;
import org.optimizationBenchmarking.utils.text.TextUtils;

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

  /**
   * Are there any sources defined for this module in the given
   * configuration? In other words, if we create a job builder for this
   * input driver and configure it based on {@code config}, can we create
   * and execute a job from it?
   *
   * @param config
   *          the configuration
   * @return {@code true} if sources are defined, {@code false} otherwise
   */
  public final boolean areSourcesDefined(final Configuration config) {
    ArrayListView<String> sources;

    sources = this._getSources(config);
    return ((sources != null) && (!(sources.isEmpty())));
  }

  /**
   * Get the sources
   *
   * @param config
   *          the configuration
   * @return the sources
   */
  final ArrayListView<String> _getSources(final Configuration config) {
    String prefix, suffix;

    prefix = this.getParameterPrefix();
    if (prefix == null) {
      prefix = IOTool.INPUT_PARAM_PREFIX;
    }
    suffix = this.getSourcesParameterSuffix();
    if (suffix == null) {
      suffix = IOTool.PARAM_SOURCES_SUFFIX;
    }

    return config.getStringList((prefix + suffix), null);
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public IFileInputJobBuilder<S> use() {
    this.checkCanUse();
    return new _FileInputJobBuilder(this);
  }

  /** {@inheritDoc} */
  @Override
  protected String getParameterPrefix() {
    return IOTool.INPUT_PARAM_PREFIX;
  }

  /**
   * Get the suffix to be used for the source parameter
   *
   * @return the suffix to be used for the source parameter
   */
  protected String getSourcesParameterSuffix() {
    return IOTool.PARAM_SOURCES_SUFFIX;
  }

  /** {@inheritDoc} */
  @Override
  void _handle(final IOJob job, final S data, final _Location location)
      throws Throwable {
    final Logger logger;
    Class<?> clazz;

    logger = job.getLogger();

    if (location.m_location1 instanceof InputStream) {
      if (location.m_archiveType == null) {
        this._checkRawStreams();
      }
      if ((logger != null)
          && (logger.isLoggable(IOTool.DEFAULT_LOG_LEVEL))) {
        logger.log(IOTool.DEFAULT_LOG_LEVEL,//
            "Beginning input from InputStream."); //$NON-NLS-1$
      }
      this._stream(job, data, ((InputStream) (location.m_location1)),
          location.m_encoding, location.m_archiveType);
      if ((logger != null)
          && (logger.isLoggable(IOTool.DEFAULT_LOG_LEVEL))) {
        logger.log(IOTool.DEFAULT_LOG_LEVEL,//
            "Finished input from InputStream."); //$NON-NLS-1$
      }
      return;
    }

    if (location.m_location1 instanceof URL) {
      if (location.m_archiveType == null) {
        this._checkRawStreams();
      }
      if ((logger != null)
          && (logger.isLoggable(IOTool.DEFAULT_LOG_LEVEL))) {
        logger.log(IOTool.DEFAULT_LOG_LEVEL,//
            "Beginning input from URL " + location.m_location1); //$NON-NLS-1$
      }
      this.__url(job, data, ((URL) (location.m_location1)),
          location.m_encoding, location.m_archiveType);
      if ((logger != null)
          && (logger.isLoggable(IOTool.DEFAULT_LOG_LEVEL))) {
        logger.log(IOTool.DEFAULT_LOG_LEVEL,//
            "Finished input from URL " + location.m_location1); //$NON-NLS-1$
      }
      return;
    }

    if (location.m_location1 instanceof URI) {
      if (location.m_archiveType == null) {
        this._checkRawStreams();
      }
      if ((logger != null)
          && (logger.isLoggable(IOTool.DEFAULT_LOG_LEVEL))) {
        logger.log(IOTool.DEFAULT_LOG_LEVEL,//
            "Beginning input from URI " + location.m_location1); //$NON-NLS-1$
      }
      this.__uri(job, data, ((URI) (location.m_location1)),
          location.m_encoding, location.m_archiveType);
      if ((logger != null)
          && (logger.isLoggable(IOTool.DEFAULT_LOG_LEVEL))) {
        logger.log(IOTool.DEFAULT_LOG_LEVEL,//
            "Finished input from URI " + location.m_location1); //$NON-NLS-1$
      }
      return;
    }

    if ((location.m_location1 instanceof Class)
        && (location.m_location2 instanceof String)) {
      if (location.m_archiveType == null) {
        this._checkRawStreams();
      }
      if ((logger != null)
          && (logger.isLoggable(IOTool.DEFAULT_LOG_LEVEL))) {
        logger.log(IOTool.DEFAULT_LOG_LEVEL,//
            "Beginning input from resource '" + //$NON-NLS-1$
                location.m_location2
                + "' of class " + location.m_location1); //$NON-NLS-1$
      }
      this.__resource(job, data, ((Class<?>) (location.m_location1)),
          ((String) (location.m_location2)), location.m_encoding,
          location.m_archiveType);
      if ((logger != null)
          && (logger.isLoggable(IOTool.DEFAULT_LOG_LEVEL))) {
        logger.log(IOTool.DEFAULT_LOG_LEVEL,//
            "Finished input from resource '" + //$NON-NLS-1$
                location.m_location2
                + "' of class " + location.m_location1); //$NON-NLS-1$
      }
      return;
    }

    if (location.m_location1 instanceof String) {

      if (location.m_location2 instanceof Class) {
        clazz = ((Class<?>) (location.m_location2));

        if (URL.class.isAssignableFrom(clazz)) {
          if (location.m_archiveType == null) {
            this._checkRawStreams();
          }
          if ((logger != null)
              && (logger.isLoggable(IOTool.DEFAULT_LOG_LEVEL))) {
            logger.log(IOTool.DEFAULT_LOG_LEVEL,//
                "Beginning input from URL specified as String: " + //$NON-NLS-1$
                    location.m_location1);
          }
          this.__url(job, data, new URL((String) (location.m_location1)),
              location.m_encoding, location.m_archiveType);
          if ((logger != null)
              && (logger.isLoggable(IOTool.DEFAULT_LOG_LEVEL))) {
            logger.log(IOTool.DEFAULT_LOG_LEVEL,//
                "Finished input from URL specified as String: " + //$NON-NLS-1$
                    location.m_location1);
          }
          return;
        }

        if (URI.class.isAssignableFrom(clazz)) {
          if (location.m_archiveType == null) {
            this._checkRawStreams();
          }
          if ((logger != null)
              && (logger.isLoggable(IOTool.DEFAULT_LOG_LEVEL))) {
            logger.log(IOTool.DEFAULT_LOG_LEVEL,//
                "Beginning input from URI specified as String: " + //$NON-NLS-1$
                    location.m_location1);
          }
          this.__uri(job, data, new URI((String) (location.m_location1)),
              location.m_encoding, location.m_archiveType);
          if ((logger != null)
              && (logger.isLoggable(IOTool.DEFAULT_LOG_LEVEL))) {
            logger.log(IOTool.DEFAULT_LOG_LEVEL,//
                "Finished input from URI specified as String: " + //$NON-NLS-1$
                    location.m_location1);
          }
          return;
        }
      }

      if (location.m_location2 instanceof String) {
        if (location.m_archiveType == null) {
          this._checkRawStreams();
        }
        if ((logger != null)
            && (logger.isLoggable(IOTool.DEFAULT_LOG_LEVEL))) {
          logger.log(IOTool.DEFAULT_LOG_LEVEL,//
              "Beginning input from Resource specified as String: " + //$NON-NLS-1$
                  location.m_location1 + ':' + location.m_location2);
        }
        this.__resource(job, data,
            Class.forName((String) (location.m_location1)),
            ((String) (location.m_location2)), location.m_encoding,
            location.m_archiveType);
        if ((logger != null)
            && (logger.isLoggable(IOTool.DEFAULT_LOG_LEVEL))) {
          logger.log(IOTool.DEFAULT_LOG_LEVEL,//
              "Finished input from Resource specified as String: " + //$NON-NLS-1$
                  location.m_location1 + ':' + location.m_location2);
        }
        return;
      }
    }

    super._handle(job, data, location);
  }

  /**
   * Load an archive
   *
   * @param job
   *          the job where logging info can be written
   * @param data
   *          the data to be read
   * @param stream
   *          the stream
   * @param encoding
   *          the encoding
   * @param type
   *          the archive type
   * @throws Throwable
   *           if it must
   */
  private final void __loadArchive(final IOJob job, final S data,
      final InputStream stream, final StreamEncoding<?, ?> encoding,
      final EArchiveType type) throws Throwable {
    final Path path;
    final Logger logger;

    logger = job.getLogger();
    try (final TempDir temp = new TempDir()) {
      path = temp.getPath();

      if ((logger != null) && (logger.isLoggable(IOTool.FINE_LOG_LEVEL))) {
        logger.log(IOTool.FINE_LOG_LEVEL,//
            ((("Begin decompressing " + type.getName() + //$NON-NLS-1$
                " to temporary folder '" //$NON-NLS-1$
            + path) + '\'') + '.'));
      }
      type.decompressStreamToFolder(stream, path,
          this.getArchiveFallbackFileName());
      if ((logger != null) && (logger.isLoggable(IOTool.FINE_LOG_LEVEL))) {
        logger.log(IOTool.FINE_LOG_LEVEL,//
            ((("Finished decompressing to temporary folder '" //$NON-NLS-1$
            + path) + '\'') + '.'));
      }
      this._path(job, data, path,
          Files.readAttributes(path, BasicFileAttributes.class), encoding,
          null);
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
   * @param archiveType
   *          the expected archive type
   * @throws Throwable
   *           if it must
   */
  final void _file(final IOJob job, final S data, final Path path,
      final BasicFileAttributes attributes,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType)
      throws Throwable {
    final Object oldCur;
    final Logger logger;

    oldCur = job.m_current;
    logger = job.getLogger();
    try {
      job.m_current = path;

      if (archiveType != null) {
        if ((logger != null)
            && (logger.isLoggable(IOTool.DEFAULT_LOG_LEVEL))) {
          logger.log(IOTool.DEFAULT_LOG_LEVEL,//
              (("Decompressing path '" + path) + '\'')); //$NON-NLS-1$
        }
        try (final InputStream stream = PathUtils.openInputStream(path)) {
          this.__loadArchive(job, data, stream, encoding, archiveType);
        }
      } else {
        this.file(job, data, path, attributes, encoding);
      }
    } finally {
      job.m_current = oldCur;
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
   * @param archiveType
   *          the expected archive type
   * @throws Throwable
   *           if it must
   */
  private final void __resource(final IOJob job, final S data,
      final Class<?> clazz, final String name,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType)
      throws Throwable {
    final Object oldCur;

    oldCur = job.m_current;
    try {
      job.m_current = (TextUtils.className(clazz) + '#' + name);
      try (final InputStream stream = clazz.getResourceAsStream(name)) {
        this._stream(job, data, stream, encoding, archiveType);
      }
    } finally {
      job.m_current = oldCur;
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
   * @param archiveType
   *          the archive type
   * @throws Throwable
   *           if it must
   */
  private final void __url(final IOJob job, final S data, final URL url,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType)
      throws Throwable {
    final Object oldCur;
    oldCur = job.m_current;
    try {
      job.m_current = url;
      try (final InputStream stream = url.openStream()) {
        this._stream(job, data, stream, encoding, archiveType);
      }
    } finally {
      job.m_current = oldCur;
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
   * @param archiveType
   *          the archive type
   * @throws Throwable
   *           if it must
   */
  private final void __uri(final IOJob job, final S data, final URI uri,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType)
      throws Throwable {
    URL url;
    Path path;
    Throwable errorA, errorB, ioError;

    final Object oldCur;
    oldCur = job.m_current;
    try {
      job.m_current = uri;

      errorA = null;
      url = null;
      try {
        url = uri.toURL();
      } catch (final Throwable throwable) {
        errorA = throwable;
        url = null;
      }

      if (url != null) {
        this.__url(job, data, url, encoding, archiveType);
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
        this._path(job, data, path,
            Files.readAttributes(path, BasicFileAttributes.class),
            encoding, archiveType);
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
    } finally {
      job.m_current = oldCur;
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
   * @param archiveType
   *          is the stream compressed?
   * @throws Throwable
   *           if it must
   */
  final void _stream(final IOJob job, final S data,
      final InputStream stream, final StreamEncoding<?, ?> encoding,
      final EArchiveType archiveType) throws Throwable {
    final Class<?> clazz;
    final Logger logger;

    if (archiveType != null) {
      this.__loadArchive(job, data, stream, encoding, archiveType);
    } else {
      if ((encoding != null) && (encoding != StreamEncoding.UNKNOWN)
          && (encoding != StreamEncoding.TEXT)
          && (encoding != StreamEncoding.BINARY)
          && ((clazz = encoding.getInputClass()) != null)
          && InputStream.class.isAssignableFrom(clazz)) {
        logger = job.getLogger();
        if ((logger != null) && (logger.isLoggable(IOTool.FINE_LOG_LEVEL))) {
          logger.log(IOTool.FINE_LOG_LEVEL,//
              ("Using byte stream encoding " + encoding.name())); //$NON-NLS-1$
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
    return ((path != null) && (attributes != null) && //
    (attributes.isRegularFile()));
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
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType)
      throws Throwable {

    final Object oldCur;
    oldCur = job.m_current;
    try {
      job.m_current = path;

      if (attributes == null) {
        throw new IOException("Path '" + path + //$NON-NLS-1$
            "' does not exist.");//$NON-NLS-1$
      }

      if (archiveType == null) {
        this.path(job, data, path, attributes, encoding);
      } else {
        if (attributes.isRegularFile()) {
          this._file(job, data, path, attributes, encoding, archiveType);
        } else {
          if (attributes.isDirectory()) {
            Files.walkFileTree(path, new _FileWalker<>(job, data,
                encoding, archiveType, this));
          }
        }
      }
    } finally {
      job.m_current = oldCur;
    }
  }

  /**
   * Process an uncompressed path, which may either be a directory or a
   * file.
   *
   * @param job
   *          the job
   * @param data
   *          the data store
   * @param path
   *          the path
   * @param attributes
   *          the attributes
   * @param encoding
   *          the encoding
   * @throws Throwable
   *           if i/o fails
   */
  protected void path(final IOJob job, final S data, final Path path,
      final BasicFileAttributes attributes,
      final StreamEncoding<?, ?> encoding) throws Throwable {
    final Object oldCur;

    oldCur = job.m_current;
    try {
      job.m_current = path;

      if (attributes.isRegularFile()) {
        this._file(job, data, path, attributes, encoding, null);
      } else {
        if (attributes.isDirectory()) {
          Files.walkFileTree(path, new _FileWalker<>(job, data, encoding,
              null, this));
        }
      }
    } finally {
      job.m_current = oldCur;
    }
  }

}
