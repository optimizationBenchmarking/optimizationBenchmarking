package org.optimizationBenchmarking.experimentation.data.impl.ref;

import org.optimizationBenchmarking.utils.hierarchy.FSM;
import org.optimizationBenchmarking.utils.hierarchy.NormalizingFSM;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * the hierarchical context root
 */
class _FSM extends NormalizingFSM {

  /** the context can now be modified */
  static final int STATE_OPEN = (FSM.STATE_NOTHING + 1);

  /** the state open name */
  static final String STATE_OPEN_NAME = "open"; //$NON-NLS-1$

  /**
   * create
   *
   * @param owner
   *          the owner
   */
  _FSM(final _FSM owner) {
    super(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected void fsmStateAppendName(final int state,
      final MemoryTextOutput append) {
    if (state == _FSM.STATE_OPEN) {
      append.append(_FSM.STATE_OPEN_NAME);
      return;
    }
    super.fsmStateAppendName(state, append);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void onOpen() {
    this.fsmStateAssertAndSet(FSM.STATE_NOTHING, _FSM.STATE_OPEN);
    super.onOpen();
  }

  /**
   * Published version of the {@link #normalize(Object)} method.
   *
   * @param input
   *          the input
   * @return the result
   * @param <T>
   *          the type
   */
  final <T> T _normalize(final T input) {
    return this.normalize(input);
  }

  /**
   * Published version of the {@link #normalize(Object)} method.
   *
   * @param input
   *          the input
   * @return the result
   * @param <T>
   *          the type
   */
  final <T> T _normalizeLocal(final T input) {
    return this.normalizeLocal(input);
  }
}
