package org.optimizationBenchmarking.utils.io.structured;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;
import org.optimizationBenchmarking.utils.io.files.TempDir;
import org.optimizationBenchmarking.utils.io.files.UnzipToFolder;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.AbstractTextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A base class for structured, file-based I/O
 * 
 * @param <S>
 *          the object to store
 * @param <L>
 *          the context object to load into
 */
public abstract class FileIODriver<S, L> {

  /** create */
  protected FileIODriver() {
    super();
  }

  /**
   * Load an object from a file, while writing logging information to the
   * given logger. The specified encoding {@code defaultEncoding} is
   * assumed if no encoding can automatically detected. This method is
   * called under two circumstances:
   * <ol>
   * <li>The loader was applied to a directory and {@code file} is a file
   * in that directory or a subdirectory for which
   * {@link #isFileInDirectoryLoadable(Object, Path)} returned {@code true}
   * .</li>
   * <li>The loader was applied to a single file. In this case,
   * {@link #isFileInDirectoryLoadable(Object, Path)} is not checked.</li>
   * </ol>
   * 
   * @param loadContext
   *          the context to load the data into
   * @param file
   *          the normalized absolute path representing a file
   * @param logger
   *          the logger for log output
   * @param defaultEncoding
   *          the expected default encoding
   * @throws IOException
   *           if I/O fails
   */
  protected void doLoadFile(final L loadContext, final Path file,
      final Logger logger, final StreamEncoding<?, ?> defaultEncoding)
      throws IOException {
    try (final InputStream stream = file.getFileSystem().provider()
        .newInputStream(file)) {
      this.doLoadStream(loadContext, file, stream, logger, defaultEncoding);
    }
  }

  /**
   * Load an object from a stream, while writing logging information to the
   * given logger. The specified encoding {@code defaultEncoding} is
   * assumed if no encoding can automatically detected.
   * 
   * @param loadContext
   *          the object to store the load data into
   * @param file
   *          the path to the file represented by the stream
   * @param stream
   *          the input stream
   * @param logger
   *          the logger for log output
   * @param defaultEncoding
   *          the expected default encoding
   * @throws IOException
   *           if I/O fails
   */
  @SuppressWarnings("unused")
  protected void doLoadStream(final L loadContext, final Path file,
      final InputStream stream, final Logger logger,
      final StreamEncoding<?, ?> defaultEncoding) throws IOException {

    try {
      try (final Reader reader = (((defaultEncoding != null) && //
      (Reader.class.isAssignableFrom(defaultEncoding.getInputClass())))) ? //
      ((Reader) (defaultEncoding.wrapInputStream(stream)))
          : new InputStreamReader(stream)) {
        if (reader instanceof BufferedReader) {
          this.doLoadReader(loadContext, file, ((BufferedReader) reader),
              logger);
        } else {
          try (final BufferedReader br = new BufferedReader(reader)) {
            this.doLoadReader(loadContext, file, br, logger);
          }
        }
      }
    } catch (final IOException t) {
      if ((logger != null) && (logger.isLoggable(Level.SEVERE))) {
        logger.log(//
            Level.SEVERE,//
            ("Error during the doLoadStream-doLoadReaders bridge of " + //$NON-NLS-1$
                TextUtils.className(this.getClass()) + '.'),//
            t);
      }
    }
  }

  /**
   * Load an object from a set of readers, while writing logging
   * information to the given logger.
   * 
   * @param loadContext
   *          the loading context
   * @param file
   *          the path to the file represented by the given reader
   * @param reader
   *          the reader
   * @param logger
   *          the logger for log output
   * @throws IOException
   *           if I/O fails
   */
  @SuppressWarnings("unused")
  protected void doLoadReader(final L loadContext, final Path file,
      final BufferedReader reader, final Logger logger) throws IOException {
    throw new UnsupportedOperationException();
  }

