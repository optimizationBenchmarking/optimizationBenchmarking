package org.optimizationBenchmarking.utils.io.structured.spec;

import java.io.File;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.io.EArchiveType;
import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

/**
 * A stream output job builder.
 *
 * @param <D>
 *          the data type which can be stored
 */
public interface IStreamOutputJobBuilder<D> extends
    IFileOutputJobBuilder<D> {

  /** {@inheritDoc} */
  @Override
  public abstract IStreamOutputJobBuilder<D> configure(
      final Configuration config);

  /** {@inheritDoc} */
  @Override
  public abstract IStreamOutputJobBuilder<D> setFileProducerListener(
      final IFileProducerListener listener);

  /** {@inheritDoc} */
  @Override
  public abstract IStreamOutputJobBuilder<D> setBasePath(final Path path);

  /** {@inheritDoc} */
  @Override
  public abstract IStreamOutputJobBuilder<D> setLogger(final Logger logger);

  /** {@inheritDoc} */
  @Override
  public abstract IStreamOutputJobBuilder<D> setSource(final D source);

  /** {@inheritDoc} */
  @Override
  public abstract IStreamOutputJobBuilder<D> setPath(final Path path,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /** {@inheritDoc} */
  @Override
  public abstract IStreamOutputJobBuilder<D> setPath(final Path path);

  /** {@inheritDoc} */
  @Override
  public abstract IStreamOutputJobBuilder<D> setFile(final File file,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /** {@inheritDoc} */
  @Override
  public abstract IStreamOutputJobBuilder<D> setFile(final File file);

  /** {@inheritDoc} */
  @Override
  public abstract IStreamOutputJobBuilder<D> setPath(final String path,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /** {@inheritDoc} */
  @Override
  public abstract IStreamOutputJobBuilder<D> setPath(final String path);

  /** {@inheritDoc} */
  @Override
  public abstract IStreamOutputJobBuilder<D> setFile(final String file,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /** {@inheritDoc} */
  @Override
  public abstract IStreamOutputJobBuilder<D> setFile(final String file);

  /**
   * Set the stream to write to. The stream may or may not be closed upon
   * termination.
   *
   * @param stream
   *          the stream to write the output to
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @param archiveType
   *          if the output should be put into an archive
   * @return this builder
   */
  public abstract IStreamOutputJobBuilder<D> setStream(
      final OutputStream stream, final StreamEncoding<?, ?> encoding,
      final EArchiveType archiveType);

  /**
   * Set the stream to write to, using the default encoding and no ZIP
   * compression. The stream may or may not be closed upon termination.
   *
   * @param stream
   *          the stream to write the output to
   * @return this builder
   */
  public abstract IStreamOutputJobBuilder<D> setStream(
      final OutputStream stream);

  /**
   * Equivalent to
   * {@link #setStream(OutputStream, StreamEncoding, EArchiveType)
   * setStream(stream, encoding, archiveType)}
   *
   * @param stream
   *          the stream to write the output to
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @param archiveType
   *          the archive type
   * @return this builder
   */
  @Override
  public abstract IStreamOutputJobBuilder<D> setArchiveStream(
      final OutputStream stream, final StreamEncoding<?, ?> encoding,
      final EArchiveType archiveType);

  /**
   * Equivalent to
   * {@link #setStream(OutputStream, StreamEncoding, EArchiveType)
   * setStream(stream, null, archiveType)}
   *
   * @param stream
   *          the stream to write the output to
   * @param archiveType
   *          the archive type
   * @return this builder
   */
  @Override
  public abstract IStreamOutputJobBuilder<D> setArchiveStream(
      final OutputStream stream, final EArchiveType archiveType);
}
