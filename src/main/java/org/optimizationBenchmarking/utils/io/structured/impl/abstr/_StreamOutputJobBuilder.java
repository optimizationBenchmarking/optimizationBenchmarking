package org.optimizationBenchmarking.utils.io.structured.impl.abstr;

import java.io.OutputStream;

import org.optimizationBenchmarking.utils.io.EArchiveType;
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
  @Override
  public final JBT setStream(final OutputStream stream,
      final StreamEncoding<?, ?> encoding, final EArchiveType archiveType) {
    return super.setStream(stream, encoding, archiveType);
  }

  /** {@inheritDoc} */
  @Override
  public final JBT setStream(final OutputStream stream) {
    return this.setStream(stream, null, null);
  }
}
