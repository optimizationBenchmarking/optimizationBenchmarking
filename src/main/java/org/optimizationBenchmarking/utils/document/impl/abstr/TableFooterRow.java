package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.document.spec.ETableCellDef;
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
   */
  protected TableFooterRow(final TableFooter owner) {
    super(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected TableFooter getOwner() {
    return ((TableFooter) (super.getOwner()));
  }

  /** {@inheritDoc} */
  @Override
  final TableFooterCell createCell(final int rowSpan, final int colSpan,
      final ETableCellDef[] def) {
    return this.m_driver
        .createTableFooterCell(this, rowSpan, colSpan, def);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final TableFooterCell cell(final int rowSpan,
      final int colSpan, final ETableCellDef... definition) {
    return ((TableFooterCell) (super.cell(rowSpan, colSpan, definition)));
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
