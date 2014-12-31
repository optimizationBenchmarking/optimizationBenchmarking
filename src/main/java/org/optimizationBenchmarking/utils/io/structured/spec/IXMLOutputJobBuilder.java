package org.optimizationBenchmarking.utils.io.structured.spec;

import java.io.File;
import java.io.OutputStream;
import java.io.Writer;
import java.nio.file.Path;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;
import org.optimizationBenchmarking.utils.io.xml.XMLBase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * An XML I/O job builder.
 * 
 * @param <D>
 *          the data type which can be stored
 */
public interface IXMLOutputJobBuilder<D> extends ITextOutputJobBuilder<D> {

  /** {@inheritDoc} */
  @Override
  public abstract IXMLOutputJobBuilder<D> setLogger(final Logger logger);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLOutputJobBuilder<D> setSource(final D source);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLOutputJobBuilder<D> setPath(final Path path,
      final StreamEncoding<?, ?> encoding, final boolean zipCompress);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLOutputJobBuilder<D> setPath(final Path path);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLOutputJobBuilder<D> setFile(final File file,
      final StreamEncoding<?, ?> encoding, final boolean zipCompress);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLOutputJobBuilder<D> setFile(final File file);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLOutputJobBuilder<D> setPath(final String path,
      final StreamEncoding<?, ?> encoding, final boolean zipCompress);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLOutputJobBuilder<D> setPath(final String path);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLOutputJobBuilder<D> setFile(final String file,
      final StreamEncoding<?, ?> encoding, final boolean zipCompress);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLOutputJobBuilder<D> setFile(final String file);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLOutputJobBuilder<D> setStream(
      final OutputStream stream, final StreamEncoding<?, ?> encoding,
      final boolean zipCompress);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLOutputJobBuilder<D> setStream(
      final OutputStream stream);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLOutputJobBuilder<D> setZIPStream(
      final OutputStream stream, final StreamEncoding<?, ?> encoding);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLOutputJobBuilder<D> setZIPStream(
      final OutputStream stream);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLOutputJobBuilder<D> setWriter(final Writer writer);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLOutputJobBuilder<D> setTextOutput(
      final ITextOutput textOut);

  /**
   * The the {@link org.optimizationBenchmarking.utils.io.xml.XMLBase} to
   * store the XML output to. The XMLBase may or may not be closed when the
   * output is finished.
   * 
   * @param xmlBase
   *          the xmlBase
   * @return this builder
   */
  public abstract IXMLOutputJobBuilder<D> setWriter(final XMLBase xmlBase);
}
