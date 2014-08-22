package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.document.spec.IComplexText;
import org.optimizationBenchmarking.utils.document.spec.ITableRow;
import org.optimizationBenchmarking.utils.document.spec.TableCellDef;

/**
 * A table row
 */
public class TableRow extends DocumentPart implements ITableRow {

  /** the index of the table row in its section */
  private final int m_index;

  /** the overall table row index */
  private final int m_totalIndex;

  /**
   * Create the table row
   * 
   * @param owner
   *          the owning table section
   * @param index
   *          the index of the table row in its section
   * @param totalIndex
   *          the overall row index
   */
  protected TableRow(final TableSection owner, final int index,
      final int totalIndex) {
    super(owner, null);

    if (index < 0) {
      throw new IllegalArgumentException(//
          "Table row index cannot be less than zero, but is " + index); //$NON-NLS-1$
    }
    if (totalIndex < index) {
      throw new IllegalArgumentException(//
          "Table row total index cannot be less than row index, but " + //$NON-NLS-1$ 
              totalIndex + " is less than " + index); //$NON-NLS-1$
    }

    this.m_index = index;
    this.m_totalIndex = totalIndex;
  }

  /**
   * Get the index of this row within its section
   * 
   * @return the index of this row within its section
   */
  public final int getIndex() {
    return this.m_index;
  }

  /**
   * Get the total index of this row
   * 
   * @return the total index of this row
   */
  public final int getTotalIndex() {
    return this.m_totalIndex;
  }

  /** {@inheritDoc} */
  @Override
  protected TableSection getOwner() {
    return ((TableSection) (super.getOwner()));
  }

  /** {@inheritDoc} */
  @Override
  public IComplexText cell(final int rowSpan, final int colSpan,
      final TableCellDef... definition) {
    // TODO Auto-generated method stub
    return null;
  }

  /** {@inheritDoc} */
  @Override
  public IComplexText cell() {
    // TODO Auto-generated method stub
    return null;
  }
  
}
