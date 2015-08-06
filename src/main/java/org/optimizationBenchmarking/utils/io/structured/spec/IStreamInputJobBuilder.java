package org.optimizationBenchmarking.utils.io.structured.spec;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.io.EArchiveType;
import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;

/**
 * A stream input job builder.
 *
 * @param <D>
 *          the data type which can be stored
 */
public interface IStreamInputJobBuilder<D> extends IFileInputJobBuilder<D> {

  /** {@inheritDoc} */
  @Override
  public abstract IStreamInputJobBuilder<D> configure(
      final Configuration config);

  /** {@inheritDoc} */
  @Override
  public abstract IStreamInputJobBuilder<D> setLogger(final Logger logger);

  /** {@inheritDoc} */
  @Override
  public abstract IStreamInputJobBuilder<D> setBasePath(final Path path);

  /** {@inheritDoc} */
  @Override
  public abstract IStreamInputJobBuilder<D> setDestination(
      final D destination);

  /** {@inheritDoc} */
  @Override
  public abstract IStreamInputJobBuilder<D> addPath(final Path path,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /** {@inheritDoc} */
  @Override
  public abstract IStreamInputJobBuilder<D> addPath(final Path path);

  /** {@inheritDoc} */
  @Override
  public abstract IStreamInputJobBuilder<D> addFile(final File file,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /** {@inheritDoc} */
  @Override
  public abstract IStreamInputJobBuilder<D> addFile(final File file);

  /** {@inheritDoc} */
  @Override
  public abstract IStreamInputJobBuilder<D> addPath(final String path,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /** {@inheritDoc} */
  @Override
  public abstract IStreamInputJobBuilder<D> addPath(final String path);

  /** {@inheritDoc} */
  @Override
  public abstract IStreamInputJobBuilder<D> addFile(final String file,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /** {@inheritDoc} */
  @Override
  public abstract IStreamInputJobBuilder<D> addFile(final String file);

  /**
   * Add a stream to read from. The stream may or may not be closed upon
   * termination.
   *
   * @param stream
   *          the stream to read the input from
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @param archiveType
   *          Should we assume that the stream is a Archive-compressed
   *          archive?
   * @return this builder
   */
  public abstract IStreamInputJobBuilder<D> addStream(
      final InputStream stream, final StreamEncoding<?, ?> encoding,
      final EArchiveType archiveType);

  /**
   * Add a stream to read from, using the default encoding and expecting no
   * Archive compression. The stream may or may not be closed upon
   * termination.
   *
   * @param stream
   *          the stream to read the input from
   * @return this builder
   */
  public abstract IStreamInputJobBuilder<D> addStream(
      final InputStream stream);

  /**
   * Add an input resource
   *
   * @param clazz
   *          the class from which the resource should be loaded
   * @param name
   *          the name of the resource
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @param archiveType
   *          the expected archive type of the input source, or
   *          {@code null} if no archive type is expected (i.e., we have
   *          plain files or streams)
   * @return this builder
   */
  public abstract IStreamInputJobBuilder<D> addResource(
      final Class<?> clazz, final String name,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /**
   * Add an input resource, using the default encoding and expecting no
   * Archive compression.
   *
   * @param clazz
   *          the class from which the resource should be loaded
   * @param name
   *          the name of the resource
   * @return this builder
   */
  public abstract IStreamInputJobBuilder<D> addResource(
      final Class<?> clazz, final String name);

  /**
   * Add an input resource
   *
   * @param clazz
   *          the class from which the resource should be loaded
   * @param name
   *          the name of the resource
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @param archiveType
   *          the expected archive type of the input source, or
   *          {@code null} if no archive type is expected (i.e., we have
   *          plain files or streams)
   * @return this builder
   */
  public abstract IStreamInputJobBuilder<D> addResource(
      final String clazz, final String name,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /**
   * Add an input resource, using the default encoding and expecting no
   * Archive compression.
   *
   * @param clazz
   *          the class from which the resource should be loaded
   * @param name
   *          the name of the resource
   * @return this builder
   */
  public abstract IStreamInputJobBuilder<D> addResource(
      final String clazz, final String name);

  /**
   * Add a URI to read from.
   *
   * @param uri
   *          the URI to read the input from
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @param archiveType
   *          Should we assume that the stream is a Archive-compressed
   *          archive?
   * @return this builder
   */
  public abstract IStreamInputJobBuilder<D> addURI(final URI uri,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /**
   * Add a URI to read from, using the default encoding and expecting no
   * Archive compression.
   *
   * @param uri
   *          the URI to read the input from
   * @return this builder
   */
  public abstract IStreamInputJobBuilder<D> addURI(final URI uri);

  /**
   * Add a URL to read from.
   *
   * @param url
   *          the URL to read the input from
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @param archiveType
   *          Should we assume that the stream is a Archive-compressed
   *          archive?
   * @return this builder
   */
  public abstract IStreamInputJobBuilder<D> addURL(final URL url,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /**
   * Add a URL to read from, using the default encoding and expecting no
   * Archive compression.
   *
   * @param url
   *          the URL to read the input from
   * @return this builder
   */
  public abstract IStreamInputJobBuilder<D> addURL(final URL url);

  /**
   * Add a URI to read from.
   *
   * @param uri
   *          the URI to read the input from
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @param archiveType
   *          Should we assume that the stream is a Archive-compressed
   *          archive?
   * @return this builder
   */
  public abstract IStreamInputJobBuilder<D> addURI(final String uri,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /**
   * Add a URI to read from, using the default encoding and expecting no
   * Archive compression.
   *
   * @param uri
   *          the URI to read the input from
   * @return this builder
   */
  public abstract IStreamInputJobBuilder<D> addURI(final String uri);

  /**
   * Add a URL to read from.
   *
   * @param url
   *          the URL to read the input from
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @param archiveType
   *          Should we assume that the stream is a Archive-compressed
   *          archive?
   * @return this builder
   */
  public abstract IStreamInputJobBuilder<D> addURL(final String url,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /**
   * Add a URL to read from, using the default encoding and expecting no
   * Archive compression.
   *
   * @param url
   *          the URL to read the input from
   * @return this builder
   */
  public abstract IStreamInputJobBuilder<D> addURL(final String url);

  /**
   * Equivalent to
   * {@link #addStream(InputStream, StreamEncoding, EArchiveType)
   * addStream(stream, encoding, archiveType)}.
   *
   * @param stream
   *          the stream to read the input from
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @return this builder
   */
  @Override
  public abstract IStreamInputJobBuilder<D> addArchiveStream(
      final InputStream stream, final StreamEncoding<?, ?> encoding,
      final EArchiveType archiveType);

  /**
   * Equivalent to
   * {@link #addStream(InputStream, StreamEncoding, EArchiveType)
   * addStream(stream, null, archiveType)}.
   *
   * @param stream
   *          the stream to read the input from
   * @return this builder
   */
  @Override
  public abstract IStreamInputJobBuilder<D> addArchiveStream(
      final InputStream stream, final EArchiveType archiveType);

  /**
   * Equivalent to
   * {@link #addResource(Class, String, StreamEncoding, EArchiveType)
   * addResource(clazz, name, encoding, archiveType)}.
   *
   * @param clazz
   *          the class from which the resource should be loaded
   * @param name
   *          the name of the resource
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @return this builder
   */
  @Override
  public abstract IStreamInputJobBuilder<D> addArchiveResource(
      final Class<?> clazz, final String name,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /**
   * Equivalent to
   * {@link #addResource(Class, String, StreamEncoding, EArchiveType)
   * addResource(clazz, name, null, archiveType)}.
   *
   * @param clazz
   *          the class from which the resource should be loaded
   * @param name
   *          the name of the resource
   * @return this builder
   */
  @Override
  public abstract IStreamInputJobBuilder<D> addArchiveResource(
      final Class<?> clazz, final String name,
      final EArchiveType archiveType);

  /**
   * Equivalent to
   * {@link #addResource(String, String, StreamEncoding, EArchiveType)
   * addResource(clazz, name, encoding, archiveType)}.
   *
   * @param clazz
   *          the class from which the resource should be loaded
   * @param name
   *          the name of the resource
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @return this builder
   */
  @Override
  public abstract IStreamInputJobBuilder<D> addArchiveResource(
      final String clazz, final String name,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /**
   * Equivalent to
   * {@link #addResource(String, String, StreamEncoding, EArchiveType)
   * addResource(clazz, name, null, archiveType)}.
   *
   * @param clazz
   *          the class from which the resource should be loaded
   * @param name
   *          the name of the resource
   * @return this builder
   */
  @Override
  public abstract IStreamInputJobBuilder<D> addArchiveResource(
      final String clazz, final String name, final EArchiveType archiveType);

  /**
   * Equivalent to {@link #addURI(URI, StreamEncoding, EArchiveType)
   * addURI(uri, encoding, archiveType)}.
   *
   * @param uri
   *          the URI to read the input from
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @return this builder
   */
  @Override
  public abstract IStreamInputJobBuilder<D> addArchiveURI(final URI uri,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /**
   * Equivalent to {@link #addURI(URI, StreamEncoding, EArchiveType)
   * addURI(uri, null, archiveType)}.
   *
   * @param uri
   *          the URI to read the input from
   * @return this builder
   */
  @Override
  public abstract IStreamInputJobBuilder<D> addArchiveURI(final URI uri,
      final EArchiveType archiveType);

  /**
   * Equivalent to {@link #addURL(URL, StreamEncoding, EArchiveType)
   * addURL(url, encoding, archiveType)}.
   *
   * @param url
   *          the URL to read the input from
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @return this builder
   */
  @Override
  public abstract IStreamInputJobBuilder<D> addArchiveURL(final URL url,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /**
   * Equivalent to {@link #addURL(URL, StreamEncoding, EArchiveType)
   * addURL(url, null, archiveType)}.
   *
   * @param url
   *          the URL to read the input from
   * @return this builder
   */
  @Override
  public abstract IStreamInputJobBuilder<D> addArchiveURL(final URL url,
      final EArchiveType archiveType);

  /**
   * Equivalent to {@link #addURI(String, StreamEncoding, EArchiveType)
   * addURI(uri, encoding, archiveType)}.
   *
   * @param uri
   *          the URI to read the input from
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @return this builder
   */
  @Override
  public abstract IStreamInputJobBuilder<D> addArchiveURI(
      final String uri, final StreamEncoding<?, ?> encoding,
      final EArchiveType archiveType);

  /**
   * Equivalent to {@link #addURI(String, StreamEncoding, EArchiveType)
   * addURI(uri, null, archiveType)}.
   *
   * @param uri
   *          the URI to read the input from
   * @return this builder
   */
  @Override
  public abstract IStreamInputJobBuilder<D> addArchiveURI(
      final String uri, final EArchiveType archiveType);

  /**
   * Equivalent to {@link #addURL(URL, StreamEncoding, EArchiveType)
   * addURL(url, encoding, archiveType)}.
   *
   * @param url
   *          the URL to read the input from
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @return this builder
   */
  @Override
  public abstract IStreamInputJobBuilder<D> addArchiveURL(
      final String url, final StreamEncoding<?, ?> encoding,
      final EArchiveType archiveType);

  /**
   * Equivalent to {@link #addURL(URL, StreamEncoding, EArchiveType)
   * addURL(url, null, archiveType)}.
   *
   * @param url
   *          the URL to read the input from
   * @return this builder
   */
  @Override
  public abstract IStreamInputJobBuilder<D> addArchiveURL(
      final String url, final EArchiveType archiveType);
}
