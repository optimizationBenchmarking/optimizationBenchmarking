package org.optimizationBenchmarking.utils.document.impl.abstr;

/**
 * A cell in a table footer
 */
public class TableFooterCell extends TableCell {
  /**
   * Create the table cell
   * 
   * @param owner
   *          the owning row
   */
  public TableFooterCell(final TableFooterRow owner) {
    super(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected TableFooterRow getOwner() {
    return ((TableFooterRow) (super.getOwner()));
  }
}
