package org.optimizationBenchmarking.utils.document.impl.abstr;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.bibliography.data.Bibliography;
import org.optimizationBenchmarking.utils.bibliography.data.BibliographyBuilder;
import org.optimizationBenchmarking.utils.document.spec.IDocumentBody;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.hierarchy.FSM;

/**
 * The output object for the document footer. The document footer contains
 * stuff such as appendices.
 */
public class DocumentFooter extends _StyleProviderPart implements
    IDocumentBody {

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

  /** {@inheritDoc} */
  @Override
  public synchronized final Section section(final ILabel useLabel) {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    this.fsmFlagsAssertFalse(DocumentFooter.FLAG_CITATIONS_TAKEN);
    return this.m_driver.createSection(this, useLabel,
        (++this.m_subsectionCount));
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
  protected void processCitations(final Bibliography bib) {
    //
  }

  /**
   * This method is invoked before the footer is closed
   */
  protected void doClose() {
    Logger log;
    Bibliography bib;
    BibliographyBuilder cb;

    if ((cb = this.m_doc.m_citations) != null) {
      bib = cb.getResult();
      cb.close();
      this.m_doc.m_citations = cb = null;

      if ((bib != null) && (!(bib.isEmpty()))) {

        log = this.m_doc.m_logger;
        if ((log != null) && (log.isLoggable(Level.FINER))) {
          log.finer("Begin processing citations of" + this.m_doc.__name()); //$NON-NLS-1$
        }

        this.processCitations(bib);

        log = this.m_doc.m_logger;
        if ((log != null) && (log.isLoggable(Level.FINER))) {
          log.finer("Finished processing citations of" + this.m_doc.__name()); //$NON-NLS-1$
        }

      }
      bib = null;
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final synchronized void onClose() {
    final Logger log;

    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        DocumentFooter.FLAG_CITATIONS_TAKEN,
        DocumentFooter.FLAG_CITATIONS_TAKEN, FSM.FLAG_NOTHING);

    this.doClose();

    super.onClose();

    log = this.m_doc.m_logger;
    if ((log != null) && (log.isLoggable(Level.FINER))) {
      log.finer("Finished writing footer of" + this.m_doc.__name()); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void onOpen() {
    final Logger log;

    super.onOpen();

    log = this.m_doc.m_logger;
    if ((log != null) && (log.isLoggable(Level.FINER))) {
      log.finer("Begin writing body of" + this.m_doc.__name()); //$NON-NLS-1$
    }
  }
}
