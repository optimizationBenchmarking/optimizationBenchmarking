package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import java.net.URI;

import org.optimizationBenchmarking.utils.bibliography.data.BibArticle;
import org.optimizationBenchmarking.utils.bibliography.data.BibAuthors;
import org.optimizationBenchmarking.utils.bibliography.data.BibBook;
import org.optimizationBenchmarking.utils.bibliography.data.BibDate;
import org.optimizationBenchmarking.utils.bibliography.data.BibInCollection;
import org.optimizationBenchmarking.utils.bibliography.data.BibInProceedings;
import org.optimizationBenchmarking.utils.bibliography.data.BibOrganization;
import org.optimizationBenchmarking.utils.bibliography.data.BibProceedings;
import org.optimizationBenchmarking.utils.bibliography.data.BibRecord;
import org.optimizationBenchmarking.utils.bibliography.data.BibTechReport;
import org.optimizationBenchmarking.utils.bibliography.data.BibThesis;
import org.optimizationBenchmarking.utils.bibliography.data.BibWebsite;
import org.optimizationBenchmarking.utils.bibliography.data.EBibMonth;
import org.optimizationBenchmarking.utils.bibliography.data.EBibQuarter;
import org.optimizationBenchmarking.utils.bibliography.data.EThesisType;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.text.ESequenceMode;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.charset.QuotationMarks;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * The appendr for bibliography. <@javaAuthorVersion/>
 */
final class _XHTML10Bibliography {

  /** begin the title */
  private static final char[] BEFORE_TITLE = { ':', ' ' };

  /** the editors */
  private static final char[] EDITORS = { ',', ' ', 'e', 'd', 'i', 't',
      'o', 'r', 's' };

  /** the dash */
  private static final char[] M_DASH = { '&', 'm', 'd', 'a', 's', 'h',
      ';', };

  /** the small separator */
  private static final char[] SMALL_SEP = { ',', ' ' };

  /** the in */
  private static final char[] IN = { ' ', 'i', 'n', '&', 'n', 'b', 's',
      'p', ';', };

  /** the article */
  private static final char[] SPAN_ARTICLE = { '<', 's', 'p', 'a', 'n',
      ' ', 'c', 'l', 'a', 's', 's', '=', '"', 'a', 'r', 't', 'i', 'c',
      'l', 'e', '"', '>' };
  /** the in collection */
  private static final char[] SPAN_BOOK = { '<', 's', 'p', 'a', 'n', ' ',
      'c', 'l', 'a', 's', 's', '=', '"', 'b', 'o', 'o', 'k', '"', '>' };
  /** the in collection */
  private static final char[] SPAN_IN_COLLECTION = { '<', 's', 'p', 'a',
      'n', ' ', 'c', 'l', 'a', 's', 's', '=', '"', 'i', 'n', 'C', 'o',
      'l', 'l', 'e', 'c', 't', 'i', 'o', 'n', '"', '>' };
  /** the in proceedings */
  private static final char[] SPAN_IN_PROCEEDINGS = { '<', 's', 'p', 'a',
      'n', ' ', 'c', 'l', 'a', 's', 's', '=', '"', 'i', 'n', 'P', 'r',
      'o', 'c', 'e', 'e', 'd', 'i', 'n', 'g', 's', '"', '>' };
  /** the proceedings */
  private static final char[] SPAN_PROCEEDINGS = { '<', 's', 'p', 'a',
      'n', ' ', 'c', 'l', 'a', 's', 's', '=', '"', 'p', 'r', 'o', 'c',
      'e', 'e', 'd', 'i', 'n', 'g', 's', '"', '>' };
  /** the tech report */
  private static final char[] SPAN_TECH_REPORT = { '<', 's', 'p', 'a',
      'n', ' ', 'c', 'l', 'a', 's', 's', '=', '"', 't', 'e', 'c', 'h',
      'R', 'e', 'p', 'o', 'r', 't', '"', '>' };
  /** the thesis */
  private static final char[] SPAN_THESIS = { '<', 's', 'p', 'a', 'n',
      ' ', 'c', 'l', 'a', 's', 's', '=', '"', 't', 'h', 'e', 's', 'i',
      's', '"', '>' };
  /** the website */
  private static final char[] SPAN_WEBSITE = { '<', 's', 'p', 'a', 'n',
      ' ', 'c', 'l', 'a', 's', 's', '=', '"', 'w', 'e', 'b', 's', 'i',
      't', 'e', '"', '>' };
  /** the authors */
  private static final char[] SPAN_AUTHORS = { '<', 's', 'p', 'a', 'n',
      ' ', 'c', 'l', 'a', 's', 's', '=', '"', 'a', 'u', 't', 'h', 'o',
      'r', 's', '"', '>' };
  /** the author */
  private static final char[] SPAN_AUTHOR = { '<', 's', 'p', 'a', 'n',
      ' ', 'c', 'l', 'a', 's', 's', '=', '"', 'a', 'u', 't', 'h', 'o',
      'r', '"', '>' };
  /** the editors */
  private static final char[] SPAN_EDITORS = { '<', 's', 'p', 'a', 'n',
      ' ', 'c', 'l', 'a', 's', 's', '=', '"', 'e', 'd', 'i', 't', 'o',
      'r', 's', '"', '>' };
  /** the editor */
  private static final char[] SPAN_EDITOR = { '<', 's', 'p', 'a', 'n',
      ' ', 'c', 'l', 'a', 's', 's', '=', '"', 'e', 'd', 'i', 't', 'o',
      'r', '"', '>' };
  /** the title */
  private static final char[] SPAN_TITLE = { '<', 's', 'p', 'a', 'n', ' ',
      'c', 'l', 'a', 's', 's', '=', '"', 't', 'i', 't', 'l', 'e', '"', '>' };
  /** the book title */
  private static final char[] SPAN_BOOK_TITLE = { '<', 's', 'p', 'a', 'n',
      ' ', 'c', 'l', 'a', 's', 's', '=', '"', 'b', 'o', 'o', 'k', 'T',
      'i', 't', 'l', 'e', '"', '>' };
  /** the date */
  private static final char[] SPAN_DATE = { '<', 's', 'p', 'a', 'n', ' ',
      'c', 'l', 'a', 's', 's', '=', '"', 'd', 'a', 't', 'e', '"', '>' };
  /** the date */
  private static final char[] SPAN_JOURNAL = { '<', 's', 'p', 'a', 'n',
      ' ', 'c', 'l', 'a', 's', 's', '=', '"', 'j', 'o', 'u', 'r', 'n',
      'a', 'l', '"', '>' };
  /** the pages */
  private static final char[] SPAN_PAGES = { '<', 's', 'p', 'a', 'n', ' ',
      'c', 'l', 'a', 's', 's', '=', '"', 'p', 'a', 'g', 'e', 's', '"',
      '>', };
  /** the page */
  private static final char[] PAGE = { 'p', 'a', 'g', 'e', '&', 'n', 'b',
      's', 'p', ';' };
  /** the page */
  private static final char[] PAGES = { 'p', 'a', 'g', 'e', 's', '&', 'n',
      'b', 's', 'p', ';' };

