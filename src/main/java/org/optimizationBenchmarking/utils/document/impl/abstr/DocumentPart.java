package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.document.spec.IDocumentPart;
import org.optimizationBenchmarking.utils.document.spec.IStyle;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;

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
   * @param out
   *          the output destination
   */
  protected DocumentPart(final HierarchicalFSM owner, final Appendable out) {
    super(owner, out);
    this.m_doc = ((owner instanceof Document) ? ((Document) owner)
        : (((DocumentPart) owner).m_doc));
  }

  /** {@inheritDoc} */
  @Override
  public final Document getDocument() {
    return this.m_doc;
  }

  /**
   * Obtain the default plain style
   * 
   * @param part
   *          the document part
   * @return the default plain style
   */
  static final IStyle _plain(final DocumentPart part) {
    return part.m_doc.plain();
  }

  /**
   * check a style
   * 
   * @param style
   *          the style
   */
  static final void _checkStyle(final IStyle style) {
    if (style == null) {
      throw new IllegalArgumentException("Style must not be null."); //$NON-NLS-1$
    }
  }

}
