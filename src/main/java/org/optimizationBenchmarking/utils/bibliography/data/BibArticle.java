package org.optimizationBenchmarking.utils.bibliography.data;

import java.net.URI;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** A bibliographic record for a journal article. */
public class BibArticle extends BibRecordWithPublisher {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the journal */
  private final String m_journal;
  /** the journal issn */
  private final String m_issn;
  /** the volume */
  private final String m_volume;
  /** the number */
  private final String m_number;
  /** the start page */
  private final String m_startPage;
  /** the end page */
  private final String m_endPage;

  /**
   * create a record for articles
   * 
   * @param authors
   *          the authors
   * @param title
   *          the title
   * @param date
   *          the date
   * @param journal
   *          the journal
   * @param volume
   *          the volume
   * @param number
   *          the number
   * @param startPage
   *          the start page
   * @param endPage
   *          the end page
   * @param uri
   *          the uri
   * @param doi
   *          the doi
   * @param issn
   *          the issn
   * @param publisher
   *          the publisher
   */
  public BibArticle(final BibAuthors authors, final String title,
      final BibDate date, final String journal, final String issn,
      final String volume, final String number, final String startPage,
      final String endPage, final BibOrganization publisher,
      final URI uri, final String doi) {
    this(false, authors, title, date, journal, issn, volume, number,
        startPage, endPage, publisher, uri, doi);
  }

  /**
   * create a record for technical records
   * 
   * @param authors
   *          the authors
   * @param title
   *          the title
   * @param date
   *          the date
   * @param journal
   *          the journal
   * @param volume
   *          the volume
   * @param number
   *          the number
   * @param startPage
   *          the start page
   * @param endPage
   *          the end page
   * @param uri
   *          the uri
   * @param doi
   *          the doi
   * @param issn
   *          the issn
   * @param publisher
   *          the publisher
   * @param direct
   *          store values directly?
   */
  BibArticle(final boolean direct, final BibAuthors authors,
      final String title, final BibDate date, final String journal,
      final String issn, final String volume, final String number,
      final String startPage, final String endPage,
      final BibOrganization publisher, final URI uri, final String doi) {
    super(direct, authors, title, date, publisher, uri, doi);

    long i, j;

    this.m_journal = (direct ? journal : TextUtils.normalize(journal));
    if (this.m_journal == null) {
      throw new IllegalArgumentException(//
          "Journal must not be null or empty, but '" + //$NON-NLS-1$
              journal + "' is.");//$NON-NLS-1$
    }

    this.m_volume = (direct ? volume : TextUtils.normalize(volume));
    if (this.m_volume == null) {
      throw new IllegalArgumentException(//
          "Volume must not be null or empty, but '" + //$NON-NLS-1$
              volume + "' is.");//$NON-NLS-1$
    }

    this.m_number = (direct ? number : TextUtils.normalize(number));
    if (this.m_number == null) {
      throw new IllegalArgumentException(//
          "Number must not be null or empty, but '" + //$NON-NLS-1$
              number + "' is.");//$NON-NLS-1$
    }

    this.m_startPage = (direct ? startPage : TextUtils
        .normalize(startPage));
    this.m_endPage = (direct ? endPage : TextUtils.normalize(endPage));
    if (this.m_startPage == null) {
      if (this.m_endPage != null) {
        throw new IllegalArgumentException(//
            "Start page must not be empty if end page is defined.");//$NON-NLS-1$
      }
    }
    if (this.m_endPage == null) {
      if (this.m_startPage != null) {
        throw new IllegalArgumentException(//
            "End page must not be empty if start page is defined.");//$NON-NLS-1$
      }
    } else {
      if ((this.m_startPage != null) && (this.m_endPage != null)) {
        try {
          i = Long.parseLong(this.m_startPage);
          j = Long.parseLong(this.m_endPage);
          if (i > j) {
            throw new IllegalArgumentException(//
                "Start page cannot be greater than end page, but start page '"//$NON-NLS-1$ 
                    + this.m_startPage + "' is " //$NON-NLS-1$
                    + i + " and end page '" //$NON-NLS-1$
                    + this.m_endPage + "' is " + j);//$NON-NLS-1$
          }
        } catch (final NumberFormatException x) {
          // ignore
        }
      }
    }

    this.m_issn = (direct ? issn : TextUtils.normalize(issn));
  }

