package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.document.spec.IMathName;

/** A text class for mathematical names */
public class MathName extends Text implements IMathName {

  /**
   * Create a text.
   *
   * @param owner
   *          the owning FSM
   */
  protected MathName(final BasicMath owner) {
    super(owner);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final Subscript subscript() {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.m_driver.createSubscript(this);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final Superscript superscript() {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.m_driver.createSuperscript(this);
  }
}