  /**
   * The parser enters a directory.
   * 
   * @param loadContext
   *          the context to load the data into
   * @param directory
   *          the normalized absolute path representing the directory
   * @param logger
   *          the logger for log output
   * @param defaultEncoding
   *          the expected default encoding
   * @return {@code true} if the directory should be visited, {@code false}
   *         if the directory and all of its sub-directories should be
   *         skipped
   * @throws IOException
   *           if I/O fails
   */
  @SuppressWarnings("unused")
  protected boolean doEnterDirectory(final L loadContext,
      final Path directory, final Logger logger,
      final StreamEncoding<?, ?> defaultEncoding) throws IOException {
    return true;
  }

  /**
   * The parser leaves a directory.
   * 
   * @param loadContext
   *          the context to load the data into
   * @param directory
   *          the normalized absolute path representing the directory
   * @param logger
   *          the logger for log output
   * @param defaultEncoding
   *          the expected default encoding
   * @throws IOException
   *           if I/O fails
   */
  @SuppressWarnings("unused")
  protected void doLeaveDirectory(final L loadContext,
      final Path directory, final Logger logger,
      final StreamEncoding<?, ?> defaultEncoding) throws IOException {
    //
  }

  /**
   * Is a file loadable?
   * 
   * @param loadContext
   *          the load context
   * @param file
   *          the file
   * @return {@code true} if the
   *         {@link #doLoadFile(Object, Path, Logger, StreamEncoding)}
   *         method should be invoked for the file, {@code false} otherwise
   */
  protected boolean isFileInDirectoryLoadable(final L loadContext,
      final Path file) {
    return true;
  }

  /**
   * Load an object from a path, while writing logging information to the
   * given logger. The specified encoding {@code defaultEncoding} is
   * assumed if no encoding can automatically detected.
   * 
   * @param loadContext
   *          the context to load the data into
   * @param path
   *          the path
   * @param logger
   *          the logger for log output
   * @param defaultEncoding
   *          the expected default encoding
   * @throws IOException
   *           if I/O fails
   */
  protected void doLoadPath(final L loadContext, final Path path,
      final Logger logger, final StreamEncoding<?, ?> defaultEncoding)
      throws IOException {
    BasicFileAttributes bas;

    bas = Files.readAttributes(path, BasicFileAttributes.class);
    if (bas.isRegularFile()) {
      this.doLoadFile(loadContext, path, logger, defaultEncoding);
    } else {
      Files.walkFileTree(path, new _FileWalker<>(loadContext, logger,
          defaultEncoding, this));
    }
  }

  /**
   * Load an object from a path, while writing logging information to the
   * given logger. The specified encoding {@code defaultEncoding} is
   * assumed if no encoding can automatically detected.
   * 
   * @param loadContext
   *          the context to load the data into
   * @param path
   *          the path
   * @param logger
   *          the logger for log output
   * @param defaultEncoding
   *          the expected default encoding
   * @throws IOException
   *           if I/O fails
   */
  public final void loadPath(final L loadContext, final Path path,
      final Logger logger, final StreamEncoding<?, ?> defaultEncoding)
      throws IOException {
    Path use, tp;
    Throwable recoverable;

    recoverable = null;

    try {
      if ((logger != null) && (logger.isLoggable(Level.INFO))) {
        logger.info((("Beginning to process source '" + path) //$NON-NLS-1$
            + '\'') + '.');
      }

      use = path;
      try {
        tp = use.normalize();
        if (tp != null) {
          use = tp;
        }
        tp = use.toAbsolutePath();
        if (tp != null) {
          use = tp;
        }
        tp = use.toRealPath();
        if (tp != null) {
          use = tp;
        }

        if (tp != use) {
          if ((logger != null) && (logger.isLoggable(Level.FINEST))) {
            logger.finest(((((("Source path '" + path) //$NON-NLS-1$
                + "' has been converted to normalized/absolute/real path '")//$NON-NLS-1$
                + use) + '\'') + '.'));
          }
        }
      } catch (final Throwable c) {
        recoverable = c;
      }

      this.doLoadPath(loadContext, use, logger, defaultEncoding);

      if ((logger != null) && (logger.isLoggable(Level.INFO))) {
        logger.info((("Finished processing source '" + path) //$NON-NLS-1$
            + '\'') + '.');
      }
    } catch (final Throwable t) {
      ErrorUtils.throwAsIOException(t, recoverable);
    }
  }

