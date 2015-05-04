package org.optimizationBenchmarking.utils.document.spec;

/**
 * A list
 */
public interface IList extends IDocumentElement {

  /**
   * Create a new list item to write to
   *
   * @return the new list item to write to
   */
  public abstract IStructuredText item();

}
