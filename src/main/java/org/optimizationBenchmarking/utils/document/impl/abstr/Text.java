package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.document.spec.IText;

/** A text class */
public class Text extends PlainText implements IText {

  /**
   * Create a text.
   * 
   * @param owner
   *          the owning FSM
   */
  protected Text(final DocumentElement owner) {
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
