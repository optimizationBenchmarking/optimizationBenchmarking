package org.optimizationBenchmarking.utils.io.structured.impl.abstr;

import java.io.Writer;

import org.optimizationBenchmarking.utils.io.structured.spec.ITextOutputJobBuilder;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * The class for building text IO jobs
 *
 * @param <DT>
 *          the source data type
 * @param <JBT>
 *          the job builder type
 */
class _TextOutputJobBuilder<DT, JBT extends _TextOutputJobBuilder<DT, JBT>>
    extends _StreamOutputJobBuilder<DT, JBT> implements
    ITextOutputJobBuilder<DT> {

  /**
   * create the job builder
   *
   * @param tool
   *          the owning tool
   */
  _TextOutputJobBuilder(final TextOutputTool<DT> tool) {
    super(tool);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public final JBT setWriter(final Writer writer) {
    if (writer == null) {
      throw new IllegalArgumentException("Writer cannot be null."); //$NON-NLS-1$
    }
    this.m_dest._setLocation(writer, null);
    return ((JBT) this);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public final JBT setTextOutput(final ITextOutput textOut) {
    if (textOut == null) {
      throw new IllegalArgumentException("ITextOutput cannot be null."); //$NON-NLS-1$
    }
    this.m_dest._setLocation(textOut, null);
    return ((JBT) this);
  }

}
