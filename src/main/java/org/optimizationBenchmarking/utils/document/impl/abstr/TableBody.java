package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;

/**
 * The body of a table
 */
public class TableBody extends TableSection {
  /**
   * Create a table body
   *
   * @param owner
   *          the owning FSM
   */
  protected TableBody(final Table owner) {
    super(owner);
  }

  /** {@inheritDoc} */
  @Override
  final TableBodyRow createRow() {
    return this.m_driver.createTableBodyRow(this);
  }

  /** {@inheritDoc} */
  @Override
  public final TableBodyRow row() {
    return ((TableBodyRow) (super.row()));
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void onClose() {
    if (this.m_rowCount <= 0) {
      throw new IllegalStateException(//
          "A table body must have at least one row."); //$NON-NLS-1$
    }
    super.onClose();
  }

  /** {@inheritDoc} */
  @Override
  protected final synchronized void beforeChildOpens(
      final HierarchicalFSM child, final boolean hasOtherChildren) {

    super.beforeChildOpens(child, hasOtherChildren);

    if (!(child instanceof TableBodyRow)) {
      this.throwChildNotAllowed(child);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final synchronized void afterChildOpened(
      final HierarchicalFSM child, final boolean hasOtherChildren) {

    super.afterChildOpened(child, hasOtherChildren);

    if (!(child instanceof TableBodyRow)) {
      this.throwChildNotAllowed(child);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final synchronized void afterChildClosed(
      final HierarchicalFSM child) {

    super.afterChildClosed(child);

    if (!(child instanceof TableBodyRow)) {
      this.throwChildNotAllowed(child);
    }
  }
}
