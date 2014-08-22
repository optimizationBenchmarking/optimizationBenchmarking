package org.optimizationBenchmarking.utils.document.impl.abstr;

/**
 * A table cell
 */
public class TableCell extends ComplexText {

  /**
   * Create the table cell
   * 
   * @param owner
   *          the owning row
   */
  public TableCell(final TableRow owner) {
    super(owner, null, DocumentPart._plain(owner));
  }

  /** {@inheritDoc} */
  @Override
  protected TableRow getOwner() {
    return ((TableRow) (super.getOwner()));
  }
}
