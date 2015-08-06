package org.optimizationBenchmarking.utils.io.structured.spec;

import java.io.File;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.io.EArchiveType;
import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerJobBuilder;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

/**
 * A file output job builder is a device that may store data into a single
 * file or into multiple files in a folder structure..
 *
 * @param <D>
 *          the data type which can be stored
 */
public interface IFileOutputJobBuilder<D> extends IOutputJobBuilder<D>,
    IFileProducerJobBuilder {

  /** {@inheritDoc} */
  @Override
  public abstract IFileOutputJobBuilder<D> setFileProducerListener(
      final IFileProducerListener listener);

  /** {@inheritDoc} */
  @Override
  public abstract IFileOutputJobBuilder<D> setLogger(final Logger logger);

  /** {@inheritDoc} */
  @Override
  public abstract IFileOutputJobBuilder<D> setBasePath(final Path path);

  /** {@inheritDoc} */
  @Override
  public abstract IFileOutputJobBuilder<D> setSource(final D source);

  /**
   * Set the path to the output
   *
   * @param path
   *          the path to write to
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @param archiveType
   *          the archive type, or {@code null} if no archive should be
   *          created
   * @return this builder
   */
  public abstract IFileOutputJobBuilder<D> setPath(final Path path,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /**
   * Set the path to the output, using the default encoding and no ZIP
   * compression
   *
   * @param path
   *          the path to write to
   * @return this builder
   */
  public abstract IFileOutputJobBuilder<D> setPath(final Path path);

  /**
   * Set the file to the output
   *
   * @param file
   *          the file to write to
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @param archiveType
   *          the archive type, or {@code null} if no archive should be
   *          created
   * @return this builder
   */
  public abstract IFileOutputJobBuilder<D> setFile(final File file,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /**
   * Set a file to the output, using the default encoding and no ZIP
   * compression
   *
   * @param file
   *          the file to write to
   * @return this builder
   */
  public abstract IFileOutputJobBuilder<D> setFile(final File file);

  /**
   * Set the path to the output
   *
   * @param path
   *          the path to write to
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @param archiveType
   *          the archive type, or {@code null} if no archive should be
   *          created
   * @return this builder
   */
  public abstract IFileOutputJobBuilder<D> setPath(final String path,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /**
   * Set the path to the output, using the default encoding and no ZIP
   * compression
   *
   * @param path
   *          the path to write to
   * @return this builder
   */
  public abstract IFileOutputJobBuilder<D> setPath(final String path);

  /**
   * Set the file to the output
   *
   * @param file
   *          the file to write to
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @param archiveType
   *          the archive type, or {@code null} if no archive should be
   *          created
   * @return this builder
   */
  public abstract IFileOutputJobBuilder<D> setFile(final String file,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /**
   * Set a file to the output, using the default encoding and no ZIP
   * compression
   *
   * @param file
   *          the file to write to
   * @return this builder
   */
  public abstract IFileOutputJobBuilder<D> setFile(final String file);

  /**
   * Set the stream to which should receive a ZIP archive with all the
   * output. The stream may or may not be closed upon termination.
   *
   * @param stream
   *          the stream to write the output to
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @param archiveType
   *          the archive type &ndash; {@code null} may lead to exceptions
   *          if an archive type is necessary
   * @return this builder
   */
  public abstract IFileOutputJobBuilder<D> setArchiveStream(
      final OutputStream stream, final StreamEncoding<?, ?> encoding,
      final EArchiveType archiveType);

  /**
   * Set the stream to write all the output as ZIP archive to, using the
   * default encoding and ZIP compression. The stream may or may not be
   * closed upon termination.
   *
   * @param stream
   *          the stream to write the output to
   * @param archiveType
   *          the archive type &ndash; {@code null} may lead to exceptions
   *          if an archive type is necessary
   * @return this builder
   */
  public abstract IFileOutputJobBuilder<D> setArchiveStream(
      final OutputStream stream, final EArchiveType archiveType);
}
