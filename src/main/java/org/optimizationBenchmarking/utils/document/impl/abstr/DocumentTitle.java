package org.optimizationBenchmarking.utils.document.impl.abstr;

/**
 * A title of a document
 */
public class DocumentTitle extends Text {
  /**
   * create the title
   * 
   * @param owner
   *          the owner
   */
  protected DocumentTitle(final DocumentHeader owner) {
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
