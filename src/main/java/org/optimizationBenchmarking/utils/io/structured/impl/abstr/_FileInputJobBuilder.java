package org.optimizationBenchmarking.utils.io.structured.impl.abstr;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.io.EArchiveType;
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
  @SuppressWarnings("rawtypes")
  @Override
  public JBT configure(final Configuration config) {
    final JBT res;
    final ArrayListView<String> strings;

    res = super.configure(config);
    strings = ((FileInputTool) (this.m_tool))._getSources(config);

    if ((strings != null) && (!(strings.isEmpty()))) {
      for (final String source : strings) {
        if (source != null) {
          this._location(source);
        }
      }
    }

    return res;
  }

  /** {@inheritDoc} */
  @Override
  final void _setResource(final String clazz, final String resource,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType) {
    this.addResource(clazz, resource, encoding, archiveType);
  }

  /** {@inheritDoc} */
  @Override
  final void _setPath(final String path,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType) {
    this.addPath(path, encoding, archiveType);
  }

  /** {@inheritDoc} */
  @Override
  final void _setURI(final String uri,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType) {
    this.addURI(uri, encoding, archiveType);
  }

  /** {@inheritDoc} */
  @Override
  final void _setURL(final String url,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType) {
    this.addURL(url, encoding, archiveType);
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
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType) {

    if (path == null) {
      throw new IllegalArgumentException("Source Path cannot be null."); //$NON-NLS-1$
    }
    this.m_sources.add(new _Location(path, null, encoding, archiveType));
    return ((JBT) this);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addPath(final Path path) {
    return this.addPath(path, null, null);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public final JBT addFile(final File file,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType) {
    if (file == null) {
      throw new IllegalArgumentException(
          "Destination File cannot be null."); //$NON-NLS-1$
    }
    this.m_sources.add(new _Location(file, null, encoding, archiveType));
    return ((JBT) this);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addFile(final File file) {
    return this.addFile(file, null, null);
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("unchecked")
  public final JBT addPath(final String path,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType) {
    if (path == null) {
      throw new IllegalArgumentException(
          "Destination Path String cannot be null."); //$NON-NLS-1$
    }
    this.m_sources.add(new _Location(path, Path.class, encoding,
        archiveType));
    return ((JBT) this);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addPath(final String path) {
    return this.addPath(path, null, null);
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("unchecked")
  public final JBT addFile(final String file,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType) {
    if (file == null) {
      throw new IllegalArgumentException(
          "Destination File String cannot be null."); //$NON-NLS-1$
    }
    this.m_sources.add(new _Location(file, File.class, encoding,
        archiveType));
    return ((JBT) this);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addFile(final String file) {
    return this.addFile(file, null, null);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addArchiveStream(final InputStream stream,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType) {
    return this.addStream(stream, encoding, archiveType);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addArchiveStream(final InputStream stream,
      final EArchiveType archiveType) {
    return this.addStream(stream, null, archiveType);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addArchiveResource(final Class<?> clazz,
      final String name, final StreamEncoding<?, ?> encoding,
      final EArchiveType archiveType) {
    return this.addResource(clazz, name, encoding, archiveType);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addArchiveResource(final Class<?> clazz,
      final String name, final EArchiveType archiveType) {
    return this.addResource(clazz, name, null, archiveType);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addArchiveResource(final String clazz,
      final String name, final StreamEncoding<?, ?> encoding,
      final EArchiveType archiveType) {
    return this.addResource(clazz, name, encoding, archiveType);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addArchiveResource(final String clazz,
      final String name, final EArchiveType archiveType) {
    return this.addResource(clazz, name, null, archiveType);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addArchiveURI(final URI uri,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType) {
    return this.addURI(uri, encoding, archiveType);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addArchiveURI(final URI uri,
      final EArchiveType archiveType) {
    return this.addURI(uri, null, archiveType);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addArchiveURI(final String uri,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType) {
    return this.addURI(uri, encoding, archiveType);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addArchiveURI(final String uri,
      final EArchiveType archiveType) {
    return this.addURI(uri, null, archiveType);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addArchiveURL(final URL url,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType) {
    return this.addURL(url, encoding, archiveType);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addArchiveURL(final URL url,
      final EArchiveType archiveType) {
    return this.addURL(url, null, archiveType);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addArchiveURL(final String url,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType) {
    return this.addURL(url, encoding, archiveType);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addArchiveURL(final String url,
      final EArchiveType archiveType) {
    return this.addURL(url, null, archiveType);
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
   * @param archiveType
   *          the archive type
   * @return this builder
   */
  @SuppressWarnings("unchecked")
  JBT addStream(final InputStream stream,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType) {
    if (stream == null) {
      throw new IllegalArgumentException(
          "Source InputStream cannot be null."); //$NON-NLS-1$
    }
    this.m_sources.add(new _Location(stream, null, encoding, archiveType));
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
   * @param archiveType
   *          the archive type
   * @return this builder
   */
  @SuppressWarnings("unchecked")
  JBT addResource(final Class<?> clazz, final String name,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType) {
    if (clazz == null) {
      throw new IllegalArgumentException(
          "Source Class for Resource cannot be null."); //$NON-NLS-1$
    }
    if (name == null) {
      throw new IllegalArgumentException("Resource name cannot be null."); //$NON-NLS-1$
    }
    this.m_sources.add(new _Location(clazz, name, encoding, archiveType));
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
   * @param archiveType
   *          the archive type
   * @return this builder
   */
  @SuppressWarnings("unchecked")
  JBT addResource(final String clazz, final String name,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType) {
    if (clazz == null) {
      throw new IllegalArgumentException(
          "Source Class name for Resource cannot be null."); //$NON-NLS-1$
    }
    if (name == null) {
      throw new IllegalArgumentException("Resource name cannot be null."); //$NON-NLS-1$
    }
    this.m_sources.add(new _Location(clazz, name, encoding, archiveType));
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
   * @param archiveType
   *          the archive type
   * @return this builder
   */
  @SuppressWarnings("unchecked")
  JBT addURI(final URI uri, final StreamEncoding<?, ?> encoding,
      final EArchiveType archiveType) {
    if (uri == null) {
      throw new IllegalArgumentException("Source URI cannot be null."); //$NON-NLS-1$
    }
    this.m_sources.add(new _Location(uri, null, encoding, archiveType));
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
   * @param archiveType
   *          the archive type
   * @return this builder
   */
  @SuppressWarnings("unchecked")
  JBT addURI(final String uri, final StreamEncoding<?, ?> encoding,
      final EArchiveType archiveType) {
    if (uri == null) {
      throw new IllegalArgumentException(
          "Source URI string cannot be null."); //$NON-NLS-1$
    }
    this.m_sources
        .add(new _Location(uri, URI.class, encoding, archiveType));
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
   * @param archiveType
   *          the archive type
   * @return this builder
   */
  @SuppressWarnings("unchecked")
  JBT addURL(final URL url, final StreamEncoding<?, ?> encoding,
      final EArchiveType archiveType) {
    if (url == null) {
      throw new IllegalArgumentException("Source URL cannot be null."); //$NON-NLS-1$
    }
    this.m_sources.add(new _Location(url, null, encoding, archiveType));
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
   * @param archiveType
   *          the archive type
   * @return this builder
   */
  @SuppressWarnings("unchecked")
  JBT addURL(final String url, final StreamEncoding<?, ?> encoding,
      final EArchiveType archiveType) {
    if (url == null) {
      throw new IllegalArgumentException(
          "Source URL string cannot be null."); //$NON-NLS-1$
    }
    this.m_sources
        .add(new _Location(url, URL.class, encoding, archiveType));
    return ((JBT) this);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("rawtypes")
  @Override
  final _InputJob _doCreate() {
    _Location[] ar;
    ar = this.m_sources.toArray(new _Location[this.m_sources.size()]);
    this.m_sources = null;
    return new _InputJob(this.getLogger(),
        ((FileInputTool) (this.m_tool)), this.m_dest, this.m_basePath, ar);
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
