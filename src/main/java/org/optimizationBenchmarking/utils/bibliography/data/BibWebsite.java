package org.optimizationBenchmarking.utils.bibliography.data;

import java.net.URI;

/** A bibliographic record for a Website. */
public final class BibWebsite extends BibRecordWithPublisher {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * Create a new bibliography record
   *
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
   */
  public BibWebsite(final BibAuthors authors, final String title,
      final BibDate date, final BibOrganization publisher, final URI uri) {
    this(false, authors, title, date, publisher, uri, null);
  }

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
  BibWebsite(final boolean direct, final BibAuthors authors,
      final String title, final BibDate date,
      final BibOrganization publisher, final URI uri, final String doi) {
    super(direct, authors, title, date, publisher, uri, doi);

    if (this.m_url == null) {
      throw new IllegalArgumentException(//
          "URL of a website must not be null."); //$NON-NLS-1$
    }
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
