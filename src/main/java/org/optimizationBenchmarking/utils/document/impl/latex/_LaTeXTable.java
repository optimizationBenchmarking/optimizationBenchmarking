package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.Table;
import org.optimizationBenchmarking.utils.document.impl.abstr.TableCell;
import org.optimizationBenchmarking.utils.document.spec.ETableCellDef;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * <p>
 * A table in a LaTeX document.
 * </p>
 * <p>
 * TODO: Multi-row cells will not work properly. They should be rendered at
 * the bottom with negative rowspan, but are rendered at the top with
 * positive rowspan. See <a href=
 * "http://texblog.org/2014/05/19/coloring-multi-row-tables-in-latex/"
 * >here</a> and <a href=
 * "http://tex.stackexchange.com/questions/200075/table-white-spacing-with-rowcolor-multicolumn-and-multirow"
 * >here</a>.
 * </p>
 */
final class _LaTeXTable extends Table {

  /** a normal cell */
  static final int MODE_NORMAL = 0;
  /** a multi-column cell */
  static final int MODE_MULTI_COL = 1;
  /** a multi-row cell */
  static final int MODE_MULTI_ROW = 2;

  /** begin the table a */
  private static final char[] TABLE_A_BEGIN = { '\\', 'b', 'e', 'g', 'i',
      'n', '{', 't', 'a', 'b', 'l', 'e', '}', '[', 't', 'b', ']' };
  /** begin the table b */
  private static final char[] TABLE_A_END = { '\\', 'e', 'n', 'd', '{',
      't', 'a', 'b', 'l', 'e', '}' };
  /** end the table a */
  private static final char[] TABLE_B_BEGIN = { '\\', 'b', 'e', 'g', 'i',
      'n', '{', 't', 'a', 'b', 'l', 'e', '*', '}', '[', 't', 'b', ']' };
  /** end the table b */
  private static final char[] TABLE_B_END = { '\\', 'e', 'n', 'd', '{',
      't', 'a', 'b', 'l', 'e', '*', '}' };

  /** end the tabular */
  private static final char[] TABULAR_END = { '\\', 'e', 'n', 'd', '{',
      't', 'a', 'b', 'u', 'l', 'a', 'r', '}' };
  /** end small */
  private static final char[] SMALL_END = { '\\', 'e', 'n', 'd', '{', 's',
      'm', 'a', 'l', 'l', '}' };

  /** the hline */
  static final char[] HLINE = { '\\', 'h', 'l', 'i', 'n', 'e' };
  /** the multi-col */
  private static final char[] MULTI_COLUMN = { '\\', 'm', 'u', 'l', 't',
      'i', 'c', 'o', 'l', 'u', 'm', 'n', '{' };
  /** the first part of a multi-row */
  private static final char[] MULTI_ROW_1 = { '\\', 'm', 'u', 'l', 't',
      'i', 'r', 'o', 'w', '{' };
  /** the second part of a multi-row */
  private static final char[] MULTI_ROW_2 = { '}', '{', '*', '}', '{', };

  /** does this table span all columns */
  private boolean m_pageWide;

  /**
   * Create a table
   *
   * @param owner
   *          the owning section body
   * @param index
   *          the table index in the owning section
   * @param useLabel
   *          the label to use
   * @param spansAllColumns
   *          does the table span all columns
   * @param definition
   *          the table cell definition
   */
  _LaTeXTable(final _LaTeXSectionBody owner, final ILabel useLabel,
      final boolean spansAllColumns, final int index,
      final ETableCellDef[] definition) {
    super(owner, useLabel, spansAllColumns, index, definition);
    this.open();
  }

