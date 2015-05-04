package org.optimizationBenchmarking.utils.document.spec;

/**
 * The interface to create code sections
 */
public interface ICode extends IDocumentElement, ILabeledObject {

  /**
   * Write the code caption
   *
   * @return the complex text to write the code caption
   */
  public abstract IComplexText caption();

  /**
   * The code body
   *
   * @return the code body
   */
  public abstract IText body();
}
