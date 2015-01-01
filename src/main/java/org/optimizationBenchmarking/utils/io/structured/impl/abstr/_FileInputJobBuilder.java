package org.optimizationBenchmarking.utils.io.structured.impl.abstr;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;
import org.optimizationBenchmarking.utils.io.structured.spec.IFileInputJobBuilder;

/**
 * The class for building file input jobs
 * 
 * @param <DT>
 *          the source data type
 * @param <JBT>
 *          the job builder type
 */
class _FileInputJobBuilder<DT, JBT extends _FileInputJobBuilder<DT, JBT>>
    extends _IOJobBuilder<JBT> implements IFileInputJobBuilder<DT> {

  /** the destination */
  DT m_dest;

  /** the sources */
  ArrayList<_Location> m_sources;

  /**
   * create the job builder
   * 
   * @param tool
   *          the owning tool
   */
  _FileInputJobBuilder(final FileInputTool<DT> tool) {
    super(tool);
    this.m_sources = new ArrayList<>();
  }

  /** {@inheritDoc} */
  @Override
  protected String getParameterPrefix() {
    return IOTool.INPUT_PARAM_PREFIX;
  }

  /** {@inheritDoc} */
  @Override
  public void configure(final Configuration config) {
    super.configure(config);

    for (final String source : config.getStringList(
        IOTool.PARAM_INPUT_SOURCES, null)) {
      if (source != null) {
        this._location(source);
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  final void _setPath(final String path,
      final StreamEncoding<?, ?> encoding, final boolean zipCompress) {
    this.addPath(path, encoding, zipCompress);
  }

  /** {@inheritDoc} */
  @Override
  final void _setURI(final String uri,
      final StreamEncoding<?, ?> encoding, final boolean zipCompress) {
    this.addURI(uri, encoding, zipCompress);
  }

  /** {@inheritDoc} */
  @Override
  final void _setURL(final String url,
      final StreamEncoding<?, ?> encoding, final boolean zipCompress) {
    this.addURL(url, encoding, zipCompress);
  }

  /**
   * validate the destination data
   * 
   * @param destination
   *          the destination data
   */
  private static final void __validateDestination(final Object destination) {
    if (destination == null) {
      throw new IllegalArgumentException(
          "Destination data cannot be null."); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public final JBT setDestination(final DT destination) {
    _FileInputJobBuilder.__validateDestination(destination);
    this.m_dest = destination;
    return ((JBT) this);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public final JBT addPath(final Path path,
      final StreamEncoding<?, ?> encoding, final boolean zipCompress) {

    if (path == null) {
      throw new IllegalArgumentException("Source Path cannot be null."); //$NON-NLS-1$
    }
    this.m_sources.add(new _Location(path, null, encoding, zipCompress));
    return ((JBT) this);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addPath(final Path path) {
    return this.addPath(path, null, false);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public final JBT addFile(final File file,
      final StreamEncoding<?, ?> encoding, final boolean zipCompress) {
    if (file == null) {
      throw new IllegalArgumentException(
          "Destination File cannot be null."); //$NON-NLS-1$
    }
    this.m_sources.add(new _Location(file, null, encoding, zipCompress));
    return ((JBT) this);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addFile(final File file) {
    return this.addFile(file, null, false);
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("unchecked")
  public final JBT addPath(final String path,
      final StreamEncoding<?, ?> encoding, final boolean zipCompress) {
    if (path == null) {
      throw new IllegalArgumentException(
          "Destination Path String cannot be null."); //$NON-NLS-1$
    }
    this.m_sources.add(new _Location(path, Path.class, encoding,
        zipCompress));
    return ((JBT) this);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addPath(final String path) {
    return this.addPath(path, null, false);
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("unchecked")
  public final JBT addFile(final String file,
      final StreamEncoding<?, ?> encoding, final boolean zipCompress) {
    if (file == null) {
      throw new IllegalArgumentException(
          "Destination File String cannot be null."); //$NON-NLS-1$
    }
    this.m_sources.add(new _Location(file, File.class, encoding,
        zipCompress));
    return ((JBT) this);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addFile(final String file) {
    return this.addFile(file, null, false);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addZIPStream(final InputStream stream,
      final StreamEncoding<?, ?> encoding) {
    return this.addStream(stream, encoding, true);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addZIPStream(final InputStream stream) {
    return this.addStream(stream, null, true);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addZIPResource(final Class<?> clazz, final String name,
      final StreamEncoding<?, ?> encoding) {
    return this.addResource(clazz, name, encoding, true);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addZIPResource(final Class<?> clazz, final String name) {
    return this.addResource(clazz, name, null, true);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addZIPResource(final String clazz, final String name,
      final StreamEncoding<?, ?> encoding) {
    return this.addResource(clazz, name, encoding, true);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addZIPResource(final String clazz, final String name) {
    return this.addResource(clazz, name, null, true);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addZIPURI(final URI uri,
      final StreamEncoding<?, ?> encoding) {
    return this.addURI(uri, encoding, true);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addZIPURI(final URI uri) {
    return this.addURI(uri, null, true);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addZIPURI(final String uri,
      final StreamEncoding<?, ?> encoding) {
    return this.addURI(uri, encoding, true);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addZIPURI(final String uri) {
    return this.addURI(uri, null, true);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addZIPURL(final URL url,
      final StreamEncoding<?, ?> encoding) {
    return this.addURL(url, encoding, true);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addZIPURL(final URL url) {
    return this.addURL(url, null, true);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addZIPURL(final String url,
      final StreamEncoding<?, ?> encoding) {
    return this.addURL(url, encoding, true);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addZIPURL(final String url) {
    return this.addURL(url, null, true);
  }

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
  @SuppressWarnings("unchecked")
  JBT addStream(final InputStream stream,
      final StreamEncoding<?, ?> encoding, final boolean isZipCompressed) {
    if (stream == null) {
      throw new IllegalArgumentException(
          "Source InputStream cannot be null."); //$NON-NLS-1$
    }
    this.m_sources.add(new _Location(stream, null, encoding,
        isZipCompressed));
    return ((JBT) this);
  }

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
  @SuppressWarnings("unchecked")
  JBT addResource(final Class<?> clazz, final String name,
      final StreamEncoding<?, ?> encoding, final boolean isZipCompressed) {
    if (clazz == null) {
      throw new IllegalArgumentException(
          "Source Class for Resource cannot be null."); //$NON-NLS-1$
    }
    if (name == null) {
      throw new IllegalArgumentException("Resource name cannot be null."); //$NON-NLS-1$
    }
    this.m_sources.add(new _Location(clazz, name, encoding,
        isZipCompressed));
    return ((JBT) this);
  }

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
  @SuppressWarnings("unchecked")
  JBT addResource(final String clazz, final String name,
      final StreamEncoding<?, ?> encoding, final boolean isZipCompressed) {
    if (clazz == null) {
      throw new IllegalArgumentException(
          "Source Class name for Resource cannot be null."); //$NON-NLS-1$
    }
    if (name == null) {
      throw new IllegalArgumentException("Resource name cannot be null."); //$NON-NLS-1$
    }
    this.m_sources.add(new _Location(clazz, name, encoding,
        isZipCompressed));
    return ((JBT) this);
  }

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
  @SuppressWarnings("unchecked")
  JBT addURI(final URI uri, final StreamEncoding<?, ?> encoding,
      final boolean isZipCompressed) {
    if (uri == null) {
      throw new IllegalArgumentException("Source URI cannot be null."); //$NON-NLS-1$
    }
    this.m_sources
        .add(new _Location(uri, null, encoding, isZipCompressed));
    return ((JBT) this);
  }

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
  @SuppressWarnings("unchecked")
  JBT addURI(final String uri, final StreamEncoding<?, ?> encoding,
      final boolean isZipCompressed) {
    if (uri == null) {
      throw new IllegalArgumentException(
          "Source URI string cannot be null."); //$NON-NLS-1$
    }
    this.m_sources.add(new _Location(uri, URI.class, encoding,
        isZipCompressed));
    return ((JBT) this);
  }

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
  @SuppressWarnings("unchecked")
  JBT addURL(final URL url, final StreamEncoding<?, ?> encoding,
      final boolean isZipCompressed) {
    if (url == null) {
      throw new IllegalArgumentException("Source URL cannot be null."); //$NON-NLS-1$
    }
    this.m_sources
        .add(new _Location(url, null, encoding, isZipCompressed));
    return ((JBT) this);
  }

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
  @SuppressWarnings("unchecked")
  JBT addURL(final String url, final StreamEncoding<?, ?> encoding,
      final boolean isZipCompressed) {
    if (url == null) {
      throw new IllegalArgumentException(
          "Source URL string cannot be null."); //$NON-NLS-1$
    }
    this.m_sources.add(new _Location(url, URL.class, encoding,
        isZipCompressed));
    return ((JBT) this);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("rawtypes")
  @Override
  final _InputJob _doCreate() {
    _Location[] ar;
    ar = this.m_sources.toArray(new _Location[this.m_sources.size()]);
    this.m_sources = null;
    return new _InputJob(this.m_logger, ((FileInputTool) (this.m_tool)),
        this.m_dest, ar);
  }

  /** {@inheritDoc} */
  @Override
  protected final void validate() {
    super.validate();
    _FileInputJobBuilder.__validateDestination(this.m_dest);
    if (this.m_sources.isEmpty()) {
      throw new IllegalArgumentException(//
          "At least one source must be specified."); //$NON-NLS-1$
    }
  }
}
