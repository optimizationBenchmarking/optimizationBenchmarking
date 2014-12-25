package org.optimizationBenchmarking.utils.io.structured.impl.abstr;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;

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
