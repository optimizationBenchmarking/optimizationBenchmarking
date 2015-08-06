package org.optimizationBenchmarking.utils.io.structured.impl.abstr;

import java.io.File;
import java.io.OutputStream;
import java.nio.file.Path;

import org.optimizationBenchmarking.utils.config.Configuration;
import org.optimizationBenchmarking.utils.io.EArchiveType;
import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;
import org.optimizationBenchmarking.utils.io.structured.spec.IFileOutputJobBuilder;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

/**
 * The class for building file output jobs
 *
 * @param <DT>
 *          the source data type
 * @param <JBT>
 *          the job builder type
 */
class _FileOutputJobBuilder<DT, JBT extends _FileOutputJobBuilder<DT, JBT>>
    extends _IOJobBuilder<JBT> implements IFileOutputJobBuilder<DT> {

  /** the source */
  DT m_source;

  /** the destination */
  final _Location m_dest;

  /** the file producer listener */
  private IFileProducerListener m_listener;

  /**
   * create the job builder
   *
   * @param tool
   *          the owning tool
   */
  _FileOutputJobBuilder(final FileOutputTool<DT> tool) {
    super(tool);
    this.m_dest = new _Location();
  }

  /** {@inheritDoc} */
  @SuppressWarnings("rawtypes")
  @Override
  public JBT configure(final Configuration config) {
    final String dest;
    final JBT res;
    String prefix, suffix;

    res = super.configure(config);

    prefix = this.getParameterPrefix();
    if (prefix == null) {
      prefix = IOTool.OUTPUT_PARAM_PREFIX;
    }
    suffix = ((FileOutputTool) (this.m_tool)).getDestinationParamSuffix();
    if (suffix == null) {
      suffix = IOTool.PARAM_DESTINATION_SUFFIX;
    }
    dest = config.getString((prefix + suffix), null);
    if (dest != null) {
      this._location(dest);
    }

    return res;
  }

  /** {@inheritDoc} */
  @Override
  final void _setPath(final String path,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType) {
    this.setPath(path, encoding, archiveType);
  }

  /** {@inheritDoc} */
  @Override
  final void _setResource(final String clazz, final String resource,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType) {
    throw new UnsupportedOperationException(
        "Output not possible to a resource."); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  final void _setURI(final String uri,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType) {
    throw new UnsupportedOperationException(
        "Output not possible to a URI."); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  final void _setURL(final String url,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType) {
    throw new UnsupportedOperationException(
        "Output not possible to a URL."); //$NON-NLS-1$
  }

  /**
   * validate the source data
   *
   * @param source
   *          the source data
   */
  private static final void __validateSource(final Object source) {
    if (source == null) {
      throw new IllegalArgumentException("Source data cannot be null."); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public final JBT setSource(final DT source) {
    _FileOutputJobBuilder.__validateSource(source);
    this.m_source = source;
    return ((JBT) this);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public final JBT setPath(final Path path,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType) {
    if (path == null) {
      throw new IllegalArgumentException(
          "Destination Path cannot be null."); //$NON-NLS-1$
    }
    this.m_dest._setLocation(path, null);
    this.m_dest.m_encoding = ((encoding != null) ? encoding
        : StreamEncoding.UNKNOWN);
    this.m_dest.m_archiveType = archiveType;
    return ((JBT) this);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT setPath(final Path path) {
    return this.setPath(path, null, null);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public final JBT setFile(final File file,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType) {
    if (file == null) {
      throw new IllegalArgumentException(
          "Destination File cannot be null."); //$NON-NLS-1$
    }
    this.m_dest._setLocation(file, null);
    this.m_dest.m_encoding = ((encoding != null) ? encoding
        : StreamEncoding.UNKNOWN);
    this.m_dest.m_archiveType = archiveType;
    return ((JBT) this);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT setFile(final File file) {
    return this.setFile(file, null, null);
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("unchecked")
  public final JBT setPath(final String path,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType) {
    if (path == null) {
      throw new IllegalArgumentException(
          "Destination Path String cannot be null."); //$NON-NLS-1$
    }
    this.m_dest._setLocation(path, Path.class);
    this.m_dest.m_encoding = ((encoding != null) ? encoding
        : StreamEncoding.UNKNOWN);
    this.m_dest.m_archiveType = archiveType;
    return ((JBT) this);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT setPath(final String path) {
    return this.setPath(path, null, null);
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("unchecked")
  public final JBT setFile(final String file,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType) {
    if (file == null) {
      throw new IllegalArgumentException(
          "Destination File String cannot be null."); //$NON-NLS-1$
    }
    this.m_dest._setLocation(file, File.class);
    this.m_dest.m_encoding = ((encoding != null) ? encoding
        : StreamEncoding.UNKNOWN);
    this.m_dest.m_archiveType = archiveType;
    return ((JBT) this);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT setFile(final String file) {
    return this.setFile(file, null, null);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("rawtypes")
  @Override
  final _OutputJob _doCreate() {
    return new _OutputJob(this.getLogger(),
        ((FileOutputTool) (this.m_tool)), this.m_basePath, this.m_source,
        this.m_dest, this.m_listener);
  }

  /** {@inheritDoc} */
  @Override
  protected final void validate() {
    super.validate();
    _FileOutputJobBuilder.__validateSource(this.m_source);
    if (this.m_dest.m_location1 == null) {
      throw new IllegalArgumentException(//
          "Destination for output must be set."); //$NON-NLS-1$
    }
  }

  /**
   * Set the stream to write to. The stream may or may not be closed upon
   * termination.
   *
   * @param stream
   *          the stream to write the output to
   * @param encoding
   *          a stream encoding to use ({@code null} if not specified or
   *          not necessary)
   * @param archiveType
   *          the archive type
   * @return this builder
   */
  @SuppressWarnings("unchecked")
  JBT setStream(final OutputStream stream,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType) {
    if (stream == null) {
      throw new IllegalArgumentException(
          "Destination OutputStream cannot be null."); //$NON-NLS-1$
    }
    this.m_dest._setLocation(stream, null);
    this.m_dest.m_encoding = ((encoding != null) ? encoding
        : StreamEncoding.UNKNOWN);
    this.m_dest.m_archiveType = archiveType;
    return ((JBT) this);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT setArchiveStream(final OutputStream stream,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType) {
    return this.setStream(stream, encoding, archiveType);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT setArchiveStream(final OutputStream stream,
      final EArchiveType archiveType) {
    return this.setStream(stream, null, archiveType);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public final JBT setFileProducerListener(
      final IFileProducerListener listener) {
    this.m_listener = listener;
    return ((JBT) this);
  }
}
