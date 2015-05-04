package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.TableFooterRow;

/** a row of a footer of a table in a XHTML document */
final class _XHTML10TableFooterRow extends TableFooterRow {

  /** the start of a normal tr */
  private static final char[] TAB_TR_F_BEGIN = { '<', 't', 'r', ' ', 'c',
      'l', 'a', 's', 's', '=', '"', 't', 'a', 'b', 'F', '"', '>' };

  /**
   * Create a row of a footer of a table
   *
   * @param owner
   *          the owning table footer
   */
  _XHTML10TableFooterRow(final _XHTML10TableFooter owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    super.onOpen();
    this.getTextOutput().append(_XHTML10TableFooterRow.TAB_TR_F_BEGIN);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    this.getTextOutput().append(_XHTML10Table.TR_END);
    super.onClose();
  }
}
