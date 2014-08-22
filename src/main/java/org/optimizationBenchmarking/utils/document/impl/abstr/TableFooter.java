package org.optimizationBenchmarking.utils.document.impl.abstr;

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
  public TableFooter(final Table owner) {
    super(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected TableFooterRow createRow(final int index, final int totalIndex) {
    return new TableFooterRow(this, index, totalIndex);
  }

  /** {@inheritDoc} */
  @Override
  public final TableFooterRow row() {
    return ((TableFooterRow) (super.row()));
  }
}
