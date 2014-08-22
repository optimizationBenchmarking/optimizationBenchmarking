package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.document.spec.TableCellDef;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;

/**
 * A row in a table footer
 */
public class TableFooterRow extends TableRow {
  /**
   * Create the table footer row
   * 
   * @param owner
   *          the owning table footer
   * @param index
   *          the index of the table row in its section
   * @param totalIndex
   *          the overall row index
   */
  protected TableFooterRow(final TableFooter owner, final int index,
      final int totalIndex) {
    super(owner, index, totalIndex);
  }

  /** {@inheritDoc} */
  @Override
  protected TableFooter getOwner() {
    return ((TableFooter) (super.getOwner()));
  }

  /** {@inheritDoc} */
  @Override
  protected TableFooterCell createCell(final int rowSpan,
      final int colSpan, final TableCellDef[] def) {
    return new TableFooterCell(this, rowSpan, colSpan, def);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final TableFooterCell cell(final int rowSpan,
      final int colSpan, final TableCellDef... definition) {
    return ((TableFooterCell) (super.cell(rowSpan, colSpan, definition)));
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

    if (!(child instanceof TableFooterCell)) {
      this.throwChildNotAllowed(child);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void afterChildOpened(
      final HierarchicalFSM child, final boolean hasOtherChildren) {

    super.afterChildOpened(child, hasOtherChildren);

    if (!(child instanceof TableFooterCell)) {
      this.throwChildNotAllowed(child);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void afterChildClosed(
      final HierarchicalFSM child) {

    super.afterChildClosed(child);

    if (!(child instanceof TableFooterCell)) {
      this.throwChildNotAllowed(child);
    }
  }
}
