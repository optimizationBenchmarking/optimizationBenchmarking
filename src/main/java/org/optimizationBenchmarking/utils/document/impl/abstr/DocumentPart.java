package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.document.spec.IDocumentPart;
import org.optimizationBenchmarking.utils.graphics.style.IStyle;

/**
 * The base object for the document API.
 */
public abstract class DocumentPart extends DocumentElement implements
    IDocumentPart {

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

  /** {@inheritDoc} */
  @Override
  public final Document getDocument() {
    return this.m_doc;
  }

  /** {@inheritDoc} */
  @Override
  protected void styleUsed(final IStyle style) {
    this.m_doc.styleUsed(style);
  }
}
