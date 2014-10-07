package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.ComplexText;
import org.optimizationBenchmarking.utils.document.impl.abstr.InlineMath;

/** an inline math element of a section in a XHTML document */
final class _XHTML10InlineMath extends InlineMath {
  /** the math span begin */
  static final char[] MATH_DIV_BEGIN = { '<', 'd', 'i', 'v', ' ', 'c',
      'l', 'a', 's', 's', '=', '"', 'm', 'a', 't', 'h', '"', '>' };

  /** the math-op table */
  static final char[] MO_TAB = { '<', 't', 'a', 'b', 'l', 'e', ' ', 'c',
      'l', 'a', 's', 's', '=', '"', 'm', 'a', 't', 'h', 'O', 'p', '"', '>' };
  /** the math-op tr */
  static final char[] MO_TR = { '<', 't', 'r', ' ', 'c', 'l', 'a', 's',
      's', '=', '"', 'm', 'a', 't', 'h', 'O', 'p', '"', '>' };
  /** the math-op td */
  static final char[] MO_TD = { '<', 't', 'd', ' ', 'c', 'l', 'a', 's',
      's', '=', '"', 'm', 'a', 't', 'h', 'O', 'p', '"', '>' };

  /**
   * create the inline math element
   * 
   * @param owner
   *          the owner
   */
  _XHTML10InlineMath(final ComplexText owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    super.onOpen();
    this.getTextOutput().append(_XHTML10InlineMath.MATH_DIV_BEGIN);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    this.getTextOutput().append(XHTML10Driver.DIV_END);
    super.onClose();
  }
}