  /** the chapter */
  private static final char[] SPAN_CHAPTER = { '<', 's', 'p', 'a', 'n',
      ' ', 'c', 'l', 'a', 's', 's', '=', '"', 'c', 'h', 'a', 'p', 't',
      'e', 'r', '"', '>', 'c', 'h', 'a', 'p', 't', 'e', 'r', '&', 'n',
      'b', 's', 'p', ';' };
  /** the journal volume */
  private static final char[] SPAN_JOURNAL_VOLUME = { '<', 's', 'p', 'a',
      'n', ' ', 'c', 'l', 'a', 's', 's', '=', '"', 'j', 'v', 'o', 'l',
      'u', 'm', 'e', '"', '>' };
  /** the issue */
  private static final char[] SPAN_ISSUE = { '<', 's', 'p', 'a', 'n', ' ',
      'c', 'l', 'a', 's', 's', '=', '"', 'i', 's', 's', 'u', 'e', '"', '>' };
  /** the edition */
  private static final char[] SPAN_EDITION = { '<', 's', 'p', 'a', 'n',
      ' ', 'c', 'l', 'a', 's', 's', '=', '"', 'e', 'd', 'i', 't', 'i',
      'o', 'n', '"', '>' };
  /** the series */
  private static final char[] SPAN_SERIES = { '<', 's', 'p', 'a', 'n',
      ' ', 'c', 'l', 'a', 's', 's', '=', '"', 's', 'e', 'r', 'i', 'e',
      's', '"', '>' };
  /** the series */
  private static final char[] SPAN_RSERIES = { '<', 's', 'p', 'a', 'n',
      ' ', 'c', 'l', 'a', 's', 's', '=', '"', 'r', 's', 'e', 'r', 'i',
      'e', 's', '"', '>' };
  /** the series volume */
  private static final char[] SPAN_SERIES_VOLUME = { '<', 's', 'p', 'a',
      'n', ' ', 'c', 'l', 'a', 's', 's', '=', '"', 's', 'v', 'o', 'l',
      'u', 'm', 'e', '"', '>', 'v', 'o', 'l', 'u', 'm', 'e', '&', 'n',
      'b', 's', 'p', ';' };
  /** the report number */
  private static final char[] SPAN_REPORT_NUMBER = { '<', 's', 'p', 'a',
      'n', ' ', 'c', 'l', 'a', 's', 's', '=', '"', 'r', 'n', 'u', 'm',
      'b', 'e', 'r', '"', '>', 'n', 'u', 'm', 'b', 'e', 'r', '&', 'n',
      'b', 's', 'p', ';' };
  /** the publisher */
  private static final char[] SPAN_PUBLISHER = { '<', 's', 'p', 'a', 'n',
      ' ', 'c', 'l', 'a', 's', 's', '=', '"', 'p', 'u', 'b', 'l', 'i',
      's', 'h', 'e', 'r', '"', '>' };

