package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.Subscript;
import org.optimizationBenchmarking.utils.document.impl.abstr.Text;

/** an subscript element of a section in a XHTML document */
final class _XHTML10Subscript extends Subscript {
  /** the sub begin */
  private static final char[] SUB_BEGIN = { '<', 's', 'u', 'b', '>' };
  /** the sub end */
  static final char[] SUB_END = { '<', '/', 's', 'u', 'b', '>' };

  /**
   * create the subscript element
   *
   * @param owner
   *          the owner
   */
  _XHTML10Subscript(final Text owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void appendLineBreak() {
    this.assertNoChildren();
    this.getTextOutput().append(' ');
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    super.onOpen();
    this.getTextOutput().append(_XHTML10Subscript.SUB_BEGIN);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    this.getTextOutput().append(_XHTML10Subscript.SUB_END);
    super.onClose();
  }
}
