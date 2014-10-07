package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.graphics.style.IStyle;

/**
 * The base object for the document API.
 */
public abstract class DocumentPart extends DocumentElement {

  /** the document */
  final Document m_doc;

  /**
   * Create a document part.
   * 
   * @param owner
   *          the owning FSM
   */
  DocumentPart(final DocumentElement owner) {
    super(owner);
    this.m_doc = ((owner instanceof Document) ? ((Document) owner)
        : (((DocumentPart) owner).m_doc));
  }

  /**
   * Get the document
   * 
   * @return the document
   */
  protected final Document getDocument() {
    return this.m_doc;
  }

  /** {@inheritDoc} */
  @Override
  protected void styleUsed(final IStyle style) {
    this.m_doc.styleUsed(style);
  }
}