  /** the location */
  private static final char[] SPAN_LOCATION = { '<', 's', 'p', 'a', 'n',
      ' ', 'c', 'l', 'a', 's', 's', '=', '"', 'l', 'o', 'c', 'a', 't',
      'i', 'o', 'n', '"', '>' };
  /** the doi */
  private static final char[] SPAN_DOI = { '<', 's', 'p', 'a', 'n', ' ',
      'c', 'l', 'a', 's', 's', '=', '"', 'd', 'o', 'i', '"', '>', 'd',
      'o', 'i', ':', '<', 'a', ' ', 'h', 'r', 'e', 'f', '=', '"', 'h',
      't', 't', 'p', ':', '/', '/', 'd', 'x', '.', 'd', 'o', 'i', '.',
      'o', 'r', 'g', '/' };
  /** the link */
  private static final char[] SPAN_LINK = { '<', 's', 'p', 'a', 'n', ' ',
      'c', 'l', 'a', 's', 's', '=', '"', 'l', 'i', 'n', 'k', '"', '>',
      '[', '<', 'a', ' ', 'h', 'r', 'e', 'f', '=', '"' };
  /** the thesis type */
  private static final char[] SPAN_THESIS_TYPE = { '<', 's', 'p', 'a',
      'n', ' ', 'c', 'l', 'a', 's', 's', '=', '"', 't', 't', 'y', 'p',
      'e', '"', '>' };

  /** the institute span */
  private static final char[] SPAN_INSTITUTE = { '<', 's', 'p', 'a', 'n',
      ' ', 'c', 'l', 'a', 's', 's', '=', '"', 'i', 'n', 's', 't', 'i',
      't', 'u', 't', 'e', '"', '>' };

  /** the school */
  private static final char[] SPAN_SCHOOL = { '<', 's', 'p', 'a', 'n',
      ' ', 'c', 'l', 'a', 's', 's', '=', '"', 's', 'c', 'h', 'o', 'o',
      'l', '"', '>' };

  /**
   * Write a record
   * 
   * @param r
   *          the record
   * @param out
   *          the document to append to
   * @param raw
   *          the raw output destination
   */
  static final void _appendRecord(final BibRecord r,
      final ITextOutput out, final ITextOutput raw) {

    if (r instanceof BibArticle) {
      _XHTML10Bibliography.__appendArticle(((BibArticle) r), out, raw);
      return;
    }
    if (r instanceof BibProceedings) {
      _XHTML10Bibliography.__appendProceedings(((BibProceedings) r), out,
          raw);
      return;
    }
    if (r instanceof BibInProceedings) {
      _XHTML10Bibliography.__appendInProceedings(((BibInProceedings) r),
          out, raw);
      return;
    }
    if (r instanceof BibInCollection) {
      _XHTML10Bibliography.__appendInCollection(((BibInCollection) r),
          out, raw);
      return;
    }
    if (r instanceof BibTechReport) {
      _XHTML10Bibliography.__appendTechReport(((BibTechReport) r), out,
          raw);
      return;
    }
    if (r instanceof BibThesis) {
      _XHTML10Bibliography.__appendThesis(((BibThesis) r), out, raw);
      return;
    }
    if (r instanceof BibBook) {
      _XHTML10Bibliography.__appendBook(((BibBook) r), out, raw);
      return;
    }
    if (r instanceof BibWebsite) {
      _XHTML10Bibliography.__appendWebsite(((BibWebsite) r), out, raw);
      return;
    }

    throw new IllegalArgumentException(r.toString());
  }

  /**
   * append a full date
   * 
   * @param year
   *          the year
   * @param quarter
   *          the quarter
   * @param month
   *          the month
   * @param day
   *          the eay
   * @param wco
   *          the output
   * @param raw
   *          the raw output
   */
  private static final void __fullDate(final int year,
      final EBibQuarter quarter, final EBibMonth month, final int day,
      final ITextOutput wco, final ITextOutput raw) {
    String s;

    if (month != null) {
      s = month.getLongName();
      wco.append(s);
      if (day > 0) {
        wco.appendNonBreakingSpace();
        s = String.valueOf(day);
        wco.append(s);
      }
      if (year > 0) {
        raw.append(_XHTML10Bibliography.SMALL_SEP);
      }
    } else {
      if (quarter != null) {
        s = quarter.getLongName();
        wco.append(s);
        if (year > 0) {
          wco.appendNonBreakingSpace();
        }
      }
    }

    if (year > 0) {
      s = String.valueOf(year);
      wco.append(s);
    }
  }

