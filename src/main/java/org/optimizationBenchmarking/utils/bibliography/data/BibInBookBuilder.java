package org.optimizationBenchmarking.utils.bibliography.data;

import org.optimizationBenchmarking.utils.hierarchy.BuilderFSM;
import org.optimizationBenchmarking.utils.hierarchy.FSM;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * A base class for builders of bibliographic records of things that
 * appeared in a book, i.e., chapters or conference papers.
 */
public abstract class BibInBookBuilder extends BibRecordBuilder {

  /** the book has been set */
  static final int FLAG_BOOK_SET = (BibRecordBuilder.FLAG_RECORD_LAST << 1);
  /** the start page has been set */
  private static final int FLAG_START_PAGE_SET = (BibInBookBuilder.FLAG_BOOK_SET << 1);
  /** the end page has been set */
  private static final int FLAG_END_PAGE_SET = (BibInBookBuilder.FLAG_START_PAGE_SET << 1);
  /** the chapter has been set */
  private static final int FLAG_CHAPTER_SET = (BibInBookBuilder.FLAG_END_PAGE_SET << 1);

  /** the book */
  BibBookRecord m_book;

  /** the start page */
  String m_startPage;

  /** the end page */
  String m_endPage;

  /** the chapter */
  String m_chapter;

  /**
   * create the website builder
   *
   * @param owner
   *          the owner
   */
  BibInBookBuilder(final BuilderFSM<?> owner) {
    super(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected void fsmFlagsAppendName(final int flagValue,
      final int flagIndex, final MemoryTextOutput append) {
    switch (flagValue) {
      case FLAG_BOOK_SET: {
        append.append("bookSet"); //$NON-NLS-1$
        return;
      }
      case FLAG_START_PAGE_SET: {
        append.append("startPageSet"); //$NON-NLS-1$
        return;
      }
      case FLAG_END_PAGE_SET: {
        append.append("endPaeSet"); //$NON-NLS-1$
        return;
      }
      case FLAG_CHAPTER_SET: {
        append.append("chapterSet"); //$NON-NLS-1$
        return;
      }
      default: {
        super.fsmFlagsAppendName(flagValue, flagIndex, append);
      }
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
        (BibInBookBuilder.FLAG_START_PAGE_SET),
        BibInBookBuilder.FLAG_START_PAGE_SET, FSM.FLAG_NOTHING);
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
        (BibInBookBuilder.FLAG_END_PAGE_SET),
        BibInBookBuilder.FLAG_END_PAGE_SET, FSM.FLAG_NOTHING);
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    if ((this.m_endPage = this.normalize(endPage)) == null) {
      throw new IllegalArgumentException(//
          "End page cannot be set to empty or null, but '" //$NON-NLS-1$
              + endPage + "' is."); //$NON-NLS-1$
    }
  }

  /**
   * Set the chapter
   *
   * @param chapter
   *          the chapter
   */
  public synchronized final void setChapter(final String chapter) {
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        (BibInBookBuilder.FLAG_CHAPTER_SET),
        BibInBookBuilder.FLAG_CHAPTER_SET, FSM.FLAG_NOTHING);
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    if ((this.m_chapter = this.normalize(chapter)) == null) {
      throw new IllegalArgumentException(//
          "Chapter name cannot be set to empty or null, but '" //$NON-NLS-1$
              + chapter + "' is."); //$NON-NLS-1$
    }
  }

  /**
   * Set the book
   *
   * @param book
   *          the book
   * @param mustClone
   *          do we need to clone?
   */
  synchronized final void _bookSet(final BibBookRecord book,
      final boolean mustClone) {
    this.fsmFlagsAssertAndUpdate(FSM.FLAG_NOTHING,
        (BibInBookBuilder.FLAG_BOOK_SET), BibInBookBuilder.FLAG_BOOK_SET,
        FSM.FLAG_NOTHING);
    this.fsmStateAssert(BuilderFSM.STATE_OPEN);
    if (book == null) {
      throw new IllegalArgumentException(//
          "Book cannot be set to null."); //$NON-NLS-1$
    }
    this.m_book = ((BibBookRecord) (this._addOrRegister(book, false,
        mustClone)));
  }

}
