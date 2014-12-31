package org.optimizationBenchmarking.utils.io.structured.spec;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.logging.Logger;

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
  public abstract IStreamInputJobBuilder<D> setLogger(final Logger logger);

  /** {@inheritDoc} */
  @Override
  public abstract IStreamInputJobBuilder<D> setDestination(
      final D destination);

  /** {@inheritDoc} */
  @Override
  public abstract IStreamInputJobBuilder<D> addPath(final Path path,
      final StreamEncoding<?, ?> encoding, final boolean isZipCompressed);

  /** {@inheritDoc} */
  @Override
  public abstract IStreamInputJobBuilder<D> addPath(final Path path);

  /** {@inheritDoc} */
  @Override
  public abstract IStreamInputJobBuilder<D> addFile(final File file,
      final StreamEncoding<?, ?> encoding, final boolean isZipCompressed);

  /** {@inheritDoc} */
  @Override
  public abstract IStreamInputJobBuilder<D> addFile(final File file);

  /** {@inheritDoc} */
  @Override
  public abstract IStreamInputJobBuilder<D> addPath(final String path,
      final StreamEncoding<?, ?> encoding, final boolean isZipCompressed);

  /** {@inheritDoc} */
  @Override
  public abstract IStreamInputJobBuilder<D> addPath(final String path);

  /** {@inheritDoc} */
  @Override
  public abstract IStreamInputJobBuilder<D> addFile(final String file,
      final StreamEncoding<?, ?> encoding, final boolean isZipCompressed);

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
   * @param isZipCompressed
   *          Should we assume that the stream is a ZIP-compressed archive?
   * @return this builder
   */
  public abstract IStreamInputJobBuilder<D> addStream(
      final InputStream stream, final StreamEncoding<?, ?> encoding,
      final boolean isZipCompressed);

  /**
   * Add a stream to read from, using the default encoding and expecting no
   * ZIP compression. The stream may or may not be closed upon termination.
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
   * @param isZipCompressed
   *          Is the input a compressed ZIP archive?
   * @return this builder
   */
  public abstract IStreamInputJobBuilder<D> addResource(
      final Class<?> clazz, final String name,
      final StreamEncoding<?, ?> encoding, final boolean isZipCompressed);

  /**
   * Add an input resource, using the default encoding and expecting no ZIP
   * compression.
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
   * @param isZipCompressed
   *          Is the input a compressed ZIP archive?
   * @return this builder
   */
  public abstract IStreamInputJobBuilder<D> addResource(
      final String clazz, final String name,
      final StreamEncoding<?, ?> encoding, final boolean isZipCompressed);

  /**
   * Add an input resource, using the default encoding and expecting no ZIP
   * compression.
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
   * @param isZipCompressed
   *          Should we assume that the stream is a ZIP-compressed archive?
   * @return this builder
   */
  public abstract IStreamInputJobBuilder<D> addURI(final URI uri,
      final StreamEncoding<?, ?> encoding, final boolean isZipCompressed);

  /**
   * Add a URI to read from, using the default encoding and expecting no
   * ZIP compression.
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
   * @param isZipCompressed
   *          Should we assume that the stream is a ZIP-compressed archive?
   * @return this builder
   */
  public abstract IStreamInputJobBuilder<D> addURL(final URL url,
      final StreamEncoding<?, ?> encoding, final boolean isZipCompressed);

  /**
   * Add a URL to read from, using the default encoding and expecting no
   * ZIP compression.
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
   * @param isZipCompressed
   *          Should we assume that the stream is a ZIP-compressed archive?
   * @return this builder
   */
  public abstract IStreamInputJobBuilder<D> addURI(final String uri,
      final StreamEncoding<?, ?> encoding, final boolean isZipCompressed);

  /**
   * Add a URI to read from, using the default encoding and expecting no
   * ZIP compression.
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
   * @param isZipCompressed
   *          Should we assume that the stream is a ZIP-compressed archive?
   * @return this builder
   */
  public abstract IStreamInputJobBuilder<D> addURL(final String url,
      final StreamEncoding<?, ?> encoding, final boolean isZipCompressed);

  /**
   * Add a URL to read from, using the default encoding and expecting no
   * ZIP compression.
   * 
   * @param url
   *          the URL to read the input from
   * @return this builder
   */
  public abstract IStreamInputJobBuilder<D> addURL(final String url);

  /**
   * Equivalent to {@link #addStream(InputStream, StreamEncoding, boolean)
   * addStream(stream, encoding, true)}.
   * 
   * @param stream
   *          the stream to read the input from
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @return this builder
   */
  @Override
  public abstract IStreamInputJobBuilder<D> addZIPStream(
      final InputStream stream, final StreamEncoding<?, ?> encoding);

  /**
   * Equivalent to {@link #addStream(InputStream, StreamEncoding, boolean)
   * addStream(stream, null, true)}.
   * 
   * @param stream
   *          the stream to read the input from
   * @return this builder
   */
  @Override
  public abstract IStreamInputJobBuilder<D> addZIPStream(
      final InputStream stream);

  /**
   * Equivalent to
   * {@link #addResource(Class, String, StreamEncoding, boolean)
   * addResource(clazz, name, encoding, true)}.
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
  public abstract IStreamInputJobBuilder<D> addZIPResource(
      final Class<?> clazz, final String name,
      final StreamEncoding<?, ?> encoding);

  /**
   * Equivalent to
   * {@link #addResource(Class, String, StreamEncoding, boolean)
   * addResource(clazz, name, null, true)}.
   * 
   * @param clazz
   *          the class from which the resource should be loaded
   * @param name
   *          the name of the resource
   * @return this builder
   */
  @Override
  public abstract IStreamInputJobBuilder<D> addZIPResource(
      final Class<?> clazz, final String name);

  /**
   * Equivalent to
   * {@link #addResource(String, String, StreamEncoding, boolean)
   * addResource(clazz, name, encoding, true)}.
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
  public abstract IStreamInputJobBuilder<D> addZIPResource(
      final String clazz, final String name,
      final StreamEncoding<?, ?> encoding);

  /**
   * Equivalent to
   * {@link #addResource(String, String, StreamEncoding, boolean)
   * addResource(clazz, name, null, true)}.
   * 
   * @param clazz
   *          the class from which the resource should be loaded
   * @param name
   *          the name of the resource
   * @return this builder
   */
  @Override
  public abstract IStreamInputJobBuilder<D> addZIPResource(
      final String clazz, final String name);

  /**
   * Equivalent to {@link #addURI(URI, StreamEncoding, boolean) addURI(uri,
   * encoding, true)}.
   * 
   * @param uri
   *          the URI to read the input from
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @return this builder
   */
  @Override
  public abstract IStreamInputJobBuilder<D> addZIPURI(final URI uri,
      final StreamEncoding<?, ?> encoding);

  /**
   * Equivalent to {@link #addURI(URI, StreamEncoding, boolean) addURI(uri,
   * null, true)}.
   * 
   * @param uri
   *          the URI to read the input from
   * @return this builder
   */
  @Override
  public abstract IStreamInputJobBuilder<D> addZIPURI(final URI uri);

  /**
   * Equivalent to {@link #addURL(URL, StreamEncoding, boolean) addURL(url,
   * encoding, true)}.
   * 
   * @param url
   *          the URL to read the input from
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @return this builder
   */
  @Override
  public abstract IStreamInputJobBuilder<D> addZIPURL(final URL url,
      final StreamEncoding<?, ?> encoding);

  /**
   * Equivalent to {@link #addURL(URL, StreamEncoding, boolean) addURL(url,
   * null, true)}.
   * 
   * @param url
   *          the URL to read the input from
   * @return this builder
   */
  @Override
  public abstract IStreamInputJobBuilder<D> addZIPURL(final URL url);

  /**
   * Equivalent to {@link #addURI(String, StreamEncoding, boolean)
   * addURI(uri, encoding, true)}.
   * 
   * @param uri
   *          the URI to read the input from
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @return this builder
   */
  @Override
  public abstract IStreamInputJobBuilder<D> addZIPURI(final String uri,
      final StreamEncoding<?, ?> encoding);

  /**
   * Equivalent to {@link #addURI(String, StreamEncoding, boolean)
   * addURI(uri, null, true)}.
   * 
   * @param uri
   *          the URI to read the input from
   * @return this builder
   */
  @Override
  public abstract IStreamInputJobBuilder<D> addZIPURI(final String uri);

  /**
   * Equivalent to {@link #addURL(URL, StreamEncoding, boolean) addURL(url,
   * encoding, true)}.
   * 
   * @param url
   *          the URL to read the input from
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @return this builder
   */
  @Override
  public abstract IStreamInputJobBuilder<D> addZIPURL(final String url,
      final StreamEncoding<?, ?> encoding);

  /**
   * Equivalent to {@link #addURL(URL, StreamEncoding, boolean) addURL(url,
   * null, true)}.
   * 
   * @param url
   *          the URL to read the input from
   * @return this builder
   */
  @Override
  public abstract IStreamInputJobBuilder<D> addZIPURL(final String url);
}
