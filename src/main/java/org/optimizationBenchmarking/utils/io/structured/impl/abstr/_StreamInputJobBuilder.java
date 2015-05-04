package org.optimizationBenchmarking.utils.io.structured.impl.abstr;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import org.optimizationBenchmarking.utils.io.EArchiveType;
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
  @Override
  public final JBT addStream(final InputStream stream,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType) {
    return super.addStream(stream, encoding, archiveType);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addStream(final InputStream stream) {
    return this.addStream(stream, null, null);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addResource(final Class<?> clazz, final String name,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType) {
    return super.addResource(clazz, name, encoding, archiveType);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addResource(final Class<?> clazz, final String name) {
    return this.addResource(clazz, name, null, null);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addResource(final String clazz, final String name,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType) {
    return super.addResource(clazz, name, encoding, archiveType);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addResource(final String clazz, final String name) {
    return this.addResource(clazz, name, null, null);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addURI(final URI uri,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType) {
    return super.addURI(uri, encoding, archiveType);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addURI(final URI uri) {
    return this.addURI(uri, null, null);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addURI(final String uri,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType) {
    return super.addURI(uri, encoding, archiveType);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addURI(final String uri) {
    return this.addURI(uri, null, null);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addURL(final URL url,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType) {
    return super.addURL(url, encoding, archiveType);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addURL(final URL url) {
    return this.addURL(url, null, null);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addURL(final String url,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType) {
    return super.addURL(url, encoding, archiveType);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT addURL(final String url) {
    return this.addURL(url, null, null);
  }

}
