package org.optimizationBenchmarking.utils.bibliography.data;

import java.net.URI;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.hash.HashUtils;

/**
 * A bibliographic record which also has a publisher information.
 */
public class BibRecordWithPublisher extends BibRecord {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * the publisher
   *
   * @serial serial field
   */
  final BibOrganization m_publisher;

  /**
   * Create the website record
   *
   * @param direct
   *          direct?
   * @param authors
   *          the authors
   * @param title
   *          the title
   * @param date
   *          the date
   * @param publisher
   *          the publisher
   * @param uri
   *          the uri
   * @param doi
   *          the doi
   */
  BibRecordWithPublisher(final boolean direct, final BibAuthors authors,
      final String title, final BibDate date,
      final BibOrganization publisher, final URI uri, final String doi) {

    super(direct, authors, title, date, uri, doi);

    this.m_publisher = publisher;
  }

  /** {@inheritDoc} */
  @Override
  protected int calcHashCode() {
    return HashUtils.combineHashes(
        HashUtils.combineHashes(super.calcHashCode(),
            HashUtils.hashCode(this.m_publisher)),
            HashUtils.hashCode(this.m_publisher));
  }

  /**
   * Get the publisher
   *
   * @return the publisher
   */
  public final BibOrganization getPublisher() {
    return this.m_publisher;
  }

  /** {@inheritDoc} */
  @Override
  boolean _equals(final BibRecord r) {
    final BibRecordWithPublisher x;

    if (super._equals(r)) {
      x = ((BibRecordWithPublisher) r);

      return (EComparison.equals(this.m_publisher, x.m_publisher));

    }

    return false;
  }

  /** {@inheritDoc} */
  @Override
  int _compareRest(final BibRecord o) {
    BibRecordWithPublisher bb;
    int r;

    if (o instanceof BibRecordWithPublisher) {
      bb = ((BibRecordWithPublisher) o);

      r = EComparison.compareObjects(this.m_publisher, bb.m_publisher);
      if (r != 0) {
        return r;
      }
    }

    return super._compareRest(o);
  }
}
