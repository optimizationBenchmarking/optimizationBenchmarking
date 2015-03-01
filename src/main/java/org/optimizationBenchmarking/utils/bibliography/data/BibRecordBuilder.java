package org.optimizationBenchmarking.utils.bibliography.data;

import java.net.URI;
import java.net.URL;

import org.optimizationBenchmarking.utils.error.ErrorUtils;
import org.optimizationBenchmarking.utils.hierarchy.BuilderFSM;
import org.optimizationBenchmarking.utils.hierarchy.FSM;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalFSM;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/** A builder for bibliography records objects. */
public abstract class BibRecordBuilder extends BuilderFSM<BibRecord> {

  /** the authors have been set */
  private static final int FLAG_AUTHORS_SET = (FSM.FLAG_NOTHING + 1);
  /** the title has been set */
  static final int FLAG_TITLE_SET = (BibRecordBuilder.FLAG_AUTHORS_SET << 1);
  /** the date has been set */
  static final int FLAG_DATE_SET = (BibRecordBuilder.FLAG_TITLE_SET << 1);
  /** the url has been set */
  static final int FLAG_URL_SET = (BibRecordBuilder.FLAG_DATE_SET << 1);
  /** the doi has been set */
  static final int FLAG_DOI_SET = (BibRecordBuilder.FLAG_URL_SET << 1);
  /** the last flag used by the record */
  static final int FLAG_RECORD_LAST = BibRecordBuilder.FLAG_DOI_SET;

  /** the authors */
  BibAuthors m_authors;

  /** the title */
  String m_title;

  /** the start date */
  BibDate m_date;

  /** the url */
  URI m_url;

  /** the doi */
  String m_doi;

  /**
   * create the author builder
   * 
   * @param owner
   *          the owner
   */
  BibRecordBuilder(final BuilderFSM<?> owner) {
    super(owner);
    if ((owner instanceof BibRecordBuilder)
        || (owner instanceof BibliographyBuilder)) {
      this.m_authors = BibAuthors.EMPTY_AUTHORS;
    } else {
      throw new IllegalArgumentException("Owner " + owner + //$NON-NLS-1$
          " not permitted."); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void fsmFlagsAppendName(final int flagValue,
      final int flagIndex, final MemoryTextOutput append) {
    switch (flagValue) {
      case FLAG_AUTHORS_SET: {
        append.append("authorsSet"); //$NON-NLS-1$
        return;
      }
      case FLAG_TITLE_SET: {
        append.append("titleSet"); //$NON-NLS-1$
        return;
      }
      case FLAG_DATE_SET: {
        append.append("dateSet"); //$NON-NLS-1$
        return;
      }
      case FLAG_URL_SET: {
        append.append("urlSet"); //$NON-NLS-1$
        return;
      }
      case FLAG_DOI_SET: {
        append.append("doiSet"); //$NON-NLS-1$
        return;
      }
      default: {
        super.fsmFlagsAppendName(flagValue, flagIndex, append);
      }
    }
  }

  /**
   * Create the authors setter
   * 
   * @return the authors setter
   */
  public synchronized final BibAuthorsBuilder setAuthors() {
    this.fsmFlagsAssertFalse(BibRecordBuilder.FLAG_AUTHORS_SET);
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    return new BibAuthorsBuilder(this, 0);
  }

  /**
   * Set the authors
   * 
   * @param authors
   *          the authors list
   */
  public synchronized final void setAuthors(final BibAuthors authors) {
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        BibRecordBuilder.FLAG_AUTHORS_SET,
        BibRecordBuilder.FLAG_AUTHORS_SET, FSM.FLAG_NOTHING);
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);

    if ((this.m_authors = this.normalize(authors)) == null) {
      throw new IllegalArgumentException(//
          "Cannot set null authors."); //$NON-NLS-1$
    }
  }

  /**
   * Create the date setter
   * 
   * @return the date setter
   */
  synchronized BibDateBuilder date() {
    this.fsmFlagsAssertFalse(BibRecordBuilder.FLAG_DATE_SET);
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    return new BibDateBuilder(this, 0);
  }

