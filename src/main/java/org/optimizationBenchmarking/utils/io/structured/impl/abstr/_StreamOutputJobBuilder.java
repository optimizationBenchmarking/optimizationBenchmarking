package org.optimizationBenchmarking.utils.io.structured.impl.abstr;

import java.io.OutputStream;

import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;
import org.optimizationBenchmarking.utils.io.structured.spec.IStreamOutputJobBuilder;

/**
 * The class for building stream IO jobs
 * 
 * @param <DT>
 *          the source data type
 * @param <JBT>
 *          the job builder type
 */
class _StreamOutputJobBuilder<DT, JBT extends _StreamOutputJobBuilder<DT, JBT>>
    extends _FileOutputJobBuilder<DT, JBT> implements
    IStreamOutputJobBuilder<DT> {

  /**
   * create the job builder
   * 
   * @param tool
   *          the owning tool
   */
  _StreamOutputJobBuilder(final StreamOutputTool<DT> tool) {
    super(tool);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public final JBT setStream(final OutputStream stream,
      final StreamEncoding<?, ?> encoding, final boolean zipCompress) {
    if (stream == null) {
      throw new IllegalArgumentException(
          "Destination OutputStream cannot be null."); //$NON-NLS-1$
    }
    this.m_dest._setLocation(stream, null);
    this.m_dest.m_encoding = ((encoding != null) ? encoding
        : StreamEncoding.UNKNOWN);
    this.m_dest.m_zipped = zipCompress;
    return ((JBT) this);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT setStream(final OutputStream stream) {
    return this.setStream(stream, null, false);
  }
}
