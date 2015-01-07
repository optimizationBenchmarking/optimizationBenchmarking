package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.optimizationBenchmarking.utils.bibliography.data.BibAuthors;
import org.optimizationBenchmarking.utils.bibliography.data.BibDate;
import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentHeader;
import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentSummary;
import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentTitle;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalText;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/** the XHTML document header */
final class _XHTML10DocumentHeader extends DocumentHeader {
  /** the title start */
  private static final char[] TITLE_BEGIN = { '<', 't', 'i', 't', 'l',
      'e', '>' };
  /** the title end */
  private static final char[] TITLE_END = { '<', '/', 't', 'i', 't', 'l',
      'e', '>' };
  /** the meta author data */
  private static final char[] META_AUTHORS = { '<', 'm', 'e', 't', 'a',
      ' ', 'n', 'a', 'm', 'e', '=', '"', 'a', 'u', 't', 'h', 'o', 'r',
      '"', ' ', 'c', 'o', 'n', 't', 'e', 'n', 't', '=', '"' };
  /** the meta creator data */
  private static final char[] META_CREATOR = { '<', 'm', 'e', 't', 'a',
      ' ', 'n', 'a', 'm', 'e', '=', '"', 'D', 'C', '.', 'C', 'r', 'e',
      'a', 't', 'o', 'r', '"', ' ', 'c', 'o', 'n', 't', 'e', 'n', 't',
      '=', '"' };
  /** the meta language data */
  private static final char[] META_LANG_EN = { '<', 'm', 'e', 't', 'a',
      ' ', 'n', 'a', 'm', 'e', '=', '"', 'D', 'C', '.', 'L', 'a', 'n',
      'g', 'u', 'a', 'g', 'e', '"', ' ', 'c', 'o', 'n', 't', 'e', 'n',
      't', '=', '"', 'e', 'n', '"', '/', '>', };
  /** the meta date data */
  private static final char[] META_DATE = { '<', 'm', 'e', 't', 'a', ' ',
      'n', 'a', 'm', 'e', '=', '"', 'D', 'C', '.', 'D', 'a', 't', 'e',
      '"', ' ', 'c', 'o', 'n', 't', 'e', 'n', 't', '=', '"', };
  /** the meta description data */
  private static final char[] META_DESC = { '<', 'm', 'e', 't', 'a', ' ',
      'n', 'a', 'm', 'e', '=', '"', 'D', 'C', '.', 'D', 'e', 's', 'c',
      'r', 'i', 'p', 't', 'i', 'o', 'n', '"', ' ', 'c', 'o', 'n', 't',
      'e', 'n', 't', '=', '"', };
  /** the meta title data */
  private static final char[] META_TITLE = { '<', 'm', 'e', 't', 'a', ' ',
      'n', 'a', 'm', 'e', '=', '"', 'D', 'C', '.', 'T', 'i', 't', 'l',
      'e', '"', ' ', 'c', 'o', 'n', 't', 'e', 'n', 't', '=', '"' };

  /** the header end and body begins */
  private static final char[] HEADER_END_BODY_BEGINS = { '<', '/', 'h',
      'e', 'a', 'd', '>', '<', 'b', 'o', 'd', 'y', '>' };

  /** the authors start */
  private static final char[] AUTHORS_DIV_BEGIN = { '<', 'd', 'i', 'v',
      ' ', 'c', 'l', 'a', 's', 's', '=', '"', 'a', 'u', 't', 'h', 'o',
      'r', 's', '"', '>', 'b', 'y', '&', 'n', 'b', 's', 'p', ';' };
  /** the date start */
  private static final char[] DATE_DIV_BEGIN = { '<', 'd', 'i', 'v', ' ',
      'c', 'l', 'a', 's', 's', '=', '"', 'd', 'a', 't', 'e', '"', '>',
      'o', 'n', '&', 'n', 'b', 's', 'p', ';' };

  /** the abstract start */
  private static final char[] ABSTRACT_DIV_BEGIN = { '<', 'd', 'i', 'v',
      ' ', 'c', 'l', 'a', 's', 's', '=', '"', 'a', 'b', 's', 't', 'r',
      'a', 'c', 't', '"', '>', '<', 's', 'p', 'a', 'n', ' ', 'c', 'l',
      'a', 's', 's', '=', '"', 'a', 'b', 's', 't', 'r', 'a', 'c', 't',
      'T', 'i', 't', 'l', 'e', '"', '>', 'A', 'b', 's', 't', 'r', 'a',
      'c', 't', '.', '<', '/', 's', 'p', 'a', 'n', '>', '&', 'n', 'b',
      's', 'p', ';' };

  /** the title */
  private char[] m_title;

  /** the authors */
  private BibAuthors m_authors;

