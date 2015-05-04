package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.ItemizationItem;

/** an itemization item in a XHTML document */
final class _XHTML10ItemizationItem extends ItemizationItem {
  /** the start of il */
  static final char[] IL_BEGIN = { '<', 'l', 'i', '>' };
  /** the end of il */
  static final char[] IL_END = { '<', '/', 'l', 'i', '>' };

  /**
   * Create a new itemization item
   *
   * @param owner
   *          the owning text
   */
  _XHTML10ItemizationItem(final _XHTML10Itemization owner) {
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
