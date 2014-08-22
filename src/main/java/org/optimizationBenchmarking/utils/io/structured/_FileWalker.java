package org.optimizationBenchmarking.utils.io.structured;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;

/**
 * the internal file walker
 * 
 * @param <S>
 *          the storer type
 * @param <L>
 *          the loader type
 */
final class _FileWalker<S, L> implements FileVisitor<Object> {

  /** the context */
  private final L m_loadContext;
  /** the logger */
  private final Logger m_logger;
  /** the default encoding */
  private final StreamEncoding<?, ?> m_defaultEncoding;
  /** the driver to delegate to */
  private final FileIODriver<?, L> m_driver;

  /**
   * create
   * 
   * @param loadContext
   *          the load context
   * @param logger
   *          the logger
   * @param defaultEncoding
   *          the encoding
   * @param driver
   *          the driver
   */
  _FileWalker(final L loadContext, final Logger logger,
      final StreamEncoding<?, ?> defaultEncoding,
      final FileIODriver<S, L> driver) {
    super();
    this.m_loadContext = loadContext;
    this.m_logger = logger;
    this.m_defaultEncoding = defaultEncoding;
    this.m_driver = driver;
  }

  /** {@inheritDoc} */
  @Override
  public final FileVisitResult preVisitDirectory(final Object dir,
      final BasicFileAttributes attrs) throws IOException {
    if (dir instanceof Path) {

      if (this.m_driver.doEnterDirectory(this.m_loadContext, ((Path) dir),
          this.m_logger, this.m_defaultEncoding)) {
        if ((this.m_logger != null)
            && (this.m_logger.isLoggable(Level.INFO))) {
          this.m_logger.info("Now entering directory '" + //$NON-NLS-1$
              dir + '\'' + '.');
        }
        return FileVisitResult.CONTINUE;
      }

      if ((this.m_logger != null)
          && (this.m_logger.isLoggable(Level.INFO))) {
        this.m_logger.info("Skipping directory '" + //$NON-NLS-1$
            dir + "' and all of the sub-directories and files inside.");//$NON-NLS-1$
      }

      return FileVisitResult.SKIP_SUBTREE;
    }

    if ((this.m_logger != null)
        && (this.m_logger.isLoggable(Level.WARNING))) {
      this.m_logger.info(//
          "Encountered object '" + //$NON-NLS-1$
              dir
              + "' in preVisitDirectory which is not a path but claimes to be a directory (so we just skip it)?");//$NON-NLS-1$
    }

    return FileVisitResult.SKIP_SUBTREE;
  }

  /** {@inheritDoc} */
  @Override
  public final FileVisitResult visitFile(final Object file,
      final BasicFileAttributes attrs) throws IOException {
    final Logger logger;
    final Path p;

    logger = this.m_logger;
    if (file instanceof Path) {
      p = ((Path) file);
      if (this.m_driver.isFileInDirectoryLoadable(this.m_loadContext, p)) {
        if ((logger != null) && (logger.isLoggable(Level.FINE))) {
          logger.info("Now processing file '" + //$NON-NLS-1$
              p + '\'' + '.');
        }

        this.m_driver.doLoadFile(this.m_loadContext, p, logger,
            this.m_defaultEncoding);

        if ((logger != null) && (logger.isLoggable(Level.FINE))) {
          logger.info("Finished processing file '" + //$NON-NLS-1$
              p + '\'' + '.');
        }
      } else {
        if ((logger != null) && (logger.isLoggable(Level.FINER))) {
          logger.info("Skipping file '" + //$NON-NLS-1$
              p + '\'' + '.');
        }
      }

      return FileVisitResult.CONTINUE;
    }

    if ((logger != null) && (logger.isLoggable(Level.WARNING))) {
      logger
          .info("Encountered object '" + file//$NON-NLS-1$
              + "' in visitFile which is not a path but claimes to be a file (so we just ignore it)?");//$NON-NLS-1$
    }

    return FileVisitResult.CONTINUE;
  }

  /** {@inheritDoc} */
  @Override
  public final FileVisitResult visitFileFailed(final Object file,
      final IOException exc) throws IOException {

    if ((this.m_logger != null)
        && (this.m_logger.isLoggable(Level.SEVERE))) {
      this.m_logger.log(Level.SEVERE,
          ((("An error has occured when visiting element '" + //$NON-NLS-1$
          file) + '\'') + '.'), exc);
    }

    throw exc;
  }

  /** {@inheritDoc} */
  @Override
  public final FileVisitResult postVisitDirectory(final Object dir,
      final IOException exc) throws IOException {

    if (dir instanceof Path) {

      this.m_driver.doLeaveDirectory(this.m_loadContext, ((Path) dir),
          this.m_logger, this.m_defaultEncoding);

      if ((this.m_logger != null)
          && (this.m_logger.isLoggable(Level.INFO))) {
        this.m_logger.info("Now leaving directory '" + //$NON-NLS-1$
            dir + '\'' + '.');
      }

      return FileVisitResult.CONTINUE;
    }

    if ((this.m_logger != null)
        && (this.m_logger.isLoggable(Level.WARNING))) {
      this.m_logger.info(//
          "Encountered object '" + //$NON-NLS-1$
              dir
              + "' in postVisitDirectory which is not a path but claimes to be a directory (so we just ignore it)?");//$NON-NLS-1$
    }

    return FileVisitResult.CONTINUE;
  }
}
