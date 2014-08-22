package org.optimizationBenchmarking.utils.document.impl.abstr;

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
}
