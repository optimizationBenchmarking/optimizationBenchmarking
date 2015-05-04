package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.document.spec.ETableCellDef;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;

/**
 * A row in a table's body
 */
public class TableBodyRow extends TableRow {
  /**
   * Create the table body row
   *
   * @param owner
   *          the owning table body
   */
  protected TableBodyRow(final TableBody owner) {
    super(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected TableBody getOwner() {
    return ((TableBody) (super.getOwner()));
  }

  /** {@inheritDoc} */
  @Override
  final TableBodyCell createCell(final int rowSpan, final int colSpan,
      final ETableCellDef[] def) {
    return this.m_driver.createTableBodyCell(this, rowSpan, colSpan, def);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final TableBodyCell cell(final int rowSpan,
      final int colSpan, final ETableCellDef... definition) {
    return ((TableBodyCell) (super.cell(rowSpan, colSpan, definition)));
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final TableCell cell() {
    return this.cell(1, 1, ((ETableCellDef[]) null));
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void beforeChildOpens(
      final HierarchicalFSM child, final boolean hasOtherChildren) {

    super.beforeChildOpens(child, hasOtherChildren);

    if (!(child instanceof TableBodyCell)) {
      this.throwChildNotAllowed(child);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void afterChildOpened(
      final HierarchicalFSM child, final boolean hasOtherChildren) {

    super.afterChildOpened(child, hasOtherChildren);

    if (!(child instanceof TableBodyCell)) {
      this.throwChildNotAllowed(child);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void afterChildClosed(
      final HierarchicalFSM child) {

    super.afterChildClosed(child);

    if (!(child instanceof TableBodyCell)) {
      this.throwChildNotAllowed(child);
    }
  }
}
