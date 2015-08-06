package org.optimizationBenchmarking.utils.io.structured.spec;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.io.EArchiveType;
import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;
import org.xml.sax.InputSource;

/**
 * A xml input job builder.
 *
 * @param <D>
 *          the data type which can be stored
 */
public interface IXMLInputJobBuilder<D> extends ITextInputJobBuilder<D> {

  /** {@inheritDoc} */
  @Override
  public abstract IXMLInputJobBuilder<D> configure(
      final Configuration config);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLInputJobBuilder<D> setLogger(final Logger logger);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLInputJobBuilder<D> setBasePath(final Path path);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLInputJobBuilder<D> setDestination(final D destination);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLInputJobBuilder<D> addPath(final Path path,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLInputJobBuilder<D> addPath(final Path path);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLInputJobBuilder<D> addFile(final File file,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLInputJobBuilder<D> addFile(final File file);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLInputJobBuilder<D> addPath(final String path,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLInputJobBuilder<D> addPath(final String path);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLInputJobBuilder<D> addFile(final String file,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLInputJobBuilder<D> addFile(final String file);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLInputJobBuilder<D> addStream(
      final InputStream stream, final StreamEncoding<?, ?> encoding,
      final EArchiveType archiveType);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLInputJobBuilder<D> addStream(final InputStream stream);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLInputJobBuilder<D> addResource(final Class<?> clazz,
      final String name, final StreamEncoding<?, ?> encoding,
      final EArchiveType archiveType);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLInputJobBuilder<D> addResource(final Class<?> clazz,
      final String name);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLInputJobBuilder<D> addResource(final String clazz,
      final String name, final StreamEncoding<?, ?> encoding,
      final EArchiveType archiveType);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLInputJobBuilder<D> addResource(final String clazz,
      final String name);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLInputJobBuilder<D> addURI(final URI uri,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLInputJobBuilder<D> addURI(final URI uri);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLInputJobBuilder<D> addURL(final URL url,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLInputJobBuilder<D> addURL(final URL url);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLInputJobBuilder<D> addURI(final String uri,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLInputJobBuilder<D> addURI(final String uri);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLInputJobBuilder<D> addURL(final String url,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLInputJobBuilder<D> addURL(final String url);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLInputJobBuilder<D> addReader(final Reader reader);

  /**
   * Add an input source
   *
   * @param source
   *          the input source
   * @return this builder
   */
  public abstract IXMLInputJobBuilder<D> addInputSource(
      final InputSource source);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLInputJobBuilder<D> addArchiveStream(
      final InputStream stream, final StreamEncoding<?, ?> encoding,
      final EArchiveType archiveType);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLInputJobBuilder<D> addArchiveStream(
      final InputStream stream, final EArchiveType archiveType);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLInputJobBuilder<D> addArchiveResource(
      final Class<?> clazz, final String name,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLInputJobBuilder<D> addArchiveResource(
      final Class<?> clazz, final String name,
      final EArchiveType archiveType);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLInputJobBuilder<D> addArchiveResource(
      final String clazz, final String name,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLInputJobBuilder<D> addArchiveResource(
      final String clazz, final String name, final EArchiveType archiveType);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLInputJobBuilder<D> addArchiveURI(final URI uri,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLInputJobBuilder<D> addArchiveURI(final URI uri,
      final EArchiveType archiveType);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLInputJobBuilder<D> addArchiveURL(final URL url,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLInputJobBuilder<D> addArchiveURL(final URL url,
      final EArchiveType archiveType);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLInputJobBuilder<D> addArchiveURI(final String uri,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLInputJobBuilder<D> addArchiveURI(final String uri,
      final EArchiveType archiveType);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLInputJobBuilder<D> addArchiveURL(final String url,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /** {@inheritDoc} */
  @Override
  public abstract IXMLInputJobBuilder<D> addArchiveURL(final String url,
      final EArchiveType archiveType);
}
