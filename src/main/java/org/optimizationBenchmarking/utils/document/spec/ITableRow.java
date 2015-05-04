package org.optimizationBenchmarking.utils.document.spec;

/** A table row */
public interface ITableRow extends IDocumentElement {

  /**
   * Create a cell which may span multiple rows or columns
   *
   * @param rowSpan
   *          the number of rows the cell spans
   * @param colSpan
   *          the number of columns the cell spans
   * @param definition
   *          the cell's definition
   * @return the text interface where the table cell's text can be written
   *         to
   */
  public abstract IComplexText cell(final int rowSpan, final int colSpan,
      final ETableCellDef... definition);

  /**
   * Create a cell
   *
   * @return the text interface where the table cell's text can be written
   *         to
   */
  public abstract IComplexText cell();
}
