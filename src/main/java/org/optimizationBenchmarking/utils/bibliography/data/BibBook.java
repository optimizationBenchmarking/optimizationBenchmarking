package org.optimizationBenchmarking.utils.bibliography.data;

import java.net.URI;

/** a bibliographic record for a book */
public class BibBook extends BibBookRecord {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * Create a new bibliography record
   *
   * @param authors
   *          the authors
   * @param title
   *          the title
   * @param uri
   *          the uri
   * @param date
   *          the date
   * @param editors
   *          the book's editors
   * @param publisher
   *          the publisher
   * @param series
   *          the series
   * @param issn
   *          the issn of the series
   * @param volume
   *          the volume
   * @param isbn
   *          the isbn of the book
   * @param edition
   *          the edition
   * @param doi
   *          the doi
   */
  public BibBook(final BibAuthors authors, final String title,
      final BibDate date, final BibAuthors editors,
      final BibOrganization publisher, final String series,
      final String issn, final String volume, final String edition,
      final String isbn, final URI uri, final String doi) {
    this(false, authors, title, date, editors, publisher, series, issn,
        volume, edition, isbn, uri, doi, true, true);
  }

  /**
   * Create a new bibliography record
   *
   * @param authors
   *          the authors
   * @param title
   *          the title
   * @param uri
   *          the uri
   * @param date
   *          the date
   * @param editors
   *          the book's editors
   * @param publisher
   *          the publisher
   * @param series
   *          the series
   * @param issn
   *          the issn of the series
   * @param volume
   *          the volume
   * @param isbn
   *          the isbn of the book
   * @param edition
   *          the edition
   * @param publisherMustNotBeNull
   *          if the publisher can be null
   * @param needsAuthorsOrEditors
   *          are authors or editors needed?
   * @param doi
   *          the doi
   * @param direct
   *          are we setting the data directly?
   */
  BibBook(final boolean direct, final BibAuthors authors,
      final String title, final BibDate date, final BibAuthors editors,
      final BibOrganization publisher, final String series,
      final String issn, final String volume, final String edition,
      final String isbn, final URI uri, final String doi,
      final boolean publisherMustNotBeNull,
      final boolean needsAuthorsOrEditors) {
    super(direct, authors, title, date, editors, publisher, series, issn,
        volume, edition, isbn, uri, doi, publisherMustNotBeNull,
        needsAuthorsOrEditors);
  }

  /**
   * Get the date
   *
   * @return the date
   */
  public final BibDate getDate() {
    return this.m_date;
  }
}
