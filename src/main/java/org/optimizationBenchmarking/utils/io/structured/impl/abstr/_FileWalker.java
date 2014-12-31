package org.optimizationBenchmarking.utils.io.structured.impl.abstr;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;

/**
 * the internal file walker
 * 
 * @param <L>
 *          the loader type
 */
final class _FileWalker<L> extends SimpleFileVisitor<Path> {

  /** the context */
  private final L m_data;
  /** the logger */
  private final IOJob m_log;
  /** the default encoding */
  private final StreamEncoding<?, ?> m_encoding;
  /** are files zipped? */
  private final boolean m_zipped;
  /** the driver to delegate to */
  private final FileInputTool<L> m_tool;

  /**
   * create
   * 
   * @param data
   *          the load context
   * @param log
   *          the logger
   * @param encoding
   *          the encoding
   * @param zipped
   *          are we zipped?
   * @param tool
   *          the tool
   */
  _FileWalker(final IOJob log, final L data,
      final StreamEncoding<?, ?> encoding, final boolean zipped,
      final FileInputTool<L> tool) {
    super();
    this.m_data = data;
    this.m_log = log;
    this.m_encoding = encoding;
    this.m_tool = tool;
    this.m_zipped = zipped;
  }

  /** {@inheritDoc} */
  @Override
  public final FileVisitResult preVisitDirectory(final Path dir,
      final BasicFileAttributes attrs) throws IOException {
    boolean enter;

    try {
      enter = this.m_tool.enterDirectory(this.m_log, this.m_data, dir,
          attrs);
    } catch (final Throwable throwable) {
      enter = false;
      this.m_log.handleError(throwable,
          ((("Error when entering directory '" //$NON-NLS-1$
          + dir) + '\'') + '.'));
    }

    if (enter) {
      if (this.m_log.canLog(IOJob.FINE_LOG_LEVEL)) {
        this.m_log.log(IOJob.FINE_LOG_LEVEL, ("Now entering directory '" + //$NON-NLS-1$
            dir + '\'') + '.');
      }
      return FileVisitResult.CONTINUE;
    }

    if (this.m_log.canLog(IOJob.FINE_LOG_LEVEL)) {
      this.m_log.log(IOJob.FINE_LOG_LEVEL, ("Skipping directory '" + //$NON-NLS-1$
          dir + '\'') + '.');
    }

    return FileVisitResult.SKIP_SUBTREE;
  }

  /** {@inheritDoc} */
  @Override
  public final FileVisitResult visitFile(final Path file,
      final BasicFileAttributes attrs) throws IOException {
    boolean load;

    if (this.m_zipped) {
      load = true;
    } else {
      try {
        load = this.m_tool.isFileInDirectoryLoadable(this.m_log,
            this.m_data, file, attrs);
      } catch (final Throwable throwable) {
        load = false;
        this.m_log.handleError(throwable,
            (("Error when checking whether file '" + file) + //$NON-NLS-1$
            "' is loadable.")); //$NON-NLS-1$
      }
    }

    if (load) {
      if (this.m_log.canLog(IOJob.FINER_LOG_LEVEL)) {
        this.m_log.log(IOJob.FINER_LOG_LEVEL, "Begin loading file '" + //$NON-NLS-1$
            file + '\'' + '.');
      }

      try {
        this.m_tool._file(this.m_log, this.m_data, file, attrs,
            this.m_encoding, this.m_zipped);
      } catch (final Throwable throwable) {
        this.m_log.handleError(throwable,
            ((("Error when loading file '" + file) + //$NON-NLS-1$
            '\'') + '.'));
      }

      if (this.m_log.canLog(IOJob.FINER_LOG_LEVEL)) {
        this.m_log.log(IOJob.FINER_LOG_LEVEL, "Finished loading file '" + //$NON-NLS-1$
            file + '\'' + '.');
      }
    } else {
      if (this.m_log.canLog(IOJob.FINER_LOG_LEVEL)) {
        this.m_log.log(IOJob.FINER_LOG_LEVEL, "Skipping file '" + //$NON-NLS-1$
            file + '\'' + '.');
      }
    }

    return FileVisitResult.CONTINUE;
  }

  /** {@inheritDoc} */
  @Override
  public final FileVisitResult visitFileFailed(final Path file,
      final IOException exc) throws IOException {
    this.m_log.handleError(exc,
        ((("An error has occured when visiting element '" + //$NON-NLS-1$
        file) + '\'') + '.'));
    return FileVisitResult.CONTINUE;
  }

  /** {@inheritDoc} */
  @Override
  public final FileVisitResult postVisitDirectory(final Path dir,
      final IOException exc) throws IOException {

    try {
      this.m_tool.leaveDirectory(this.m_log, this.m_data, dir);
    } catch (final Throwable throwable) {
      this.m_log.handleError(throwable,
          ((("Error when leaving directory '" //$NON-NLS-1$
          + dir) + '\'') + '.'));
    }

    if (this.m_log.canLog(IOJob.FINE_LOG_LEVEL)) {
      this.m_log.log(IOJob.FINE_LOG_LEVEL, ("Now leaving directory '" + //$NON-NLS-1$
          dir + '\'') + '.');
    }

    return FileVisitResult.CONTINUE;
  }
}