  /** {@inheritDoc} */
  @SuppressWarnings("resource")
  @Override
  protected synchronized final void onOpen() {
    final ITextOutput out;
    final LaTeXDocument doc;

    super.onOpen();

    doc = ((LaTeXDocument) (this.getDocument()));
    doc._registerTable();

    out = this.getTextOutput();
    LaTeXDriver._endLine(out);
    LaTeXDriver._endLine(out);

    this.m_pageWide = (this.spansAllColumns() && //
    (doc.m_class.getColumnCount() > 1));
    out.append(this.m_pageWide ? _LaTeXTable.TABLE_B_BEGIN
        : _LaTeXTable.TABLE_A_BEGIN);
    LaTeXDriver._endLine(out);

    out.append(LaTeXDriver.CENTER_BEGIN);
    LaTeXDriver._endLine(out);

    LaTeXDriver._endLine(out);
  }

  /**
   * translate a table cell definition into a cell mode sequence
   *
   * @param defs
   *          the table cell definitions
   * @param out
   *          the destination
   */
  static final void _appendCellDef(final Iterable<ETableCellDef> defs,
      final ITextOutput out) {
    char ch;

    for (final ETableCellDef def : defs) {
      switch (def) {
        case CENTER: {
          ch = 'c';
          break;
        }
        case RIGHT: {
          ch = 'r';
          break;
        }
        case LEFT: {
          ch = 'l';
          break;
        }
        case VERTICAL_SEPARATOR: {
          ch = '|';
          break;
        }
        default: {
          throw new IllegalArgumentException(//
              "Unknown table cell definition " //$NON-NLS-1$
                  + def);
        }
      }
      out.append(ch);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    final ITextOutput out;

    out = this.getTextOutput();

    out.append(_LaTeXTable.TABULAR_END);
    LaTeXDriver._endLine(out);
    out.append(_LaTeXTable.SMALL_END);
    LaTeXDriver._endLine(out);
    out.append(LaTeXDriver.CENTER_END);
    LaTeXDriver._endLine(out);
    out.append(this.m_pageWide ? _LaTeXTable.TABLE_B_END
        : _LaTeXTable.TABLE_A_END);
    LaTeXDriver._endLine(out);
    LaTeXDriver._endLine(out);

    super.onClose();
  }

  /**
   * begin a table cell
   *
   * @param cell
   *          the cell
   * @param out
   *          the output
   * @return the cell mode: {@code 0} = normal, {@code 1} = multi-col,
   *         {@code 2} = mult-row, {@code 3} = both multi-col and multi-row
   */
  static final int _beginCell(final TableCell cell, final ITextOutput out) {
    final int cols, rows;
    int mode;

    if (cell.getStartColumn() > 0) {
      out.append('&');
    }

    mode = _LaTeXTable.MODE_NORMAL;
    if (cell.hasIrregularDefinition()) {
      cols = cell.getColumnSpan();
      rows = cell.getRowSpan();

      if ((cols > 1) || ((cols <= 1) && (rows <= 1))) {
        out.append(_LaTeXTable.MULTI_COLUMN);
        out.append(cols);
        out.append('}');
        out.append('{');
        _LaTeXTable._appendCellDef(cell, out);
        out.append('}');
        out.append('{');
        mode |= _LaTeXTable.MODE_MULTI_COL;
      }
      if (rows > 1) {
        out.append(_LaTeXTable.MULTI_ROW_1);
        out.append(rows);
        out.append(_LaTeXTable.MULTI_ROW_2);
        mode |= _LaTeXTable.MODE_MULTI_ROW;
      }
    }
    return mode;
  }

  /**
   * end a table cell
   *
   * @param cell
   *          the cell
   * @param out
   *          the output
   */
  static final void _endCell(final TableCell cell, final ITextOutput out) {
    final int cols, rows;

    if (cell.hasIrregularDefinition()) {
      cols = cell.getColumnSpan();
      rows = cell.getRowSpan();

      if ((cols > 1) || ((cols <= 1) && (rows <= 1))) {
        out.append('}');
      }
      if (rows > 1) {
        out.append('}');
      }
    }
  }
}
