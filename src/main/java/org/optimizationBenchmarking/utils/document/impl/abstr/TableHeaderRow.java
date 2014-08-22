package org.optimizationBenchmarking.utils.document.impl.abstr;

/**
 * A row in a table header
 */
public class TableHeaderRow extends TableRow {
  /**
   * Create the table header row
   * 
   * @param owner
   *          the owning table header
   * @param index
   *          the index of the table row in its section
   * @param totalIndex
   *          the overall row index
   */
  protected TableHeaderRow(final TableHeader owner, final int index,
      final int totalIndex) {
    super(owner, index, totalIndex);
  }

  /** {@inheritDoc} */
  @Override
  protected TableHeader getOwner() {
    return ((TableHeader) (super.getOwner()));
  }
}
