package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.document.spec.IPlainText;

/** A text class */
public class PlainText extends Text implements IPlainText {

  /**
   * Create a text.
   *
   * @param owner
   *          the owning FSM
   */
  PlainText(final DocumentElement owner) {
    super(owner);
  }

  /** {@inheritDoc} */
  @Override
  public final synchronized InQuotes inQuotes() {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.m_driver.createInQuotes(this);
  }

  /** {@inheritDoc} */
  @Override
  public final synchronized InBraces inBraces() {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.m_driver.createInBraces(this);
  }
}
