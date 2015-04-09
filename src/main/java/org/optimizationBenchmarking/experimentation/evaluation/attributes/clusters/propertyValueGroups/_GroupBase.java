package org.optimizationBenchmarking.experimentation.evaluation.attributes.clusters.propertyValueGroups;

import org.optimizationBenchmarking.experimentation.data.DataElement;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * The base class for all kinds of group objects
 */
abstract class _GroupBase extends DataElement {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the hash code */
  transient int m_hashCode;

  /** create the object */
  _GroupBase() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    final MemoryTextOutput mto;

    mto = new MemoryTextOutput();
    this.toText(mto);
    return mto.toString();
  }

  /** {@inheritDoc} */
  @Override
  public abstract void toText(ITextOutput textOut);

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return this.m_hashCode;
  }
}
