package org.optimizationBenchmarking.utils.document.impl.abstr;

/** A cell in a table body */
public class TableBodyCell extends TableCell {
  /**
   * Create the table cell
   * 
   * @param owner
   *          the owning row
   */
  public TableBodyCell(final TableBodyRow owner) {
    super(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected TableBodyRow getOwner() {
    return ((TableBodyRow) (super.getOwner()));
  }
}
