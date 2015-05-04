package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.Superscript;
import org.optimizationBenchmarking.utils.document.impl.abstr.Text;

/** an superscript element of a section in a XHTML document */
final class _XHTML10Superscript extends Superscript {
  /** the sup begin */
  static final char[] SUP_BEGIN = { '<', 's', 'u', 'p', '>' };
  /** the sup end */
  static final char[] SUP_END = { '<', '/', 's', 'u', 'p', '>' };

  /**
   * create the superscript element
   *
   * @param owner
   *          the owner
   */
  _XHTML10Superscript(final Text owner) {
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
    this.getTextOutput().append(_XHTML10Superscript.SUP_BEGIN);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    this.getTextOutput().append(_XHTML10Superscript.SUP_END);
    super.onClose();
  }
}
