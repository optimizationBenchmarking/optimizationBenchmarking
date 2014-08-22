package org.optimizationBenchmarking.utils.document.spec;

/**
 * A section of a table, such as the table header, body, or footer.
 */
public interface ITableSection extends IDocumentPart {

  /**
   * create a new table row
   * 
   * @return the table row to write to
   */
  public abstract ITableRow row();

}
