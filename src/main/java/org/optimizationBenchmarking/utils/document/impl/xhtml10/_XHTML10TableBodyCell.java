package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.TableBodyCell;
import org.optimizationBenchmarking.utils.document.impl.abstr.TableCell;
import org.optimizationBenchmarking.utils.document.spec.ETableCellDef;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** a body cell of a table in a XHTML document */
final class _XHTML10TableBodyCell extends TableBodyCell {
  /** the start of the td */
  static final char[] TAB_TD_BEGIN = { '<', 't', 'd' };
  /** the class c */
  private static final char[] TAB_CLASS_C = { ' ', 'c', 'l', 'a', 's',
      's', '=', '"', 't', 'a', 'b', 'C', '"', };
  /** the class l */
  private static final char[] TAB_CLASS_L = { ' ', 'c', 'l', 'a', 's',
      's', '=', '"', 't', 'a', 'b', 'L', '"', };
  /** the class r */
  private static final char[] TAB_CLASS_R = { ' ', 'c', 'l', 'a', 's',
      's', '=', '"', 't', 'a', 'b', 'R', '"', };
  /** the class colspan */
  private static final char[] TAB_CS = { ' ', 'c', 'o', 'l', 's', 'p',
      'a', 'n', '=', '"', };
  /** the class rowspan */
  private static final char[] TAB_RS = { ' ', 'r', 'o', 'w', 's', 'p',
      'a', 'n', '=', '"', };

  /**
   * Create a body cell of a table
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
  _XHTML10TableBodyCell(final _XHTML10TableBodyRow owner, final int cols,
      final int rows, final ETableCellDef[] def) {
    super(owner, cols, rows, def);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void appendLineBreak() {
    this.assertNoChildren();
    this.getTextOutput().append(XHTML10Driver.BR);
  }

  /**
   * print the cell mode
   * 
   * @param out
   *          the text output
   * @param cell
   *          the cell
   */
  static final void _cellMode(final ITextOutput out, final TableCell cell) {
    int i;

    looper: for (final ETableCellDef d : cell) {
      if (d == ETableCellDef.LEFT) {
        out.append(_XHTML10TableBodyCell.TAB_CLASS_L);
        break looper;
      }
      if (d == ETableCellDef.RIGHT) {
        out.append(_XHTML10TableBodyCell.TAB_CLASS_R);
        break looper;
      }
      if (d == ETableCellDef.CENTER) {
        out.append(_XHTML10TableBodyCell.TAB_CLASS_C);
        break looper;
      }
    }

    i = cell.getColumnSpan();
    if (i > 1) {
      out.append(_XHTML10TableBodyCell.TAB_CS);
      out.append(i);
      out.append('"');
    }

    i = cell.getRowSpan();
    if (i > 1) {
      out.append(_XHTML10TableBodyCell.TAB_RS);
      out.append(i);
      out.append('"');
    }

    out.append('>');
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
