package org.optimizationBenchmarking.utils.bibliography.data;

import org.optimizationBenchmarking.utils.hierarchy.BuilderFSM;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;

/** A builder for book chapters */
public final class BibInCollectionBuilder extends BibInBookBuilder {

  /** create */
  public BibInCollectionBuilder() {
    this(null);
  }

  /**
   * create the in-proceedings builder
   * 
   * @param owner
   *          the owner
   */
  BibInCollectionBuilder(final HierarchicalFSM owner) {
    super(owner);
    this.open();
  }

  /**
   * Set the book
   * 
   * @param book
   *          the proceedings
   */
  public final void setBook(final BibBook book) {
    this._bookSet(book);
  }

  /**
   * Build the book
   * 
   * @return the book builder
   */
  public synchronized final BibBookBuilder setBook() {
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    return new BibBookBuilder(this);
  }

  /** {@inheritDoc} */
  @Override
  final void _handleBeforeChildOpens(final HierarchicalFSM child) {
    if (!(child instanceof BibBookBuilder)) {
      super._handleBeforeChildOpens(child);
    }
  }

  /** {@inheritDoc} */
  @Override
  final void _handleAfterChildClosed(final HierarchicalFSM child) {
    if (child instanceof BibProceedingsBuilder) {
      this.setBook(((BibBookBuilder) child).getResult());
    } else {
      super._handleAfterChildClosed(child);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final BibInCollection compile() {
    this.fsmFlagsAssertTrue(BibInBookBuilder.FLAG_BOOK_SET);
    return new BibInCollection(true, this.m_authors, this.m_title,
        ((BibBook) (this.m_book)), this.m_startPage, this.m_endPage,
        this.m_chapter, this.m_url, this.m_doi);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final BibInCollection getResult() {
    return ((BibInCollection) (super.getResult()));
  }
}
