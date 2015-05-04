package org.optimizationBenchmarking.utils.hierarchy;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * An abstract base class for FSMs that can build a complex object.
 *
 * @param <T>
 *          the name of the thing to be built
 */
public abstract class BuilderFSM<T> extends NormalizingFSM {

  /** the text style builder has been opened */
  protected static final int STATE_OPEN = (FSM.STATE_NOTHING + 1);
  /** the text style builder is compiling */
  private static final int STATE_COMPILING = (BuilderFSM.STATE_OPEN + 1);
  /** the text style builder has been compiled */
  private static final int STATE_COMPILED = (BuilderFSM.STATE_OPEN + 1);
  /** the text style builder has been closed */
  private static final int STATE_CLOSED = (BuilderFSM.STATE_COMPILED + 1);

  /** the state names */
  private static final String[] STATES;

  static {
    STATES = new String[BuilderFSM.STATE_CLOSED + 1];
    BuilderFSM.STATES[BuilderFSM.STATE_OPEN] = "open";//$NON-NLS-1$
    BuilderFSM.STATES[BuilderFSM.STATE_COMPILING] = "compiling";//$NON-NLS-1$
    BuilderFSM.STATES[BuilderFSM.STATE_COMPILED] = "compiled";//$NON-NLS-1$
    BuilderFSM.STATES[BuilderFSM.STATE_CLOSED] = "closed"; //$NON-NLS-1$
  }

  /** the product */
  private volatile T m_product;

  /**
   * Create the builder fsm
   *
   * @param owner
   *          the owner
   */
  protected BuilderFSM(final HierarchicalFSM owner) {
    super(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    super.onOpen();
    this.fsmStateAssertAndSet(FSM.STATE_NOTHING, BuilderFSM.STATE_OPEN);
  }

  /** {@inheritDoc} */
  @Override
  protected final void fsmStateAppendName(final int state,
      final MemoryTextOutput sb) {
    if ((state >= BuilderFSM.STATE_OPEN)
        && (state < BuilderFSM.STATES.length)) {
      sb.append(BuilderFSM.STATES[state]);
    } else {
      super.fsmStateAppendName(state, sb);
    }
  }

  /**
   * Build the product of this builder
   *
   * @return the product
   */
  protected abstract T compile();

  /**
   * Obtain the product of this builder. If the product has not yet been
   * created, {@link #compile() compile} the builder's data first.
   *
   * @return the product
   */
  public synchronized T getResult() {
    if (this.m_product != null) {
      this.fsmStateAssert(EComparison.GREATER_OR_EQUAL,
          BuilderFSM.STATE_COMPILED);
      return this.m_product;
    }
    this.fsmStateAssertAndSet(BuilderFSM.STATE_OPEN,
        BuilderFSM.STATE_COMPILING);
    this.m_product = this.compile();
    this.fsmStateAssertAndSet(BuilderFSM.STATE_COMPILING,
        BuilderFSM.STATE_COMPILED);
    return this.m_product;
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void onClose() {
    super.onClose();

    // Make sure that the result is actually compiled and accessed in one
    // way or another.
    // Do not allow this class to be used without actually using its
    // result.
    this.getResult();
    this.fsmStateAssertAndSet(BuilderFSM.STATE_COMPILED,
        BuilderFSM.STATE_CLOSED);
  }
}
