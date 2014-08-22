package org.optimizationBenchmarking.utils.document.impl.abstr;

/**
 * A row in a table footer
 */
public class TableFooterRow extends TableRow {
  /**
   * Create the table footer row
   * 
   * @param owner
   *          the owning table footer
   * @param index
   *          the index of the table row in its section
   * @param totalIndex
   *          the overall row index
   */
  protected TableFooterRow(final TableFooter owner, final int index,
      final int totalIndex) {
    super(owner, index, totalIndex);
  }

  /** {@inheritDoc} */
  @Override
  protected TableFooter getOwner() {
    return ((TableFooter) (super.getOwner()));
  }
}
