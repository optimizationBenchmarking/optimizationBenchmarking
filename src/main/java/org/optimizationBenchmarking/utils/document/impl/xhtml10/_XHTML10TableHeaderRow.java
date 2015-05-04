package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.TableHeaderRow;

/** a row of a header of a table in a XHTML document */
final class _XHTML10TableHeaderRow extends TableHeaderRow {
  /** the start of a normal tr */
  private static final char[] TAB_TR_H_BEGIN = { '<', 't', 'r', ' ', 'c',
      'l', 'a', 's', 's', '=', '"', 't', 'a', 'b', 'H', '"', '>' };

  /**
   * Create a row of a header of a table
   *
   * @param owner
   *          the owning table header
   */
  _XHTML10TableHeaderRow(final _XHTML10TableHeader owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    super.onOpen();
    this.getTextOutput().append(_XHTML10TableHeaderRow.TAB_TR_H_BEGIN);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    this.getTextOutput().append(_XHTML10Table.TR_END);
    super.onClose();
  }
}
