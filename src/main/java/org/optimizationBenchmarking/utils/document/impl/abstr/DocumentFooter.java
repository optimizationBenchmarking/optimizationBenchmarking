package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.bibliography.data.Bibliography;
import org.optimizationBenchmarking.utils.document.spec.IDocumentFooter;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.hierarchy.FSM;

/**
 * The output object for the document footer. The document footer contains
 * stuff such as appendices.
 */
public class DocumentFooter extends _StyledElement implements
    IDocumentFooter {

  /** a flag indicating that the citations have been taken */
  private static final int FLAG_CITATIONS_TAKEN = (FSM.FLAG_NOTHING + 1);

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
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    this.fsmFlagsAssertFalse(DocumentFooter.FLAG_CITATIONS_TAKEN);
    return this.createSection(useLabel, (++this.m_subsectionCount));
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
   * Process the bibliography
   * 
   * @param bib
   *          the bibliography
   */
  protected void processBibliography(final Bibliography bib) {
    //
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void onClose() {
    Bibliography bib;

    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        DocumentFooter.FLAG_CITATIONS_TAKEN,
        DocumentFooter.FLAG_CITATIONS_TAKEN, FSM.FLAG_NOTHING);

    bib = this.m_doc.m_citations.getResult();
    this.m_doc.m_citations.close();
    this.m_doc.m_citations = null;

    if ((bib != null) && (!(bib.isEmpty()))) {
      this.processBibliography(bib);
    }
    bib = null;

    super.onClose();
  }
}
