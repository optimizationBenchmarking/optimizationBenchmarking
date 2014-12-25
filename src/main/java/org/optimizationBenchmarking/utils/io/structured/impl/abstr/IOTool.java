package org.optimizationBenchmarking.utils.io.structured.impl.abstr;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;

import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.io.structured.spec.IIOTool;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;
import org.optimizationBenchmarking.utils.tools.impl.abstr.Tool;

/**
 * The base class for tools
 * 
 * @param <D>
 *          the source/dest data type
 */
public abstract class IOTool<D> extends Tool implements IIOTool {

  /** the default log level */
  protected static final Level DEFAULT_LOG_LEVEL = Level.FINE;

  /** the finer log level */
  protected static final Level FINE_LOG_LEVEL = Level.FINER;

  /** the even finer log level */
  protected static final Level FINER_LOG_LEVEL = Level.FINEST;

  /** the job counter */
  final AtomicLong m_jobCounter;

  /** create the tool */
  IOTool() {
    super();
    this.m_jobCounter = new AtomicLong();
  }

  /** {@inheritDoc} */
  @Override
  public boolean canUse() {
    return true;
  }

  /**
   * handle a location
   * 
   * @param log
   *          the log
   * @param data
   *          the data
   * @param location
   *          the location
   * @throws Throwable
   *           if it must
   */
  void _handle(final IOJobLog log, final D data, final _Location location)
      throws Throwable {
    final MemoryTextOutput text;
    final Class<?> clazz;
    final boolean in;

    in = (this instanceof FileInputTool);

    if (location.m_location1 instanceof Path) {
      if (log.canLog()) {
        log.log((in ? "Beginning input from Path '" : //$NON-NLS-1$
            "Beginning output to Path '")//$NON-NLS-1$
            + location.m_location1 + '\'');
      }
      this.__pathPath(log, data, ((Path) (location.m_location1)),
          location.m_encoding, location.m_zipped);
      if (log.canLog()) {
        log.log((in ? "Finished input from Path '" : //$NON-NLS-1$
            "Finished output to Path '")//$NON-NLS-1$
            + location.m_location1 + '\'');
      }
      return;
    }

    if (location.m_location1 instanceof File) {
      if (log.canLog()) {
        log.log((in ? "Beginning input from File '" : //$NON-NLS-1$
            "Beginning output to File '")//$NON-NLS-1$
            + location.m_location1 + '\'');
      }
      this.__fileFile(log, data, ((File) (location.m_location1)),
          location.m_encoding, location.m_zipped);
      if (log.canLog()) {
        log.log((in ? "Finished input from File '" : //$NON-NLS-1$
            "Finished output to File '")//$NON-NLS-1$
            + location.m_location1 + '\'');
      }
      return;
    }

    if (location.m_location1 instanceof String) {
      if (location.m_location2 instanceof Class) {
        clazz = ((Class<?>) (location.m_location2));

        if (Path.class.isAssignableFrom(clazz)) {
          if (log.canLog()) {
            log.log((in ? "Beginning input from Path identified by String '" : //$NON-NLS-1$
                "Beginning output to Path identified by String '")//$NON-NLS-1$
                + location.m_location1 + '\'');
          }
          this.__pathString(log, data, ((String) (location.m_location1)),
              location.m_encoding, location.m_zipped);
          if (log.canLog()) {
            log.log((in ? "Finished input from Path identified by String '" : //$NON-NLS-1$
                "Finished output to Path identified by String '")//$NON-NLS-1$
                + location.m_location1 + '\'');
          }
        }

        if (File.class.isAssignableFrom(clazz)) {
          if (log.canLog()) {
            log.log((in ? "Beginning input from File identified by String '" : //$NON-NLS-1$
                "Beginning output to File identified by String '")//$NON-NLS-1$
                + location.m_location1 + '\'');
          }
          this.__fileString(log, data, ((String) (location.m_location1)),
              location.m_encoding, location.m_zipped);
          if (log.canLog()) {
            log.log((in ? "Finished input from File identified by String '" : //$NON-NLS-1$
                "Finished output to File identified by String '")//$NON-NLS-1$
                + location.m_location1 + '\'');
          }
        }

      }
    }

    text = new MemoryTextOutput();
    text.append("Location type ");//$NON-NLS-1$
    if (location.m_location1 != null) {
      text.append(TextUtils.className(location.m_location1.getClass()));
      if (location.m_location2 != null) {
        text.append('+');
        text.append(TextUtils.className(location.m_location2.getClass()));
      }
    } else {
      text.append("null");//$NON-NLS-1$
    }
    text.append(in ? " not supported as input source by " : //$NON-NLS-1$
        " not supported as output destination by "); //$NON-NLS-1$
    this.toText(text);
    text.append(" for data "); //$NON-NLS-1$
    if (data != null) {
      text.append(TextUtils.className(data.getClass()));
    } else {
      text.append("null");//$NON-NLS-1$
    }
    text.append('.');
    throw new UnsupportedOperationException(text.toString());
  }

