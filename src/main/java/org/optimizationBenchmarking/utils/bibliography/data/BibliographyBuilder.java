package org.optimizationBenchmarking.utils.bibliography.data;

import java.util.HashMap;

import org.optimizationBenchmarking.utils.hierarchy.BuilderFSM;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;

/** A builder for bibliographies. */
public class BibliographyBuilder extends CitationsBuilder {

  /** the data */
  private HashMap<Object, Object> m_data;

  /** create the author builder */
  public BibliographyBuilder() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void beforeChildOpens(
      final HierarchicalFSM child, final boolean hasOtherChildren) {
    super.beforeChildOpens(child, hasOtherChildren);

    if (child instanceof BibRecordBuilder) {
      return;
    }
    this.throwChildNotAllowed(child);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void afterChildClosed(
      final HierarchicalFSM child) {
    super.afterChildClosed(child);

    if (child instanceof BibRecordBuilder) {
      this._add(((BibRecordBuilder) child).getResult(), false);
      return;
    }
    this.throwChildNotAllowed(child);
  }

  /**
   * Build a website record
   * 
   * @return the website record builder
   */
  public synchronized final BibWebsiteBuilder addWebsite() {
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    return new BibWebsiteBuilder(this);
  }

  /**
   * Build a article record
   * 
   * @return the article record builder
   */
  public synchronized final BibArticleBuilder addArticle() {
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    return new BibArticleBuilder(this);
  }

  /**
   * Build a thesis record
   * 
   * @return the thesis record builder
   */
  public synchronized final BibThesisBuilder addThesis() {
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    return new BibThesisBuilder(this);
  }

  /**
   * Build a technical report
   * 
   * @return the technical report record builder
   */
  public synchronized final BibTechReportBuilder addTechReport() {
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    return new BibTechReportBuilder(this);
  }

  /**
   * Build a book record
   * 
   * @return the book record builder
   */
  public synchronized final BibBookBuilder addBook() {
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    return new BibBookBuilder(this);
  }

  /**
   * Build a proceedings record
   * 
   * @return the proceedings record builder
   */
  public synchronized final BibProceedingsBuilder addProceedings() {
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    return new BibProceedingsBuilder(this);
  }

  /**
   * Build an in-proceedings record
   * 
   * @return the in-proceedings record builder
   */
  public synchronized final BibInProceedingsBuilder addInProceedings() {
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    return new BibInProceedingsBuilder(this);
  }

  /**
   * Build an in-collection record
   * 
   * @return the in-collection record builder
   */
  public synchronized final BibInCollectionBuilder addInCollection() {
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    return new BibInCollectionBuilder(this);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  protected final <T> T doNormalizePersistently(final T input) {
    Object r;

    r = input;
    if (this.m_data != null) {
      r = this.m_data.get(r);
      if (r == null) {
        r = input;
        this.m_data.put(r, r);
      }
    }
    return ((T) r);
  }

}
