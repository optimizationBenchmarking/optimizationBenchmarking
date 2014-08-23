package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.document.spec.IStyle;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;

/**
 * A table header.
 */
public class TableHeader extends TableSection {
  /**
   * Create a table header
   * 
   * @param owner
   *          the owning FSM
   */
  public TableHeader(final Table owner) {
    super(owner);
  }

  /**
   * Get the header cell style
   * 
   * @param styles
   *          the styles
   * @return the style to use for header cells
   */
  protected IStyle headerCellStyle(final StyleSet styles) {
    return styles.emphasized();
  }

  /** {@inheritDoc} */
  @Override
  protected TableHeaderRow createRow(final int index, final int totalIndex) {
    return new TableHeaderRow(this, index, totalIndex);
  }

  /** {@inheritDoc} */
  @Override
  public final TableHeaderRow row() {
    return ((TableHeaderRow) (super.row()));
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void onClose() {
    if (this.m_rowCount <= 0) {
      throw new IllegalStateException(//
          "A table header must have at least one row."); //$NON-NLS-1$
    }
    super.onClose();
  }

  /** {@inheritDoc} */
  @Override
  protected final synchronized void beforeChildOpens(
      final HierarchicalFSM child, final boolean hasOtherChildren) {

    super.beforeChildOpens(child, hasOtherChildren);

    if (!(child instanceof TableHeaderRow)) {
      this.throwChildNotAllowed(child);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final synchronized void afterChildOpened(
      final HierarchicalFSM child, final boolean hasOtherChildren) {

    super.afterChildOpened(child, hasOtherChildren);

    if (!(child instanceof TableHeaderRow)) {
      this.throwChildNotAllowed(child);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final synchronized void afterChildClosed(
      final HierarchicalFSM child) {

    super.afterChildClosed(child);

    if (!(child instanceof TableHeaderRow)) {
      this.throwChildNotAllowed(child);
    }
  }
}
