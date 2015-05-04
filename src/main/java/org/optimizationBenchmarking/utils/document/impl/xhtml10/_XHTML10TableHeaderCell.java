package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.TableHeaderCell;
import org.optimizationBenchmarking.utils.document.spec.ETableCellDef;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** a header cell of a table in a XHTML document */
final class _XHTML10TableHeaderCell extends TableHeaderCell {
  /** the start of the TH */
  private static final char[] TAB_TH_BEGIN = { '<', 't', 'h' };
  /** the end of the TH */
  private static final char[] TAB_TH_END = { '<', '/', 't', 'h', '>' };

  /**
   * Create a header cell of a table
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
  _XHTML10TableHeaderCell(final _XHTML10TableHeaderRow owner,
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
    out.append(_XHTML10TableHeaderCell.TAB_TH_BEGIN);
    _XHTML10TableBodyCell._cellMode(out, this);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    this.getTextOutput().append(_XHTML10TableHeaderCell.TAB_TH_END);
    super.onClose();
  }
}
