package org.optimizationBenchmarking.experimentation.evaluation.attributes.clusters.propertyValueGroups;

import org.optimizationBenchmarking.utils.hash.HashObject;
import org.optimizationBenchmarking.utils.text.ITextable;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * The base class for all kinds of group objects
 */
abstract class _GroupBase extends HashObject implements ITextable {

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
}