  /**
   * Load an object from a file, while writing logging information to the
   * given logger. The specified encoding {@code defaultEncoding} is
   * assumed if no encoding can automatically detected.
   * 
   * @param loadContext
   *          the context to load the data into
   * @param file
   *          the file
   * @param logger
   *          the logger for log output
   * @param defaultEncoding
   *          the expected default encoding
   * @throws IOException
   *           if I/O fails
   */
  public final void loadFile(final L loadContext, final File file,
      final Logger logger, final StreamEncoding<?, ?> defaultEncoding)
      throws IOException {
    this.loadPath(loadContext, file.toPath(), logger, defaultEncoding);
  }

  /**
   * Load an object from a set of files, without producing any log output.
   * 
   * @param loadContext
   *          the context to load the data into
   * @param path
   *          the path
   * @throws IOException
   *           if I/O fails
   */
  public final void loadPath(final L loadContext, final Path path)
      throws IOException {
    this.loadPath(loadContext, path, null, StreamEncoding.UNKNOWN);
  }

  /**
   * Load an object from a set of files, without producing any log output.
   * 
   * @param loadContext
   *          the context to load the data into
   * @param file
   *          the file
   * @throws IOException
   *           if I/O fails
   */
  public final void loadFile(final L loadContext, final File file)
      throws IOException {
    this.loadFile(loadContext, file, null, StreamEncoding.UNKNOWN);
  }

  /**
   * Store the given object in a file or directory.
   * 
   * @param data
   *          the object to write
   * @param path
   *          the destination path
   * @throws IOException
   *           if I/O fails
   */
  public final void storePath(final S data, final Path path)
      throws IOException {
    this.storePath(data, path, null, StreamEncoding.UNKNOWN);
  }

  /**
   * Store the given object in a file or directory, while writing log
   * information to the given logger and using the specified
   * {@code defaultEncoding} if the file format allows to do so.
   * 
   * @param data
   *          the object to write
   * @param path
   *          the destination path
   * @param logger
   *          the logger
   * @param defaultEncoding
   *          the encoding to be used by default
   * @throws IOException
   *           if I/O fails
   */
  public final void storePath(final S data, final Path path,
      final Logger logger, final StreamEncoding<?, ?> defaultEncoding)
      throws IOException {
    final BasicFileAttributes attr;
    Path use, tp;
    Throwable recoverable;
    boolean isFile, isDir;

    recoverable = null;

    try {
      if ((logger != null) && (logger.isLoggable(Level.INFO))) {
        logger.info((("Beginning to process destination '" + path) //$NON-NLS-1$
            + '\'') + '.');
      }

      use = path;
      try {
        tp = use.normalize();
        if (tp != null) {
          use = tp;
        }
        tp = use.toAbsolutePath();
        if (tp != null) {
          use = tp;
        }
        tp = use.toRealPath();
        if (tp != null) {
          use = tp;
        }

        if (tp != use) {
          if ((logger != null) && (logger.isLoggable(Level.FINEST))) {
            logger.finest(((((("Destination path '" + path) //$NON-NLS-1$
                + "' has been converted to normalized/absolute/real path '")//$NON-NLS-1$
                + use) + '\'') + '.'));
          }
        }
      } catch (final Throwable c) {
        recoverable = c;
      }

      isDir = false;
      isFile = true;
      try {
        attr = Files.readAttributes(use, BasicFileAttributes.class);
        isFile = attr.isRegularFile();
        isDir = attr.isDirectory();
      } catch (final Throwable tt) {
        recoverable = ErrorUtils.aggregateError(recoverable, tt);
      }

      if (isDir) {
        this.doStoreDirectory(data, use, logger, defaultEncoding);
      } else {
        if (isFile) {
          Files.createDirectories(use.getParent());
          this.doStoreFile(data, use, logger, defaultEncoding);
        }
      }

      if ((logger != null) && (logger.isLoggable(Level.INFO))) {
        logger.info((("Finished processing destination '" + path) //$NON-NLS-1$
            + '\'') + '.');
      }
    } catch (final Throwable t) {
      ErrorUtils.throwAsIOException(t, recoverable);
    }
  }

