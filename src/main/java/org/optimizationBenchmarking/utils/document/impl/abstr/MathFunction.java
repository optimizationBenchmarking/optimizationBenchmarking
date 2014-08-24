package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.document.spec.IMathFunction;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;

/** a mathematics operator */
public class MathFunction extends DocumentPart implements IMathFunction {

  /** the operators */
  private final EMathOperators m_op;

  /** the parameter index */
  private int m_paramIndex;

  /**
   * Create a document part.
   * 
   * @param owner
   *          the owning FSM
   * @param op
   *          the mathematic operator
   */
  protected MathFunction(final BasicMath owner, final EMathOperators op) {
    super(owner, null);
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
   * Create a parameter
   * 
   * @param index
   *          the index of the parameter
   * @return the parameter
   */
  protected MathParameter createParameter(final int index) {
    return new MathParameter(this, index);
  }

  /**
   * this method is called before the {@code index}<sup>th</sup> parameter
   * 
   * @param index
   *          the index of the parameter
   */
  protected void beforeParameter(final int index) {
    //
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final MathParameter nextArgument() {
    final int pi;

    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    this.assertNoChildren();
    pi = (++this.m_paramIndex);
    if (pi > this.m_op.m_maxParamCount) {
      throw new IllegalStateException(this.m_op
          + " cannot have more than " + //$NON-NLS-1$
          this.m_op.m_maxParamCount + " parameters."); //$NON-NLS-1$
    }
    this.beforeParameter(pi);
    return this.createParameter(pi);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void beforeChildOpens(
      final HierarchicalFSM child, final boolean hasOtherChildren) {

    super.beforeChildOpens(child, hasOtherChildren);

    if (!(child instanceof MathParameter)) {
      this.throwChildNotAllowed(child);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void afterChildOpened(
      final HierarchicalFSM child, final boolean hasOtherChildren) {

    super.afterChildOpened(child, hasOtherChildren);

    if (!(child instanceof MathParameter)) {
      this.throwChildNotAllowed(child);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void afterChildClosed(
      final HierarchicalFSM child) {

    super.afterChildClosed(child);

    if (!(child instanceof MathParameter)) {
      this.throwChildNotAllowed(child);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void onClose() {
    if (this.m_paramIndex < this.m_op.m_minParamCount) {
      throw new IllegalStateException(this.m_op + //
          " needs at least " + this.m_op.m_minParamCount + //$NON-NLS-1$
          " parameters, but has only " + this.m_paramIndex); //$NON-NLS-1$
    }
    this.fsmStateAssertAndSet(DocumentElement.STATE_ALIFE,
        DocumentElement.STATE_DEAD);
    super.onClose();
  }
}
