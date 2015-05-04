package org.optimizationBenchmarking.utils.bibliography.data;

import java.net.URI;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.text.TextUtils;

/** A bibliographic record for a technical report. */
public class BibTechReport extends BibRecordWithPublisher {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the series */
  private final String m_series;
  /** the number */
  private final String m_number;
  /** the issn */
  private final String m_issn;

  /**
   * create a record for technical reports
   *
   * @param authors
   *          the authors
   * @param title
   *          the title
   * @param date
   *          the date
   * @param series
   *          the series
   * @param number
   *          the number
   * @param publisher
   *          the publisher
   * @param uri
   *          the uri
   * @param doi
   *          the doi
   * @param issn
   *          the issn
   */
  public BibTechReport(final BibAuthors authors, final String title,
      final BibDate date, final String series, final String number,
      final String issn, final BibOrganization publisher, final URI uri,
      final String doi) {
    this(false, authors, title, date, series, number, issn, publisher,
        uri, doi);
  }

  /**
   * create a record for technical reports
   *
   * @param authors
   *          the authors
   * @param title
   *          the title
   * @param date
   *          the date
   * @param series
   *          the series
   * @param number
   *          the number *
   * @param direct
   *          direct?
   * @param publisher
   *          the publisher
   * @param uri
   *          the uri
   * @param doi
   *          the doi
   * @param issn
   *          the issn
   */
  BibTechReport(final boolean direct, final BibAuthors authors,
      final String title, final BibDate date, final String series,
      final String number, final String issn,
      final BibOrganization publisher, final URI uri, final String doi) {
    super(direct, authors, title, date, publisher, uri, doi);

    this.m_series = (direct ? series : TextUtils.normalize(series));
    this.m_number = (direct ? number : TextUtils.normalize(number));
    this.m_issn = (direct ? issn : TextUtils.normalize(issn));
  }

  /** {@inheritDoc} */
  @Override
  protected final int calcHashCode() {
    return HashUtils.combineHashes(
        HashUtils.combineHashes(super.calcHashCode(),//
            HashUtils.hashCode(this.m_issn)),//
        HashUtils.combineHashes(
            //
            HashUtils.hashCode(this.m_series),
            HashUtils.hashCode(this.m_number)));
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
   * Get the series
   *
   * @return the series
   */
  public final String getSeries() {
    return this.m_series;
  }

  /**
   * Get the number
   *
   * @return the number
   */
  public final String getNumber() {
    return this.m_number;
  }

  /**
   * Get the date
   *
   * @return the date
   */
  public final BibDate getDate() {
    return this.m_date;
  }

  /** {@inheritDoc} */
  @Override
  final boolean _equals(final BibRecord r) {
    final BibTechReport x;

    if (super._equals(r)) {
      x = ((BibTechReport) r);

      return (EComparison.equals(this.m_issn, x.m_issn) && //
          EComparison.equals(this.m_series, x.m_series) && //
      EComparison.equals(this.m_number, x.m_number));
    }

    return false;
  }

  /** {@inheritDoc} */
  @Override
  final int _compareRest(final BibRecord o) {
    BibTechReport bb;
    int r;

    if (o instanceof BibTechReport) {
      bb = ((BibTechReport) o);

      r = EComparison.compareObjects(this.m_series, bb.m_series);
      if (r != 0) {
        return r;
      }

      r = EComparison.compareObjects(this.m_number, bb.m_number);
      if (r != 0) {
        return r;
      }

      r = EComparison.compareObjects(this.m_issn, bb.m_issn);
      if (r != 0) {
        return r;
      }
    }

    return super._compareRest(o);
  }
}
