package org.optimizationBenchmarking.utils.document.spec;

/**
 * A part of a document
 */
public interface IDocumentPart extends IDocumentElement {

  /**
   * Access the owning document interface
   * 
   * @return the owning document interface
   */
  public abstract IDocument getDocument();

}