  /**
   * Store the given object in a file, while writing log information to the
   * given logger and using the specified {@code defaultEncoding} if the
   * file format allows to do so.
   * 
   * @param data
   *          the object to write
   * @param file
   *          the destination file
   * @param logger
   *          the logger
   * @param defaultEncoding
   *          the encoding to be used by default
   * @throws IOException
   *           if I/O fails
   */
  protected void doStoreFile(final S data, final Path file,
      final Logger logger, final StreamEncoding<?, ?> defaultEncoding)
      throws IOException {
    try (final OutputStream stream = file.getFileSystem().provider()
        .newOutputStream(file)) {
      this.doStoreStream(data, file, stream, logger, defaultEncoding);
    }
  }

  /**
   * Store the given object in a stream, while writing log information to
   * the given logger and using the specified {@code defaultEncoding} if
   * the file format allows to do so.
   * 
   * @param data
   *          the object
   * @param file
   *          the file to which the stream belongs
   * @param stream
   *          the destination stream
   * @param logger
   *          the logger
   * @param defaultEncoding
   *          the encoding to be used by default
   * @throws IOException
   *           if I/O fails
   */
  protected void doStoreStream(final S data, final Path file,
      final OutputStream stream, final Logger logger,
      final StreamEncoding<?, ?> defaultEncoding) throws IOException {
    try {

      if (stream instanceof Appendable) {
        this.doStoreText(data, file,
            AbstractTextOutput.wrap((Appendable) stream), logger);
      } else {
        try (final Writer writer = (((defaultEncoding != null) && //
        (Writer.class.isAssignableFrom(defaultEncoding.getOutputClass())))) ? //
        ((Writer) (defaultEncoding.wrapOutputStream(stream)))
            : new OutputStreamWriter(stream)) {
          this.doStoreWriter(data, file, writer, logger);
        }
      }
    } catch (final IOException t) {
      if ((logger != null) && (logger.isLoggable(Level.SEVERE))) {
        logger.log(//
            Level.SEVERE,//
            ("Error during doStoreStream-doStorewriter bridge of " + //$NON-NLS-1$
                TextUtils.className(this.getClass()) + '.'),//
            t);
      }
      throw t;
    }
  }

  /**
   * Store the given object in a writer, while writing log information to
   * the given logger.
   * 
   * @param data
   *          the object
   * @param file
   *          the file to which the stream belongs
   * @param writer
   *          the destination writer
   * @param logger
   *          the logger
   * @throws IOException
   *           if I/O fails
   */
  protected final void doStoreWriter(final S data, final Path file,
      final Writer writer, final Logger logger) throws IOException {
    try {
      this.doStoreText(data, file, AbstractTextOutput.wrap(writer), logger);
    } catch (final Throwable t) {
      if ((logger != null) && (logger.isLoggable(Level.SEVERE))) {
        logger.log(//
            Level.SEVERE,//
            ("Error caught by doStoreWriter-doStoreTextOutput bridge of " + //$NON-NLS-1$
                TextUtils.className(this.getClass()) + '.'),//
            t);
      }
      ErrorUtils.throwAsIOException(t);
    }
  }

