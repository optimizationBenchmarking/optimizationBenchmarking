package org.optimizationBenchmarking.utils.bibliography.data;

import java.net.URI;

/**
 * A bibliographic record for a paper that appeared in a conference
 * proceedings.
 */
public class BibInProceedings extends BibInBook {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * Create a new bibliography record for papers that appeared in
   * proceedings
   * 
   * @param authors
   *          the authors
   * @param title
   *          the title
   * @param uri
   *          the uri
   * @param proceedings
   *          the proceedings
   * @param startPage
   *          the start page
   * @param endPage
   *          the end page
   * @param chapter
   *          the chapter
   * @param doi
   *          the doi
   */
  public BibInProceedings(final BibAuthors authors, final String title,
      final BibProceedings proceedings, final String startPage,
      final String endPage, final String chapter, final URI uri,
      final String doi) {
    this(false, authors, title, proceedings, startPage, endPage, chapter,
        uri, doi);
  }

  /**
   * Create a new bibliography record for papers that appeared in
   * proceedings
   * 
   * @param authors
   *          the authors
   * @param title
   *          the title
   * @param uri
   *          the uri
   * @param proceedings
   *          the proceedings
   * @param startPage
   *          the start page
   * @param endPage
   *          the end page
   * @param chapter
   *          the chapter
   * @param doi
   *          the doi
   * @param direct
   *          use parameters directly?
   */
  BibInProceedings(final boolean direct, final BibAuthors authors,
      final String title, final BibProceedings proceedings,
      final String startPage, final String endPage, final String chapter,
      final URI uri, final String doi) {
    super(direct, authors, title, proceedings, startPage, endPage,
        chapter, uri, doi);
  }

  /** {@inheritDoc} */
  @Override
  public final BibProceedings getBook() {
    return ((BibProceedings) (this.m_book));
  }
}
