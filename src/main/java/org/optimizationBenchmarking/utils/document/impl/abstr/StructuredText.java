package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.document.spec.IStructuredText;

/**
 * A structured text
 */
public class StructuredText extends ComplexText implements IStructuredText {
  /**
   * Create a structured text.
   *
   * @param owner
   *          the owning FSM
   */
  protected StructuredText(final DocumentElement owner) {
    super(owner);
  }

  /** {@inheritDoc} */
  @Override
  public final synchronized Enumeration enumeration() {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.m_driver.createEnumeration(this);
  }

  /** {@inheritDoc} */
  @Override
  public final synchronized Itemization itemization() {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.m_driver.createItemization(this);
  }
}
