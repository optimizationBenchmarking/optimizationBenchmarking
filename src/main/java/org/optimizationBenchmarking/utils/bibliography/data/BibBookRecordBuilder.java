package org.optimizationBenchmarking.utils.bibliography.data;

import org.optimizationBenchmarking.utils.hierarchy.BuilderFSM;
import org.optimizationBenchmarking.utils.hierarchy.FSM;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * A base class for builders for things that are bibliographic for a books,
 * such as books, collections, and proceedings.
 */
public abstract class BibBookRecordBuilder extends
    BibRecordWithPublisherBuilder {

  /** the editors have been set */
  private static final int FLAG_EDITORS_SET = (BibRecordWithPublisherBuilder.FLAG_PUBLISHER_LAST << 1);
  /** the series has been set */
  private static final int FLAG_SERIES_SET = (BibBookRecordBuilder.FLAG_EDITORS_SET << 1);
  /** the volume has been set */
  private static final int FLAG_VOLUME_SET = (BibBookRecordBuilder.FLAG_SERIES_SET << 1);
  /** the edition has been set */
  private static final int FLAG_EDITION_SET = (BibBookRecordBuilder.FLAG_VOLUME_SET << 1);
  /** the issn has been set */
  private static final int FLAG_ISSN_SET = (BibBookRecordBuilder.FLAG_EDITION_SET << 1);
  /** the isbn has been set */
  private static final int FLAG_ISBN_SET = (BibBookRecordBuilder.FLAG_ISSN_SET << 1);
  /** the last flag occupied by book records */
  static final int FLAG_BOOK_RECORD_LAST = BibBookRecordBuilder.FLAG_ISBN_SET;

  /** the editors */
  BibAuthors m_editors;

  /** the series */
  String m_series;

  /** the volume */
  String m_volume;

  /** the edition */
  String m_edition;

  /** the isbn */
  String m_isbn;

  /** the issn of the series */
  String m_issn;

  /**
   * create the website builder
   *
   * @param owner
   *          the owner
   */
  BibBookRecordBuilder(final BuilderFSM<?> owner) {
    super(owner);
    this.m_editors = BibAuthors.EMPTY_AUTHORS;
  }

  /** {@inheritDoc} */
  @Override
  protected void fsmFlagsAppendName(final int flagValue,
      final int flagIndex, final MemoryTextOutput append) {
    switch (flagValue) {
      case FLAG_EDITORS_SET: {
        append.append("editorsSet"); //$NON-NLS-1$
        return;
      }
      case FLAG_SERIES_SET: {
        append.append("seriesSet"); //$NON-NLS-1$
        return;
      }
      case FLAG_VOLUME_SET: {
        append.append("volumeSet"); //$NON-NLS-1$
        return;
      }
      case FLAG_EDITION_SET: {
        append.append("editionSet"); //$NON-NLS-1$
        return;
      }
      case FLAG_ISSN_SET: {
        append.append("issnSet"); //$NON-NLS-1$
        return;
      }
      case FLAG_ISBN_SET: {
        append.append("isbnSet"); //$NON-NLS-1$
        return;
      }
      default: {
        super.fsmFlagsAppendName(flagValue, flagIndex, append);
      }
    }
  }

  /**
   * Set the series
   *
   * @param series
   *          the series
   */
  public synchronized final void setSeries(final String series) {
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        (BibBookRecordBuilder.FLAG_SERIES_SET),
        BibBookRecordBuilder.FLAG_SERIES_SET, FSM.FLAG_NOTHING);
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    if ((this.m_series = this.normalize(series)) == null) {
      throw new IllegalArgumentException(//
          "Series name cannot be set to empty or null, but '" //$NON-NLS-1$
              + series + "' is."); //$NON-NLS-1$
    }
  }

  /**
   * Set the volume
   *
   * @param volume
   *          the volume
   */
  public synchronized final void setVolume(final String volume) {
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        (BibBookRecordBuilder.FLAG_VOLUME_SET),
        BibBookRecordBuilder.FLAG_VOLUME_SET, FSM.FLAG_NOTHING);
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    if ((this.m_volume = this.normalize(volume)) == null) {
      throw new IllegalArgumentException(//
          "Volume name cannot be set to empty or null, but '" //$NON-NLS-1$
              + volume + "' is."); //$NON-NLS-1$
    }
  }

  /**
   * Set the edition
   *
   * @param edition
   *          the edition
   */
  public synchronized final void setEdition(final String edition) {
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        (BibBookRecordBuilder.FLAG_EDITION_SET),
        BibBookRecordBuilder.FLAG_EDITION_SET, FSM.FLAG_NOTHING);
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    if ((this.m_edition = this.normalize(edition)) == null) {
      throw new IllegalArgumentException(//
          "Edition name cannot be set to empty or null, but '" //$NON-NLS-1$
              + edition + "' is."); //$NON-NLS-1$
    }
  }

  /**
   * Set the issn
   *
   * @param issn
   *          the issn
   */
  public synchronized final void setISSN(final String issn) {
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        (BibBookRecordBuilder.FLAG_ISSN_SET),
        BibBookRecordBuilder.FLAG_ISSN_SET, FSM.FLAG_NOTHING);
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    if ((this.m_issn = this.normalize(issn)) == null) {
      throw new IllegalArgumentException(//
          "ISSN name cannot be set to empty or null, but '" //$NON-NLS-1$
              + issn + "' is."); //$NON-NLS-1$
    }
  }

  /**
   * Create the editors setter
   *
   * @return the editors setter
   */
  public synchronized final BibAuthorsBuilder setEditors() {
    this.fsmFlagsAssertFalse(BibBookRecordBuilder.FLAG_EDITORS_SET);
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    return new BibAuthorsBuilder(this, 1);
  }

  /**
   * Set the editors
   *
   * @param editors
   *          the editors list
   */
  public synchronized final void setEditors(final BibAuthors editors) {
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        BibBookRecordBuilder.FLAG_EDITORS_SET,
        BibBookRecordBuilder.FLAG_EDITORS_SET, FSM.FLAG_NOTHING);
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    if ((this.m_editors = this.normalize(editors)) == null) {
      throw new IllegalArgumentException(//
          "Cannot set null editors."); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  final void _handleAuthors(final BibAuthors authors, final int tag) {
    if (tag == 1) {
      this.setEditors(authors);
    } else {
      super._handleAuthors(authors, tag);
    }
  }

  /**
   * Set the isbn
   *
   * @param isbn
   *          the isbn
   */
  public synchronized final void setISBN(final String isbn) {
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        (BibBookRecordBuilder.FLAG_ISBN_SET),
        BibBookRecordBuilder.FLAG_ISBN_SET, FSM.FLAG_NOTHING);
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    if ((this.m_isbn = this.normalize(isbn)) == null) {
      throw new IllegalArgumentException(//
          "ISBN name cannot be set to empty or null, but '" //$NON-NLS-1$
              + isbn + "' is."); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  public synchronized BibBookRecord getResult() {
    return ((BibBookRecord) (super.getResult()));
  }
}