  /**
   * Perform I/O to a file
   * 
   * @param log
   *          the log
   * @param data
   *          the data
   * @param file
   *          the file
   * @param encoding
   *          the encoding
   * @param zipped
   *          should we compress to ZIP
   * @throws Throwable
   *           if I/O fails
   */
  private final void __fileString(final IOJobLog log, final D data,
      final String file, final StreamEncoding<?, ?> encoding,
      final boolean zipped) throws Throwable {
    final String prepared;

    prepared = TextUtils.prepare(file);
    if (prepared == null) {
      throw new IllegalArgumentException(//
          "File name cannot be null, empty, or only composed of white spaces, but '" //$NON-NLS-1$
              + file + "' is.");//$NON-NLS-1$
    }

    this.__fileFile(log, data, new File(prepared), encoding, zipped);
  }

  /**
   * Perform I/O to a file
   * 
   * @param log
   *          the log
   * @param data
   *          the data
   * @param file
   *          the file
   * @param encoding
   *          the encoding
   * @param zipped
   *          should we compress to ZIP
   * @throws Throwable
   *           if I/O fails
   */
  private final void __fileFile(final IOJobLog log, final D data,
      final File file, final StreamEncoding<?, ?> encoding,
      final boolean zipped) throws Throwable {
    Path path;

    path = file.toPath();
    if (path == null) {
      throw new IOException("Could not convert file '" + file//$NON-NLS-1$
          + "' to a path."); //$NON-NLS-1$
    }

    this.__pathPath(log, data, path, encoding, zipped);
  }

  /**
   * Perform I/O to a path
   * 
   * @param log
   *          the log
   * @param data
   *          the data
   * @param path
   *          the path
   * @param encoding
   *          the encoding
   * @param zipped
   *          should we compress to ZIP
   * @throws Throwable
   *           if I/O fails
   */
  private final void __pathPath(final IOJobLog log, final D data,
      final Path path, final StreamEncoding<?, ?> encoding,
      final boolean zipped) throws Throwable {
    this.__pathNormalized(log, data, PathUtils.normalize(path), encoding,
        zipped);
  }

  /**
   * Perform I/O to a path
   * 
   * @param log
   *          the log
   * @param data
   *          the data
   * @param path
   *          the path
   * @param encoding
   *          the encoding
   * @param zipped
   *          should we compress to ZIP
   * @throws Throwable
   *           if I/O fails
   */
  private final void __pathString(final IOJobLog log, final D data,
      final String path, final StreamEncoding<?, ?> encoding,
      final boolean zipped) throws Throwable {
    this.__pathNormalized(log, data, PathUtils.normalize(path), encoding,
        zipped);
  }

  /**
   * Perform I/O to a path
   * 
   * @param log
   *          the log
   * @param data
   *          the data
   * @param path
   *          the path
   * @param encoding
   *          the encoding
   * @param zipped
   *          should we compress to ZIP
   * @throws Throwable
   *           if I/O fails
   */
  private final void __pathNormalized(final IOJobLog log, final D data,
      final Path path, final StreamEncoding<?, ?> encoding,
      final boolean zipped) throws Throwable {
    BasicFileAttributes attributes;

    if (path == null) {
      throw new IOException("Path cannot be null."); //$NON-NLS-1$
    }

    try {
      attributes = Files.readAttributes(path, BasicFileAttributes.class);
    } catch (final IOException ioe) {
      attributes = null;
    }

    if (log.canLog()) {
      log.log(((this instanceof FileInputTool) ? //
      "Reading input from Path '" : //$NON-NLS-1$
          "Writing output ot Path '") + //$NON-NLS-1$
          path + "' with attributes " + attributes);//$NON-NLS-1$
    }
    this.path(log, data, path, attributes, encoding, zipped);
  }

  /**
   * Handle a path
   * 
   * @param log
   *          the log where logging info can be written
   * @param data
   *          the data to be written or read
   * @param path
   *          the path
   * @param attributes
   *          the attributes
   * @param encoding
   *          the encoding
   * @param zipped
   *          should we ZIP-compress / expect ZIP compression
   * @throws Throwable
   */
  protected void path(final IOJobLog log, final D data, final Path path,
      final BasicFileAttributes attributes,
      final StreamEncoding<?, ?> encoding, final boolean zipped)
      throws Throwable {
    //
  }

}