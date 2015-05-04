package org.optimizationBenchmarking.utils.bibliography.data;

import java.net.URI;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * A base class for things that are bibliographic for a books, such as
 * books, collections, and proceedings.
 */
public class BibBookRecord extends BibRecordWithPublisher {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * the editors
   *
   * @serial serial field
   */
  private final BibAuthors m_editors;

  /**
   * the series
   *
   * @serial serial field
   */
  private final String m_series;

  /**
   * the volume
   *
   * @serial serial field
   */
  private final String m_volume;

  /**
   * the edition
   *
   * @serial serial field
   */
  private final String m_edition;

  /**
   * the isbn
   *
   * @serial a string
   */
  private final String m_isbn;

  /**
   * the issn of the series
   *
   * @serial an issn
   */
  private final String m_issn;

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
  BibBookRecord(final boolean direct, final BibAuthors authors,
      final String title, final BibDate date, final BibAuthors editors,
      final BibOrganization publisher, final String series,
      final String issn, final String volume, final String edition,
      final String isbn, final URI uri, final String doi,
      final boolean publisherMustNotBeNull,
      final boolean needsAuthorsOrEditors) {
    super(direct, authors, title, date, publisher, uri, doi);

    if (editors == null) {
      throw new IllegalArgumentException(//
          "Editors must not be null."); //$NON-NLS-1$
    }
    this.m_editors = editors;

    if (needsAuthorsOrEditors) {
      if ((authors.size() + editors.size()) <= 0) {
        throw new IllegalArgumentException(//
            "Either authors or editors must be specified."); //$NON-NLS-1$
      }
    }

    if (publisherMustNotBeNull && (this.m_publisher == null)) {
      throw new IllegalArgumentException(//
          "Publisher must not be empty."); //$NON-NLS-1$
    }

    this.m_series = (direct ? series : TextUtils.normalize(series));
    this.m_issn = (direct ? issn : TextUtils.normalize(issn));
    this.m_volume = (direct ? volume : TextUtils.normalize(volume));
    if (this.m_volume != null) {
      if (this.m_series == null) {
        throw new IllegalArgumentException(//
            "If volume is not empty, then series must not be empty."); //$NON-NLS-1$
      }
    }
    if (this.m_issn != null) {
      if (this.m_series == null) {
        throw new IllegalArgumentException(//
            "If issn is not empty, then series must not be empty."); //$NON-NLS-1$
      }
    }

    this.m_edition = (direct ? edition : TextUtils.normalize(edition));
    this.m_isbn = (direct ? isbn : TextUtils.normalize(isbn));
  }

  /** {@inheritDoc} */
  @Override
  protected int calcHashCode() {
    return HashUtils.combineHashes(HashUtils.combineHashes(//
        HashUtils.combineHashes(
            HashUtils.combineHashes(super.calcHashCode(),
                HashUtils.hashCode(this.m_edition)),
            HashUtils.hashCode(this.m_editors)),//
        HashUtils.combineHashes(HashUtils.hashCode(this.m_series),
            HashUtils.combineHashes(HashUtils.hashCode(this.m_issn),
                HashUtils.hashCode(this.m_volume)))),//
        HashUtils.hashCode(this.m_isbn));
  }

  /**
   * Get the bibliography editors
   *
   * @return the bibliography editors
   */
  public final BibAuthors getEditors() {
    return this.m_editors;
  }

  /**
   * Get the series
   *
   * @return the series
   */
  public final String getSeries() {
    return this.m_series;
  }

  /**
   * Get the isbn
   *
   * @return the isbn
   */
  public final String getISBN() {
    return this.m_isbn;
  }

  /**
   * Get the issn
   *
   * @return the issn
   */
  public final String getISSN() {
    return this.m_issn;
  }

  /**
   * Get the volume
   *
   * @return the volume
   */
  public final String getVolume() {
    return this.m_volume;
  }

  /**
   * Get the edition
   *
   * @return the edition
   */
  public final String getEdition() {
    return this.m_edition;
  }

  /** {@inheritDoc} */
  @Override
  boolean _equals(final BibRecord r) {
    final BibBookRecord x;

    if (super._equals(r)) {
      x = ((BibBookRecord) r);

      return (EComparison.equals(this.m_edition, x.m_edition) && //
          EComparison.equals(this.m_series, x.m_series) && //
      EComparison.equals(this.m_volume, x.m_volume));

    }

    return false;
  }

  /** {@inheritDoc} */
  @Override
  int _compareRest(final BibRecord o) {
    BibBookRecord bb;
    int r;

    if (o instanceof BibBookRecord) {
      bb = ((BibBookRecord) o);

      r = EComparison.compareObjects(this.m_editors, bb.m_editors);
      if (r != 0) {
        return r;
      }

      r = EComparison.compareObjects(this.m_series, bb.m_series);
      if (r != 0) {
        return r;
      }

      r = EComparison.compareObjects(this.m_volume, bb.m_volume);
      if (r != 0) {
        return r;
      }

      r = EComparison.compareObjects(this.m_edition, bb.m_edition);
      if (r != 0) {
        return r;
      }
    }

    return super._compareRest(o);
  }
}
