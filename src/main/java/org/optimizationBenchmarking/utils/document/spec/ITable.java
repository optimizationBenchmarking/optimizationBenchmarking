package org.optimizationBenchmarking.utils.document.spec;

/**
 * A table.
 */
public interface ITable extends IDocumentElement, ILabeledObject {

  /**
   * Write the table caption
   *
   * @return the table caption
   */
  public abstract IComplexText caption();

  /**
   * Write the table header
   *
   * @return the table header
   */
  public abstract ITableSection header();

  /**
   * Write the table body
   *
   * @return the table body
   */
  public abstract ITableSection body();

  /**
   * Write the table footer
   *
   * @return the table footer
   */
  public abstract ITableSection footer();
}
