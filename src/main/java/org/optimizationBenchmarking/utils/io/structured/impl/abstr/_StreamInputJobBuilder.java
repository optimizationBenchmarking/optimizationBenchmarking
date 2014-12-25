package org.optimizationBenchmarking.utils.io.structured.impl.abstr;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;
import org.optimizationBenchmarking.utils.io.structured.spec.IStreamInputJobBuilder;

/**
 * The class for building stream input jobs
 * 
 * @param <DT>
 *          the source data type
 * @param <JBT>
 *          the job builder type
 */
class _StreamInputJobBuilder<DT, JBT extends _StreamInputJobBuilder<DT, JBT>>
    extends _FileInputJobBuilder<DT, JBT> implements
    IStreamInputJobBuilder<DT> {

  /**
   * create the job builder
   * 
   * @param tool
   *          the owning tool
   */
  _StreamInputJobBuilder(final StreamInputTool<DT> tool) {
    super(tool);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public final JBT addStream(final InputStream stream,
      final StreamEncoding<?, ?> encoding, final boolean isZipCompressed) {
    if (stream == null) {
      throw new IllegalArgumentException(
          "Source InputStream cannot be null."); //$NON-NLS-1$
    }
    this.m_sources.add(new _Location(stream, null, encoding,
        isZipCompressed));
    return ((JBT) this);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addStream(final InputStream stream) {
    return this.addStream(stream, null, false);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public final JBT addResource(final Class<?> clazz, final String name,
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

  /** {@inheritDoc} */
  @Override
  public final JBT addResource(final Class<?> clazz, final String name) {
    return this.addResource(clazz, name, null, false);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public final JBT addResource(final String clazz, final String name,
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

  /** {@inheritDoc} */
  @Override
  public final JBT addResource(final String clazz, final String name) {
    return this.addResource(clazz, name, null, false);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public final JBT addURI(final URI uri,
      final StreamEncoding<?, ?> encoding, final boolean isZipCompressed) {
    if (uri == null) {
      throw new IllegalArgumentException("Source URI cannot be null."); //$NON-NLS-1$
    }
    this.m_sources
        .add(new _Location(uri, null, encoding, isZipCompressed));
    return ((JBT) this);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addURI(final URI uri) {
    return this.addURI(uri, null, false);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public final JBT addURI(final String uri,
      final StreamEncoding<?, ?> encoding, final boolean isZipCompressed) {
    if (uri == null) {
      throw new IllegalArgumentException(
          "Source URI string cannot be null."); //$NON-NLS-1$
    }
    this.m_sources.add(new _Location(uri, URI.class, encoding,
        isZipCompressed));
    return ((JBT) this);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addURI(final String uri) {
    return this.addURI(uri, null, false);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public final JBT addURL(final URL url,
      final StreamEncoding<?, ?> encoding, final boolean isZipCompressed) {
    if (url == null) {
      throw new IllegalArgumentException("Source URL cannot be null."); //$NON-NLS-1$
    }
    this.m_sources
        .add(new _Location(url, null, encoding, isZipCompressed));
    return ((JBT) this);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addURL(final URL url) {
    return this.addURL(url, null, false);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public final JBT addURL(final String url,
      final StreamEncoding<?, ?> encoding, final boolean isZipCompressed) {
    if (url == null) {
      throw new IllegalArgumentException(
          "Source URL string cannot be null."); //$NON-NLS-1$
    }
    this.m_sources.add(new _Location(url, URL.class, encoding,
        isZipCompressed));
    return ((JBT) this);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addURL(final String url) {
    return this.addURL(url, null, false);
  }

}
