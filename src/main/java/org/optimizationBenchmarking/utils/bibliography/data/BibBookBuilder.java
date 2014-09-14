package org.optimizationBenchmarking.utils.bibliography.data;

import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;

/** A builder for book record objects. */
public class BibBookBuilder extends BibBookRecordBuilder {

  /** create the book builder */
  public BibBookBuilder() {
    this(null);
  }

  /**
   * create the book builder
   * 
   * @param owner
   *          the owner
   */
  BibBookBuilder(final HierarchicalFSM owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  public final synchronized BibDateBuilder setDate() {
    return super.setDate();
  }

  /** {@inheritDoc} */
  @Override
  public final synchronized void setDate(final BibDate date) {
    super.setDate(date);
  }

  /** {@inheritDoc} */
  @SuppressWarnings("resource")
  @Override
  protected BibBook compile() {
    final HierarchicalFSM o;

    this.fsmFlagsAssertTrue(BibRecordBuilder.FLAG_TITLE_SET
        | BibRecordBuilder.FLAG_DATE_SET
        | BibRecordWithPublisherBuilder.FLAG_PUBLISHER_SET);

    o = this.getOwner();

    return new BibBook(true, this.m_authors, this.m_title, this.m_date,
        this.m_editors, this.m_publisher, this.m_series, this.m_issn,
        this.m_volume, this.m_edition, this.m_isbn, this.m_url,
        this.m_doi, true,
        ((o == null) || (o instanceof BibliographyBuilder)));
  }

  /** {@inheritDoc} */
  @Override
  public synchronized BibBook getResult() {
    return ((BibBook) (super.getResult()));
  }
}
