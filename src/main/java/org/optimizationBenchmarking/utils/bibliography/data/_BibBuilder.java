package org.optimizationBenchmarking.utils.bibliography.data;

import org.optimizationBenchmarking.utils.hierarchy.FSM;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;
import org.optimizationBenchmarking.utils.hierarchy.NormalizingFSM;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * An internal, abstract base class for builders for bibliographic objects.
 * 
 * @param <RT>
 *          the result type
 */
abstract class _BibBuilder<RT> extends NormalizingFSM {

  /** the finalized flag */
  static final int FLAG_FINALIZED = (FSM.FLAG_NOTHING + 1);

  /** the result */
  private RT m_result;

  /**
   * create the author builder
   * 
   * @param owner
   *          the owner
   */
  _BibBuilder(final HierarchicalFSM owner) {
    super(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected void fsmFlagsAppendName(final int flagValue,
      final int flagIndex, final MemoryTextOutput append) {
    if (flagValue == _BibBuilder.FLAG_FINALIZED) {
      append.append("finalized"); //$NON-NLS-1$
    } else {
      super.fsmFlagsAppendName(flagValue, flagIndex, append);
    }
  }

  /**
   * Compile the result
   * 
   * @return the result
   */
  abstract RT _compile();

  /**
   * get the result
   * 
   * @return the result
   */
  public synchronized RT getResult() {
    if (this.fsmFlagsSet(_BibBuilder.FLAG_FINALIZED)) {
      this.m_result = this.normalize(this._compile());
    }
    return this.m_result;
  }
}
