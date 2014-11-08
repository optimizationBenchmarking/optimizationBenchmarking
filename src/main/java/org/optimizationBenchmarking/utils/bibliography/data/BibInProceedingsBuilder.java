package org.optimizationBenchmarking.utils.bibliography.data;

import org.optimizationBenchmarking.utils.hierarchy.BuilderFSM;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;

/** A builder for conference papers */
public final class BibInProceedingsBuilder extends BibInBookBuilder {

  /**
   * create the in-proceedings builder
   * 
   * @param owner
   *          the owner
   */
  BibInProceedingsBuilder(final BuilderFSM<?> owner) {
    super(owner);
    this.open();
  }

  /**
   * Set the proceedings
   * 
   * @param proceedings
   *          the proceedings
   */
  public final void setProceedings(final BibProceedings proceedings) {
    this._bookSet(proceedings, true);
  }

  /**
   * Build the proceedings
   * 
   * @return the proceedings builder
   */
  public synchronized final BibProceedingsBuilder proceedings() {
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    return new BibProceedingsBuilder(this);
  }

  /** {@inheritDoc} */
  @Override
  final void _handleBeforeChildOpens(final HierarchicalFSM child) {
    if (!(child instanceof BibProceedingsBuilder)) {
      super._handleBeforeChildOpens(child);
    }
  }

  /** {@inheritDoc} */
  @Override
  final void _handleAfterChildClosed(final HierarchicalFSM child) {
    if (child instanceof BibProceedingsBuilder) {
      this._bookSet(((BibProceedingsBuilder) child).getResult(), false);
    } else {
      super._handleAfterChildClosed(child);
    }
  }

  /** {@inheritDoc} */
  @Override
  final BibInProceedings _doCompile() {
    this.fsmFlagsAssertTrue(BibInBookBuilder.FLAG_BOOK_SET);
    return new BibInProceedings(true, this.m_authors, this.m_title,
        ((BibProceedings) (this.m_book)), this.m_startPage,
        this.m_endPage, this.m_chapter, this.m_url, this.m_doi);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final BibInProceedings getResult() {
    return ((BibInProceedings) (super.getResult()));
  }
}
