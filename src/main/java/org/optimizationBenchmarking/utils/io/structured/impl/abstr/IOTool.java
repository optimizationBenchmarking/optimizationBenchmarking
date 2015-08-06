package org.optimizationBenchmarking.utils.io.structured.impl.abstr;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.io.EArchiveType;
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

  /** the input prefix */
  public static final String INPUT_PARAM_PREFIX = "input"; //$NON-NLS-1$

  /** the input sources parameter */
  public static final String PARAM_SOURCES_SUFFIX = "Source"; //$NON-NLS-1$

  /** the output prefix */
  public static final String OUTPUT_PARAM_PREFIX = "output"; //$NON-NLS-1$

  /** the output destination parameter */
  public static final String PARAM_DESTINATION_SUFFIX = "Dest"; //$NON-NLS-1$

  /**
   * indicates a path element {@value} , which can be used in the
   * {@link #PARAM_SOURCES_SUFFIX} or {@link #PARAM_DESTINATION_SUFFIX}
   * parameters
   */
  public static final String PATH_ELEMENT = "path"; //$NON-NLS-1$

  /**
   * indicates a zipped path element {@value} , which can be used in the
   * {@link #PARAM_SOURCES_SUFFIX} or {@link #PARAM_DESTINATION_SUFFIX}
   * parameters
   */
  public static final String ZIPPED_PATH_ELEMENT = "zipPath"; //$NON-NLS-1$

  /**
   * indicates a zipped url element {@value} , which can be used in the
   * {@link #PARAM_SOURCES_SUFFIX} parameter
   */
  public static final String ZIPPED_URL_ELEMENT = "zipURL"; //$NON-NLS-1$

  /**
   * indicates a zipped uri element {@value} , which can be used in the
   * {@link #PARAM_SOURCES_SUFFIX} parameter
   */
  public static final String ZIPPED_URI_ELEMENT = "zipURI"; //$NON-NLS-1$

  /**
   * indicates a resource element {@value} of the form
   * {@code class#resource}, which can be used in the
   * {@link #PARAM_SOURCES_SUFFIX} parameters
   */
  public static final String RESOURCE_ELEMENT = "resource"; //$NON-NLS-1$

  /**
   * indicates a zipped resource element {@value} of the form
   * {@code class#resource}, which can be used in the
   * {@link #PARAM_SOURCES_SUFFIX} parameters
   */
  public static final String ZIPPED_RESOURCE_ELEMENT = "zipResource"; //$NON-NLS-1$

  /** the job counter */
  final AtomicLong m_jobCounter;

  /** the default log level */
  public static final Level DEFAULT_LOG_LEVEL = Level.FINE;

  /** the finer log level */
  public static final Level FINE_LOG_LEVEL = Level.FINER;

  /** the even finer log level */
  public static final Level FINER_LOG_LEVEL = Level.FINEST;

  /** create the tool */
  IOTool() {
    super();
    this.m_jobCounter = new AtomicLong();
  }

  /**
   * Get a parameter prefix
   *
   * @return the parameter prefix, or {@code null} if none is needed
   */
  protected abstract String getParameterPrefix();

  /** {@inheritDoc} */
  @Override
  public boolean canUse() {
    return true;
  }

  /**
   * This method is the very first method to be invoked before a job
   * begins, even before {@link #before(IOJob, Object)}, and creates a
   * token for the job. This token can later be used to store job-specific
   * data.
   *
   * @param job
   *          the job
   * @param data
   *          the source/destination data
   * @return the token, or {@code null} if none is needed.
   * @throws Throwable
   *           if it must
   */
  protected Object createToken(final IOJob job, final D data)
      throws Throwable {
    return null;
  }

  /**
   * This method is invoked before beginning the I/O process
   *
   * @param job
   *          the job
   * @param data
   *          the source/destination data
   * @throws Throwable
   *           if it must
   */
  protected void before(final IOJob job, final D data) throws Throwable {//
  }

  /**
   * This method is invoked after finishing the I/O process
   *
   * @param job
   *          the job
   * @param data
   *          the source/destination data
   * @throws Throwable
   *           if it must
   */
  protected void after(final IOJob job, final D data) throws Throwable {
    //
  }

  /** can we do raw streams ? */
  void _checkRawStreams() {
    throw new UnsupportedOperationException(//
        this.getClass().getSimpleName() + //
            " cannot deal with raw (uncompressed) streams, only with files and folders."); //$NON-NLS-1$
  }

  /**
   * handle a location
   *
   * @param job
   *          the job
   * @param data
   *          the data
   * @param location
   *          the location
   * @throws Throwable
   *           if it must
   */
  void _handle(final IOJob job, final D data, final _Location location)
      throws Throwable {
    final MemoryTextOutput text;
    final Class<?> clazz;
    final boolean in;
    final Logger logger;

    in = (this instanceof FileInputTool);

    logger = job.getLogger();
    if (location.m_location1 instanceof Path) {
      if ((logger != null)
          && (logger.isLoggable(IOTool.DEFAULT_LOG_LEVEL))) {
        logger.log(IOTool.DEFAULT_LOG_LEVEL,//
            (in ? "Beginning input from Path '" : //$NON-NLS-1$
                "Beginning output to Path '")//$NON-NLS-1$
                + location.m_location1 + '\'');
      }
      this.__pathPath(job, data, ((Path) (location.m_location1)),
          location.m_encoding, location.m_archiveType);
      if ((logger != null)
          && (logger.isLoggable(IOTool.DEFAULT_LOG_LEVEL))) {
        logger.log(IOTool.DEFAULT_LOG_LEVEL,//
            (in ? "Finished input from Path '" : //$NON-NLS-1$
                "Finished output to Path '")//$NON-NLS-1$
                + location.m_location1 + '\'');
      }
      return;
    }

    if (location.m_location1 instanceof File) {
      if ((logger != null)
          && (logger.isLoggable(IOTool.DEFAULT_LOG_LEVEL))) {
        logger.log(IOTool.DEFAULT_LOG_LEVEL,//
            (in ? "Beginning input from File '" : //$NON-NLS-1$
                "Beginning output to File '")//$NON-NLS-1$
                + location.m_location1 + '\'');
      }
      this.__fileFile(job, data, ((File) (location.m_location1)),
          location.m_encoding, location.m_archiveType);
      if ((logger != null)
          && (logger.isLoggable(IOTool.DEFAULT_LOG_LEVEL))) {
        logger.log(IOTool.DEFAULT_LOG_LEVEL,//
            (in ? "Finished input from File '" : //$NON-NLS-1$
                "Finished output to File '")//$NON-NLS-1$
                + location.m_location1 + '\'');
      }
      return;
    }

    if (location.m_location1 instanceof String) {
      if (location.m_location2 instanceof Class) {
        clazz = ((Class<?>) (location.m_location2));

        if (Path.class.isAssignableFrom(clazz)) {
          if ((logger != null)
              && (logger.isLoggable(IOTool.DEFAULT_LOG_LEVEL))) {
            logger.log(IOTool.DEFAULT_LOG_LEVEL,//
                (in ? "Beginning input from Path identified by String '" : //$NON-NLS-1$
                    "Beginning output to Path identified by String '")//$NON-NLS-1$
                    + location.m_location1 + '\'');
          }
          this.__pathString(job, data, ((String) (location.m_location1)),
              location.m_encoding, location.m_archiveType);
          if ((logger != null)
              && (logger.isLoggable(IOTool.DEFAULT_LOG_LEVEL))) {
            logger.log(IOTool.DEFAULT_LOG_LEVEL,//
                (in ? "Finished input from Path identified by String '" : //$NON-NLS-1$
                    "Finished output to Path identified by String '")//$NON-NLS-1$
                    + location.m_location1 + '\'');
          }

          return;
        }

        if (File.class.isAssignableFrom(clazz)) {
          if ((logger != null)
              && (logger.isLoggable(IOTool.DEFAULT_LOG_LEVEL))) {
            logger.log(IOTool.DEFAULT_LOG_LEVEL,//
                (in ? "Beginning input from File identified by String '" : //$NON-NLS-1$
                    "Beginning output to File identified by String '")//$NON-NLS-1$
                    + location.m_location1 + '\'');
          }
          this.__fileString(job, data, ((String) (location.m_location1)),
              location.m_encoding, location.m_archiveType);
          if ((logger != null)
              && (logger.isLoggable(IOTool.DEFAULT_LOG_LEVEL))) {
            logger.log(IOTool.DEFAULT_LOG_LEVEL,//
                (in ? "Finished input from File identified by String '" : //$NON-NLS-1$
                    "Finished output to File identified by String '")//$NON-NLS-1$
                    + location.m_location1 + '\'');
          }

          return;
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
    text.append(in ? " is not supported as input source by " : //$NON-NLS-1$
        " is not supported as output destination by "); //$NON-NLS-1$
    this.toText(text);
    text.append(" for data type "); //$NON-NLS-1$
    if (data != null) {
      text.append(TextUtils.className(data.getClass()));
    } else {
      text.append("null");//$NON-NLS-1$
    }
    text.append(" by tool ");//$NON-NLS-1$
    this.toText(text);
    text.append('.');
    throw new UnsupportedOperationException(text.toString());
  }

  /**
   * Perform I/O to a file
   *
   * @param job
   *          the job
   * @param data
   *          the data
   * @param file
   *          the file
   * @param encoding
   *          the encoding
   * @param archiveType
   *          should we compress / decompress?
   * @throws Throwable
   *           if I/O fails
   */
  private final void __fileString(final IOJob job, final D data,
      final String file, final StreamEncoding<?, ?> encoding,
      final EArchiveType archiveType) throws Throwable {
    final String prepared;

    prepared = TextUtils.prepare(file);
    if (prepared == null) {
      throw new IllegalArgumentException(//
          "File name cannot be null, empty, or only composed of white spaces, but '" //$NON-NLS-1$
              + file + "' is.");//$NON-NLS-1$
    }

    this.__fileFile(job, data, new File(prepared), encoding, archiveType);
  }

  /**
   * Perform I/O to a file
   *
   * @param job
   *          the job
   * @param data
   *          the data
   * @param file
   *          the file
   * @param encoding
   *          the encoding
   * @param archiveType
   *          should we compress / decompress?
   * @throws Throwable
   *           if I/O fails
   */
  private final void __fileFile(final IOJob job, final D data,
      final File file, final StreamEncoding<?, ?> encoding,
      final EArchiveType archiveType) throws Throwable {
    Throwable convert, reason;
    Path path;
    File useFile;
    MemoryTextOutput memText;
    IOException ioe;
    String string;

    convert = null;
    try {
      useFile = file.getCanonicalFile();
    } catch (final Throwable t1) {
      convert = t1;
      try {
        useFile = file.getAbsoluteFile();
      } catch (final Throwable t2) {
        useFile = file;
        convert.addSuppressed(t2);
      }
    }

    reason = null;
    try {
      path = useFile.toPath();
    } catch (final Throwable t3) {
      reason = t3;
      path = null;
    }
    if (path == null) {
      if (useFile != file) {
        try {
          path = file.toPath();
        } catch (final Throwable t4) {
          if (reason != null) {
            reason.addSuppressed(t4);
          } else {
            reason = t4;
          }
          path = null;
        }
      }
    }

    if (path == null) {
      memText = new MemoryTextOutput();
      memText.append("Could not convert file '");//$NON-NLS-1$
      memText.append(file.toString());
      if (useFile != file) {
        memText.append("' canonicalized to '");//$NON-NLS-1$
        memText.append(useFile.toString());
      }
      memText.append("' to a path.");//$NON-NLS-1$

      string = memText.toString();
      memText = null;
      if (reason != null) {
        ioe = new IOException(string, reason);
      } else {
        ioe = new IOException(string);
      }
      if (convert != null) {
        ioe.addSuppressed(convert);
      }
      throw ioe;
    }

    this.__pathPath(job, data, path, encoding, archiveType);
  }

  /**
   * Perform I/O to a path
   *
   * @param job
   *          the job
   * @param data
   *          the data
   * @param path
   *          the path
   * @param encoding
   *          the encoding
   * @param archiveType
   *          should we compress / decompress?
   * @throws Throwable
   *           if I/O fails
   */
  private final void __pathPath(final IOJob job, final D data,
      final Path path, final StreamEncoding<?, ?> encoding,
      final EArchiveType archiveType) throws Throwable {
    this._pathNormalized(job, data,
        PathUtils.normalize(path, job.m_basePath), encoding, archiveType);
  }

  /**
   * Perform I/O to a path
   *
   * @param job
   *          the job
   * @param data
   *          the data
   * @param path
   *          the path
   * @param encoding
   *          the encoding
   * @param archiveType
   *          should we compress / decompress?
   * @throws Throwable
   *           if I/O fails
   */
  private final void __pathString(final IOJob job, final D data,
      final String path, final StreamEncoding<?, ?> encoding,
      final EArchiveType archiveType) throws Throwable {
    this._pathNormalized(job, data,
        PathUtils.normalize(path, job.m_basePath), encoding, archiveType);
  }

  /**
   * Perform I/O to a path
   *
   * @param job
   *          the job
   * @param data
   *          the data
   * @param path
   *          the path
   * @param encoding
   *          the encoding
   * @param archiveType
   *          should we compress/decompress?
   * @throws Throwable
   *           if I/O fails
   */
  final void _pathNormalized(final IOJob job, final D data,
      final Path path, final StreamEncoding<?, ?> encoding,
      final EArchiveType archiveType) throws Throwable {
    final boolean input;
    final Logger logger;
    BasicFileAttributes attributes;

    if (path == null) {
      throw new IOException("Path cannot be null."); //$NON-NLS-1$
    }

    input = (this instanceof FileInputTool);

    try {
      attributes = Files.readAttributes(path, BasicFileAttributes.class);
    } catch (final IOException ioe) {
      attributes = null;
      if (input) {
        throw new IOException(
            ("Error when trying to read attributes of input path '" + //$NON-NLS-1$
                path + "' (maybe the path does not exist?)."),//$NON-NLS-1$
            ioe);
      }
    }

    logger = job.getLogger();
    if ((logger != null) && (logger.isLoggable(IOTool.DEFAULT_LOG_LEVEL))) {
      logger.log(IOTool.DEFAULT_LOG_LEVEL,//
          (input ? //
          "Reading input from Path '" : //$NON-NLS-1$
              "Writing output ot Path '") + //$NON-NLS-1$
              path + "' with attributes " + attributes);//$NON-NLS-1$
    }
    this._path(job, data, path, attributes, encoding, archiveType);
  }

  /**
   * Handle a path: Read input from the path or write output to the path.
   *
   * @param job
   *          the job where logging info can be written
   * @param data
   *          the data to be written or read
   * @param path
   *          the path
   * @param attributes
   *          the attributes
   * @param encoding
   *          the encoding
   * @param archiveType
   *          should we compress / expect compression?
   * @throws Throwable
   *           if I/O fails
   */
  void _path(final IOJob job, final D data, final Path path,
      final BasicFileAttributes attributes,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType)
      throws Throwable {
    if (archiveType != null) {
      throw new IllegalStateException(//
          "Can only handle uncompressed paths here."); //$NON-NLS-1$
    }
  }

  /**
   * Obtain the default fallback file name for archived files
   *
   * @return the default file name
   */
  protected String getArchiveFallbackFileName() {
    return "data"; //$NON-NLS-1$
  }
}
