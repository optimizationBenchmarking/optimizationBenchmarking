package org.optimizationBenchmarking.utils.io.structured.spec;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;

/**
 * A file input job builder.
 * 
 * @param <D>
 *          the data element which will be filled
 */
public interface IFileInputJobBuilder<D> extends IInputJobBuilder<D> {

  /** {@inheritDoc} */
  @Override
  public abstract IFileInputJobBuilder<D> setLogger(final Logger logger);

  /** {@inheritDoc} */
  @Override
  public abstract IFileInputJobBuilder<D> setDestination(
      final D destination);

  /**
   * Add a path to an input source
   * 
   * @param path
   *          the path to read from
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @param isZipCompressed
   *          Is the input a compressed ZIP archive?
   * @return this builder
   */
  public abstract IFileInputJobBuilder<D> addPath(final Path path,
      final StreamEncoding<?, ?> encoding, final boolean isZipCompressed);

  /**
   * Add a path to an input source, using the default encoding and
   * expecting no ZIP compression
   * 
   * @param path
   *          the path to read from
   * @return this builder
   */
  public abstract IFileInputJobBuilder<D> addPath(final Path path);

  /**
   * Add an input source file
   * 
   * @param file
   *          the file to read from
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @param isZipCompressed
   *          Is the input a compressed ZIP archive?
   * @return this builder
   */
  public abstract IFileInputJobBuilder<D> addFile(final File file,
      final StreamEncoding<?, ?> encoding, final boolean isZipCompressed);

  /**
   * Add an input source file , using the default encoding and expecting no
   * ZIP compression
   * 
   * @param file
   *          the file to read from
   * @return this builder
   */
  public abstract IFileInputJobBuilder<D> addFile(final File file);

  /**
   * Add a path to an input source
   * 
   * @param path
   *          the path to read from
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @param isZipCompressed
   *          Is the input a compressed ZIP archive?
   * @return this builder
   */
  public abstract IFileInputJobBuilder<D> addPath(final String path,
      final StreamEncoding<?, ?> encoding, final boolean isZipCompressed);

  /**
   * Add a path to an input source, using the default encoding and
   * expecting no ZIP compression
   * 
   * @param path
   *          the path to read from
   * @return this builder
   */
  public abstract IFileInputJobBuilder<D> addPath(final String path);

  /**
   * Add an input source file
   * 
   * @param file
   *          the file to read from
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @param isZipCompressed
   *          Is the input a compressed ZIP archive?
   * @return this builder
   */
  public abstract IFileInputJobBuilder<D> addFile(final String file,
      final StreamEncoding<?, ?> encoding, final boolean isZipCompressed);

  /**
   * Add an input source file , using the default encoding and expecting no
   * ZIP compression
   * 
   * @param file
   *          the file to read from
   * @return this builder
   */
  public abstract IFileInputJobBuilder<D> addFile(final String file);

  /**
   * Add a stream containing a ZIP archive. The stream may or may not be
   * closed upon termination.
   * 
   * @param stream
   *          the stream to read the input from
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @return this builder
   */
  public abstract IFileInputJobBuilder<D> addZIPStream(
      final InputStream stream, final StreamEncoding<?, ?> encoding);

  /**
   * Add a stream containing a ZIP archive, using the default encoding and
   * expecting ZIP compression. The stream may or may not be closed upon
   * termination.
   * 
   * @param stream
   *          the stream to read the input from
   * @return this builder
   */
  public abstract IFileInputJobBuilder<D> addZIPStream(
      final InputStream stream);

  /**
   * Add an input resource containing a ZIP archive
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
  public abstract IFileInputJobBuilder<D> addZIPResource(
      final Class<?> clazz, final String name,
      final StreamEncoding<?, ?> encoding);

  /**
   * Add an input resource containing a ZIP archive, using the default
   * encoding and expecting ZIP compression.
   * 
   * @param clazz
   *          the class from which the resource should be loaded
   * @param name
   *          the name of the resource
   * @return this builder
   */
  public abstract IFileInputJobBuilder<D> addZIPResource(
      final Class<?> clazz, final String name);

  /**
   * Add an input resource containing a ZIP archive
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
  public abstract IFileInputJobBuilder<D> addZIPResource(
      final String clazz, final String name,
      final StreamEncoding<?, ?> encoding);

  /**
   * Add an input resource containing a ZIP archive, using the default
   * encoding and expecting ZIP compression.
   * 
   * @param clazz
   *          the class from which the resource should be loaded
   * @param name
   *          the name of the resource
   * @return this builder
   */
  public abstract IFileInputJobBuilder<D> addZIPResource(
      final String clazz, final String name);

  /**
   * Add a URI to read a ZIP archive from.
   * 
   * @param uri
   *          the URI to read the input from
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @return this builder
   */
  public abstract IFileInputJobBuilder<D> addZIPURI(final URI uri,
      final StreamEncoding<?, ?> encoding);

  /**
   * Add a URI to read a ZIP archive from, using the default encoding and
   * expecting ZIP compression.
   * 
   * @param uri
   *          the URI to read the input from
   * @return this builder
   */
  public abstract IFileInputJobBuilder<D> addZIPURI(final URI uri);

  /**
   * Add a URL to read a ZIP archive from.
   * 
   * @param url
   *          the URL to read the input from
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @return this builder
   */
  public abstract IFileInputJobBuilder<D> addZIPURL(final URL url,
      final StreamEncoding<?, ?> encoding);

  /**
   * Add a URL to read a ZIP archive from, using the default encoding and
   * expecting ZIP compression.
   * 
   * @param url
   *          the URL to read the input from
   * @return this builder
   */
  public abstract IFileInputJobBuilder<D> addZIPURL(final URL url);

  /**
   * Add a URI to read a ZIP archive from.
   * 
   * @param uri
   *          the URI to read the input from
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @return this builder
   */
  public abstract IFileInputJobBuilder<D> addZIPURI(final String uri,
      final StreamEncoding<?, ?> encoding);

  /**
   * Add a URI to read a ZIP archive from, using the default encoding and
   * expecting ZIP compression.
   * 
   * @param uri
   *          the URI to read the input from
   * @return this builder
   */
  public abstract IFileInputJobBuilder<D> addZIPURI(final String uri);

  /**
   * Add a URL to read a ZIP archive from.
   * 
   * @param url
   *          the URL to read the input from
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @return this builder
   */
  public abstract IFileInputJobBuilder<D> addZIPURL(final String url,
      final StreamEncoding<?, ?> encoding);

  /**
   * Add a URL to read a ZIP archive from, using the default encoding and
   * expecting ZIP compression.
   * 
   * @param url
   *          the URL to read the input from
   * @return this builder
   */
  public abstract IFileInputJobBuilder<D> addZIPURL(final String url);
}
