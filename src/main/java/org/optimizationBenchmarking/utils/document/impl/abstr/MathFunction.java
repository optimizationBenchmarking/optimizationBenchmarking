package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.hierarchy.HierarchicalText;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/** a mathematics operator */
public abstract class MathFunction extends BasicMath {

  /** the done index */
  private int m_done;

  /** the data */
  private final char[][] m_data;

  /** the minimum number of arguments */
  private final int m_minArgs;

  /**
   * Create a document part.
   * 
   * @param owner
   *          the owning FSM
   * @param minArgs
   *          the minimum number of arguments
   * @param maxArgs
   *          the maximum number of arguments
   */
  MathFunction(final BasicMath owner, final int minArgs, final int maxArgs) {
    super(owner);
    this.m_data = new char[maxArgs][];
    this.m_minArgs = minArgs;
  }

  /** {@inheritDoc} */
  @Override
  protected final boolean mustChildBeBuffered(final HierarchicalText child) {
    return true;
  }

  /** {@inheritDoc} */
  @Override
  protected final void processBufferedOutputFromChild(
      final HierarchicalText child, final MemoryTextOutput out) {
    this.m_data[this.m_done++] = out.toChars();
    if (this.m_data[this.m_done - 1].length <= 0) {
      throw new IllegalArgumentException(child.toString());
    }
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void onClose() {
    final int done;

    if (this.getCurrentSize() != (done = this.m_done)) {
      throw new IllegalStateException(this.getClass().getSimpleName() + //
          " has least " + this.getCurrentSize() + //$NON-NLS-1$
          " arguments, but only " + done + //$NON-NLS-1$
          " have completed their work."); //$NON-NLS-1$
    }
    this.fsmStateAssertAndSet(DocumentElement.STATE_ALIFE,
        DocumentElement.STATE_DEAD);
    this.render(this.getTextOutput(), this.m_data, done);
    super.onClose();
  }

  /**
   * Render the output
   * 
   * @param data
   *          the data
   * @param size
   *          the size
   * @param out
   *          the text output
   */
  protected void render(final ITextOutput out, final char[][] data,
      final int size) {
    //
  }

  /** {@inheritDoc} */
  @Override
  protected final int minArgs() {
    return this.m_minArgs;
  }

  /** {@inheritDoc} */
  @Override
  protected final int maxArgs() {
    return this.m_data.length;
  }
}
