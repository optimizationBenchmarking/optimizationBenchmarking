package org.optimizationBenchmarking.utils.bibliography.data;

import java.net.URI;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.document.spec.ELabelType;
import org.optimizationBenchmarking.utils.error.RethrowMode;
import org.optimizationBenchmarking.utils.hash.HashUtils;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.numbers.AlphabeticNumberAppender;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * A bibliographic record for anything that has been published, such as a
 * paper, chapter, or even a website.
 */
public abstract class BibRecord extends _BibElement<BibRecord> implements
    Cloneable {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;
  /** the key prefix */
  private static final char[] KEY_PREFIX = { 'b',
      ELabelType.LABEL_PREFIX_SEPARATOR };
  /**
   * the authors
   * 
   * @serial serial field
   */
  final BibAuthors m_authors;

  /**
   * the title
   * 
   * @serial serial field
   */
  final String m_title;

  /**
   * the start date
   * 
   * @serial serial field
   */
  final BibDate m_date;

  /**
   * the url
   * 
   * @serial serial field
   */
  final URI m_url;

  /**
   * the doi
   * 
   * @serial serial field
   */
  final String m_doi;

  /**
   * the id of the bibliography record, uniquely identifying it in its
   * owning bibliography
   */
  private volatile int m_id;

  /** the key of this record */
  private volatile String m_key;

  /**
   * Create a new bibliography record
   * 
   * @param authors
   *          the authors
   * @param title
   *          the title
   * @param date
   *          the date of the record
   * @param url
   *          the url
   * @param doi
   *          the doi
   * @param direct
   *          should we pass parameters through directly?
   */
  BibRecord(final boolean direct, final BibAuthors authors,
      final String title, final BibDate date, final URI url,
      final String doi) {
    super();

    if (authors == null) {
      throw new IllegalArgumentException(//
          "Authors must not be null."); //$NON-NLS-1$
    }

    if (date == null) {
      throw new IllegalArgumentException(//
          "The date must not be null."); //$NON-NLS-1$
    }

    this.m_title = (direct ? title : TextUtils.normalize(title));
    if (this.m_title == null) {
      throw new IllegalArgumentException(//
          "DocumentTitle must not be empty or null, but '" + //$NON-NLS-1$
              title + "' is."); //$NON-NLS-1$
    }

    this.m_doi = (direct ? doi : BibRecord._makeDOI(doi));
    this.m_authors = authors;
    this.m_url = (direct ? url : BibRecord._makeURL(url));
    this.m_date = date;

    this.m_id = (-1);
  }

  /**
   * Get a key of this record. Every bibliography record which appears as
   * top-level element in a bibliography has a unique key. An element which
   * is not a top-level record, say a
   * {@link org.optimizationBenchmarking.utils.bibliography.data.BibProceedings
   * proceedings record} owned by a
   * {@link org.optimizationBenchmarking.utils.bibliography.data.BibInProceedings
   * in-proceedings} record, does not have a unique key or id, in which
   * case this method will return {@code null}.
   * 
   * @return the key, or {@code null} if no key is defined for the record
   * @see #getID()
   */
  public final String getKey() {
    return this.m_key;
  }

  /**
   * Clone this element and reset its id and key.
   * 
   * @return the clone
   * @see #getID()
   * @see #getKey()
   */
  @Override
  protected final BibRecord clone() {
    final BibRecord r;

    try {
      r = ((BibRecord) (super.clone()));
    } catch (final CloneNotSupportedException cnse) {
      RethrowMode.THROW_AS_RUNTIME_EXCEPTION
          .rethrow(//
              "Error while cloning bibliographic record: This should never happen.", //$NON-NLS-1$
              true, cnse);
      return null; // can never be reached
    }

    r.m_id = (-1);
    r.m_key = null;
    return r;
  }

  /**
   * Set the id of this bibliography record
   * 
   * @param id
   *          the id
   * @see #getID()
   * @see #getKey()
   */
  synchronized final void _setID(final int id) {
    final MemoryTextOutput mt;

    if (this.m_id != (-1)) {
      throw new IllegalStateException("Cannot set ID twice."); //$NON-NLS-1$
    }

    this.m_id = id;
    mt = new MemoryTextOutput();
    mt.append(BibRecord.KEY_PREFIX);
    AlphabeticNumberAppender.LOWER_CASE_INSTANCE.appendTo(id,
        ETextCase.IN_SENTENCE, mt);
    this.m_key = mt.toString();
  }

  /**
   * <p>
   * Obtain the unique ID of this element. Every bibliography record which
   * appears as top-level element in a bibliography has a unique id. This
   * id is its index in the global bibliography.
   * </p>
   * <p>
   * An element which is not a top-level record, say a
   * {@link org.optimizationBenchmarking.utils.bibliography.data.BibProceedings
   * proceedings record} owned by a
   * {@link org.optimizationBenchmarking.utils.bibliography.data.BibInProceedings
   * in-proceedings} record, may not have a unique id, in which case this
   * method will return {@code -1}.
   * </p>
   * 
   * @return the unique ID of this element.
   * @see #getKey()
   */
  public final int getID() {
    return this.m_id;
  }

  /**
   * make a doi
   * 
   * @param doi
   *          the doi
   * @return the doi string
   */
  static final String _makeDOI(final String doi) {
    final String d, d2;

    d = TextUtils.normalize(doi);
    if (d == null) {
      return null;
    }
    d2 = d.substring(0, Math.min(d.length(), 18)).toLowerCase();
    if (d2.startsWith("http://dx.doi.org/")) {//$NON-NLS-1$
      return TextUtils.prepare(d.substring(18));
    }
    if (d2.startsWith("doi:")) { //$NON-NLS-1$
      return TextUtils.prepare(d.substring(4));
    }
    return d;
  }

  /**
   * make an url
   * 
   * @param u
   *          the url
   * @return the improved url
   */
  static final URI _makeURL(final URI u) {
    if (u == null) {
      return null;
    }

    try {
      return u.normalize().toURL().toURI().normalize();
    } catch (final Throwable t) {
      return u;
    }
  }

  /**
   * Get the bibliography authors
   * 
   * @return the bibliography authors
   */
  public final BibAuthors getAuthors() {
    return this.m_authors;
  }

  /**
   * Get the title
   * 
   * @return the title
   */
  public final String getTitle() {
    return this.m_title;
  }

  /**
   * Get the doi
   * 
   * @return the doi
   */
  public final String getDOI() {
    return this.m_doi;
  }

  /**
   * Get the url
   * 
   * @return the url
   */
  public final URI getURL() {
    return this.m_url;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    final BibRecord b;

    if (o == this) {
      return true;
    }
    if (o == null) {
      return false;
    }

    if (o.getClass() == this.getClass()) {
      b = ((BibRecord) o);
      return this._equals(b);
    }
    return false;
  }

  /**
   * compare to another bib record
   * 
   * @param r
   *          the bib record
   * @return {@code true} if this object is the
   */
  boolean _equals(final BibRecord r) {
    return (EComparison.equals(this.m_authors, r.m_authors) && //
        EComparison.equals(this.m_title, r.m_title) && //
        EComparison.equals(this.m_date, r.m_date) && //
        EComparison.equals(this.m_url, r.m_url) && //
    EComparison.equals(this.m_doi, r.m_doi));
  }

  /** {@inheritDoc} */
  @Override
  protected int calcHashCode() {
    return HashUtils.combineHashes(
        //
        HashUtils.combineHashes(//
            HashUtils.hashCode(this.m_authors),//
            HashUtils.hashCode(this.m_date)),//
        HashUtils.combineHashes(
        //
            HashUtils.combineHashes(
                //
                HashUtils.hashCode(this.m_title),
                HashUtils.hashCode(this.m_url)),//
            HashUtils.hashCode(this.m_doi)));
  }

  /** {@inheritDoc} */
  @Override
  public final int compareTo(final BibRecord o) {
    int r;

    if (o == this) {
      return 0;
    }

    if (o == null) {
      return (-1);
    }

    r = Integer.compare(this.m_id, o.m_id);
    if (r != 0) {
      return r;
    }

    r = EComparison.compare(this.m_date, o.m_date);
    if (r != 0) {
      return r;
    }

    r = EComparison.compare(this.m_authors, o.m_authors);
    if (r != 0) {
      return r;
    }

    r = EComparison.compare(this.m_title, o.m_title);
    if (r != 0) {
      return r;
    }

    return this._compareRest(o);
  }

  /**
   * compare the rest of the bib record
   * 
   * @param o
   *          the other bib record
   * @return the result
   */
  int _compareRest(final BibRecord o) {
    int r;
    r = EComparison.compare(this.m_url, o.m_url);
    if (r != 0) {
      return r;
    }
    return EComparison.compare(this.m_doi, o.m_doi);
  }

  /** {@inheritDoc} */
  @Override
  public void toText(final ITextOutput textOut) {
    if (this.m_authors.size() > 0) {
      this.m_authors.toText(textOut);
      textOut.append(':');
      textOut.append(' ');
    }
    textOut.append('"');
    textOut.append(this.m_title);
    textOut.append(',');
    textOut.append('"');
    textOut.append(' ');
    this.m_date.toText(textOut);
    if (this.m_url != null) {
      textOut.append(", link: "); //$NON-NLS-1$
      textOut.append(this.m_url);
    }
    if (this.m_doi != null) {
      textOut.append(", doi:"); //$NON-NLS-1$
      textOut.append(this.m_doi);
    }
  }

  /**
   * Get the year of this publication
   * 
   * @return the year associated with this publication
   */
  public final int getYear() {
    return this.m_date.m_year;
  }
}