  /** the date */
  private BibDate m_date;

  /** the description */
  private char[] m_description;

  /**
   * Create a document header.
   * 
   * @param owner
   *          the owning document
   */
  _XHTML10DocumentHeader(final _XHTML10Document owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final boolean mustChildBeBuffered(final HierarchicalText child) {
    return ((child instanceof DocumentTitle) || (child instanceof DocumentSummary));
  }

  /** {@inheritDoc} */
  @Override
  protected final void processBufferedOutputFromChild(
      final HierarchicalText child, final MemoryTextOutput out) {
    if (child instanceof DocumentTitle) {
      this.m_title = out.toChars();
    } else {
      if (child instanceof DocumentSummary) {
        this.m_description = out.toChars();
      } else {
        super.processBufferedOutputFromChild(child, out);
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final void doAuthors(final BibAuthors authors) {
    super.doAuthors(authors);
    this.m_authors = authors;
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDate(final BibDate date) {
    super.doDate(date);
    this.m_date = date;
  }

  /**
   * write the calendar to a text output
   * 
   * @param cal
   *          the calendar
   * @param out
   *          the output
   */
  private static final void __writeCal(final Calendar cal,
      final ITextOutput out) {
    int i;

    i = cal.get(Calendar.YEAR);
    if (i > 0) {
      out.append(i);
      i = cal.get(Calendar.MONTH);
      if (i > 0) {
        out.append('-');
        i++;
        if (i < 10) {
          out.append('0');
        }
        out.append(i);
        i = cal.get(Calendar.DAY_OF_MONTH);
        if (i > 0) {
          out.append('-');
          if (i < 10) {
            out.append('0');
          }
          out.append(i);
        }
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    final ITextOutput out, enc;
    GregorianCalendar g;

    out = this.getTextOutput();
    enc = ((XHTML10Driver) (this.getDriver())).encode(out);

    out.append(_XHTML10DocumentHeader.META_TITLE);
    enc.append(this.m_title);
    out.append(XHTML10Driver.EMPTY_ATTRIB_TAG_END);

    if ((this.m_authors != null) && (!(this.m_authors.isEmpty()))) {
      out.append(_XHTML10DocumentHeader.META_AUTHORS);
      this.m_authors.toText(enc);
      out.append(XHTML10Driver.EMPTY_ATTRIB_TAG_END);

      out.append(_XHTML10DocumentHeader.META_CREATOR);
      this.m_authors.toText(enc);
      out.append(XHTML10Driver.EMPTY_ATTRIB_TAG_END);
    }

    if (this.m_date != null) {
      g = new GregorianCalendar();
      this.m_date.toCalendar(g);
      out.append(_XHTML10DocumentHeader.META_DATE);
      _XHTML10DocumentHeader.__writeCal(g, out);
      out.append(XHTML10Driver.EMPTY_ATTRIB_TAG_END);
    } else {
      g = null;
    }

    out.append(_XHTML10DocumentHeader.META_DESC);
    out.append(this.m_description);
    out.append(XHTML10Driver.EMPTY_ATTRIB_TAG_END);

    out.append(_XHTML10DocumentHeader.META_LANG_EN);

    out.append(_XHTML10DocumentHeader.TITLE_BEGIN);
    out.append(this.m_title);
    out.append(_XHTML10DocumentHeader.TITLE_END);

    out.append(_XHTML10DocumentHeader.HEADER_END_BODY_BEGINS);

    out.append(XHTML10Driver.SECTION_DIV_BEGIN);
    out.append(XHTML10Driver.SECTION_HEAD_DIV_BEGIN);
    out.append(XHTML10Driver.HEADLINE_BEGIN[0]);
    out.append(this.m_title);
    out.append(XHTML10Driver.HEADLINE_END[0]);
    out.append(XHTML10Driver.DIV_END);
    out.append(XHTML10Driver.SECTION_BODY_DIV_BEGIN);

    out.append(_XHTML10DocumentHeader.ABSTRACT_DIV_BEGIN);
    out.append(this.m_description);
    out.append(XHTML10Driver.DIV_END);

    if ((this.m_authors != null) && (!(this.m_authors.isEmpty()))) {
      out.append(_XHTML10DocumentHeader.AUTHORS_DIV_BEGIN);
      this.m_authors.toText(enc);
      out.append(XHTML10Driver.DIV_END);
    }

    if (g != null) {
      out.append(_XHTML10DocumentHeader.DATE_DIV_BEGIN);
      _XHTML10DocumentHeader.__writeCal(g, out);
      out.append(XHTML10Driver.DIV_END);
    }

    this.m_authors = null;
    this.m_date = null;
    this.m_description = null;
    this.m_title = null;

    super.onClose();
  }
}
