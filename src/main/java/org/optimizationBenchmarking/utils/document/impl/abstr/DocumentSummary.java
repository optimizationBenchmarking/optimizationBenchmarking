package org.optimizationBenchmarking.utils.document.impl.abstr;

/**
 * A summary of a document
 */
public class DocumentSummary extends Text {
  /**
   * create the summary
   * 
   * @param owner
   *          the owner
   */
  protected DocumentSummary(final DocumentHeader owner) {
    super(owner, null);
  }

  /**
   * Get the owning document header
   * 
   * @return the owning document header
   */
  @Override
  protected DocumentHeader getOwner() {
    return ((DocumentHeader) (super.getOwner()));
  }
}
