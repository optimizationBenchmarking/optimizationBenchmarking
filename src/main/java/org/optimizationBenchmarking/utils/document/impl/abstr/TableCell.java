package org.optimizationBenchmarking.utils.document.impl.abstr;

import java.util.Arrays;

import org.optimizationBenchmarking.utils.collections.iterators.ArrayIterator;
import org.optimizationBenchmarking.utils.document.spec.TableCellDef;

/**
 * A table cell
 */
public class TableCell extends ComplexText implements
    Iterable<TableCellDef> {

  /** the start column (inclusive) */
  private final int m_startCol;
  /** the end column (exclusive) */
  private final int m_endCol;

  /** the start row of this cell (inclusive) */
  private final int m_startRow;
  /** the end row of this cell (exclusive) */
  private final int m_endRow;

  /** the table cell definition */
  private final TableCellDef[] m_def;

  /** the definition offset */
  private final int m_ofs;

  /** the definition size */
  private final int m_size;

  /** does the table cell has an own, irregular definition? */
  private final boolean m_hasIrregularDefinition;

  /**
   * Create the table cell
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
  @SuppressWarnings("resource")
  public TableCell(final TableRow owner, final int cols, final int rows,
      final TableCellDef[] def) {
    super(owner);

    final TableSection ts;
    final int[] blocked;
    final int cellDefCnt;
    int sc, ec, end, r;

    if (cols <= 0) {
      throw new IllegalArgumentException(//
          "A table cell must span at least one column."); //$NON-NLS-1$
    }
    if (rows <= 0) {
      throw new IllegalArgumentException(//
          "A table cell must span at least one row."); //$NON-NLS-1$
    }

    ts = owner.getOwner();
    sc = owner.m_nextCol;
    r = owner.m_index;
    blocked = ts.m_blocked;

    while (blocked[sc] >= r) {
      sc++;
      if (sc >= blocked.length) {
        throw new IllegalArgumentException(//
            "Start column of cell is outside table definition."); //$NON-NLS-1$
      }
    }

    ec = end = (sc + cols);
    if (ec > blocked.length) {
      throw new IllegalArgumentException(//
          "End column of cell is outside table definition."); //$NON-NLS-1$
    }

    for (; (--ec) >= sc;) {
      if (blocked[ec] >= r) {
        throw new IllegalArgumentException(//
            "Cell intersects with multi-row cell."); //$NON-NLS-1$
      }
    }

    this.m_startRow = r;
    r = ((r + rows) - 1);
    this.m_endRow = r;
    Arrays.fill(blocked, sc, end, r);
    owner.m_nextCol = end;

    if ((def != null) && (def.length > 0)) {
      if ((cellDefCnt = Table._checkDef(def)) > 1) {
        throw new IllegalArgumentException(//
            "Only single regular cells are permitted, but "//$NON-NLS-1$
                + def + " contains " + cellDefCnt); //$NON-NLS-1$
      }
      this.m_def = def;
      this.m_ofs = 0;
      this.m_size = def.length;
      this.m_hasIrregularDefinition = true;
    } else {
      if (((cols > 1) || (rows > 1))) {
        throw new IllegalArgumentException(//
            "Multi column or row cells must have a definition set."); //$NON-NLS-1$
      }

      this.m_def = ts.getOwner().m_cells;
      this.m_ofs = ts.m_cellToDef[sc];
      this.m_size = (((end < blocked.length) ? ts.m_cellToDef[end]
          : this.m_def.length) - this.m_ofs);
      this.m_hasIrregularDefinition = false;
    }
    this.m_startCol = (sc + 1);
    this.m_endCol = end;

  }

  /** {@inheritDoc} */
  @Override
  protected TableRow getOwner() {
    return ((TableRow) (super.getOwner()));
  }

  /**
   * Get the number of columns spanned by this cell:
   * <code>{@link #getEndColumn()}-{@link #getStartColumn()}</code>
   * 
   * @return the number of columns spanned by this cell
   * @see #getStartColumn()
   * @see #getEndColumn()
   */
  public final int getColumnSpan() {
    return (this.m_endCol - this.m_startCol);
  }

  /**
   * Get the start column index (inclusive, 0-based)
   * 
   * @return the start column index
   * @see #getColumnSpan()
   * @see #getEndColumn()
   */
  public final int getStartColumn() {
    return this.m_startCol;
  }

  /**
   * Get the end column index (exclusive, 0-based)
   * 
   * @return the end column index
   * @see #getColumnSpan()
   * @see #getStartColumn()
   */
  public final int getEndColumn() {
    return this.m_endCol;
  }

  /**
   * Get the number of rows spanned by this cell:
   * <code>{@link #getEndRow()}-{@link #getStartRow()}</code>
   * 
   * @return the number of rows spanned by this cell
   * @see #getStartRow()
   * @see #getEndRow()
   */
  public final int getRowSpan() {
    return (this.m_endRow - this.m_startRow);
  }

  /**
   * Get the start row index (inclusive, 1-based)
   * 
   * @return the start row index
   * @see #getRowSpan()
   * @see #getEndRow()
   */
  public final int getStartRow() {
    return this.m_startRow;
  }

  /**
   * Get the end row index (exclusive, 1-based)
   * 
   * @return the end row index
   * @see #getRowSpan()
   * @see #getStartRow()
   */
  public final int getEndRow() {
    return this.m_endRow;
  }

  /**
   * Iterate over the table cell definition elements
   * 
   * @return an iterator over the table cell definition elements
   */
  @Override
  public final ArrayIterator<TableCellDef> iterator() {
    return new ArrayIterator<>(this.m_def, this.m_size, this.m_ofs);
  }

  /**
   * Returns {@code true} if this table cells has an irregular definition,
   * that is, is not a normal table cell
   * 
   * @return {@code true} if this table cells has an irregular definition,
   *         that is, is not a normal table cell &mdash; {@code false}
   *         otherwise, i.e., if the cell is a normal table cell
   */
  public final boolean hasIrregularDefinition() {
    return this.m_hasIrregularDefinition;
  }
}
