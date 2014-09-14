package org.optimizationBenchmarking.utils.bibliography.data;

import java.net.URI;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.hash.HashUtils;

/** A bibliographic record for conference proceedings. */
public class BibProceedings extends BibBook {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * the location
   * 
   * @serial serial field
   */
  private final BibOrganization m_location;

  /**
   * the end date
   * 
   * @serial serial field
   */
  private final BibDate m_endDate;

  /**
   * Create a new bibliography record
   * 
   * @param title
   *          the title
   * @param uri
   *          the uri
   * @param startDate
   *          the start date
   * @param endDate
   *          the end date
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
   * @param location
   *          the location
   * @param doi
   *          the doi
   */
  public BibProceedings(final String title, final BibDate startDate,
      final BibDate endDate, final BibAuthors editors,
      final BibOrganization location, final BibOrganization publisher,
      final String series, final String issn, final String volume,
      final String isbn, final URI uri, final String doi) {
    this(false, title, startDate, endDate, editors, location, publisher,
        series, issn, volume, isbn, uri, doi);
  }

  /**
   * Create a new bibliography record
   * 
   * @param title
   *          the title
   * @param uri
   *          the uri
   * @param startDate
   *          the start date
   * @param endDate
   *          the end date
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
   * @param location
   *          the location
   * @param doi
   *          the doi
   * @param direct
   *          use the data directly
   */
  BibProceedings(final boolean direct, final String title,
      final BibDate startDate, final BibDate endDate,
      final BibAuthors editors, final BibOrganization location,
      final BibOrganization publisher, final String series,
      final String issn, final String volume, final String isbn,
      final URI uri, final String doi) {
    super(direct, BibAuthors.EMPTY_AUTHORS, title, startDate, editors,
        publisher, series, issn, volume, null, isbn, uri, doi, true, false);

    this.m_location = location;
    if ((this.m_location == null) || (this.m_location.m_address == null)) {
      throw new IllegalArgumentException(//
          "Must provide location of proceedingsified event."); //$NON-NLS-1$
    }

    this.m_endDate = ((endDate != null) ? //
    ((endDate.equals(startDate)) ? startDate : endDate)//
        : startDate);
    if (this.m_endDate.hashCode() < startDate.hashCode()) {
      throw new IllegalArgumentException(//
          "End date must not be before start date."); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final int calcHashCode() {
    return HashUtils.combineHashes(
        HashUtils.combineHashes(super.calcHashCode(),
            HashUtils.hashCode(this.m_endDate)),
        HashUtils.hashCode(this.m_location));
  }

  /**
   * Get the location
   * 
   * @return the location
   */
  public final BibOrganization getLocation() {
    return this.m_location;
  }

  /**
   * Get the start date of the conference
   * 
   * @return the start date of the conference
   */
  public final BibDate getStartDate() {
    return this.m_date;
  }

  /**
   * Get the end date of the conference
   * 
   * @return the end date of the conference
   */
  public final BibDate getEndDate() {
    return this.m_endDate;
  }

  /** {@inheritDoc} */
  @Override
  final boolean _equals(final BibRecord r) {
    final BibProceedings x;

    if (super._equals(r)) {
      x = ((BibProceedings) r);

      return (EComparison.equals(this.m_location, x.m_location) && //
      EComparison.equals(this.m_endDate, x.m_endDate));
    }

    return false;
  }
}
