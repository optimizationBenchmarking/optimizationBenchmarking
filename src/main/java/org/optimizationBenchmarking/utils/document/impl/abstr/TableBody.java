package org.optimizationBenchmarking.utils.document.impl.abstr;

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
  public TableBody(final Table owner) {
    super(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected TableBodyRow createRow(final int index, final int totalIndex) {
    return new TableBodyRow(this, index, totalIndex);
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
}
