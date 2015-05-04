package org.optimizationBenchmarking.utils.io.structured.impl.abstr;

import java.io.Reader;

import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;
import org.optimizationBenchmarking.utils.io.structured.spec.ITextInputJobBuilder;

/**
 * The class for building text input jobs
 *
 * @param <DT>
 *          the source data type
 * @param <JBT>
 *          the job builder type
 */
class _TextInputJobBuilder<DT, JBT extends _TextInputJobBuilder<DT, JBT>>
    extends _StreamInputJobBuilder<DT, JBT> implements
    ITextInputJobBuilder<DT> {

  /**
   * create the job builder
   *
   * @param tool
   *          the owning tool
   */
  _TextInputJobBuilder(final TextInputTool<DT> tool) {
    super(tool);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public final JBT addReader(final Reader reader) {
    if (reader == null) {
      throw new IllegalArgumentException("Source Reader cannot be null."); //$NON-NLS-1$
    }
    this.m_sources.add(new _Location(reader, null, StreamEncoding.UNKNOWN,
        null));
    return ((JBT) this);
  }
}
