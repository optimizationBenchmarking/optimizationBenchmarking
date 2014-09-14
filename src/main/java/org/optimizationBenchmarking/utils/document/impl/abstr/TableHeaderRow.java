package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.document.spec.TableCellDef;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;

/**
 * A row in a table header
 */
public class TableHeaderRow extends TableRow {
  /**
   * Create the table header row
   * 
   * @param owner
   *          the owning table header
   */
  protected TableHeaderRow(final TableHeader owner) {
    super(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected TableHeader getOwner() {
    return ((TableHeader) (super.getOwner()));
  }

  /** {@inheritDoc} */
  @Override
  protected TableHeaderCell createCell(final int rowSpan,
      final int colSpan, final TableCellDef[] def) {
    return new TableHeaderCell(this, rowSpan, colSpan, def);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final TableHeaderCell cell(final int rowSpan,
      final int colSpan, final TableCellDef... definition) {
    return ((TableHeaderCell) (super.cell(rowSpan, colSpan, definition)));
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final TableCell cell() {
    return this.cell(1, 1, ((TableCellDef[]) null));
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void beforeChildOpens(
      final HierarchicalFSM child, final boolean hasOtherChildren) {

    super.beforeChildOpens(child, hasOtherChildren);

    if (!(child instanceof TableHeaderCell)) {
      this.throwChildNotAllowed(child);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void afterChildOpened(
      final HierarchicalFSM child, final boolean hasOtherChildren) {

    super.afterChildOpened(child, hasOtherChildren);

    if (!(child instanceof TableHeaderCell)) {
      this.throwChildNotAllowed(child);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void afterChildClosed(
      final HierarchicalFSM child) {

    super.afterChildClosed(child);

    if (!(child instanceof TableHeaderCell)) {
      this.throwChildNotAllowed(child);
    }
  }
}
