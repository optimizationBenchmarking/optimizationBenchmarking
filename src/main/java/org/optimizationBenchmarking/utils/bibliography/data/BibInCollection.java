package org.optimizationBenchmarking.utils.bibliography.data;

import java.net.URI;

/**
 * A bibliographic record for a chapter that appeared in a collection-
 */
public class BibInCollection extends BibInBook {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * Create a new bibliography record for things that are parts of book
   *
   * @param authors
   *          the authors
   * @param title
   *          the title
   * @param uri
   *          the uri
   * @param book
   *          the book
   * @param startPage
   *          the start page
   * @param endPage
   *          the end page
   * @param chapter
   *          the chapter
   * @param doi
   *          the doi
   */
  public BibInCollection(final BibAuthors authors, final String title,
      final BibBook book, final String startPage, final String endPage,
      final String chapter, final URI uri, final String doi) {
    this(false, authors, title, book, startPage, endPage, chapter, uri,
        doi);
  }

  /**
   * Create a new bibliography record for things that are parts of book
   *
   * @param authors
   *          the authors
   * @param title
   *          the title
   * @param uri
   *          the uri
   * @param book
   *          the book
   * @param startPage
   *          the start page
   * @param endPage
   *          the end page
   * @param chapter
   *          the chapter
   * @param doi
   *          the doi
   * @param direct
   *          parameter creation
   */
  BibInCollection(final boolean direct, final BibAuthors authors,
      final String title, final BibBook book, final String startPage,
      final String endPage, final String chapter, final URI uri,
      final String doi) {
    super(direct, authors, title, book, startPage, endPage, chapter, uri,
        doi);
  }

  /** {@inheritDoc} */
  @Override
  public final BibBook getBook() {
    return ((BibBook) (this.m_book));
  }
}
