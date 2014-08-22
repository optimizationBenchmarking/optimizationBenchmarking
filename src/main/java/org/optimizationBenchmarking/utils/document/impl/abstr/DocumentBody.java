package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.document.spec.IDocumentBody;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;

/**
 * The output object for the document body. The document body contains the
 * actual document text, sections, graphics, etc.
 */
public class DocumentBody extends _StyledElement implements IDocumentBody {

  /** the sub-section counter */
  private int m_subsectionCount;

  /**
   * Create a new document body
   * 
   * @param owner
   *          the owning document
   */
  public DocumentBody(final Document owner) {
    super(owner);
    this.m_subsectionCount = 0;
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
  public synchronized final Section section(final ILabel useLabel) {
    this.fsmFlagsAssertTrue(DocumentElement.STATE_ALIFE);
    return this.createSection(useLabel, ++this.m_subsectionCount);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void beforeChildOpens(
      final HierarchicalFSM child, final boolean hasOtherChildren) {

    super.beforeChildOpens(child, hasOtherChildren);

    if (child instanceof Section) {
      return;
    }

    this.throwChildNotAllowed(child);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void afterChildOpened(
      final HierarchicalFSM child, final boolean hasOtherChildren) {

    super.afterChildOpened(child, hasOtherChildren);

    if (child instanceof Section) {
      return;
    }
    this.throwChildNotAllowed(child);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void afterChildClosed(
      final HierarchicalFSM child) {

    super.afterChildClosed(child);

    if (!(child instanceof Section)) {
      this.throwChildNotAllowed(child);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void onClose() {
    this.fsmStateAssertAndSet(DocumentElement.STATE_ALIFE,
        DocumentElement.STATE_DEAD);
    if (this.m_subsectionCount <= 0) {
      throw new IllegalStateException(//
          "Document body must have at least one section."); //$NON-NLS-1$
    }
    super.onClose();
  }
}
