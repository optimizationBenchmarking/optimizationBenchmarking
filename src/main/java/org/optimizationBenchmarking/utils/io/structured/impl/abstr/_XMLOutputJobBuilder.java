package org.optimizationBenchmarking.utils.io.structured.impl.abstr;

import org.optimizationBenchmarking.utils.io.structured.spec.IXMLOutputJobBuilder;
import org.optimizationBenchmarking.utils.io.xml.XMLBase;

/**
 * The class for building xml IO jobs
 *
 * @param <DT>
 *          the source data type
 * @param <JBT>
 *          the job builder type
 */
class _XMLOutputJobBuilder<DT, JBT extends _XMLOutputJobBuilder<DT, JBT>>
    extends _TextOutputJobBuilder<DT, JBT> implements
    IXMLOutputJobBuilder<DT> {

  /**
   * create the job builder
   *
   * @param tool
   *          the owning tool
   */
  _XMLOutputJobBuilder(final XMLOutputTool<DT> tool) {
    super(tool);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public final JBT setWriter(final XMLBase xmlBase) {
    if (xmlBase == null) {
      throw new IllegalArgumentException("XMLBase cannot be null."); //$NON-NLS-1$
    }
    this.m_dest._setLocation(xmlBase, null);
    return ((JBT) this);
  }

}
