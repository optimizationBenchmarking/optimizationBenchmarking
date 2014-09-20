package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.bibliography.data.BibAuthors;
import org.optimizationBenchmarking.utils.bibliography.data.BibAuthorsBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibDate;
import org.optimizationBenchmarking.utils.bibliography.data.BibDateBuilder;
import org.optimizationBenchmarking.utils.document.spec.IDocumentHeader;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * The output object for document header information. The header contains
 * information such as the title or authors of the document.
 */
public class DocumentHeader extends DocumentPart implements
    IDocumentHeader {

  /** an title has been created */
  private static final int STATE_TITLE_CREATED = (DocumentElement.STATE_MAX_ELEMENT + 1);
  /** a title is about to be opened */
  private static final int STATE_TITLE_BEFORE_OPEN = (DocumentHeader.STATE_TITLE_CREATED + 1);
  /** a title has been opened */
  private static final int STATE_TITLE_AFTER_OPEN = (DocumentHeader.STATE_TITLE_BEFORE_OPEN + 1);
  /** a title has been closed */
  private static final int STATE_TITLE_CLOSED = (DocumentHeader.STATE_TITLE_AFTER_OPEN + 1);

  /** an authors builder has been created */
  private static final int STATE_AUTHORS_BUILDER_CREATED = (DocumentHeader.STATE_TITLE_CLOSED + 1);
  /** an authors builder is about to be opened */
  private static final int STATE_AUTHORS_BUILDER_BEFORE_OPEN = (DocumentHeader.STATE_AUTHORS_BUILDER_CREATED + 1);
  /** an authors builder has been opened */
  private static final int STATE_AUTHORS_BUILDER_AFTER_OPEN = (DocumentHeader.STATE_AUTHORS_BUILDER_BEFORE_OPEN + 1);
  /** an authors builder has been closed */
  private static final int STATE_AUTHORS_BUILDER_CLOSED = (DocumentHeader.STATE_AUTHORS_BUILDER_AFTER_OPEN + 1);
  /** we are after the authors */
  private static final int STATE_AUTHORS_AFTER = (DocumentHeader.STATE_AUTHORS_BUILDER_CLOSED + 1);

  /** an date builder has been created */
  private static final int STATE_DATE_BUILDER_CREATED = (DocumentHeader.STATE_AUTHORS_AFTER + 1);
  /** an date builder is about to be opened */
  private static final int STATE_DATE_BUILDER_BEFORE_OPEN = (DocumentHeader.STATE_DATE_BUILDER_CREATED + 1);
  /** an date builder has been opened */
  private static final int STATE_DATE_BUILDER_AFTER_OPEN = (DocumentHeader.STATE_DATE_BUILDER_BEFORE_OPEN + 1);
  /** an date builder has been closed */
  private static final int STATE_DATE_BUILDER_CLOSED = (DocumentHeader.STATE_DATE_BUILDER_AFTER_OPEN + 1);
  /** we are after the date */
  private static final int STATE_DATE_AFTER = (DocumentHeader.STATE_DATE_BUILDER_CLOSED + 1);

  /** an summary has been created */
  private static final int STATE_SUMMARY_CREATED = (DocumentHeader.STATE_DATE_AFTER + 1);
  /** a summary is about to be opened */
  private static final int STATE_SUMMARY_BEFORE_OPEN = (DocumentHeader.STATE_SUMMARY_CREATED + 1);
  /** a summary has been opened */
  private static final int STATE_SUMMARY_AFTER_OPEN = (DocumentHeader.STATE_SUMMARY_BEFORE_OPEN + 1);
  /** a summary has been closed */
  private static final int STATE_SUMMARY_CLOSED = (DocumentHeader.STATE_SUMMARY_AFTER_OPEN + 1);

  /** the header has been finalized */
  private static final int STATE_HEADER_FINALIZED = (DocumentHeader.STATE_SUMMARY_CLOSED + 1);

  /** the state names */
  private static final String[] STATE_NAMES;

  static {
    STATE_NAMES = new String[DocumentHeader.STATE_HEADER_FINALIZED + 1];
    DocumentHeader.STATE_NAMES[DocumentHeader.STATE_TITLE_CREATED] = "titleCreated"; //$NON-NLS-1$
    DocumentHeader.STATE_NAMES[DocumentHeader.STATE_TITLE_BEFORE_OPEN] = "titleOpened"; //$NON-NLS-1$
    DocumentHeader.STATE_NAMES[DocumentHeader.STATE_TITLE_AFTER_OPEN] = "titleOpened"; //$NON-NLS-1$
    DocumentHeader.STATE_NAMES[DocumentHeader.STATE_TITLE_CLOSED] = "titleClosed"; //$NON-NLS-1$
    DocumentHeader.STATE_NAMES[DocumentHeader.STATE_AUTHORS_BUILDER_CREATED] = "authorsBuilderCreated"; //$NON-NLS-1$
    DocumentHeader.STATE_NAMES[DocumentHeader.STATE_AUTHORS_BUILDER_BEFORE_OPEN] = "authorsBuilderBeforeOpen"; //$NON-NLS-1$
    DocumentHeader.STATE_NAMES[DocumentHeader.STATE_AUTHORS_BUILDER_AFTER_OPEN] = "authorsBuilderOpened"; //$NON-NLS-1$
    DocumentHeader.STATE_NAMES[DocumentHeader.STATE_AUTHORS_BUILDER_CLOSED] = "authorsBuilderClosed"; //$NON-NLS-1$
    DocumentHeader.STATE_NAMES[DocumentHeader.STATE_AUTHORS_AFTER] = "authorsAfter"; //$NON-NLS-1$
    DocumentHeader.STATE_NAMES[DocumentHeader.STATE_DATE_BUILDER_CREATED] = "dateBuilderCreated"; //$NON-NLS-1$
    DocumentHeader.STATE_NAMES[DocumentHeader.STATE_DATE_BUILDER_BEFORE_OPEN] = "dateBuilderBeforeOpen"; //$NON-NLS-1$
    DocumentHeader.STATE_NAMES[DocumentHeader.STATE_DATE_BUILDER_AFTER_OPEN] = "dateBuilderOpened"; //$NON-NLS-1$
    DocumentHeader.STATE_NAMES[DocumentHeader.STATE_DATE_BUILDER_CLOSED] = "dateBuilderClosed"; //$NON-NLS-1$
    DocumentHeader.STATE_NAMES[DocumentHeader.STATE_DATE_AFTER] = "dateAfter"; //$NON-NLS-1$
    DocumentHeader.STATE_NAMES[DocumentHeader.STATE_SUMMARY_CREATED] = "summaryCreated"; //$NON-NLS-1$
    DocumentHeader.STATE_NAMES[DocumentHeader.STATE_SUMMARY_BEFORE_OPEN] = "summaryBeforeOpen"; //$NON-NLS-1$
    DocumentHeader.STATE_NAMES[DocumentHeader.STATE_SUMMARY_AFTER_OPEN] = "summaryOpened"; //$NON-NLS-1$
    DocumentHeader.STATE_NAMES[DocumentHeader.STATE_SUMMARY_CLOSED] = "summaryClosed"; //$NON-NLS-1$
    DocumentHeader.STATE_NAMES[DocumentHeader.STATE_HEADER_FINALIZED] = "headerFinalized"; //$NON-NLS-1$  
  }

  /**
   * Create a new document header
   * 
   * @param owner
   *          the owning document
   */
  protected DocumentHeader(final Document owner) {
    super(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected void fsmStateAppendName(final int state,
      final MemoryTextOutput sb) {
    if ((state >= DocumentHeader.STATE_TITLE_CREATED)
        && (state < DocumentHeader.STATE_NAMES.length)) {
      sb.append(DocumentHeader.STATE_NAMES[state]);
    } else {
      super.fsmStateAppendName(state, sb);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final synchronized DocumentTitle title() {
    this.fsmStateAssertAndSet(DocumentElement.STATE_ALIFE,
        DocumentHeader.STATE_TITLE_CREATED);
    return this.m_driver.createDocumentTitle(this);
  }

  /**
   * Set the authors
   * 
   * @param authors
   *          the authors
   */
  public final synchronized void authors(final BibAuthors authors) {
    this.fsmStateAssertAndSet(DocumentHeader.STATE_TITLE_CLOSED,
        DocumentHeader.STATE_AUTHORS_BUILDER_CLOSED);
    this.doAuthors(authors);
  }

  /**
   * Set the authors
   * 
   * @param authors
   *          the authors
   */
  protected void doAuthors(final BibAuthors authors) {
    this.fsmStateAssertAndSet(DocumentHeader.STATE_AUTHORS_BUILDER_CLOSED,
        DocumentHeader.STATE_AUTHORS_AFTER);
    if (authors == null) {
      throw new IllegalArgumentException(//
          "Author data must not be null."); //$NON-NLS-1$
    }
    if (authors.isEmpty()) {
      throw new IllegalArgumentException(//
          "Author data must not be empty, i.e., there must be at least one author."); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  public final synchronized BibAuthorsBuilder authors() {
    this.fsmStateAssertAndSet(DocumentHeader.STATE_TITLE_CLOSED,
        DocumentHeader.STATE_AUTHORS_BUILDER_CREATED);
    return new BibAuthorsBuilder(this);
  }

  /**
   * Set the date
   * 
   * @param date
   *          the date
   */
  public final synchronized void date(final BibDate date) {
    this.fsmStateAssertAndSet(DocumentHeader.STATE_AUTHORS_AFTER,
        DocumentHeader.STATE_DATE_BUILDER_CLOSED);
    this.doDate(date);
  }

  /**
   * Set the date
   * 
   * @param date
   *          the date
   */
  protected void doDate(final BibDate date) {
    this.fsmStateAssertAndSet(DocumentHeader.STATE_DATE_BUILDER_CLOSED,
        DocumentHeader.STATE_DATE_AFTER);
    if (date == null) {
      throw new IllegalArgumentException(//
          "Date must not be null."); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  public final synchronized BibDateBuilder date() {
    this.fsmStateAssertAndSet(DocumentHeader.STATE_AUTHORS_AFTER,
        DocumentHeader.STATE_DATE_BUILDER_CREATED);
    return new BibDateBuilder(this);
  }

  /** {@inheritDoc} */
  @Override
  public final synchronized DocumentSummary summary() {
    this.fsmStateAssertAndSet(DocumentHeader.STATE_DATE_AFTER,
        DocumentHeader.STATE_SUMMARY_CREATED);
    return this.m_driver.createDocumentSummary(this);
  }

  /** {@inheritDoc} */
  @Override
  protected final synchronized void beforeChildOpens(
      final HierarchicalFSM child, final boolean hasOtherChildren) {

    super.beforeChildOpens(child, hasOtherChildren);

    if (child instanceof DocumentTitle) {
      this.fsmStateAssertAndSet(DocumentHeader.STATE_TITLE_CREATED,
          DocumentHeader.STATE_TITLE_BEFORE_OPEN);
      return;
    }

    if (child instanceof BibAuthorsBuilder) {
      this.fsmStateAssertAndSet(
          DocumentHeader.STATE_AUTHORS_BUILDER_CREATED,
          DocumentHeader.STATE_AUTHORS_BUILDER_BEFORE_OPEN);
      return;
    }

    if (child instanceof BibDateBuilder) {
      this.fsmStateAssertAndSet(DocumentHeader.STATE_DATE_BUILDER_CREATED,
          DocumentHeader.STATE_DATE_BUILDER_BEFORE_OPEN);
      return;
    }

    if (child instanceof DocumentSummary) {
      this.fsmStateAssertAndSet(DocumentHeader.STATE_SUMMARY_CREATED,
          DocumentHeader.STATE_SUMMARY_BEFORE_OPEN);
      return;
    }

    this.throwChildNotAllowed(child);
  }

  /** {@inheritDoc} */
  @Override
  protected final synchronized void afterChildOpened(
      final HierarchicalFSM child, final boolean hasOtherChildren) {

    super.afterChildOpened(child, hasOtherChildren);

    if (child instanceof DocumentTitle) {
      this.fsmStateAssertAndSet(DocumentHeader.STATE_TITLE_BEFORE_OPEN,
          DocumentHeader.STATE_TITLE_AFTER_OPEN);
      return;
    }

    if (child instanceof BibAuthorsBuilder) {
      this.fsmStateAssertAndSet(
          DocumentHeader.STATE_AUTHORS_BUILDER_BEFORE_OPEN,
          DocumentHeader.STATE_AUTHORS_BUILDER_AFTER_OPEN);
      return;
    }

    if (child instanceof BibDateBuilder) {
      this.fsmStateAssertAndSet(
          DocumentHeader.STATE_DATE_BUILDER_BEFORE_OPEN,
          DocumentHeader.STATE_DATE_BUILDER_AFTER_OPEN);
      return;
    }

    if (child instanceof DocumentSummary) {
      this.fsmStateAssertAndSet(DocumentHeader.STATE_SUMMARY_BEFORE_OPEN,
          DocumentHeader.STATE_SUMMARY_AFTER_OPEN);
      return;
    }

    this.throwChildNotAllowed(child);
  }

  /** {@inheritDoc} */
  @Override
  protected final synchronized void afterChildClosed(
      final HierarchicalFSM child) {

    super.afterChildClosed(child);

    if (child instanceof DocumentTitle) {
      this.fsmStateAssertAndSet(DocumentHeader.STATE_TITLE_AFTER_OPEN,
          DocumentHeader.STATE_TITLE_CLOSED);
      return;
    }

    if (child instanceof BibAuthorsBuilder) {
      this.fsmStateAssertAndSet(
          DocumentHeader.STATE_AUTHORS_BUILDER_AFTER_OPEN,
          DocumentHeader.STATE_AUTHORS_BUILDER_CLOSED);
      this.doAuthors(((BibAuthorsBuilder) child).getResult());
      return;
    }

    if (child instanceof BibDateBuilder) {
      this.fsmStateAssertAndSet(
          DocumentHeader.STATE_DATE_BUILDER_AFTER_OPEN,
          DocumentHeader.STATE_DATE_BUILDER_CLOSED);
      this.doDate(((BibDateBuilder) child).getResult());
      return;
    }

    if (child instanceof DocumentSummary) {
      this.fsmStateAssertAndSet(DocumentHeader.STATE_SUMMARY_AFTER_OPEN,
          DocumentHeader.STATE_SUMMARY_CLOSED);
      return;
    }

    this.throwChildNotAllowed(child);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized void onClose() {
    super.onClose();
    this.fsmStateAssertAndSet(DocumentHeader.STATE_SUMMARY_CLOSED,
        DocumentHeader.STATE_HEADER_FINALIZED);
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
