package org.optimizationBenchmarking.utils.document.impl.abstr;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.document.spec.IDocumentBody;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;

/**
 * The output object for the document body. The document body contains the
 * actual document text, sections, graphics, etc.
 */
public class DocumentBody extends _StyleProviderPart implements
IDocumentBody {

  /** the sub-section counter */
  private int m_subsectionCount;

  /**
   * Create a new document body
   *
   * @param owner
   *          the owning document
   */
  protected DocumentBody(final Document owner) {
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

  /** {@inheritDoc} */
  @Override
  public synchronized final Section section(final ILabel useLabel) {
    this.fsmStateAssert(DocumentElement.STATE_ALIFE);
    return this.m_driver.createSection(this, useLabel,
        (++this.m_subsectionCount));
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
  protected synchronized void onOpen() {
    final Logger log;

    super.onOpen();

    log = this.m_doc.m_logger;
    if ((log != null) && (log.isLoggable(Level.FINER))) {
      log.finer("Begin writing body of" + this.m_doc.__name()); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void onClose() {
    final Logger log;

    this.fsmStateAssertAndSet(DocumentElement.STATE_ALIFE,
        DocumentElement.STATE_DEAD);

    if (this.m_subsectionCount <= 0) {
      throw new IllegalStateException(//
          "Document body must have at least one section."); //$NON-NLS-1$
    }
    super.onClose();

    log = this.m_doc.m_logger;
    if ((log != null) && (log.isLoggable(Level.FINER))) {
      log.finer("Finished writing body of" + this.m_doc.__name()); //$NON-NLS-1$
    }
  }
}
