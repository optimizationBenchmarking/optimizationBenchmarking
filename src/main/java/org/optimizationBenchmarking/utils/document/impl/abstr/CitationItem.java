package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.bibliography.data.BibAuthors;
import org.optimizationBenchmarking.utils.bibliography.data.BibBook;
import org.optimizationBenchmarking.utils.bibliography.data.BibRecord;
import org.optimizationBenchmarking.utils.document.spec.ECitationMode;
import org.optimizationBenchmarking.utils.text.ESequenceMode;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.ISequenceable;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A container for a citation bibliographic record
 */
public class CitationItem implements ISequenceable {

  /** the bibliographic bibliographic record */
  private final BibRecord m_record;

  /** the citation mode */
  private final ECitationMode m_mode;

  /**
   * Create the bibliographic record
   * 
   * @param record
   *          the bibliographic record
   * @param mode
   *          the citation mode
   */
  protected CitationItem(final BibRecord record, final ECitationMode mode) {
    super();

    if (record == null) {
      throw new IllegalArgumentException(//
          "Bibliographic bibliographic record cannot be null."); //$NON-NLS-1$
    }
    this.m_record = record;

    if (mode == null) {
      throw new IllegalArgumentException(//
          "Citation mode cannot be null.");//$NON-NLS-1$
    }
    this.m_mode = mode;
  }

  /**
   * Get the citation mode
   * 
   * @return the citation mode
   */
  public final ECitationMode getMode() {
    return this.m_mode;
  }

  /**
   * Get the bibliographic record
   * 
   * @return the bibliographic record
   */
  public final BibRecord getRecord() {
    return this.m_record;
  }

  /**
   * render the author list
   * 
   * @param authors
   *          the author list, guaranteed to be non-empty
   * @param textCase
   *          the text case
   * @param out
   *          the complex output text
   * @param raw
   *          the raw, unencoded output
   */
  protected void renderAuthors(final BibAuthors authors,
      final ETextCase textCase, final ComplexText out,
      final ITextOutput raw) {
    ESequenceMode.ET_AL.appendSequence(textCase, authors, true, out);
  }

  /**
   * render the year
   * 
   * @param year
   *          the year
   * @param textCase
   *          the text case
   * @param out
   *          the complex output text
   * @param raw
   *          the raw, unencoded output
   */
  protected void renderYear(final int year, final ETextCase textCase,
      final ComplexText out, final ITextOutput raw) {
    out.append(year);
  }

  /**
   * render the id
   * 
   * @param index
   *          the index
   * @param id
   *          the id of the publication
   * @param textCase
   *          the text case
   * @param out
   *          the complex output text
   * @param raw
   *          the raw, unencoded output
   */
  protected void renderID(final int index, final String id,
      final ETextCase textCase, final ComplexText out,
      final ITextOutput raw) {
    out.append('[');
    out.append(index + 1);
    out.append(']');
  }

  /**
   * render the title
   * 
   * @param title
   *          the title
   * @param textCase
   *          the text case
   * @param out
   *          the complex output text
   * @param raw
   *          the raw, unencoded output
   */
  protected void renderTitle(final String title, final ETextCase textCase,
      final ComplexText out, final ITextOutput raw) {
    try (final InQuotes q = out.inQuotes()) {
      q.append(title);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void toSequence(final boolean isFirstInSequence,
      final boolean isLastInSequence, final ETextCase textCase,
      final ITextOutput textOut) {
    final ComplexText o;

    o = ((ComplexText) textOut);

    this.doSequence(isFirstInSequence, isLastInSequence, textCase, o,
        o._raw());
  }

  /**
   * Write this sequence item
   * 
   * @param isFirstInSequence
   *          is this the first item in the sequence?
   * @param isLastInSequence
   *          is this the last item in the sequence?
   * @param textCase
   *          the text case
   * @param out
   *          the complex output text
   * @param raw
   *          the raw, unencoded output
   */
  protected void doSequence(final boolean isFirstInSequence,
      final boolean isLastInSequence, final ETextCase textCase,
      final ComplexText out, final ITextOutput raw) {

    final BibRecord rec;
    final ECitationMode mode;
    boolean has;
    BibAuthors a;
    ETextCase tc;
    String t;

    has = false;
    mode = this.m_mode;
    rec = this.m_record;
    tc = textCase;

    if (mode.printAuthors()) {
      finder: {
        a = rec.getAuthors();
        if ((a == null) || (a.isEmpty())) {
          if (rec instanceof BibBook) {
            a = (((BibBook) (rec)).getEditors());
            if ((a == null) || (a.isEmpty())) {
              break finder;
            }
          }
        }
        this.renderAuthors(a, textCase, out, raw);
        tc = tc.nextCase();
        has = true;
      }
    }

    if (mode.printYear()) {
      if (has) {
        out.append(' ');
      } else {
        has = true;
      }

      this.renderYear(rec.getYear(), textCase, out, raw);
      tc = tc.nextCase();
    }

    if (mode.printTitle()) {
      if (has) {
        out.append(' ');
      }

      t = rec.getTitle();
      if ((t != null) && (t.length() > 0)) {
        this.renderTitle(t, textCase, out, raw);
        tc = tc.nextCase();
        has = true;
      }
    }

    if (mode.printID()) {
      if (has) {
        out.appendNonBreakingSpace();
      } else {
        has = true;
      }

      this.renderID(rec.getID(), rec.getKey(), tc, out, raw);
    } else {
      this.dontRenderID(rec.getID(), rec.getKey(), tc, out, raw);
    }
  }

  /**
   * this method is called if the ID is not to be rendered
   * 
   * @param index
   *          the index
   * @param id
   *          the id
   * @param textCase
   *          the text case
   * @param out
   *          the output destination
   * @param raw
   *          the raw text output
   */
  protected void dontRenderID(final int index, final String id,
      final ETextCase textCase, final ComplexText out,
      final ITextOutput raw) {
    //
  }
}
