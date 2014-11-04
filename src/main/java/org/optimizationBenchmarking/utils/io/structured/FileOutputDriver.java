package org.optimizationBenchmarking.utils.io.structured;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.ErrorUtils;
import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.AbstractTextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A base class for structured, file-based output
 * 
 * @param <S>
 *          the object to store
 */
public abstract class FileOutputDriver<S> {

  /** create */
  protected FileOutputDriver() {
    super();
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
}