  /**
   * Store data to a
   * {@link org.optimizationBenchmarking.utils.text.textOutput.ITextOutput
   * text output} device
   * 
   * @param data
   *          the data to be stored
   * @param file
   *          the path it is written to
   * @param textOut
   *          the
   *          {@link org.optimizationBenchmarking.utils.text.textOutput.ITextOutput
   *          text output} device
   * @param logger
   *          the logger
   */
  protected void doStoreText(final S data, final Path file,
      final ITextOutput textOut, final Logger logger) {
    throw new UnsupportedOperationException();
  }

  /**
   * Store the given object in a directory, while writing log information
   * to the given logger and using the specified {@code defaultEncoding} if
   * the file format allows to do so.
   * 
   * @param data
   *          the object to write
   * @param directory
   *          the destination directory
   * @param logger
   *          the logger
   * @param defaultEncoding
   *          the encoding to be used by default
   * @throws IOException
   *           if I/O fails
   */
  protected void doStoreDirectory(final S data, final Path directory,
      final Logger logger, final StreamEncoding<?, ?> defaultEncoding)
      throws IOException {
    this.doStoreFile(
        data,
        directory.getFileSystem().getPath(directory.toString(),
            "output.txt"), //$NON-NLS-1$
        logger, defaultEncoding);
  }

  /**
   * Store the given object in a file or directory, while writing log
   * information to the given logger and using the specified
   * {@code defaultEncoding} if the file format allows to do so.
   * 
   * @param data
   *          the object to write
   * @param file
   *          the destination file or directory
   * @param logger
   *          the logger
   * @param defaultEncoding
   *          the encoding to be used by default
   * @throws IOException
   *           if I/O fails
   */
  public final void storeFile(final S data, final File file,
      final Logger logger, final StreamEncoding<?, ?> defaultEncoding)
      throws IOException {
    this.storePath(data, file.toPath(), logger, defaultEncoding);
  }

  /**
   * Store the given object in a file or directory.
   * 
   * @param data
   *          the object
   * @param dest
   *          the destination file or directory
   * @throws IOException
   *           if I/O fails
   */
  public final void storeFile(final S data, final File dest)
      throws IOException {
    this.storeFile(data, dest, null, StreamEncoding.UNKNOWN);
  }

  /**
   * Load data from a zip archive or resource
   * 
   * @param loadContext
   *          the loader context
   * @param stream
   *          the stream
   * @throws IOException
   *           if I/O fails
   */
  public final void loadZIPArchive(final L loadContext,
      final InputStream stream) throws IOException {
    this.loadZIPArchive(loadContext, stream, null, null);
  }

  /**
   * Load data from a ZIP archive. This method first unpacks the stream
   * into a temporary folder and then parses the folder with
   * {@link #loadFile(Object, File, Logger, StreamEncoding)}.
   * 
   * @param loadContext
   *          the loader context
   * @param stream
   *          the stream
   * @param logger
   *          the logger
   * @param defaultEncoding
   *          the encoding to be used by default
   * @throws IOException
   *           if I/O fails
   */
  public final void loadZIPArchive(final L loadContext,
      final InputStream stream, final Logger logger,
      final StreamEncoding<?, ?> defaultEncoding) throws IOException {
    final File dir;

    if ((logger != null) && (logger.isLoggable(Level.FINER))) {
      logger.finer(//
          "Creating temporary folder to unzip ZIP stream into."); //$NON-NLS-1$
    }

    try (final TempDir td = new TempDir()) {
      dir = td.getDir();

      if ((logger != null) && (logger.isLoggable(Level.FINE))) {
        logger.fine(((//
            "Unzipping ZIP stream into temporary folder '" + //$NON-NLS-1$
            dir) + '\'') + '.');
      }

      new UnzipToFolder(stream, dir).call();

      if ((logger != null) && (logger.isLoggable(Level.FINE))) {
        logger.fine((//
            "Finished unzipping ZIP stream into temporary folder '" + //$NON-NLS-1$
            dir)
            + "', now loading the folder."); //$NON-NLS-1$
      }

      this.loadFile(loadContext, dir, logger, defaultEncoding);

      if ((logger != null) && (logger.isLoggable(Level.FINE))) {
        logger.fine((//
            "Finished loading the data from the temporary folder '" + //$NON-NLS-1$
            dir)
            + "', now cleaning up the temporary files."); //$NON-NLS-1$
      }
    }
  }

