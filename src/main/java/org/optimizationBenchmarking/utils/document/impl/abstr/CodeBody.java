package org.optimizationBenchmarking.utils.document.impl.abstr;

/** A text class for a code block */
public class CodeBody extends PlainText {

  /**
   * Create a code block text.
   * 
   * @param owner
   *          the owning FSM
   */
  public CodeBody(final Code owner) {
    super(owner);
  }

  /** {@inheritDoc} */
  @Override
  public final synchronized CodeInBraces inBraces() {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.m_driver.createCodeInBraces(this);
  }
}
