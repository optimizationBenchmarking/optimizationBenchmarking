package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.document.spec.IMathFunction;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;

/** a mathematics operator */
public class MathFunction extends DocumentPart implements IMathFunction {

  /** the operators */
  private final EMathOperators m_op;

  /** the argument index */
  int m_argIndex;

  /**
   * Create a document part.
   * 
   * @param owner
   *          the owning FSM
   * @param op
   *          the mathematic operator
   */
  protected MathFunction(final BasicMath owner, final EMathOperators op) {
    super(owner);
    if (op == null) {
      throw new IllegalArgumentException(//
          "Math operator must not be null."); //$NON-NLS-1$
    }
    this.m_op = op;
  }

  /**
   * Get the mathematic operator
   * 
   * @return the mathematic operator
   */
  public final EMathOperators getOperator() {
    return this.m_op;
  }

  /**
   * this method is called before the {@code index}<sup>th</sup> argument
   * 
   * @param index
   *          the index of the argument
   */
  protected void beforeArgument(final int index) {
    //
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathArgument nextArgument() {
    final int pi;

    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    this.assertNoChildren();
    pi = (++this.m_argIndex);
    if (pi > this.m_op.m_maxArgumentCount) {
      throw new IllegalStateException(this.m_op
          + " cannot have more than " + //$NON-NLS-1$
          this.m_op.m_maxArgumentCount + " arguments."); //$NON-NLS-1$
    }
    this.beforeArgument(pi);
    return this.m_driver.createMathArgument(this);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void beforeChildOpens(
      final HierarchicalFSM child, final boolean hasOtherChildren) {

    super.beforeChildOpens(child, hasOtherChildren);

    if (!(child instanceof MathArgument)) {
      this.throwChildNotAllowed(child);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void afterChildOpened(
      final HierarchicalFSM child, final boolean hasOtherChildren) {

    super.afterChildOpened(child, hasOtherChildren);

    if (!(child instanceof MathArgument)) {
      this.throwChildNotAllowed(child);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void afterChildClosed(
      final HierarchicalFSM child) {

    super.afterChildClosed(child);

    if (!(child instanceof MathArgument)) {
      this.throwChildNotAllowed(child);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void onClose() {
    if (this.m_argIndex < this.m_op.m_minArgumentCount) {
      throw new IllegalStateException(this.m_op + //
          " needs at least " + this.m_op.m_minArgumentCount + //$NON-NLS-1$
          " arguments, but has only " + this.m_argIndex); //$NON-NLS-1$
    }
    this.fsmStateAssertAndSet(DocumentElement.STATE_ALIFE,
        DocumentElement.STATE_DEAD);
    super.onClose();
  }
}
