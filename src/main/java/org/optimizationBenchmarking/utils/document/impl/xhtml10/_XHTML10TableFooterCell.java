package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.TableFooterCell;
import org.optimizationBenchmarking.utils.document.spec.ETableCellDef;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** a footer cell of a table in a XHTML document */
final class _XHTML10TableFooterCell extends TableFooterCell {
  /**
   * Create a footer cell of a table
   *
   * @param owner
   *          the owning row
   * @param cols
   *          the number of columns occupied by the cell
   * @param rows
   *          the number of rows occupied by the cell
   * @param def
   *          the cell definition
   */
  _XHTML10TableFooterCell(final _XHTML10TableFooterRow owner,
      final int cols, final int rows, final ETableCellDef[] def) {
    super(owner, cols, rows, def);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void appendLineBreak() {
    this.assertNoChildren();
    this.getTextOutput().append(XHTML10Driver.BR);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    final ITextOutput out;

    super.onOpen();

    out = this.getTextOutput();
    out.append(_XHTML10TableBodyCell.TAB_TD_BEGIN);
    _XHTML10TableBodyCell._cellMode(out, this);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    this.getTextOutput().append(_XHTML10Table.TD_END);
    super.onClose();
  }
}
