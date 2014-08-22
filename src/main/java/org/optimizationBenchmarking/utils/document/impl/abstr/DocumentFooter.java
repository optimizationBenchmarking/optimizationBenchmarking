package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.document.spec.IDocumentFooter;
import org.optimizationBenchmarking.utils.document.spec.ILabel;

/**
 * The output object for the document footer. The document footer contains
 * stuff such as appendices.
 */
public class DocumentFooter extends _StyledElement implements
    IDocumentFooter {
  /** the sub-section counter */
  private int m_subsectionCount;

  /**
   * Create a new document body
   * 
   * @param owner
   *          the owning document
   */
  protected DocumentFooter(final Document owner) {
    super(owner);
  }

  /**
   * Create the section
   * 
   * @param useLabel
   *          the label to use
   * @param index
   *          the index
   * @return the section
   */
  protected Section createSection(final ILabel useLabel, final int index) {
    return new Section(this, useLabel, index);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final Section appendix(final ILabel useLabel) {
    this.fsmFlagsAssertTrue(DocumentElement.STATE_ALIFE);
    return this.createSection(useLabel, ++this.m_subsectionCount);
  }

  /**
   * Get the owning document
   * 
   * @return the owning document
   */
  @Override
  protected Document getOwner() {
    return ((Document) (super.getOwner()));
  }
}
