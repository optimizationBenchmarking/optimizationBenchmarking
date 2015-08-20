package org.optimizationBenchmarking.utils.io.structured.impl.abstr;

import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;
import org.optimizationBenchmarking.utils.io.structured.spec.IXMLInputJobBuilder;
import org.xml.sax.InputSource;

/**
 * The class for building XML input jobs
 *
 * @param <DT>
 *          the source data type
 * @param <JBT>
 *          the job builder type
 */
class _XMLInputJobBuilder<DT, JBT extends _XMLInputJobBuilder<DT, JBT>>
    extends _TextInputJobBuilder<DT, JBT> implements
    IXMLInputJobBuilder<DT> {

  /**
   * create the job builder
   *
   * @param tool
   *          the owning tool
   */
  _XMLInputJobBuilder(final XMLInputTool<DT> tool) {
    super(tool);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public final JBT addInputSource(final InputSource source) {
    if (source == null) {
      throw new IllegalArgumentException("InputSource cannot be null."); //$NON-NLS-1$
    }
    this.m_sources.add(new _Location(source, null, StreamEncoding.UNKNOWN,
        null));
    return ((JBT) this);
  }
}
