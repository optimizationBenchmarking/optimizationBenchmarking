package org.optimizationBenchmarking.utils.document.impl.abstr;

import java.nio.file.Path;

import org.optimizationBenchmarking.utils.document.spec.ELabelType;
import org.optimizationBenchmarking.utils.document.spec.IDocument;
import org.optimizationBenchmarking.utils.document.spec.IStyle;
import org.optimizationBenchmarking.utils.hierarchy.FSM;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * The root object for the document API.
 */
public abstract class Document extends DocumentElement implements
    IDocument {

  /** the state when the header has been created */
  private static final int STATE_HEADER_CREATED = (DocumentElement.STATE_MAX_ELEMENT + 1);
  /** the state before the header */
  private static final int STATE_HEADER_BEFORE_OPEN = (Document.STATE_HEADER_CREATED + 1);
  /** the state in the header */
  private static final int STATE_HEADER_OPENED = (Document.STATE_HEADER_BEFORE_OPEN + 1);
  /** the state after the header */
  private static final int STATE_HEADER_CLOSED = (Document.STATE_HEADER_OPENED + 1);
  /** the state when the body has been created */
  private static final int STATE_BODY_CREATED = (Document.STATE_HEADER_CLOSED + 1);
  /** the state before the body */
  private static final int STATE_BODY_BEFORE_OPEN = (Document.STATE_BODY_CREATED + 1);
  /** the state in the body */
  private static final int STATE_BODY_OPENED = (Document.STATE_BODY_BEFORE_OPEN + 1);
  /** the state after the body */
  private static final int STATE_BODY_CLOSED = (Document.STATE_BODY_OPENED + 1);
  /** the state when the footer has been created */
  private static final int STATE_FOOTER_CREATED = (Document.STATE_BODY_CLOSED + 1);
  /** the state before the footer */
  private static final int STATE_FOOTER_BEFORE_OPEN = (Document.STATE_FOOTER_CREATED + 1);
  /** the state in the footer */
  private static final int STATE_FOOTER_OPENED = (Document.STATE_FOOTER_BEFORE_OPEN + 1);
  /** the state after the footer */
  private static final int STATE_FOOTER_CLOSED = (Document.STATE_FOOTER_OPENED + 1);

  /** the state names */
  private static final String[] STATE_NAMES;

  static {
    STATE_NAMES = new String[Document.STATE_FOOTER_CLOSED + 1];
    Document.STATE_NAMES[Document.STATE_HEADER_CREATED] = "headerCreated"; //$NON-NLS-1$
    Document.STATE_NAMES[Document.STATE_HEADER_BEFORE_OPEN] = "headerBeforeOpen"; //$NON-NLS-1$
    Document.STATE_NAMES[Document.STATE_HEADER_OPENED] = "headerOpened"; //$NON-NLS-1$
    Document.STATE_NAMES[Document.STATE_HEADER_CLOSED] = "headerClosed"; //$NON-NLS-1$
    Document.STATE_NAMES[Document.STATE_BODY_CREATED] = "bodyCreated"; //$NON-NLS-1$
    Document.STATE_NAMES[Document.STATE_BODY_BEFORE_OPEN] = "bodyBeforeOpen"; //$NON-NLS-1$
    Document.STATE_NAMES[Document.STATE_BODY_OPENED] = "bodyOpened"; //$NON-NLS-1$
    Document.STATE_NAMES[Document.STATE_BODY_CLOSED] = "bodyClosed"; //$NON-NLS-1$
    Document.STATE_NAMES[Document.STATE_FOOTER_CREATED] = "footerCreated"; //$NON-NLS-1$
    Document.STATE_NAMES[Document.STATE_FOOTER_BEFORE_OPEN] = "footerBeforeOpen"; //$NON-NLS-1$
    Document.STATE_NAMES[Document.STATE_FOOTER_OPENED] = "footerOpened"; //$NON-NLS-1$
    Document.STATE_NAMES[Document.STATE_FOOTER_CLOSED] = "footerClosed"; //$NON-NLS-1$
  }

  /** the style set */
  final StyleSet m_styles;

  /** the label manager */
  final LabelManager m_manager;

  /** the base path, i.e., the folder containing the document */
  final Path m_basePath;

  /** the path to the document's main file */
  private final Path m_documentPath;

  /**
   * Create a document.
   * 
   * @param out
   *          the output destination
   * @param docPath
   *          the path to the document
   */
  protected Document(final Appendable out, final Path docPath) {
    super(null, out);
    this.m_styles = new StyleSet(this.createStyles());
    this.m_manager = new LabelManager();
    this.m_documentPath = docPath;
    this.m_basePath = docPath.getParent();
  }

  /**
   * Get the path to the document's main file
   * 
   * @return the path to the document's main file
   */
  public final Path getDocumentPath() {
    return this.m_documentPath;
  }

  /**
   * Get the path to the folder containing the document
   * 
   * @return the path to the folder containing the document
   */
  public final Path getDocumentFolder() {
    return this.m_basePath;
  }

  /** {@inheritDoc} */
  @Override
  protected void fsmStateAppendName(final int state,
      final MemoryTextOutput sb) {
    if ((state >= Document.STATE_HEADER_CREATED)
        && (state < Document.STATE_NAMES.length)) {
      sb.append(Document.STATE_NAMES[state]);
    } else {
      super.fsmStateAppendName(state, sb);
    }
  }

  /**
   * create the styles to be used by the document
   * 
   * @return the styles to be used by the document
   */
  protected abstract Styles createStyles();

  /**
   * obtain the style set for a given fsm
   * 
   * @param owner
   *          the owner
   * @return the style set
   */
  @SuppressWarnings("resource")
  static final StyleSet _getStyleSet(final HierarchicalFSM owner) {
    HierarchicalFSM f;

    f = owner;
    for (;;) {
      if (f instanceof DocumentElement) {
        if (f instanceof Document) {
          return ((Document) f).m_styles;
        }
        if (f instanceof ComplexText) {
          return ((ComplexText) f).m_styles;
        }
        if (f instanceof _StyledElement) {
          return ((_StyledElement) f).m_styles;
        }

        f = ((DocumentElement) f)._owner();
      } else {
        throw new IllegalStateException(//
            "Cannot find styles."); //$NON-NLS-1$
      }
    }
  }

  /**
   * Encode a text output
   * 
   * @param raw
   *          the raw text output
   * @return the encoded version
   */
  protected ITextOutput encode(final ITextOutput raw) {
    return raw;
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void onOpen() {
    super.onOpen();
    this.fsmStateAssertAndSet(FSM.STATE_NOTHING,
        DocumentElement.STATE_ALIFE);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final DocumentHeader header() {
    this.fsmStateAssertAndSet(DocumentElement.STATE_ALIFE,
        Document.STATE_HEADER_CREATED);
    return this.createHeader();
  }

  /**
   * Create the document header
   * 
   * @return the document header
   */
  protected DocumentHeader createHeader() {
    return new DocumentHeader(this);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final DocumentBody body() {
    this.fsmStateAssertAndSet(Document.STATE_HEADER_CLOSED,
        Document.STATE_BODY_CREATED);
    return this.createBody();
  }

  /**
   * Create the document body
   * 
   * @return the document body
   */
  protected DocumentBody createBody() {
    return new DocumentBody(this);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final DocumentFooter footer() {
    this.fsmStateAssertAndSet(Document.STATE_BODY_CLOSED,
        Document.STATE_FOOTER_CREATED);
    return this.createFooter();
  }

  /**
   * Create the document footer
   * 
   * @return the document footer
   */
  protected DocumentFooter createFooter() {
    return new DocumentFooter(this);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void beforeChildOpens(
      final HierarchicalFSM child, final boolean hasOtherChildren) {

    super.beforeChildOpens(child, hasOtherChildren);

    if (child instanceof DocumentHeader) {
      this.fsmStateAssertAndSet(Document.STATE_HEADER_CREATED,
          Document.STATE_HEADER_BEFORE_OPEN);
      return;
    }
    if (child instanceof DocumentBody) {
      this.fsmStateAssertAndSet(Document.STATE_HEADER_CREATED,
          Document.STATE_BODY_BEFORE_OPEN);
      return;
    }
    if (child instanceof DocumentFooter) {
      this.fsmStateAssertAndSet(Document.STATE_FOOTER_CREATED,
          Document.STATE_FOOTER_BEFORE_OPEN);
      return;
    }

    this.throwChildNotAllowed(child);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void afterChildOpened(
      final HierarchicalFSM child, final boolean hasOtherChildren) {

    super.afterChildOpened(child, hasOtherChildren);

    if (child instanceof DocumentHeader) {
      this.fsmStateAssertAndSet(Document.STATE_HEADER_BEFORE_OPEN,
          Document.STATE_HEADER_OPENED);
      return;
    }
    if (child instanceof DocumentBody) {
      this.fsmStateAssertAndSet(Document.STATE_BODY_BEFORE_OPEN,
          Document.STATE_BODY_OPENED);
      return;
    }
    if (child instanceof DocumentFooter) {
      this.fsmStateAssertAndSet(Document.STATE_FOOTER_BEFORE_OPEN,
          Document.STATE_FOOTER_OPENED);
      return;
    }
    this.throwChildNotAllowed(child);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void afterChildClosed(
      final HierarchicalFSM child) {

    super.afterChildClosed(child);

    if (child instanceof DocumentHeader) {
      this.fsmStateAssertAndSet(Document.STATE_HEADER_OPENED,
          Document.STATE_HEADER_CLOSED);
      return;
    }
    if (child instanceof DocumentBody) {
      this.fsmStateAssertAndSet(Document.STATE_BODY_OPENED,
          Document.STATE_BODY_CLOSED);
      return;
    }
    if (child instanceof DocumentFooter) {
      this.fsmStateAssertAndSet(Document.STATE_FOOTER_OPENED,
          Document.STATE_FOOTER_CLOSED);
      return;
    }
    this.throwChildNotAllowed(child);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void onClose() {
    this.fsmStateAssertAndSet(Document.STATE_FOOTER_CLOSED,
        DocumentElement.STATE_DEAD);
    super.onClose();
  }

  /** {@inheritDoc} */
  @Override
  public final IStyle createTextStyle() {
    return this.m_styles.createTextStyle();
  }

  /** {@inheritDoc} */
  @Override
  public final IStyle createGraphicalStyle() {
    return this.m_styles.createGraphicalStyle();
  }

  /** {@inheritDoc} */
  @Override
  public final IStyle emphasized() {
    return this.m_styles.emphasized();
  }

  /** {@inheritDoc} */
  @Override
  public final IStyle plain() {
    return this.m_styles.plain();
  }

  /** {@inheritDoc} */
  @Override
  public final Label createLabel(final ELabelType type) {
    return this.m_manager.createLabel(type);
  }
}
