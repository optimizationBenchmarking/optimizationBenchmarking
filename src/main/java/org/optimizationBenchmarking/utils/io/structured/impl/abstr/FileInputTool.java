package org.optimizationBenchmarking.utils.io.structured.impl.abstr;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
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

  /**
   * This method is invoked before loading data into {@code data}.
   * 
   * @param log
   *          the log
   * @param data
   *          the destination data
   */
  protected void before(final IOJobLog log, final S data) {
    //
  }

  /**
   * This method is invoked after loading data into {@code data}.
   * 
   * @param log
   *          the log
   * @param data
   *          the destination data
   */
  protected void after(final IOJobLog log, final S data) {
    //
  }

  /**
   * Load a ZIP archive
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
  final void _loadZIP(final IOJobLog log, final S data,
      final InputStream stream, final StreamEncoding<?, ?> encoding)
      throws Throwable {
    final Path path;
    try (final TempDir temp = new TempDir()) {
      path = temp.getPath();
      if (log.canLog(IOTool.FINE_LOG_LEVEL)) {
        log.log(IOTool.FINE_LOG_LEVEL,
            ((("Begin unzipping to temporary folder '" //$NON-NLS-1$
            + path) + '\'') + '.'));
      }
      PathUtils.unzipToFolder(stream, path);
      if (log.canLog(IOTool.FINE_LOG_LEVEL)) {
        log.log(IOTool.FINE_LOG_LEVEL,
            ((("Finished unzipping to temporary folder '" //$NON-NLS-1$
            + path) + '\'') + '.'));
      }
      this.path(log, data, path,
          Files.readAttributes(path, BasicFileAttributes.class), encoding,
          false);
    }
  }

  /**
   * Handle a file which may be compressed
   * 
   * @param log
   *          the log where logging info can be written
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
  final void _file(final IOJobLog log, final S data, final Path path,
      final BasicFileAttributes attributes,
      final StreamEncoding<?, ?> encoding, final boolean zipped)
      throws Throwable {
    if (zipped) {
      if (log.canLog()) {
        log.log("Unzipping path '" + path + '\''); //$NON-NLS-1$
      }
      try (final InputStream stream = PathUtils.openInputStream(path)) {
        this._loadZIP(log, data, stream, encoding);
      }
    } else {
      this.file(log, data, path, attributes, encoding);
    }
  }

  /**
   * Handle a file
   * 
   * @param log
   *          the log where logging info can be written
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
  protected void file(final IOJobLog log, final S data, final Path path,
      final BasicFileAttributes attributes,
      final StreamEncoding<?, ?> encoding) throws Throwable {
    //
  }

  /**
   * Enter a directory
   * 
   * @param log
   *          the log where logging info can be written
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
  protected boolean enterDirectory(final IOJobLog log, final S data,
      final Path path, final BasicFileAttributes attributes)
      throws Throwable {
    return true;
  }

  /**
   * Check whether a file in a directory is loadable. This method is only
   * called if the input source does not just identify a single file, in
   * which case loading is enforced.
   * 
   * @param log
   *          the log
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
  protected boolean isFileInDirectoryLoadable(final IOJobLog log,
      final S data, final Path path, final BasicFileAttributes attributes)
      throws Throwable {
    return true;
  }

  /**
   * Leave a directory
   * 
   * @param log
   *          the log where logging info can be written
   * @param data
   *          the data to be read
   * @param path
   *          the path
   * @throws Throwable
   *           if it must
   */
  protected void leaveDirectory(final IOJobLog log, final S data,
      final Path path) throws Throwable {
    //
  }

  /** {@inheritDoc} */
  @Override
  protected void path(final IOJobLog log, final S data, final Path path,
      final BasicFileAttributes attributes,
      final StreamEncoding<?, ?> encoding, final boolean zipped)
      throws Throwable {

    if (attributes == null) {
      throw new IOException("Path '" + path + //$NON-NLS-1$
          "' does not exist.");//$NON-NLS-1$
    }

    if (attributes.isRegularFile()) {
      this._file(log, data, path, attributes, encoding, zipped);
    } else {
      if (attributes.isDirectory()) {
        Files.walkFileTree(path, new _FileWalker<>(log, data, encoding,
            zipped, this));
      }
    }
  }
}
