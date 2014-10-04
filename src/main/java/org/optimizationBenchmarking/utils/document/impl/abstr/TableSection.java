package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.document.spec.ITableSection;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;

/**
 * A section of a table, such as a table body, header, or footer
 */
public abstract class TableSection extends DocumentPart implements
    ITableSection {

  /** the total number of rows */
  int m_rowCount;

  /** the blocked columns */
  final int[] m_blocked;

  /** the links between cell index and definitions */
  final int[] m_cellToDef;

  /** should we print a separator */
  private boolean m_separator;

  /**
   * Create a table section
   * 
   * @param owner
   *          the owning FSM
   */
  TableSection(final Table owner) {
    super(owner);
    this.m_blocked = owner.m_blocked;
    this.m_cellToDef = owner.m_cellToDef;
  }

  /**
   * Get the current number of rows in this table section
   * 
   * @return the current number of rows in this table section
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
   * @return the new row
   */
  abstract TableRow createRow();

  /** {@inheritDoc} */
  @SuppressWarnings("resource")
  @Override
  public TableRow row() {
    final Table t;

    t = this.getOwner();
    synchronized (t) {
      synchronized (this) {
        this.fsmStateAssert(DocumentElement.STATE_ALIFE);
        this.assertNoChildren();
        t.m_rowCount++;
        this.m_rowCount++;
        return this.createRow();
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void onClose() {
    for (final int b : this.m_blocked) {
      if (b != this.m_rowCount) {
        throw new IllegalStateException(//
            "Inconsistency of table cells row span allocation: Table section ends after row " //$NON-NLS-1$
                + this.m_rowCount + " but one cell spans to row " + b); //$NON-NLS-1$
      }
    }

    this.fsmStateAssertAndSet(DocumentElement.STATE_ALIFE,
        DocumentElement.STATE_DEAD);
    super.onClose();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void beforeChildOpens(
      final HierarchicalFSM child, final boolean hasOtherChildren) {

    super.beforeChildOpens(child, hasOtherChildren);

    if (!(child instanceof TableRow)) {
      this.throwChildNotAllowed(child);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void afterChildOpened(
      final HierarchicalFSM child, final boolean hasOtherChildren) {

    super.afterChildOpened(child, hasOtherChildren);

    if (!(child instanceof TableRow)) {
      this.throwChildNotAllowed(child);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void afterChildClosed(final HierarchicalFSM child) {

    super.afterChildClosed(child);

    if (!(child instanceof TableRow)) {
      this.throwChildNotAllowed(child);
    }
  }

  /** {@inheritDoc} */
  @Override
  public synchronized void separator() {
    this.m_separator = true;
  }

  /**
   * Get (and clear) the separator flag
   * 
   * @return {@code true} if a separator should be printed, {@code false}
   *         otherwise
   */
  protected synchronized final boolean shouldPrintSeparator() {
    final boolean ret;
    ret = this.m_separator;
    this.m_separator = false;
    return ret;
  }
}
