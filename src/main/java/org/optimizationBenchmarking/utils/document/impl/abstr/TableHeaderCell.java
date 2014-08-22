package org.optimizationBenchmarking.utils.document.impl.abstr;

/**
 * A cell in a table header
 */
public class TableHeaderCell extends TableCell {
  /**
   * Create the table cell
   * 
   * @param owner
   *          the owning row
   */
  public TableHeaderCell(final TableHeaderRow owner) {
    super(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected TableHeaderRow getOwner() {
    return ((TableHeaderRow) (super.getOwner()));
  }
}