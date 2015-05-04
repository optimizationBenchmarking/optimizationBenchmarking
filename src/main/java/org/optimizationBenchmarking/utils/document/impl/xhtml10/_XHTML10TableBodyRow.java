package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.TableBodyRow;

/** a row of a body of a table in a XHTML document */
final class _XHTML10TableBodyRow extends TableBodyRow {
  /** the start of an even tr */
  private static final char[] TAB_TR_BE_BEGIN = { '<', 't', 'r', ' ', 'c',
      'l', 'a', 's', 's', '=', '"', 't', 'a', 'b', 'B', 'E', '"', '>' };
  /** the start of an odd tr */
  private static final char[] TAB_TR_BO_BEGIN = { '<', 't', 'r', ' ', 'c',
      'l', 'a', 's', 's', '=', '"', 't', 'a', 'b', 'B', 'O', '"', '>' };
  /** the start of an even tr */
  private static final char[] TAB_TR_BEHR_BEGIN = { '<', 't', 'r', ' ',
      'c', 'l', 'a', 's', 's', '=', '"', 't', 'a', 'b', 'B', 'E', 'H',
      'R', '"', '>' };
  /** the start of an odd tr */
  private static final char[] TAB_TR_BOHR_BEGIN = { '<', 't', 'r', ' ',
      'c', 'l', 'a', 's', 's', '=', '"', 't', 'a', 'b', 'B', 'O', 'H',
      'R', '"', '>' };

  /**
   * Create a row of a body of a table
   *
   * @param owner
   *          the owning table body
   */
  _XHTML10TableBodyRow(final _XHTML10TableBody owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    final boolean b;

    super.onOpen();

    b = this.shouldPrintSeparator();
    this.getTextOutput().append(
        (((this.getIndex() & 1) == 0)//
        ? (b ? _XHTML10TableBodyRow.TAB_TR_BEHR_BEGIN
            : _XHTML10TableBodyRow.TAB_TR_BE_BEGIN)//
            : (b ? _XHTML10TableBodyRow.TAB_TR_BOHR_BEGIN
                : _XHTML10TableBodyRow.TAB_TR_BO_BEGIN)));
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    this.getTextOutput().append(_XHTML10Table.TR_END);
    super.onClose();
  }
}