  /**
   * Load an object from a URL, while writing logging information to the
   * given logger. The specified encoding {@code defaultEncoding} is
   * assumed if no encoding can automatically detected.
   * 
   * @param loadContext
   *          the object to store the load data into
   * @param url
   *          the url
   * @param logger
   *          the logger for log output
   * @param defaultEncoding
   *          the expected default encoding
   * @throws IOException
   *           if I/O fails
   */
  public final void loadURL(final L loadContext, final URL url,
      final Logger logger, final StreamEncoding<?, ?> defaultEncoding)
      throws IOException {
    if ((logger != null) && (logger.isLoggable(Level.FINE))) {
      logger.fine((("Now loading url '" + url) + '\'') + '.'); //$NON-NLS-1$
    }
    try {
      this.doLoadURL(loadContext, url, logger, defaultEncoding);
    } catch (final Throwable t) {
      if ((logger != null) && (logger.isLoggable(Level.SEVERE))) {
        logger.log(Level.SEVERE,
            ((("Unrecoverable error during loading from url '"//$NON-NLS-1$
            + url) + '\'') + '.'), t);
      }
      ErrorUtils.throwAsIOException(t);
    }
    if ((logger != null) && (logger.isLoggable(Level.FINE))) {
      logger.fine((("Finished loading url '" + url) + '\'') + '.'); //$NON-NLS-1$
    }
  }

  /**
   * Load an object from a URL, while writing logging information to the
   * given logger. The specified encoding {@code defaultEncoding} is
   * assumed if no encoding can automatically detected.
   * 
   * @param loadContext
   *          the object to store the load data into
   * @param url
   *          the url
   * @param logger
   *          the logger for log output
   * @param defaultEncoding
   *          the expected default encoding
   * @throws IOException
   *           if I/O fails
   */
  protected void doLoadURL(final L loadContext, final URL url,
      final Logger logger, final StreamEncoding<?, ?> defaultEncoding)
      throws IOException {
    try {
      this.doLoadURI(loadContext, url.toURI(), logger, defaultEncoding);
    } catch (final URISyntaxException use) {
      ErrorUtils.throwAsIOException(use);
    }
  }

  /**
   * Load an object from a URL.
   * 
   * @param loadContext
   *          the object to store the load data into
   * @param url
   *          the url
   * @throws IOException
   *           if I/O fails
   */
  public final void loadURL(final L loadContext, final URL url)
      throws IOException {
    this.loadURL(loadContext, url, null, StreamEncoding.UNKNOWN);
  }

  /**
   * Load an object from a URI.
   * 
   * @param loadContext
   *          the object to store the load data into
   * @param uri
   *          the uri
   * @throws IOException
   *           if I/O fails
   */
  public final void loadURI(final L loadContext, final URI uri)
      throws IOException {
    this.loadURI(loadContext, uri, null, StreamEncoding.UNKNOWN);
  }

  /**
   * Load an object from a URI, while writing logging information to the
   * given logger. The specified encoding {@code defaultEncoding} is
   * assumed if no encoding can automatically detected.
   * 
   * @param loadContext
   *          the object to store the load data into
   * @param uri
   *          the uri
   * @param logger
   *          the logger for log output
   * @param defaultEncoding
   *          the expected default encoding
   * @throws IOException
   *           if I/O fails
   */
  public final void loadURI(final L loadContext, final URI uri,
      final Logger logger, final StreamEncoding<?, ?> defaultEncoding)
      throws IOException {

    if ((logger != null) && (logger.isLoggable(Level.FINE))) {
      logger.fine((("Now loading uri '" + uri) + '\'') + '.'); //$NON-NLS-1$
    }

    try {
      this.doLoadURI(loadContext, uri, logger, defaultEncoding);
    } catch (final Throwable t) {
      if ((logger != null) && (logger.isLoggable(Level.SEVERE))) {
        logger.log(Level.SEVERE,
            ((("Unrecoverable error during loading from uri '"//$NON-NLS-1$
            + uri) + '\'') + '.'), t);
      }
      ErrorUtils.throwAsIOException(t);
    }

    if ((logger != null) && (logger.isLoggable(Level.FINE))) {
      logger.fine((("Finished loading uri '" + uri) + '\'') + '.'); //$NON-NLS-1$
    }
  }