  /**
   * append the authors
   * 
   * @param textCase
   *          the text case
   * @param start
   *          the start
   * @param authors
   *          the authors
   * @param ct
   *          the text
   * @param raw
   *          the raw
   */
  private static final void __appendAuthors(final ETextCase textCase,
      final char[] start, final BibAuthors authors, final ITextOutput ct,
      final ITextOutput raw) {
    final _XHTML10BibAuthorHolder[] h;
    int i;

    i = authors.size();
    h = new _XHTML10BibAuthorHolder[i];
    for (; (--i) >= 0;) {
      h[i] = new _XHTML10BibAuthorHolder(start, authors.get(i), raw);
    }
    ESequenceMode.AND.appendSequence(textCase, new ArrayListView<>(h),
        true, ct);
  }

  /**
   * append date
   * 
   * @param start
   *          the start
   * @param end
   *          the end
   * @param wco
   *          the output
   * @param raw
   *          the raw output
   */
  private static final void __date(final BibDate start, final BibDate end,
      final ITextOutput wco, final ITextOutput raw) {
    final EBibMonth month1, month2;
    final EBibQuarter quarter1, quarter2;
    final int day1, day2, year1, year2;
    BibDate a, b;
    String s;

    a = start;
    if (a == null) {
      a = end;
      if (a == null) {
        return;
      }
      b = null;
    } else {
      if (a.equals(end)) {
        b = null;
      } else {
        b = end;
      }
    }

    raw.append(_XHTML10Bibliography.SPAN_DATE);

    day1 = a.getDay();
    month1 = a.getMonth();
    quarter1 = a.getQuarter();
    year1 = a.getYear();

    if (b == null) {
      _XHTML10Bibliography.__fullDate(year1, quarter1, month1, day1, wco,
          raw);
    } else {

      day2 = b.getDay();
      month2 = b.getMonth();
      quarter2 = b.getQuarter();
      year2 = b.getYear();

      if (year1 == year2) {

        if (month1 != null) {

          if (month1 == month2) {
            s = month1.getLongName();
            wco.append(s);
            if ((day1 > 0) && (day2 > 0)) {
              wco.appendNonBreakingSpace();
              wco.append(String.valueOf(day1));
              raw.append(_XHTML10Bibliography.M_DASH);
              wco.append(String.valueOf(day2));
              raw.append(_XHTML10Bibliography.SMALL_SEP);
            }
          } else {
            _XHTML10Bibliography.__fullDate(-1, null, month1, day1, wco,
                raw);
            if (month2 != null) {
              raw.append(_XHTML10Bibliography.M_DASH);
              _XHTML10Bibliography.__fullDate(-1, null, month2, day2, wco,
                  raw);
            }
            if (quarter2 != null) {
              raw.append(_XHTML10Bibliography.M_DASH);
              wco.append(quarter2.getLongName());
            }
            raw.append(_XHTML10Bibliography.SMALL_SEP);
          }
        } else {

          if (quarter1 != null) {
            wco.append(quarter1.getLongName());

            if (quarter2 == quarter1) {
              wco.appendNonBreakingSpace();
            } else {
              if (quarter2 != null) {
                raw.append(_XHTML10Bibliography.M_DASH);
                wco.append(quarter2.getLongName());
                raw.append(_XHTML10Bibliography.SMALL_SEP);
              } else {
                if (month2 != null) {
                  raw.append(_XHTML10Bibliography.M_DASH);
                  _XHTML10Bibliography.__fullDate(-1, quarter2, month2,
                      day2, wco, raw);
                  raw.append(_XHTML10Bibliography.SMALL_SEP);
                }
              }
            }

          } else {
            _XHTML10Bibliography.__fullDate(-1, quarter2, month2, day2,
                wco, raw);
            if ((quarter2 != null) && (month2 != null)) {
              raw.append(' ');
            }
          }

        }

        wco.append(String.valueOf(year1));
      } else {
        _XHTML10Bibliography.__fullDate(year1, quarter1, month1, day1,
            wco, raw);
        raw.append(_XHTML10Bibliography.M_DASH);
        _XHTML10Bibliography.__fullDate(year2, quarter2, month2, day2,
            wco, raw);
      }

    }

    raw.append(XHTML10Driver.SPAN_END);
  }

