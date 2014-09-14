package org.optimizationBenchmarking.utils.bibliography.data;

import java.net.URI;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.hash.HashUtils;

/** A bibliographic for thesis. */
public class BibThesis extends BibBook {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * the thesis type
   * 
   * @serial serial field
   */
  private final EThesisType m_type;

  /**
   * the school
   * 
   * @serial serial field
   */
  private final BibOrganization m_school;

  /**
   * Create a new bibliography record
   * 
   * @param authors
   *          the authors
   * @param type
   *          the thesis type
   * @param school
   *          the school
   * @param title
   *          the title
   * @param uri
   *          the uri
   * @param date
   *          the date
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
  public BibThesis(final BibAuthors authors, final String title,
      final BibDate date, final EThesisType type,
      final BibOrganization school, final BibOrganization publisher,
      final String series, final String issn, final String volume,
      final String edition, final String isbn, final URI uri,
      final String doi) {
    this(false, authors, title, date, type, school, publisher, series,
        issn, volume, edition, isbn, uri, doi);
  }

  /**
   * Create a new bibliography record
   * 
   * @param authors
   *          the authors
   * @param type
   *          the thesis type
   * @param school
   *          the school
   * @param title
   *          the title
   * @param uri
   *          the uri
   * @param date
   *          the date
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
   * @param direct
   *          are we setting the data directly?
   */
  BibThesis(final boolean direct, final BibAuthors authors,
      final String title, final BibDate date, final EThesisType type,
      final BibOrganization school, final BibOrganization publisher,
      final String series, final String issn, final String volume,
      final String edition, final String isbn, final URI uri,
      final String doi) {
    super(direct, authors, title, date, BibAuthors.EMPTY_AUTHORS,
        publisher, series, issn, volume, edition, isbn, uri, doi, false,
        true);

    if ((this.m_type = type) == null) {
      throw new IllegalArgumentException(//
          "Thesis type must not be empty."); //$NON-NLS-1$
    }

    this.m_school = school;
    if (this.m_school == null) {
      throw new IllegalArgumentException(//
          "School must not be empty."); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final int calcHashCode() {
    return HashUtils.combineHashes(//
        HashUtils.combineHashes(super.calcHashCode(),//
            HashUtils.hashCode(this.m_school)),// //
        HashUtils.hashCode(this.m_type));
  }

  /**
   * Get the thesis type
   * 
   * @return the thesis type
   */
  public final EThesisType getType() {
    return this.m_type;
  }

  /**
   * Get the school
   * 
   * @return the school
   */
  public final BibOrganization getSchool() {
    return this.m_school;
  }

  /** {@inheritDoc} */
  @Override
  final boolean _equals(final BibRecord r) {
    final BibThesis x;

    if (super._equals(r)) {
      x = ((BibThesis) r);

      return (EComparison.equals(this.m_type, x.m_type) && //
      EComparison.equals(this.m_school, x.m_school));
    }

    return false;
  }

  /** {@inheritDoc} */
  @Override
  final int _compareRest(final BibRecord o) {
    BibThesis bb;
    int r;

    if (o instanceof BibThesis) {
      bb = ((BibThesis) o);

      r = EComparison.compare(this.m_type, bb.m_type);
      if (r != 0) {
        return r;
      }

      r = EComparison.compare(this.m_school, bb.m_school);
      if (r != 0) {
        return r;
      }

    }

    return super._compareRest(o);
  }
}