  /** {@inheritDoc} */
  @Override
  protected final int calcHashCode() {
    return HashUtils.combineHashes(
        //
        HashUtils.combineHashes(super.calcHashCode(),//
            HashUtils.hashCode(this.m_journal)),//
        HashUtils.combineHashes(
            HashUtils.combineHashes(HashUtils.hashCode(this.m_issn),//
                HashUtils.hashCode(this.m_volume)),//
            HashUtils.combineHashes(
                //
                HashUtils.combineHashes(
                    //
                    HashUtils.hashCode(this.m_number),
                    HashUtils.hashCode(this.m_startPage)),
                HashUtils.hashCode(this.m_endPage))));
  }

  /**
   * Get the date
   * 
   * @return the date
   */
  public final BibDate getDate() {
    return this.m_date;
  }

  /**
   * Get the journal
   * 
   * @return the journal
   */
  public final String getJournal() {
    return this.m_journal;
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
   * Get the number
   * 
   * @return the number
   */
  public final String getNumber() {
    return this.m_number;
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
   * Get the issn
   * 
   * @return the issn
   */
  public final String getISSN() {
    return this.m_issn;
  }

  /** {@inheritDoc} */
  @Override
  final boolean _equals(final BibRecord r) {
    final BibArticle x;

    if (super._equals(r)) {
      x = ((BibArticle) r);

      return (EComparison.equals(this.m_journal, x.m_journal) && //
          EComparison.equals(this.m_issn, x.m_issn) && //
          EComparison.equals(this.m_volume, x.m_volume) && //
          EComparison.equals(this.m_number, x.m_number) && //
          EComparison.equals(this.m_startPage, x.m_startPage) && //
      EComparison.equals(this.m_endPage, x.m_endPage));
    }

    return false;
  }

  /** {@inheritDoc} */
  @Override
  final int _compareRest(final BibRecord o) {
    BibArticle bb;
    int r;

    if (o instanceof BibArticle) {
      bb = ((BibArticle) o);

      r = EComparison.compare(this.m_journal, bb.m_journal);
      if (r != 0) {
        return r;
      }

      r = EComparison.compare(this.m_issn, bb.m_issn);
      if (r != 0) {
        return r;
      }

      r = EComparison.compare(this.m_volume, bb.m_volume);
      if (r != 0) {
        return r;
      }

      r = EComparison.compare(this.m_number, bb.m_number);
      if (r != 0) {
        return r;
      }

      r = EComparison.compare(this.m_startPage, bb.m_startPage);
      if (r != 0) {
        return r;
      }

      r = EComparison.compare(this.m_endPage, bb.m_endPage);
      if (r != 0) {
        return r;
      }
    }

    return super._compareRest(o);
  }

  /** {@inheritDoc} */
  @Override
  public final void toText(final ITextOutput textOut) {
    boolean b;
    BibDate d;

    this.m_authors.toText(textOut);
    textOut.append('.');
    textOut.append(' ');
    textOut.append(this.m_title);
    textOut.append('.');
    textOut.append(' ');
    textOut.append(this.m_journal);
    textOut.append(',');
    textOut.append(' ');

    b = false;
    if (this.m_volume != null) {
      textOut.append(this.m_volume);
      b = true;
    }
    if (this.m_number != null) {
      b = true;
      textOut.append('(');
      textOut.append(this.m_number);
      textOut.append(')');
    }
    if (this.m_startPage != null) {
      if (b) {
        textOut.append(':');
      }
      textOut.append(this.m_startPage);
      if (this.m_endPage != null) {
        textOut.append('-');
        textOut.append(this.m_endPage);
      }
      b = true;
    }

    if (b) {
      textOut.append(',');
      textOut.append(' ');
    }

    b = false;
    d = this.m_date;
    if (d != null) {
      if (d.m_month != null) {
        textOut.append(d.m_month.m_full);
        if (d.m_day > 0) {
          textOut.append(d.m_day);
          textOut.append(',');
        }
        textOut.append(' ');
      } else {
        if (d.m_quarter != null) {
          textOut.append(d.m_quarter.m_full);
          textOut.append(' ');
        }
      }
      textOut.append(d.m_year);
      b = true;
    }

    if (this.m_publisher != null) {
      if (b) {
        textOut.append(',');
        textOut.append(' ');
      }

      textOut.append("published by "); //$NON-NLS-1$
      this.m_publisher.toText(textOut);
      b = true;
    }

    if (this.m_url != null) {
      if (b) {
        textOut.append(',');
        textOut.append(' ');
      }
      b = true;
      textOut.append(this.m_url);
    }

    if (this.m_doi != null) {
      if (b) {
        textOut.append(',');
        textOut.append(' ');
      }
      b = true;
      textOut.append("doi:"); //$NON-NLS-1$
      textOut.append(this.m_doi);
    }

    textOut.append('.');
  }
}
