package org.optimizationBenchmarking.utils.bibliography.data;

import java.net.URI;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * A base class for bibliographic records of things that appeared in a
 * book, i.e., chapters or conference papers.
 */
public class BibInBook extends BibRecord {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the book */
  final BibBookRecord m_book;

  /** the start page */
  private final String m_startPage;

  /** the end page */
  private final String m_endPage;

  /** the chapter */
  private final String m_chapter;

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
  BibInBook(final boolean direct, final BibAuthors authors,
      final String title, final BibBookRecord book,
      final String startPage, final String endPage, final String chapter,
      final URI uri, final String doi) {
    super(direct, authors, title, book.m_date, uri, doi);

    long i, j;

    this.m_startPage = (direct ? startPage : TextUtils
        .normalize(startPage));
    this.m_endPage = (direct ? endPage : TextUtils.normalize(endPage));

    if ((this.m_endPage != null) ^ (this.m_startPage != null)) {
      throw new IllegalArgumentException(//
          "If start or end page is specified, both need to be specified."); //$NON-NLS-1$
    }
    if ((this.m_startPage != null) && (this.m_endPage != null)) {
      try {
        i = Long.parseLong(this.m_startPage);
        j = Long.parseLong(this.m_endPage);
        if (i > j) {
          throw new IllegalArgumentException(//
              "Start page cannot be greater than end page, but start page '"//$NON-NLS-1$
              + this.m_startPage + "' is " //$NON-NLS-1$
              + i + " and end page '" //$NON-NLS-1$
              + this.m_endPage + "' is " + j); //$NON-NLS-1$
        }
      } catch (final NumberFormatException x) {
        // ignore
      }
    }

    this.m_chapter = (direct ? chapter : TextUtils.normalize(chapter));

    if ((this.m_chapter == null) && (this.m_startPage == null)) {
      throw new IllegalArgumentException(//
          "Either pages or chapter need to be specified."); //$NON-NLS-1$
    }

    this.m_book = book;
  }

  /** {@inheritDoc} */
  @Override
  protected int calcHashCode() {
    return HashUtils.combineHashes(HashUtils.combineHashes(//
        HashUtils.combineHashes(super.calcHashCode(),//
            HashUtils.hashCode(this.m_book)),//
            HashUtils.combineHashes(//
                HashUtils.hashCode(this.m_startPage),//
                HashUtils.hashCode(this.m_endPage))),//
                HashUtils.hashCode(this.m_chapter));
  }

  /**
   * Get the book containing this chapter or paper
   *
   * @return the bibliography editors
   */
  public BibBookRecord getBook() {
    return this.m_book;
  }

  /**
   * Get the start page
   *
   * @return the start page
   */
  public final String getStartPage() {
    return this.m_startPage;
  }

  /**
   * Get the end page
   *
   * @return the end page
   */
  public final String getEndPage() {
    return this.m_endPage;
  }

  /**
   * Get the chapter
   *
   * @return the chapter
   */
  public final String getChapter() {
    return this.m_chapter;
  }

  /** {@inheritDoc} */
  @Override
  final boolean _equals(final BibRecord r) {
    final BibInBook x;

    if (super._equals(r)) {
      x = ((BibInBook) r);

      return (EComparison.equals(this.m_book, x.m_book) && //
          EComparison.equals(this.m_startPage, x.m_startPage) && //
          EComparison.equals(this.m_endPage, x.m_endPage) && //
          EComparison.equals(this.m_chapter, x.m_chapter));
    }

    return false;
  }

  /** {@inheritDoc} */
  @Override
  final int _compareRest(final BibRecord o) {
    BibInBook bb;
    int r;

    if (o instanceof BibInBook) {
      bb = ((BibInBook) o);

      r = EComparison.compareObjects(this.m_book, bb.m_book);
      if (r != 0) {
        return r;
      }

      r = EComparison.compareObjects(this.m_startPage, bb.m_startPage);
      if (r != 0) {
        return r;
      }

      r = EComparison.compareObjects(this.m_endPage, bb.m_endPage);
      if (r != 0) {
        return r;
      }

      r = EComparison.compareObjects(this.m_chapter, bb.m_chapter);
      if (r != 0) {
        return r;
      }
    }

    return super._compareRest(o);
  }
}
