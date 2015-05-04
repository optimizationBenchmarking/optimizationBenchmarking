package org.optimizationBenchmarking.utils.io.structured.impl.abstr;

import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.io.EArchiveType;
import org.optimizationBenchmarking.utils.io.IFileType;
import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;
import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.io.paths.TempDir;
import org.optimizationBenchmarking.utils.io.structured.spec.IFileOutputJobBuilder;
import org.optimizationBenchmarking.utils.io.structured.spec.IFileOutputTool;

/**
 * A tool for generating file output.
 *
 * @param <S>
 *          the source type
 */
public class FileOutputTool<S> extends IOTool<S> implements
    IFileOutputTool<S> {

  /** create */
  protected FileOutputTool() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected String getParameterPrefix() {
    return IOTool.OUTPUT_PARAM_PREFIX;
  }

  /**
   * Get the suffix for the destination parameter
   *
   * @return the suffix for the destination parameter
   */
  protected String getDestinationParamSuffix() {
    return IOTool.PARAM_DESTINATION_SUFFIX;
  }

  /** {@inheritDoc} */
  @Override
  void _handle(final IOJob job, final S data, final _Location location)
      throws Throwable {
    final Logger logger;

    if (location.m_location1 instanceof OutputStream) {
      if (location.m_archiveType == null) {
        this._checkRawStreams();
      }

      logger = job.getLogger();
      if ((logger != null)
          && (logger.isLoggable(IOTool.DEFAULT_LOG_LEVEL))) {
        logger.log(IOTool.DEFAULT_LOG_LEVEL,//
            "Beginning output to OutputStream."); //$NON-NLS-1$
      }
      this._stream(job, data, ((OutputStream) (location.m_location1)),
          location.m_encoding, location.m_archiveType);
      if ((logger != null)
          && (logger.isLoggable(IOTool.DEFAULT_LOG_LEVEL))) {
        logger.log(IOTool.DEFAULT_LOG_LEVEL,//
            "Finished output to OutputStream."); //$NON-NLS-1$
      }
      return;
    }

    super._handle(job, data, location);
  }

  /**
   * Store the data element to a stream
   *
   * @param job
   *          the job where logging info can be written
   * @param data
   *          the data to be written
   * @param stream
   *          the stream
   * @param encoding
   *          the encoding
   * @param archiveType
   *          the archive type to use
   * @throws Throwable
   *           if I/O fails
   */
  void _stream(final IOJob job, final S data, final OutputStream stream,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType)
      throws Throwable {
    final Path path;

    if (archiveType != null) {
      try (final TempDir temp = new TempDir()) {
        path = temp.getPath();
        this._pathNormalized(job, data, temp.getPath(), encoding, null);
        archiveType.compressPathToStream(path, stream);
      }
    } else {
      this._checkRawStreams();
    }
  }

  /**
   * Get the default archive name
   *
   * @return the default archive name
   */
  protected String getDefaultArchiveName() {
    return "data"; //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  void _path(final IOJob job, final S data, final Path path,
      final BasicFileAttributes attributes,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType)
      throws Throwable {
    final Path file, tempDir;
    final _OutputJob outJob;
    final Object oldCur;
    final Logger logger;

    oldCur = job.m_current;
    try {
      job.m_current = path;

      if (archiveType != null) {
        if ((attributes != null) && (attributes.isDirectory())) {
          file = PathUtils.createPathInside(path, (this
              .getDefaultArchiveName() + '.' + archiveType
              .getDefaultSuffix()));

          logger = job.getLogger();
          if ((logger != null)
              && (logger.isLoggable(IOTool.DEFAULT_LOG_LEVEL))) {
            logger.log(IOTool.DEFAULT_LOG_LEVEL,//
                (((("Path '" + path) + //$NON-NLS-1$
                "' identifies a directory, creating archive file '")//$NON-NLS-1$
                + file) + "' for output."));//$NON-NLS-1$
          }
        } else {
          file = path;
        }

        try (final OutputStream output = PathUtils.openOutputStream(path)) {
          try (final TempDir temp = new TempDir()) {
            tempDir = temp.getPath();
            this.path(job, data, tempDir, attributes, encoding);
            archiveType.compressPathToStream(tempDir, output);
          }
        }

        outJob = ((_OutputJob) job);
        if (outJob.m_support != null) {
          outJob.m_support.addFile(file, archiveType);
        }
      } else {
        this.path(job, data, path, attributes, encoding);
      }
    } finally {
      job.m_current = oldCur;
    }
  }

  /**
   * Write the output to the given path.
   *
   * @param job
   *          the job
   * @param data
   *          the data to be written
   * @param path
   *          the path to write to (may be a folder or a file)
   * @param attributes
   *          the attributes
   * @param encoding
   *          the encoding to use
   * @throws Throwable
   *           if i/o fails
   */
  protected void path(final IOJob job, final S data, final Path path,
      final BasicFileAttributes attributes,
      final StreamEncoding<?, ?> encoding) throws Throwable {
    //
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public IFileOutputJobBuilder<S> use() {
    this.checkCanUse();
    return new _FileOutputJobBuilder(this);
  }

  /**
   * Add a file to the job's internal list.
   *
   * @param job
   *          the job
   * @param path
   *          the path to the file
   * @param type
   *          the file type
   */
  protected final void addFile(final IOJob job, final Path path,
      final IFileType type) {
    final _OutputJob outJob;

    outJob = ((_OutputJob) job);
    if ((outJob.m_support != null)
        && (outJob.m_dest.m_archiveType == null)) {
      outJob.m_support.addFile(path, type);
    }
  }

  /**
   * Add a {@link java.nio.file.Path file}/
   * {@link org.optimizationBenchmarking.utils.io.IFileType file type}
   * -association to the job's internal list.
   *
   * @param job
   *          the job
   * @param p
   *          the path to add
   */
  protected final void addFile(final IOJob job,
      final Map.Entry<Path, IFileType> p) {
    final _OutputJob outJob;

    outJob = ((_OutputJob) job);
    if ((outJob.m_support != null)
        && (outJob.m_dest.m_archiveType == null)) {
      outJob.m_support.addFile(p);
    }
  }

  /**
   * Add a set of paths to the job's internal list.
   *
   * @param job
   *          the job
   * @param ps
   *          the paths to add
   */
  protected final void addFiles(final IOJob job,
      final Iterable<Map.Entry<Path, IFileType>> ps) {
    final _OutputJob outJob;

    outJob = ((_OutputJob) job);
    if ((outJob.m_support != null)
        && (outJob.m_dest.m_archiveType == null)) {
      outJob.m_support.addFiles(ps);
    }
  }
}
