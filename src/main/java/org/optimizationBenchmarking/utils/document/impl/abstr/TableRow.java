package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.document.spec.ITableRow;
import org.optimizationBenchmarking.utils.document.spec.TableCellDef;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;

/**
 * A table row
 */
public class TableRow extends DocumentPart implements ITableRow {

  /** the index of the table row in its section */
  final int m_index;

  /** the overall table row index */
  final int m_totalIndex;

  /** the next column */
  int m_nextCol;

  /**
   * Create the table row
   * 
   * @param owner
   *          the owning table section
   * @param index
   *          the index of the table row in its section
   * @param totalIndex
   *          the overall row index
   */
  protected TableRow(final TableSection owner, final int index,
      final int totalIndex) {
    super(owner, null);

    if (index < 0) {
      throw new IllegalArgumentException(//
          "Table row index cannot be less than zero, but is " + index); //$NON-NLS-1$
    }
    if (totalIndex < index) {
      throw new IllegalArgumentException(//
          "Table row total index cannot be less than row index, but " + //$NON-NLS-1$ 
              totalIndex + " is less than " + index); //$NON-NLS-1$
    }

    this.m_index = index;
    this.m_totalIndex = totalIndex;
  }

  /**
   * Get the index of this row within its section (1-based)
   * 
   * @return the index of this row within its section
   */
  public final int getIndex() {
    return this.m_index;
  }

  /**
   * Get the total index of this row (1-based)
   * 
   * @return the total index of this row
   */
  public final int getTotalIndex() {
    return this.m_totalIndex;
  }

  /** {@inheritDoc} */
  @Override
  protected TableSection getOwner() {
    return ((TableSection) (super.getOwner()));
  }

  /**
   * Create a new table cell
   * 
   * @param rowSpan
   *          the number of rows the cell should span
   * @param colSpan
   *          the number of columns the cell should span
   * @param def
   *          the table cell definition array
   * @return the cell
   */
  protected TableCell createCell(final int rowSpan, final int colSpan,
      final TableCellDef[] def) {
    return new TableCell(this, rowSpan, colSpan, def);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized TableCell cell(final int rowSpan, final int colSpan,
      final TableCellDef... definition) {
    this.fsmStateAssert(STATE_ALIFE);
    return this.createCell(rowSpan, colSpan,//
        (((definition == null) || (definition.length <= 0)) ? null
            : definition));
  }

  /** {@inheritDoc} */
  @Override
  public synchronized TableCell cell() {
    return this.cell(1, 1, ((TableCellDef[]) null));
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void onClose() {
    final int[] b;

    b = this.getOwner().m_blocked;
    if (this.m_nextCol < b.length) {
      throw new IllegalStateException("Row should have " + b.length + //$NON-NLS-1$
          " cells, but has only " + this.m_nextCol); //$NON-NLS-1$
    }
    super.onClose();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized  void beforeChildOpens(
      final HierarchicalFSM child, final boolean hasOtherChildren) {

    super.beforeChildOpens(child, hasOtherChildren);

    if (!(child instanceof TableCell)) {
      this.throwChildNotAllowed(child);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized  void afterChildOpened(
      final HierarchicalFSM child, final boolean hasOtherChildren) {

    super.afterChildOpened(child, hasOtherChildren);

    if (!(child instanceof TableCell)) {
      this.throwChildNotAllowed(child);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized  void afterChildClosed(
      final HierarchicalFSM child) {

    super.afterChildClosed(child);

    if (!(child instanceof TableCell)) {
      this.throwChildNotAllowed(child);
    }
  }
}