  /**
   * Write the beginning of a record
   * 
   * @param ref
   *          the article
   * @param wco
   *          the appendable char output
   * @param raw
   *          the raw output
   */
  private static final void __beginRecord(final BibRecord ref,
      final ITextOutput wco, final ITextOutput raw) {
    final BibAuthors aut;

    aut = ref.getAuthors();
    if ((aut != null) && (aut.size() > 0)) {
      raw.append(_XHTML10Bibliography.SPAN_AUTHORS);
      _XHTML10Bibliography.__appendAuthors(ETextCase.AT_SENTENCE_START,
          _XHTML10Bibliography.SPAN_AUTHOR, ref.getAuthors(), wco, raw);
      raw.append(XHTML10Driver.SPAN_END);

      raw.append(_XHTML10Bibliography.BEFORE_TITLE);
    }

    raw.append(_XHTML10Bibliography.SPAN_TITLE);
    wco.append(QuotationMarks.DEFAULT_DOUBLE.getBeginChar());
    wco.append(ref.getTitle());
    wco.append(',');
    wco.append(QuotationMarks.DEFAULT_DOUBLE.getEndChar());

    raw.append(XHTML10Driver.SPAN_END);

    raw.append(' ');
  }

  /**
   * Write the beginning of a record
   * 
   * @param ref
   *          the article
   * @param wco
   *          the appendr char output
   * @param needsComma
   *          must a comma be put first?
   * @param raw
   *          the raw output
   */
  private static final void __endRecord(final boolean needsComma,
      final BibRecord ref, final ITextOutput wco, final ITextOutput raw) {
    boolean nc;
    String s;
    URI uri;

    s = ref.getDOI();
    nc = needsComma;
    if ((s != null) && (s.length() > 0)) {
      if (nc) {
        raw.append(_XHTML10Bibliography.SMALL_SEP);
      } else {
        nc = true;
      }
      raw.append(_XHTML10Bibliography.SPAN_DOI);
      wco.append(s);
      raw.append(XHTML10Driver.ATTRIB_TAG_BEGIN_END);
      wco.append(s);
      raw.append(XHTML10Driver.A_REF_END);
      raw.append(XHTML10Driver.SPAN_END);
    }

    uri = ref.getURL();
    if (uri != null) {
      s = uri.toString();
      if ((s != null) && (s.length() > 0)) {
        if (nc) {
          raw.append(_XHTML10Bibliography.SMALL_SEP);
        } else {
          nc = true;
        }
        raw.append(_XHTML10Bibliography.SPAN_LINK);
        wco.append(s);
        raw.append(XHTML10Driver.ATTRIB_TAG_BEGIN_END);
        wco.append("link"); //$NON-NLS-1$
        raw.append(XHTML10Driver.A_REF_END);
        raw.append(']');
        raw.append(XHTML10Driver.SPAN_END);
      }
    }
  }

  /**
   * Write an article
   * 
   * @param ref
   *          the article
   * @param wco
   *          the append char output
   * @param raw
   *          the raw output
   */
  private static final void __appendArticle(final BibArticle ref,
      final ITextOutput wco, final ITextOutput raw) {
    String v, i, sp, ep;

    raw.append(_XHTML10Bibliography.SPAN_ARTICLE);

    _XHTML10Bibliography.__beginRecord(ref, wco, raw);

    raw.append(_XHTML10Bibliography.SPAN_JOURNAL);
    wco.append(ref.getJournal());
    raw.append(XHTML10Driver.SPAN_END);

    raw.append(' ');
    v = ref.getVolume();
    if ((v != null) && (v.length() > 0)) {
      raw.append(_XHTML10Bibliography.SPAN_JOURNAL_VOLUME);
      wco.append(v);
      raw.append(XHTML10Driver.SPAN_END);
    } else {
      v = null;
    }

    i = ref.getNumber();
    if ((i != null) && (i.length() > 0)) {
      if (v != null) {
        raw.append('(');
      }
      raw.append(_XHTML10Bibliography.SPAN_ISSUE);
      wco.append(i);
      raw.append(XHTML10Driver.SPAN_END);
      if (v != null) {
        raw.append(')');
      }
    } else {
      i = null;
    }

    sp = ref.getStartPage();
    ep = ref.getEndPage();
    if ((sp != null) && (sp.length() > 0)) {
      if ((v != null) || (i != null)) {
        raw.append(':');
      }
      raw.append(_XHTML10Bibliography.SPAN_PAGES);
      wco.append(sp);
      if ((ep != null) && (ep.length() > 0)) {
        raw.append(_XHTML10Bibliography.M_DASH);
        wco.append(ep);
      }
      raw.append(XHTML10Driver.SPAN_END);
    }

    raw.append(_XHTML10Bibliography.SMALL_SEP);
    _XHTML10Bibliography.__date(ref.getDate(), null, wco, raw);

    _XHTML10Bibliography.__endRecord(true, ref, wco, raw);
    raw.append(XHTML10Driver.SPAN_END);
  }

