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
 * A file input job builder.
 *
 * @param <D>
 *          the data element which will be filled
 */
public interface IFileInputJobBuilder<D> extends IInputJobBuilder<D> {

  /** {@inheritDoc} */
  @Override
  public abstract IFileInputJobBuilder<D> configure(
      final Configuration config);

  /** {@inheritDoc} */
  @Override
  public abstract IFileInputJobBuilder<D> setBasePath(final Path path);

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
   * @param archiveType
   *          the expected archive type of the input source, or
   *          {@code null} if no archive type is expected (i.e., we have
   *          plain files or streams)
   * @return this builder
   */
  public abstract IFileInputJobBuilder<D> addPath(final Path path,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /**
   * Add a path to an input source, using the default encoding and
   * expecting no Archive compression
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
   * @param archiveType
   *          the expected archive type of the input source, or
   *          {@code null} if no archive type is expected (i.e., we have
   *          plain files or streams)
   * @return this builder
   */
  public abstract IFileInputJobBuilder<D> addFile(final File file,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /**
   * Add an input source file , using the default encoding and expecting no
   * Archive compression
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
   * @param archiveType
   *          the expected archive type of the input source, or
   *          {@code null} if no archive type is expected (i.e., we have
   *          plain files or streams)
   * @return this builder
   */
  public abstract IFileInputJobBuilder<D> addPath(final String path,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /**
   * Add a path to an input source, using the default encoding and
   * expecting no Archive compression
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
   * @param archiveType
   *          the expected archive type of the input source, or
   *          {@code null} if no archive type is expected (i.e., we have
   *          plain files or streams)
   * @return this builder
   */
  public abstract IFileInputJobBuilder<D> addFile(final String file,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /**
   * Add an input source file, using the default encoding and expecting no
   * Archive compression
   *
   * @param file
   *          the file to read from
   * @return this builder
   */
  public abstract IFileInputJobBuilder<D> addFile(final String file);

  /**
   * Add a stream containing an archive. The stream may or may not be
   * closed upon termination.
   *
   * @param stream
   *          the stream to read the input from
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @param archiveType
   *          the expected archive type of the input source, {@code null}
   *          may lead to an exception if no plain streams are supported
   * @return this builder
   */
  public abstract IFileInputJobBuilder<D> addArchiveStream(
      final InputStream stream, final StreamEncoding<?, ?> encoding,
      final EArchiveType archiveType);

  /**
   * Add a stream containing an archive, using the default encoding and
   * expecting a (compressed) archive. The stream may or may not be closed
   * upon termination.
   *
   * @param stream
   *          the stream to read the input from
   * @param archiveType
   *          the expected archive type of the input source, {@code null}
   *          may lead to an exception if no plain streams are supported
   * @return this builder
   */
  public abstract IFileInputJobBuilder<D> addArchiveStream(
      final InputStream stream, final EArchiveType archiveType);

  /**
   * Add an input resource containing an archive
   *
   * @param clazz
   *          the class from which the resource should be loaded
   * @param name
   *          the name of the resource
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @param archiveType
   *          the expected archive type of the input source, {@code null}
   *          may lead to an exception if no plain streams are supported
   * @return this builder
   */
  public abstract IFileInputJobBuilder<D> addArchiveResource(
      final Class<?> clazz, final String name,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /**
   * Add an input resource containing an archive, using the default
   * encoding and expecting a (compressed) archive.
   *
   * @param clazz
   *          the class from which the resource should be loaded
   * @param name
   *          the name of the resource
   * @param archiveType
   *          the expected archive type of the input source, {@code null}
   *          may lead to an exception if no plain streams are supported
   * @return this builder
   */
  public abstract IFileInputJobBuilder<D> addArchiveResource(
      final Class<?> clazz, final String name,
      final EArchiveType archiveType);

  /**
   * Add an input resource containing an archive
   *
   * @param clazz
   *          the class from which the resource should be loaded
   * @param name
   *          the name of the resource
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @param archiveType
   *          the expected archive type of the input source, {@code null}
   *          may lead to an exception if no plain streams are supported
   * @return this builder
   */
  public abstract IFileInputJobBuilder<D> addArchiveResource(
      final String clazz, final String name,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /**
   * Add an input resource containing an archive, using the default
   * encoding and expecting a (compressed) archive.
   *
   * @param clazz
   *          the class from which the resource should be loaded
   * @param name
   *          the name of the resource
   * @param archiveType
   *          the expected archive type of the input source, {@code null}
   *          may lead to an exception if no plain streams are supported
   * @return this builder
   */
  public abstract IFileInputJobBuilder<D> addArchiveResource(
      final String clazz, final String name, final EArchiveType archiveType);

  /**
   * Add a URI to read an archive from.
   *
   * @param uri
   *          the URI to read the input from
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @param archiveType
   *          the expected archive type of the input source, {@code null}
   *          may lead to an exception if no plain streams are supported
   * @return this builder
   */
  public abstract IFileInputJobBuilder<D> addArchiveURI(final URI uri,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /**
   * Add a URI to read an archive from, using the default encoding and
   * expecting a (compressed) archive.
   *
   * @param uri
   *          the URI to read the input from
   * @param archiveType
   *          the expected archive type of the input source, {@code null}
   *          may lead to an exception if no plain streams are supported
   * @return this builder
   */
  public abstract IFileInputJobBuilder<D> addArchiveURI(final URI uri,
      final EArchiveType archiveType);

  /**
   * Add a URL to read an archive from.
   *
   * @param url
   *          the URL to read the input from
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @param archiveType
   *          the expected archive type of the input source, {@code null}
   *          may lead to an exception if no plain streams are supported
   * @return this builder
   */
  public abstract IFileInputJobBuilder<D> addArchiveURL(final URL url,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /**
   * Add a URL to read an archive from, using the default encoding and
   * expecting a (compressed) archive.
   *
   * @param url
   *          the URL to read the input from
   * @param archiveType
   *          the expected archive type of the input source, {@code null}
   *          may lead to an exception if no plain streams are supported
   * @return this builder
   */
  public abstract IFileInputJobBuilder<D> addArchiveURL(final URL url,
      final EArchiveType archiveType);

  /**
   * Add a URI to read an archive from.
   *
   * @param uri
   *          the URI to read the input from
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @param archiveType
   *          the expected archive type of the input source, {@code null}
   *          may lead to an exception if no plain streams are supported
   * @return this builder
   */
  public abstract IFileInputJobBuilder<D> addArchiveURI(final String uri,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /**
   * Add a URI to read an archive from, using the default encoding and
   * expecting a (compressed) archive.
   *
   * @param uri
   *          the URI to read the input from
   * @param archiveType
   *          the expected archive type of the input source, {@code null}
   *          may lead to an exception if no plain streams are supported
   * @return this builder
   */
  public abstract IFileInputJobBuilder<D> addArchiveURI(final String uri,
      final EArchiveType archiveType);

  /**
   * Add a URL to read an archive from.
   *
   * @param url
   *          the URL to read the input from
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @param archiveType
   *          the expected archive type of the input source, {@code null}
   *          may lead to an exception if no plain streams are supported
   * @return this builder
   */
  public abstract IFileInputJobBuilder<D> addArchiveURL(final String url,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType);

  /**
   * Add a URL to read an archive from, using the default encoding and
   * expecting a (compressed) archive.
   *
   * @param url
   *          the URL to read the input from
   * @param archiveType
   *          the expected archive type of the input source, {@code null}
   *          may lead to an exception if no plain streams are supported
   * @return this builder
   */
  public abstract IFileInputJobBuilder<D> addArchiveURL(final String url,
      final EArchiveType archiveType);
}
