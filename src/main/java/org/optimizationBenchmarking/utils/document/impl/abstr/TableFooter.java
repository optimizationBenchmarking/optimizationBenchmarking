package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;

/**
 * A table footer.
 */
public class TableFooter extends TableSection {
  /**
   * Create a table footer
   *
   * @param owner
   *          the owning FSM
   */
  protected TableFooter(final Table owner) {
    super(owner);
  }

  /** {@inheritDoc} */
  @Override
  final TableFooterRow createRow() {
    return this.m_driver.createTableFooterRow(this);
  }

  /** {@inheritDoc} */
  @Override
  public final TableFooterRow row() {
    return ((TableFooterRow) (super.row()));
  }

  /** {@inheritDoc} */
  @Override
  protected final synchronized void beforeChildOpens(
      final HierarchicalFSM child, final boolean hasOtherChildren) {

    super.beforeChildOpens(child, hasOtherChildren);

    if (!(child instanceof TableFooterRow)) {
      this.throwChildNotAllowed(child);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final synchronized void afterChildOpened(
      final HierarchicalFSM child, final boolean hasOtherChildren) {

    super.afterChildOpened(child, hasOtherChildren);

    if (!(child instanceof TableFooterRow)) {
      this.throwChildNotAllowed(child);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final synchronized void afterChildClosed(
      final HierarchicalFSM child) {

    super.afterChildClosed(child);

    if (!(child instanceof TableFooterRow)) {
      this.throwChildNotAllowed(child);
    }
  }
}