  /**
   * append the book data
   * 
   * @param org
   *          the organization
   * @param needsComma
   *          do we need a comma?
   * @param wco
   *          the dest
   * @param raw
   *          the raw output
   * @param useSpan
   *          the span to use
   * @return will subsequent output need a comma?
   */
  private static final boolean __appendOrganization(final char[] useSpan,
      final boolean needsComma, final BibOrganization org,
      final ITextOutput wco, final ITextOutput raw) {
    String s;
    boolean h;

    if (org == null) {
      return needsComma;
    }

    if (needsComma) {
      raw.append(_XHTML10Bibliography.SMALL_SEP);
    }

    raw.append(useSpan);
    s = org.getAddress();
    h = (s != null) && (s.length() > 0);
    if (h) {
      wco.append(s);
    }
    s = org.getName();
    if ((s != null) && (s.length() > 0)) {
      if (h) {
        raw.append(_XHTML10Bibliography.BEFORE_TITLE);
        wco.append(s);
      }
      h = true;
    }

    s = org.getOriginalSpelling();
    if ((s != null) && (s.length() > 0)) {
      if (h) {
        raw.append(_XHTML10BibAuthorHolder.ORIGINAL_CONNECTOR);
      }
      wco.append(s);
      if (h) {
        raw.append(']');
      }
    }

    raw.append(XHTML10Driver.SPAN_END);
    return true;
  }

  /**
   * append the book data
   * 
   * @param printTitle
   *          should we print the book title?
   * @param ref
   *          the record
   * @param wco
   *          the dest
   * @param putComma
   *          put a comma?
   * @return will subsequent output need a comma?
   * @param raw
   *          the raw output
   */
  private static final boolean __appendBookData(final boolean printTitle,
      final boolean putComma, final BibBook ref, final ITextOutput wco,
      final ITextOutput raw) {
    BibAuthors eds;
    char[] ch;
    int i;
    boolean needsComma;
    String s, t;
    final BibProceedings proc;

    needsComma = putComma;
    if (printTitle) {
      raw.append(_XHTML10Bibliography.IN, (needsComma ? 0 : 1),
          _XHTML10Bibliography.IN.length);
      raw.append(_XHTML10Bibliography.SPAN_BOOK_TITLE);
      wco.append(ref.getTitle());
      raw.append(XHTML10Driver.SPAN_END);
      needsComma = true;
    }

    s = ref.getEdition();
    if ((s != null) && (s.length() > 0)) {
      raw.append(_XHTML10Bibliography.SPAN_EDITION);
      wco.append(s);
      wco.appendNonBreakingSpace();
      raw.append("edition"); //$NON-NLS-1$
      raw.append(XHTML10Driver.SPAN_END);
      needsComma = true;
    }

    eds = ref.getEditors();
    if ((eds != null) && ((i = eds.size()) > 0)) {
      if (needsComma) {
        raw.append(_XHTML10Bibliography.SMALL_SEP);
      }
      raw.append(_XHTML10Bibliography.SPAN_EDITORS);
      _XHTML10Bibliography.__appendAuthors(
          (needsComma ? ETextCase.IN_SENTENCE
              : ETextCase.AT_SENTENCE_START),
          _XHTML10Bibliography.SPAN_EDITOR, ref.getEditors(), wco, raw);
      ch = _XHTML10Bibliography.EDITORS;
      raw.append(ch, 0, ch.length - ((i > 1) ? 0 : 1));
      raw.append(XHTML10Driver.SPAN_END);
      needsComma = true;
    }

    if (needsComma) {
      raw.append(_XHTML10Bibliography.SMALL_SEP);
    }
    if (ref instanceof BibProceedings) {
      proc = ((BibProceedings) ref);
      _XHTML10Bibliography.__date(proc.getStartDate(), proc.getEndDate(),
          wco, raw);

      _XHTML10Bibliography.__appendOrganization(
          _XHTML10Bibliography.SPAN_LOCATION, true, proc.getLocation(),
          wco, raw);
    } else {
      _XHTML10Bibliography.__date(ref.getDate(), null, wco, raw);
    }
    needsComma = true;

    s = ref.getSeries();
    if (s != null) {
      if (needsComma) {
        raw.append(_XHTML10Bibliography.SMALL_SEP);
      }

      t = ref.getVolume();
      if (t != null) {
        raw.append(_XHTML10Bibliography.SPAN_SERIES_VOLUME);
        wco.append(t);
        raw.append(XHTML10Driver.SPAN_END);
        raw.append(" of&nbsp;"); //$NON-NLS-1$
      } else {
        raw.append(_XHTML10Bibliography.IN);
      }

      raw.append(_XHTML10Bibliography.SPAN_SERIES);
      wco.append(s);
      raw.append(XHTML10Driver.SPAN_END);
      needsComma = true;
    }

    return _XHTML10Bibliography.__appendOrganization(
        _XHTML10Bibliography.SPAN_PUBLISHER, needsComma,
        ref.getPublisher(), wco, raw);
  }