  /**
   * Load an object from a URI, while writing logging information to the
   * given logger. The specified encoding {@code defaultEncoding} is
   * assumed if no encoding can automatically detected.
   * 
   * @param loadContext
   *          the object to store the load data into
   * @param uri
   *          the uri
   * @param logger
   *          the logger for log output
   * @param defaultEncoding
   *          the expected default encoding
   * @throws IOException
   *           if I/O fails
   */
  protected void doLoadURI(final L loadContext, final URI uri,
      final Logger logger, final StreamEncoding<?, ?> defaultEncoding)
      throws IOException {
    this.loadPath(loadContext, Paths.get(uri), logger, defaultEncoding);
  }

  /**
   * Load a resource from a package identified by a given class.
   * 
   * @param loadContext
   *          the object to store the load data into
   * @param clazz
   *          the class to use to resolve the resource
   * @param resource
   *          the resource name
   * @throws IOException
   *           if I/O fails
   */
  public final void loadResource(final L loadContext,
      final Class<?> clazz, final String resource) throws IOException {
    this.loadResource(loadContext, clazz, resource, null,
        StreamEncoding.UNKNOWN);
  }

  /**
   * Load a resource from a package identified by a given class, while
   * writing logging information to the given logger. The specified
   * encoding {@code defaultEncoding} is assumed if no encoding can
   * automatically detected.
   * 
   * @param loadContext
   *          the object to store the load data into
   * @param clazz
   *          the class to use to resolve the resource
   * @param resource
   *          the resource name
   * @param logger
   *          the logger for log output
   * @param defaultEncoding
   *          the expected default encoding
   * @throws IOException
   *           if I/O fails
   */
  public final void loadResource(final L loadContext,
      final Class<?> clazz, final String resource, final Logger logger,
      final StreamEncoding<?, ?> defaultEncoding) throws IOException {
    String cn;

    cn = null;
    if ((logger != null) && (logger.isLoggable(Level.FINE))) {
      logger.fine((("Now loading resource '" + resource) + //$NON-NLS-1$
          "' from class " + (cn = TextUtils.className(clazz))) + '.'); //$NON-NLS-1$
    }

    try {
      this.doLoadResource(loadContext, clazz, resource, logger,
          defaultEncoding);
    } catch (final Throwable t) {
      if ((logger != null) && (logger.isLoggable(Level.SEVERE))) {
        if (cn == null) {
          cn = TextUtils.className(clazz);
        }
        logger.log(Level.SEVERE,
            ("Unrecoverable error during loading resource '" + //$NON-NLS-1$ 
                resource + "' from class "//$NON-NLS-1$
                + cn + '.'), t);
      }
      ErrorUtils.throwAsIOException(t);
    }

    if ((logger != null) && (logger.isLoggable(Level.FINE))) {
      if (cn == null) {
        cn = TextUtils.className(clazz);
      }
      logger.fine((("Finished loading resource '" + resource) + //$NON-NLS-1$
          "' from class " + cn) + '.'); //$NON-NLS-1$
    }
  }

