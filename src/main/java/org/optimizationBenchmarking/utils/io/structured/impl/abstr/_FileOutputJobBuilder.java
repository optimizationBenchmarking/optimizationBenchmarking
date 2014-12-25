package org.optimizationBenchmarking.utils.io.structured.impl.abstr;

import java.io.File;
import java.nio.file.Path;

import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;
import org.optimizationBenchmarking.utils.io.structured.spec.IFileOutputJobBuilder;

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
      final StreamEncoding<?, ?> encoding, final boolean zipCompress) {
    if (path == null) {
      throw new IllegalArgumentException(
          "Destination Path cannot be null."); //$NON-NLS-1$
    }
    this.m_dest._setLocation(path, null);
    this.m_dest.m_encoding = ((encoding != null) ? encoding
        : StreamEncoding.UNKNOWN);
    this.m_dest.m_zipped = zipCompress;
    return ((JBT) this);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT setPath(final Path path) {
    return this.setPath(path, null, false);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public final JBT setFile(final File file,
      final StreamEncoding<?, ?> encoding, final boolean zipCompress) {
    if (file == null) {
      throw new IllegalArgumentException(
          "Destination File cannot be null."); //$NON-NLS-1$
    }
    this.m_dest._setLocation(file, null);
    this.m_dest.m_encoding = ((encoding != null) ? encoding
        : StreamEncoding.UNKNOWN);
    this.m_dest.m_zipped = zipCompress;
    return ((JBT) this);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT setFile(final File file) {
    return this.setFile(file, null, false);
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("unchecked")
  public final JBT setPath(final String path,
      final StreamEncoding<?, ?> encoding, final boolean zipCompress) {
    if (path == null) {
      throw new IllegalArgumentException(
          "Destination Path String cannot be null."); //$NON-NLS-1$
    }
    this.m_dest._setLocation(path, Path.class);
    this.m_dest.m_encoding = ((encoding != null) ? encoding
        : StreamEncoding.UNKNOWN);
    this.m_dest.m_zipped = zipCompress;
    return ((JBT) this);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT setPath(final String path) {
    return this.setPath(path, null, false);
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("unchecked")
  public final JBT setFile(final String file,
      final StreamEncoding<?, ?> encoding, final boolean zipCompress) {
    if (file == null) {
      throw new IllegalArgumentException(
          "Destination File String cannot be null."); //$NON-NLS-1$
    }
    this.m_dest._setLocation(file, File.class);
    this.m_dest.m_encoding = ((encoding != null) ? encoding
        : StreamEncoding.UNKNOWN);
    this.m_dest.m_zipped = zipCompress;
    return ((JBT) this);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT setFile(final String file) {
    return this.setFile(file, null, false);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("rawtypes")
  @Override
  final _OutputJob _doCreate() {
    return new _OutputJob(this.m_logger, ((FileOutputTool) (this.m_tool)),
        this.m_source, this.m_dest);
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
}
