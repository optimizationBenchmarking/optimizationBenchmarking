package org.optimizationBenchmarking.utils.io.structured.spec;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;

/**
 * A text input job builder.
 * 
 * @param <D>
 *          the data type which can be stored
 */
public interface ITextInputJobBuilder<D> extends IStreamInputJobBuilder<D> {

  /** {@inheritDoc} */
  @Override
  public abstract ITextInputJobBuilder<D> setLogger(final Logger logger);

  /** {@inheritDoc} */
  @Override
  public abstract ITextInputJobBuilder<D> setDestination(
      final D destination);

  /** {@inheritDoc} */
  @Override
  public abstract ITextInputJobBuilder<D> addPath(final Path path,
      final StreamEncoding<?, ?> encoding, final boolean isZipCompressed);

  /** {@inheritDoc} */
  @Override
  public abstract ITextInputJobBuilder<D> addPath(final Path path);

  /** {@inheritDoc} */
  @Override
  public abstract ITextInputJobBuilder<D> addFile(final File file,
      final StreamEncoding<?, ?> encoding, final boolean isZipCompressed);

  /** {@inheritDoc} */
  @Override
  public abstract ITextInputJobBuilder<D> addFile(final File file);

  /** {@inheritDoc} */
  @Override
  public abstract ITextInputJobBuilder<D> addPath(final String path,
      final StreamEncoding<?, ?> encoding, final boolean isZipCompressed);

  /** {@inheritDoc} */
  @Override
  public abstract ITextInputJobBuilder<D> addPath(final String path);

  /** {@inheritDoc} */
  @Override
  public abstract ITextInputJobBuilder<D> addFile(final String file,
      final StreamEncoding<?, ?> encoding, final boolean isZipCompressed);

  /** {@inheritDoc} */
  @Override
  public abstract ITextInputJobBuilder<D> addFile(final String file);

  /** {@inheritDoc} */
  @Override
  public abstract ITextInputJobBuilder<D> addStream(
      final InputStream stream, final StreamEncoding<?, ?> encoding,
      final boolean isZipCompressed);

  /** {@inheritDoc} */
  @Override
  public abstract ITextInputJobBuilder<D> addStream(
      final InputStream stream);

  /** {@inheritDoc} */
  @Override
  public abstract ITextInputJobBuilder<D> addResource(
      final Class<?> clazz, final String name,
      final StreamEncoding<?, ?> encoding, final boolean isZipCompressed);

  /** {@inheritDoc} */
  @Override
  public abstract ITextInputJobBuilder<D> addResource(
      final Class<?> clazz, final String name);

  /** {@inheritDoc} */
  @Override
  public abstract ITextInputJobBuilder<D> addResource(final String clazz,
      final String name, final StreamEncoding<?, ?> encoding,
      final boolean isZipCompressed);

  /** {@inheritDoc} */
  @Override
  public abstract ITextInputJobBuilder<D> addResource(final String clazz,
      final String name);

  /** {@inheritDoc} */
  @Override
  public abstract ITextInputJobBuilder<D> addURI(final URI uri,
      final StreamEncoding<?, ?> encoding, final boolean isZipCompressed);

  /** {@inheritDoc} */
  @Override
  public abstract ITextInputJobBuilder<D> addURI(final URI uri);

  /** {@inheritDoc} */
  @Override
  public abstract ITextInputJobBuilder<D> addURL(final URL url,
      final StreamEncoding<?, ?> encoding, final boolean isZipCompressed);

  /** {@inheritDoc} */
  @Override
  public abstract ITextInputJobBuilder<D> addURL(final URL url);

  /** {@inheritDoc} */
  @Override
  public abstract ITextInputJobBuilder<D> addURI(final String uri,
      final StreamEncoding<?, ?> encoding, final boolean isZipCompressed);

  /** {@inheritDoc} */
  @Override
  public abstract ITextInputJobBuilder<D> addURI(final String uri);

  /** {@inheritDoc} */
  @Override
  public abstract ITextInputJobBuilder<D> addURL(final String url,
      final StreamEncoding<?, ?> encoding, final boolean isZipCompressed);

  /** {@inheritDoc} */
  @Override
  public abstract ITextInputJobBuilder<D> addURL(final String url);

  /**
   * Add a reader to read input from
   * 
   * @param reader
   *          the reader to add
   * @return this builder
   */
  public abstract ITextInputJobBuilder<D> addReader(final Reader reader);

  /** {@inheritDoc} */
  @Override
  public abstract ITextInputJobBuilder<D> addZIPStream(
      final InputStream stream, final StreamEncoding<?, ?> encoding);

  /** {@inheritDoc} */
  @Override
  public abstract ITextInputJobBuilder<D> addZIPStream(
      final InputStream stream);

  /** {@inheritDoc} */
  @Override
  public abstract ITextInputJobBuilder<D> addZIPResource(
      final Class<?> clazz, final String name,
      final StreamEncoding<?, ?> encoding);

  /** {@inheritDoc} */
  @Override
  public abstract ITextInputJobBuilder<D> addZIPResource(
      final Class<?> clazz, final String name);

  /** {@inheritDoc} */
  @Override
  public abstract ITextInputJobBuilder<D> addZIPResource(
      final String clazz, final String name,
      final StreamEncoding<?, ?> encoding);

  /** {@inheritDoc} */
  @Override
  public abstract ITextInputJobBuilder<D> addZIPResource(
      final String clazz, final String name);

  /** {@inheritDoc} */
  @Override
  public abstract ITextInputJobBuilder<D> addZIPURI(final URI uri,
      final StreamEncoding<?, ?> encoding);

  /** {@inheritDoc} */
  @Override
  public abstract ITextInputJobBuilder<D> addZIPURI(final URI uri);

  /** {@inheritDoc} */
  @Override
  public abstract ITextInputJobBuilder<D> addZIPURL(final URL url,
      final StreamEncoding<?, ?> encoding);

  /** {@inheritDoc} */
  @Override
  public abstract ITextInputJobBuilder<D> addZIPURL(final URL url);

  /** {@inheritDoc} */
  @Override
  public abstract ITextInputJobBuilder<D> addZIPURI(final String uri,
      final StreamEncoding<?, ?> encoding);

  /** {@inheritDoc} */
  @Override
  public abstract ITextInputJobBuilder<D> addZIPURI(final String uri);

  /** {@inheritDoc} */
  @Override
  public abstract ITextInputJobBuilder<D> addZIPURL(final String url,
      final StreamEncoding<?, ?> encoding);

  /** {@inheritDoc} */
  @Override
  public abstract ITextInputJobBuilder<D> addZIPURL(final String url);
}