  /**
   * Load a resource from a package identified by a given class, while
   * writing logging information to the given logger. The specified
   * encoding {@code defaultEncoding} is assumed if no encoding can
   * automatically detected.
   * 
   * @param loadContext
   *          the object to store the load data into the object to store
   *          the load data into
   * @param clazz
   *          the class to use to resolve the resource
   * @param resource
   *          the resource name
   * @param logger
   *          the logger for log output
   * @param defaultEncoding
   *          the expected default encoding
   * @throws IOException
   *           if I/O fails
   */
  protected void doLoadResource(final L loadContext, final Class<?> clazz,
      final String resource, final Logger logger,
      final StreamEncoding<?, ?> defaultEncoding) throws IOException {
    this.loadURL(loadContext, clazz.getResource(resource), logger,
        defaultEncoding);
  }

  /**
   * Load a zipped resource from a package identified by a given class.
   * 
   * @param loadContext
   *          the object to store the load data into
   * @param clazz
   *          the class to use to resolve the resource
   * @param resource
   *          the resource name
   * @throws IOException
   *           if I/O fails
   */
  public final void loadResourceZIP(final L loadContext,
      final Class<?> clazz, final String resource) throws IOException {
    this.loadResourceZIP(loadContext, clazz, resource, null,
        StreamEncoding.UNKNOWN);
  }

  /**
   * Load a zipped resource from a package identified by a given class,
   * while writing logging information to the given logger. The specified
   * encoding {@code defaultEncoding} is assumed if no encoding can
   * automatically detected.
   * 
   * @param loadContext
   *          the object to store the load data into
   * @param clazz
   *          the class to use to resolve the resource
   * @param resource
   *          the resource name
   * @param logger
   *          the logger for log output
   * @param defaultEncoding
   *          the expected default encoding
   * @throws IOException
   *           if I/O fails
   */
  public final void loadResourceZIP(final L loadContext,
      final Class<?> clazz, final String resource, final Logger logger,
      final StreamEncoding<?, ?> defaultEncoding) throws IOException {
    String cn;

    cn = null;
    if ((logger != null) && (logger.isLoggable(Level.FINE))) {
      logger.fine((("Now loading zipped resource '" + resource) + //$NON-NLS-1$
          "' from class " + (cn = TextUtils.className(clazz))) + '.'); //$NON-NLS-1$
    }

    try {
      this.doLoadResourceZIP(loadContext, clazz, resource, logger,
          defaultEncoding);
    } catch (final Throwable t) {
      if ((logger != null) && (logger.isLoggable(Level.SEVERE))) {
        if (cn == null) {
          cn = TextUtils.className(clazz);
        }
        logger.log(Level.SEVERE,
            ("Unrecoverable error during loading zipped  resource '" + //$NON-NLS-1$ 
                resource + "' from class "//$NON-NLS-1$
                + cn + '.'), t);
      }
      ErrorUtils.throwAsIOException(t);
    }

    if ((logger != null) && (logger.isLoggable(Level.FINE))) {
      if (cn == null) {
        cn = TextUtils.className(clazz);
      }
      logger.fine((("Finished loading zipped resource '" + resource) + //$NON-NLS-1$
          "' from class " + cn) + '.'); //$NON-NLS-1$
    }
  }

  /**
   * Load a zipped resource from a package identified by a given class,
   * while writing logging information to the given logger. The specified
   * encoding {@code defaultEncoding} is assumed if no encoding can
   * automatically detected.
   * 
   * @param loadContext
   *          the object to store the load data into the object to store
   *          the load data into
   * @param clazz
   *          the class to use to resolve the resource
   * @param resource
   *          the resource name
   * @param logger
   *          the logger for log output
   * @param defaultEncoding
   *          the expected default encoding
   * @throws IOException
   *           if I/O fails
   */
  protected void doLoadResourceZIP(final L loadContext,
      final Class<?> clazz, final String resource, final Logger logger,
      final StreamEncoding<?, ?> defaultEncoding) throws IOException {
    try (final InputStream is = clazz.getResourceAsStream(resource)) {
      this.loadZIPArchive(loadContext, is, logger, defaultEncoding);
    }
  }
}