  /**
   * Write a book
   * 
   * @param ref
   *          the article
   * @param wco
   *          the appendable char output
   * @param raw
   *          the raw output
   */
  private static final void __appendBook(final BibBook ref,
      final ITextOutput wco, final ITextOutput raw) {
    boolean d;

    raw.append(_XHTML10Bibliography.SPAN_BOOK);

    _XHTML10Bibliography.__beginRecord(ref, wco, raw);
    d = _XHTML10Bibliography.__appendBookData(false, false, ref, wco, raw);
    _XHTML10Bibliography.__endRecord(d, ref, wco, raw);

    raw.append(XHTML10Driver.SPAN_END);
  }

  /**
   * Write a book
   * 
   * @param ref
   *          the article
   * @param wco
   *          the appendable char output
   * @param raw
   *          the raw output
   */
  private static final void __appendWebsite(final BibWebsite ref,
      final ITextOutput wco, final ITextOutput raw) {
    boolean needsComma;

    raw.append(_XHTML10Bibliography.SPAN_WEBSITE);

    _XHTML10Bibliography.__beginRecord(ref, wco, raw);
    _XHTML10Bibliography.__date(ref.getDate(), null, wco, raw);

    needsComma = _XHTML10Bibliography.__appendOrganization(
        _XHTML10Bibliography.SPAN_PUBLISHER, true, ref.getPublisher(),
        wco, raw);

    _XHTML10Bibliography.__endRecord(needsComma, ref, wco, raw);

    raw.append(XHTML10Driver.SPAN_END);
  }

  /**
   * Write a proceedings
   * 
   * @param ref
   *          the article
   * @param wco
   *          the appendable char output
   * @param raw
   *          the raw output
   */
  private static final void __appendProceedings(final BibProceedings ref,
      final ITextOutput wco, final ITextOutput raw) {
    boolean d;

    raw.append(_XHTML10Bibliography.SPAN_PROCEEDINGS);

    _XHTML10Bibliography.__beginRecord(ref, wco, raw);
    d = _XHTML10Bibliography.__appendBookData(false, false, ref, wco, raw);

    _XHTML10Bibliography.__endRecord(d, ref, wco, raw);

    raw.append(XHTML10Driver.SPAN_END);
  }

  /**
   * Write an in proceedings
   * 
   * @param ref
   *          the article
   * @param wco
   *          the appendable char output
   * @param raw
   *          the raw output
   */
  private static final void __appendInProceedings(
      final BibInProceedings ref, final ITextOutput wco,
      final ITextOutput raw) {
    boolean d;
    String sp, ep;

    raw.append(_XHTML10Bibliography.SPAN_IN_PROCEEDINGS);

    _XHTML10Bibliography.__beginRecord(ref, wco, raw);

    d = _XHTML10Bibliography.__appendBookData(true, false, ref.getBook(),
        wco, raw);

    sp = ref.getStartPage();
    ep = ref.getEndPage();
    if (sp != null) {
      if (d) {
        raw.append(_XHTML10Bibliography.SMALL_SEP);
      } else {
        d = true;
      }
      raw.append(_XHTML10Bibliography.SPAN_PAGES);
      if ((ep != null) && (ep.length() > 0)) {
        raw.append(_XHTML10Bibliography.PAGES);
      } else {
        raw.append(_XHTML10Bibliography.PAGE);
        ep = null;
      }

      wco.append(sp);
      if (ep != null) {
        raw.append(_XHTML10Bibliography.M_DASH);
        wco.append(ep);
      }
      raw.append(XHTML10Driver.SPAN_END);
    }

    sp = ref.getChapter();
    if ((sp != null) && (sp.length() > 0)) {
      if (d) {
        raw.append(_XHTML10Bibliography.SMALL_SEP);
      } else {
        d = true;
      }
      raw.append(_XHTML10Bibliography.SPAN_CHAPTER);
      wco.append(sp);
      raw.append(XHTML10Driver.SPAN_END);
    }

    _XHTML10Bibliography.__endRecord(d, ref, wco, raw);

    raw.append(XHTML10Driver.SPAN_END);
  }

