package org.optimizationBenchmarking.utils.document.impl.abstr;

/**
 * A row in a table's body
 */
public class TableBodyRow extends TableRow {
  /**
   * Create the table body row
   * 
   * @param owner
   *          the owning table body
   * @param index
   *          the index of the table row in its section
   * @param totalIndex
   *          the overall row index
   */
  protected TableBodyRow(final TableBody owner, final int index,
      final int totalIndex) {
    super(owner, index, totalIndex);
  }

  /** {@inheritDoc} */
  @Override
  protected TableBody getOwner() {
    return ((TableBody) (super.getOwner()));
  }
}
