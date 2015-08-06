package org.optimizationBenchmarking.utils.io.structured.spec;

import java.io.File;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.file.Path;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.io.EArchiveType;
import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

/**
 * A text output job builder.
 *
 * @param <D>
 *          the data type which can be stored
 */
public interface ITextOutputJobBuilder<D> extends
    IStreamOutputJobBuilder<D> {

  /** {@inheritDoc} */
  @Override
  public abstract ITextOutputJobBuilder<D> configure(
      final Configuration config);

  /** {@inheritDoc} */
  @Override
  public abstract ITextOutputJobBuilder<D> setFileProducerListener(
      final IFileProducerListener listener);

  /** {@inheritDoc} */
  @Override
  public abstract ITextOutputJobBuilder<D> setBasePath(final Path path);

  /** {@inheritDoc} */
  @Override
  public abstract ITextOutputJobBuilder<D> setLogger(final Logger logger);

  /** {@inheritDoc} */
  @Override
  public abstract ITextOutputJobBuilder<D> setSource(final D source);

  /** {@inheritDoc} */
  @Override
  public abstract ITextOutputJobBuilder<D> setPath(final Path path,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /** {@inheritDoc} */
  @Override
  public abstract ITextOutputJobBuilder<D> setPath(final Path path);

  /** {@inheritDoc} */
  @Override
  public abstract ITextOutputJobBuilder<D> setFile(final File file,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /** {@inheritDoc} */
  @Override
  public abstract ITextOutputJobBuilder<D> setFile(final File file);

  /** {@inheritDoc} */
  @Override
  public abstract ITextOutputJobBuilder<D> setPath(final String path,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /** {@inheritDoc} */
  @Override
  public abstract ITextOutputJobBuilder<D> setPath(final String path);

  /** {@inheritDoc} */
  @Override
  public abstract ITextOutputJobBuilder<D> setFile(final String file,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /** {@inheritDoc} */
  @Override
  public abstract ITextOutputJobBuilder<D> setFile(final String file);

  /** {@inheritDoc} */
  @Override
  public abstract ITextOutputJobBuilder<D> setStream(
      final OutputStream stream, final StreamEncoding<?, ?> encoding,
      final EArchiveType archiveType);

  /** {@inheritDoc} */
  @Override
  public abstract ITextOutputJobBuilder<D> setStream(
      final OutputStream stream);

  /** {@inheritDoc} */
  @Override
  public abstract ITextOutputJobBuilder<D> setArchiveStream(
      final OutputStream stream, final StreamEncoding<?, ?> encoding,
      final EArchiveType archiveType);

  /** {@inheritDoc} */
  @Override
  public abstract ITextOutputJobBuilder<D> setArchiveStream(
      final OutputStream stream, final EArchiveType archiveType);

  /**
   * Set the writer. The Writer may or may not be closed when the output is
   * finished.
   *
   * @param writer
   *          the writer to write the output to
   * @return this builder
   */
  public abstract ITextOutputJobBuilder<D> setWriter(final Writer writer);

  /**
   * Set the text output device
   *
   * @param textOut
   *          the text output device to write the output to
   * @return this builder
   */
  public abstract ITextOutputJobBuilder<D> setTextOutput(
      final ITextOutput textOut);
}