  /**
   * Write an in collection
   * 
   * @param ref
   *          the article
   * @param wco
   *          the appendable char output
   * @param raw
   *          the raw output
   */
  private static final void __appendInCollection(
      final BibInCollection ref, final ITextOutput wco,
      final ITextOutput raw) {
    boolean d;
    String sp, ep;

    raw.append(_XHTML10Bibliography.SPAN_IN_COLLECTION);

    _XHTML10Bibliography.__beginRecord(ref, wco, raw);

    d = _XHTML10Bibliography.__appendBookData(true, false, ref.getBook(),
        wco, raw);

    sp = ref.getStartPage();
    ep = ref.getEndPage();
    if (sp != null) {
      if (d) {
        raw.append(_XHTML10Bibliography.SMALL_SEP);
      } else {
        d = true;
      }
      raw.append(_XHTML10Bibliography.SPAN_PAGES);
      if ((ep != null) && (ep.length() > 0)) {
        raw.append(_XHTML10Bibliography.PAGES);
      } else {
        raw.append(_XHTML10Bibliography.PAGE);
        ep = null;
      }

      wco.append(sp);
      if (ep != null) {
        raw.append(_XHTML10Bibliography.M_DASH);
        wco.append(ep);
      }
      raw.append(XHTML10Driver.SPAN_END);
    }

    sp = ref.getChapter();
    if ((sp != null) && (sp.length() > 0)) {
      if (d) {
        raw.append(_XHTML10Bibliography.SMALL_SEP);
      } else {
        d = true;
      }
      raw.append(_XHTML10Bibliography.SPAN_CHAPTER);
      wco.append(sp);
      raw.append(XHTML10Driver.SPAN_END);
    }

    _XHTML10Bibliography.__endRecord(d, ref, wco, raw);

    raw.append(XHTML10Driver.SPAN_END);
  }

  /**
   * Write a tech report
   * 
   * @param ref
   *          the article
   * @param wco
   *          the appendable char output
   * @param raw
   *          the raw output
   */
  private static final void __appendTechReport(final BibTechReport ref,
      final ITextOutput wco, final ITextOutput raw) {
    boolean needsComma;
    String s, t;

    raw.append(_XHTML10Bibliography.SPAN_TECH_REPORT);

    _XHTML10Bibliography.__beginRecord(ref, wco, raw);

    _XHTML10Bibliography.__date(ref.getDate(), null, wco, raw);

    needsComma = true;

    s = ref.getSeries();
    t = ref.getNumber();
    if (t != null) {
      if (needsComma) {
        raw.append(_XHTML10Bibliography.SMALL_SEP);
      }

      raw.append(_XHTML10Bibliography.SPAN_REPORT_NUMBER);
      wco.append(t);
      raw.append(XHTML10Driver.SPAN_END);

      if (s != null) {
        raw.append(_XHTML10Bibliography.IN);
      }
      needsComma = true;
    }

    if (s != null) {
      if (needsComma) {
        if (t == null) {
          raw.append(_XHTML10Bibliography.SMALL_SEP);
        }
      }

      raw.append(_XHTML10Bibliography.SPAN_RSERIES);
      wco.append(t);
      raw.append(XHTML10Driver.SPAN_END);
      needsComma = true;
    }

    needsComma = _XHTML10Bibliography.__appendOrganization(
        _XHTML10Bibliography.SPAN_INSTITUTE, needsComma,
        ref.getPublisher(), wco, raw);

    _XHTML10Bibliography.__endRecord(needsComma, ref, wco, raw);

    raw.append(XHTML10Driver.SPAN_END);
  }

  /**
   * Write a tech report
   * 
   * @param ref
   *          the article
   * @param wco
   *          the appendable char output
   * @param raw
   *          the raw output
   */
  private static final void __appendThesis(final BibThesis ref,
      final ITextOutput wco, final ITextOutput raw) {
    boolean needsComma;
    EThesisType ht;

    raw.append(_XHTML10Bibliography.SPAN_THESIS);

    _XHTML10Bibliography.__beginRecord(ref, wco, raw);
    needsComma = false;

    ht = ref.getType();
    if (ht != null) {
      raw.append(_XHTML10Bibliography.SPAN_THESIS_TYPE);
      wco.append(ht.getName());
      raw.append(XHTML10Driver.SPAN_END);
      needsComma = true;
    }

    needsComma = _XHTML10Bibliography.__appendOrganization(
        _XHTML10Bibliography.SPAN_SCHOOL, needsComma, ref.getSchool(),
        wco, raw);

    needsComma = _XHTML10Bibliography.__appendBookData(false, true, ref,
        wco, raw);

    _XHTML10Bibliography.__endRecord(needsComma, ref, wco, raw);

    raw.append(XHTML10Driver.SPAN_END);
  }

}
