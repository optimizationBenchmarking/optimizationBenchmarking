package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.EnumerationItem;

/** an enumeration item in a XHTML document */
final class _XHTML10EnumerationItem extends EnumerationItem {
  /**
   * Create a new enumeration item
   *
   * @param owner
   *          the owning text
   */
  _XHTML10EnumerationItem(final _XHTML10Enumeration owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    super.onOpen();
    this.getTextOutput().append(_XHTML10ItemizationItem.IL_BEGIN);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    this.getTextOutput().append(_XHTML10ItemizationItem.IL_END);
    super.onClose();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void appendLineBreak() {
    this.assertNoChildren();
    this.getTextOutput().append(XHTML10Driver.BR);
  }
}
