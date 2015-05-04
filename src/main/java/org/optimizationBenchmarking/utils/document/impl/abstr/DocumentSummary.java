package org.optimizationBenchmarking.utils.document.impl.abstr;

/**
 * A summary of a document
 */
public class DocumentSummary extends PlainText {
  /**
   * create the summary
   *
   * @param owner
   *          the owner
   */
  protected DocumentSummary(final DocumentHeader owner) {
    super(owner);
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
