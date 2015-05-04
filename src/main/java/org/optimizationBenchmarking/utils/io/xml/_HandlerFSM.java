package org.optimizationBenchmarking.utils.io.xml;

import org.optimizationBenchmarking.utils.hierarchy.FSM;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * The internal hierarchical FSM for content handlers.
 */
final class _HandlerFSM extends HierarchicalFSM {

  /** a child is open */
  private static final int FLAG_OPEN_CHILD = (FSM.FLAG_NOTHING + 1);

  /** the managed handler */
  final DelegatingHandler m_managed;

  /**
   * create
   *
   * @param owner
   *          the owner
   * @param managed
   *          the managed handler
   */
  _HandlerFSM(final _HandlerFSM owner, final DelegatingHandler managed) {
    super(owner);
    this.m_managed = managed;
  }

  /** open */
  final void _open() {
    this.open();
  }

  /** close */
  final void _close() {
    this.close();
  }

  /** {@inheritDoc} */
  @Override
  protected final void fsmFlagsAppendName(final int flagValue,
      final int flagIndex, final MemoryTextOutput append) {
    if (flagValue == _HandlerFSM.FLAG_OPEN_CHILD) {
      append.append("childOpen"); //$NON-NLS-1$
    } else {
      super.fsmFlagsAppendName(flagValue, flagIndex, append);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final synchronized void beforeChildOpens(
      final HierarchicalFSM child, final boolean hasOtherChildren) {
    super.beforeChildOpens(child, hasOtherChildren);
    if (child instanceof _HandlerFSM) {
      this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
          _HandlerFSM.FLAG_OPEN_CHILD, _HandlerFSM.FLAG_OPEN_CHILD,
          FSM.FLAG_NOTHING);
    } else {
      this.throwChildNotAllowed(child);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final synchronized void afterChildOpened(
      final HierarchicalFSM child, final boolean hasOtherChildren) {
    final DelegatingHandler managed;
    final _HandlerFSM childFSM;

    super.afterChildOpened(child, hasOtherChildren);

    if (child instanceof _HandlerFSM) {
      managed = this.m_managed;
      if (managed.m_delegate != null) {
        throw new IllegalStateException(
            "Child handler of managed handler not null, so it cannot be set."); //$NON-NLS-1$
      }

      childFSM = ((_HandlerFSM) child);
      managed.m_delegate = childFSM.m_managed;
      managed.onStartDelegate(managed.m_delegate);
    } else {
      this.throwChildNotAllowed(child);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final synchronized void afterChildClosed(
      final HierarchicalFSM child) {
    final DelegatingHandler managed;
    final _HandlerFSM childFSM;

    super.afterChildClosed(child);

    if (child instanceof _HandlerFSM) {
      this.fsmFlagsAssertTrue(_HandlerFSM.FLAG_OPEN_CHILD);

      managed = this.m_managed;
      childFSM = ((_HandlerFSM) child);
      if (childFSM.m_managed != managed.m_delegate) {
        throw new IllegalStateException(
            "Closed child handler of managed handler inconsistent."); //$NON-NLS-1$
      }

      try {
        managed.onEndDelegate(childFSM.m_managed);
      } finally {
        managed.m_delegate = null;
      }

      this.fsmFlagsAssertAndUpdate(_HandlerFSM.FLAG_OPEN_CHILD,
          FSM.FLAG_NOTHING, FSM.FLAG_NOTHING, _HandlerFSM.FLAG_OPEN_CHILD);
    } else {
      this.throwChildNotAllowed(child);
    }
  }
}
