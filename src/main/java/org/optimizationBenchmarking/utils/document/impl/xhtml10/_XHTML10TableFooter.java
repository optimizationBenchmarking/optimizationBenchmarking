package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.TableFooter;

/** a footer of a table in a XHTML document */
final class _XHTML10TableFooter extends TableFooter {
  /** the start of table foot */
  private static final char[] TAB_FOOT_BEGIN = { '<', 't', 'f', 'o', 'o',
      't', ' ', 'c', 'l', 'a', 's', 's', '=', '"', 't', 'a', 'b', '"', '>' };
  /** the end of the table foot */
  private static final char[] TAB_FOOT_END = { '<', '/', 't', 'f', 'o',
      'o', 't', '>' };

  /**
   * Create a footer of a table
   *
   * @param owner
   *          the owning table
   */
  _XHTML10TableFooter(final _XHTML10Table owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    super.onOpen();
    this.getTextOutput().append(_XHTML10TableFooter.TAB_FOOT_BEGIN);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    this.getTextOutput().append(_XHTML10TableFooter.TAB_FOOT_END);
    super.onClose();
  }
}
