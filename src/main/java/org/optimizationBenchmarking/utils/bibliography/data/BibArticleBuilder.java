package org.optimizationBenchmarking.utils.bibliography.data;

import org.optimizationBenchmarking.utils.hierarchy.BuilderFSM;
import org.optimizationBenchmarking.utils.hierarchy.FSM;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/** A builder for article records */
public final class BibArticleBuilder extends BibRecordWithPublisherBuilder {

  /** the journal has been set */
  private static final int FLAG_JOURNAL_SET = (BibRecordWithPublisherBuilder.FLAG_PUBLISHER_LAST << 1);
  /** the volume has been set */
  private static final int FLAG_VOLUME_SET = (BibArticleBuilder.FLAG_JOURNAL_SET << 1);
  /** the number has been set */
  private static final int FLAG_NUMBER_SET = (BibArticleBuilder.FLAG_VOLUME_SET << 1);
  /** the issn has been set */
  private static final int FLAG_ISSN_SET = (BibArticleBuilder.FLAG_NUMBER_SET << 1);
  /** the start page has been set */
  private static final int FLAG_START_PAGE_SET = (BibArticleBuilder.FLAG_ISSN_SET << 1);
  /** the end pagehas been set */
  private static final int FLAG_END_PAGE_SET = (BibArticleBuilder.FLAG_START_PAGE_SET << 1);

  /** the journal */
  String m_journal;
  /** the journal issn */
  String m_issn;
  /** the volume */
  String m_volume;
  /** the number */
  String m_number;
  /** the start page */
  String m_startPage;
  /** the end page */
  String m_endPage;

  /**
   * create the article builder
   *
   * @param owner
   *          the owner
   */
  BibArticleBuilder(final BuilderFSM<?> owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void fsmFlagsAppendName(final int flagValue,
      final int flagIndex, final MemoryTextOutput append) {
    switch (flagValue) {
      case FLAG_JOURNAL_SET: {
        append.append("journalSet"); //$NON-NLS-1$
        return;
      }
      case FLAG_VOLUME_SET: {
        append.append("volumeSet"); //$NON-NLS-1$
        return;
      }
      case FLAG_NUMBER_SET: {
        append.append("numberSet"); //$NON-NLS-1$
        return;
      }
      case FLAG_ISSN_SET: {
        append.append("issnSet"); //$NON-NLS-1$
        return;
      }
      case FLAG_START_PAGE_SET: {
        append.append("startPageSet"); //$NON-NLS-1$
        return;
      }
      case FLAG_END_PAGE_SET: {
        append.append("endPageSet"); //$NON-NLS-1$
        return;
      }
      default: {
        super.fsmFlagsAppendName(flagValue, flagIndex, append);
      }
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
        (BibArticleBuilder.FLAG_VOLUME_SET),
        BibArticleBuilder.FLAG_VOLUME_SET, FSM.FLAG_NOTHING);
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    if ((this.m_volume = this.normalize(volume)) == null) {
      throw new IllegalArgumentException(//
          "Volume cannot be set to empty or null, but '" //$NON-NLS-1$
          + volume + "' is."); //$NON-NLS-1$
    }
  }

  /**
   * Set the number
   *
   * @param number
   *          the number
   */
  public synchronized final void setNumber(final String number) {
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        (BibArticleBuilder.FLAG_NUMBER_SET),
        BibArticleBuilder.FLAG_NUMBER_SET, FSM.FLAG_NOTHING);
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    if ((this.m_number = this.normalize(number)) == null) {
      throw new IllegalArgumentException(//
          "Number cannot be set to empty or null, but '" //$NON-NLS-1$
          + number + "' is."); //$NON-NLS-1$
    }
  }

  /**
   * Set the journal
   *
   * @param journal
   *          the journal
   */
  public synchronized final void setJournal(final String journal) {
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        (BibArticleBuilder.FLAG_JOURNAL_SET),
        BibArticleBuilder.FLAG_JOURNAL_SET, FSM.FLAG_NOTHING);
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    if ((this.m_journal = this.normalize(journal)) == null) {
      throw new IllegalArgumentException(//
          "Journal cannot be set to empty or null, but '" //$NON-NLS-1$
          + journal + "' is."); //$NON-NLS-1$
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
        (BibArticleBuilder.FLAG_ISSN_SET),
        BibArticleBuilder.FLAG_ISSN_SET, FSM.FLAG_NOTHING);
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    if ((this.m_issn = this.normalize(issn)) == null) {
      throw new IllegalArgumentException(//
          "ISSN cannot be set to empty or null, but '" //$NON-NLS-1$
          + issn + "' is."); //$NON-NLS-1$
    }
  }

  /**
   * Set the start page
   *
   * @param startPage
   *          the start page
   */
  public synchronized final void setStartPage(final String startPage) {
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        (BibArticleBuilder.FLAG_START_PAGE_SET),
        BibArticleBuilder.FLAG_START_PAGE_SET, FSM.FLAG_NOTHING);
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    if ((this.m_startPage = this.normalize(startPage)) == null) {
      throw new IllegalArgumentException(//
          "Start page cannot be set to empty or null, but '" //$NON-NLS-1$
          + startPage + "' is."); //$NON-NLS-1$
    }
  }

  /**
   * Set the end page
   *
   * @param endPage
   *          the end page
   */
  public synchronized final void setEndPage(final String endPage) {
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        (BibArticleBuilder.FLAG_END_PAGE_SET),
        BibArticleBuilder.FLAG_END_PAGE_SET, FSM.FLAG_NOTHING);
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    if ((this.m_endPage = this.normalize(endPage)) == null) {
      throw new IllegalArgumentException(//
          "End page cannot be set to empty or null, but '" //$NON-NLS-1$
          + endPage + "' is."); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  public final synchronized BibArticle getResult() {
    return ((BibArticle) (super.getResult()));
  }

  /** {@inheritDoc} */
  @Override
  final BibArticle _doCompile() {
    return new BibArticle(true, this.m_authors, this.m_title, this.m_date,
        this.m_journal, this.m_issn, this.m_volume, this.m_number,
        this.m_startPage, this.m_endPage, this.m_publisher, this.m_url,
        this.m_doi);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final BibDateBuilder date() {
    return super.date();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void setDate(final BibDate date) {
    super.setDate(date);
  }
}
