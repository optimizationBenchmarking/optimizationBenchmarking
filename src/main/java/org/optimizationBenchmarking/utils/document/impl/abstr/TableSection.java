package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.document.spec.ITableSection;

/**
 * A section of a table, such as a table body, header, or footer
 */
public class TableSection extends DocumentPart implements ITableSection {

  /** the total number of rows */
  int m_rowCount;

  /**
   * Create a table section
   * 
   * @param owner
   *          the owning FSM
   */
  protected TableSection(final Table owner) {
    super(owner, null);
  }

  /**
   * Get the number of rows
   * 
   * @return the number of rows
   */
  public final int getRowCount() {
    return this.m_rowCount;
  }

  /** {@inheritDoc} */
  @Override
  protected Table getOwner() {
    return ((Table) (super.getOwner()));
  }

  /**
   * Create a table row
   * 
   * @param index
   *          the row index
   * @param totalIndex
   *          the total index
   * @return the new row
   */
  protected TableRow createRow(final int index, final int totalIndex) {
    return new TableRow(this, index, totalIndex);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("resource")
  @Override
  public TableRow row() {
    final Table t;

    t = this.getOwner();
    synchronized (t) {
      synchronized (this) {
        this.fsmStateAssert(DocumentElement.STATE_ALIFE);
        return this.createRow((this.m_rowCount++), (t.m_rowCount++));
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void onClose() {
    this.fsmStateAssertAndSet(STATE_ALIFE, STATE_DEAD);
    super.onClose();
  }
}