  /**
   * Set the date
   * 
   * @param date
   *          the date
   */
  synchronized void setDate(final BibDate date) {
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        (BibRecordBuilder.FLAG_DATE_SET), BibRecordBuilder.FLAG_DATE_SET,
        FSM.FLAG_NOTHING);
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);

    if ((this.m_date = this.normalize(date)) == null) {
      throw new IllegalArgumentException("Cannot set null date."); //$NON-NLS-1$
    }
  }

  /**
   * Set the title
   * 
   * @param title
   *          the title
   */
  public synchronized final void setTitle(final String title) {
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        (BibRecordBuilder.FLAG_TITLE_SET),
        BibRecordBuilder.FLAG_TITLE_SET, FSM.FLAG_NOTHING);
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    if ((this.m_title = this.normalize(title)) == null) {
      throw new IllegalArgumentException(//
          "DocumentTitle cannot be empty or null, but '" //$NON-NLS-1$
              + title + "' is."); //$NON-NLS-1$
    }
  }

  /**
   * Set the url
   * 
   * @param url
   *          the url
   */
  public synchronized final void setURL(final URI url) {
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        (BibRecordBuilder.FLAG_URL_SET), BibRecordBuilder.FLAG_URL_SET,
        FSM.FLAG_NOTHING);
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    if ((this.m_url = this.normalize(BibRecord._makeURL(url))) == null) {
      throw new IllegalArgumentException(//
          "URL cannot be set to null"); //$NON-NLS-1$
    }
  }

  /**
   * Set the url
   * 
   * @param url
   *          the url
   */
  public final void setURL(final URL url) {
    URI u;

    try {
      u = url.toURI();
    } catch (final Throwable t) {
      ErrorUtils
          .throwRuntimeException(//
              "Error while converting document URL to URI for bibliograpy record.", //$NON-NLS-1$
              t);
      return;
    }

    this.setURL(u);
  }

  /**
   * Set the url
   * 
   * @param url
   *          the url
   */
  public final void setURL(final String url) {
    URI u;

    try {
      u = URI.create(url);
    } catch (final Throwable t) {
      ErrorUtils
          .throwRuntimeException(//
              "Error while converting document URL string to URI for bibliograpy record.", //$NON-NLS-1$
              t);
      return;
    }

    this.setURL(u);
  }

  /**
   * Set the doi
   * 
   * @param doi
   *          the title
   */
  public synchronized final void setDOI(final String doi) {
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        (BibRecordBuilder.FLAG_DOI_SET), BibRecordBuilder.FLAG_DOI_SET,
        FSM.FLAG_NOTHING);
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    if ((this.m_doi = this.normalize(BibRecord._makeDOI(doi))) == null) {
      throw new IllegalArgumentException(//
          "DOI cannot be set to empty or null, but '" //$NON-NLS-1$
              + doi + "' is."); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final synchronized void beforeChildOpens(
      final HierarchicalFSM child, final boolean hasOtherChildren) {
    super.beforeChildOpens(child, hasOtherChildren);

    if ((child instanceof BibAuthorsBuilder) || //
        (child instanceof BibDateBuilder)) {
      return;
    }

    this._handleBeforeChildOpens(child);
  }

  /**
   * handle before child opens
   * 
   * @param child
   *          the child
   */
  void _handleBeforeChildOpens(final HierarchicalFSM child) {
    this.throwChildNotAllowed(child);
  }

  /**
   * handle a date with a given tag
   * 
   * @param date
   *          the date
   * @param tag
   *          the tag
   */
  void _handleDate(final BibDate date, final int tag) {
    if (tag == 0) {
      this.setDate(date);
    } else {
      throw new IllegalArgumentException("Date with tag " + tag + //$NON-NLS-1$
          " cannot be handled."); //$NON-NLS-1$
    }
  }

  /**
   * handle a author set with a given tag
   * 
   * @param authors
   *          the authors
   * @param tag
   *          the tag
   */
  void _handleAuthors(final BibAuthors authors, final int tag) {
    if (tag == 0) {
      this.setAuthors(authors);
    } else {
      throw new IllegalArgumentException("Authors with tag " + tag + //$NON-NLS-1$
          " cannot be handled."); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final synchronized void afterChildClosed(
      final HierarchicalFSM child) {
    final BibDateBuilder b;
    final BibAuthorsBuilder a;

    super.afterChildClosed(child);

    if (child instanceof BibAuthorsBuilder) {
      a = ((BibAuthorsBuilder) child);
      this._handleAuthors(a.getResult(), a.m_tag);
      return;
    }
    if (child instanceof BibDateBuilder) {
      b = ((BibDateBuilder) child);
      this._handleDate(b.getResult(), b.m_tag);
      return;
    }
    this._handleAfterChildClosed(child);
  }

  /**
   * handle after child closed
   * 
   * @param child
   *          the child
   */
  void _handleAfterChildClosed(final HierarchicalFSM child) {
    this.throwChildNotAllowed(child);
  }

  /**
   * Build the product of this builder
   * 
   * @return the product
   */
  abstract BibRecord _doCompile();

  /**
   * Add a bib record
   * 
   * @param rec
   *          the record
   * @param canAdd
   *          can the record be added
   * @param mustClone
   *          do we need to clone?
   * @return the registered record
   */
  @SuppressWarnings("resource")
  final BibRecord _addOrRegister(final BibRecord rec,
      final boolean canAdd, final boolean mustClone) {
    final BibliographyBuilder b;
    HierarchicalFSM o;
    boolean shouldAdd;
    BibRecord res;

    shouldAdd = canAdd;
    for (o = this.getOwner(); o != null;) {

      if (o instanceof BibliographyBuilder) {
        b = ((BibliographyBuilder) o);
        if (shouldAdd) {
          res = b._add(rec, mustClone);
        } else {
          res = b._register(rec, mustClone);
        }
        if (res == null) {
          throw new IllegalStateException();
        }
        return res;
      }

      if (o instanceof BibRecordBuilder) {
        o = ((BibRecordBuilder) o).getOwner();
        shouldAdd = false;
        continue;
      }

      break;
    }

    throw new IllegalStateException(//
        "A bibliography record builder cannot exist alone, it needs a BibliographyBuilder or another BibRecordBuilder as owner."); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  protected final BibRecord compile() {
    return this._addOrRegister(this._doCompile(), true, false);
  }
}
