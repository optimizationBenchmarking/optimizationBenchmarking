package org.optimizationBenchmarking.utils.bibliography.io;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import org.optimizationBenchmarking.utils.bibliography.data.BibArticle;
import org.optimizationBenchmarking.utils.bibliography.data.BibAuthor;
import org.optimizationBenchmarking.utils.bibliography.data.BibAuthors;
import org.optimizationBenchmarking.utils.bibliography.data.BibBook;
import org.optimizationBenchmarking.utils.bibliography.data.BibBookRecord;
import org.optimizationBenchmarking.utils.bibliography.data.BibDate;
import org.optimizationBenchmarking.utils.bibliography.data.BibInCollection;
import org.optimizationBenchmarking.utils.bibliography.data.BibInProceedings;
import org.optimizationBenchmarking.utils.bibliography.data.BibOrganization;
import org.optimizationBenchmarking.utils.bibliography.data.BibProceedings;
import org.optimizationBenchmarking.utils.bibliography.data.BibRecord;
import org.optimizationBenchmarking.utils.bibliography.data.BibTechReport;
import org.optimizationBenchmarking.utils.bibliography.data.BibThesis;
import org.optimizationBenchmarking.utils.bibliography.data.BibWebsite;
import org.optimizationBenchmarking.utils.bibliography.data.Bibliography;
import org.optimizationBenchmarking.utils.bibliography.data.EBibMonth;
import org.optimizationBenchmarking.utils.bibliography.data.EBibQuarter;
import org.optimizationBenchmarking.utils.bibliography.data.EThesisType;
import org.optimizationBenchmarking.utils.io.encoding.StreamEncoding;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.IOJob;
import org.optimizationBenchmarking.utils.io.structured.impl.abstr.TextOutputTool;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.transformations.LaTeXCharTransformer;
import org.optimizationBenchmarking.utils.tools.impl.latex.ELaTeXFileType;

/**
 * A driver for <a href="http://en.wikipedia.org/wiki/BibTeX">BibTeX</a> IO
 * of bibliographic data. Currently, this API only supports output and
 * cannot yet read BibTeX data.
 */
public final class BibTeXOutput extends TextOutputTool<Object> {

  /** the spaces */
  private static final char[] FIELD;

  static {
    FIELD = new char[18];
    Arrays.fill(BibTeXOutput.FIELD, ' ');
    BibTeXOutput.FIELD[BibTeXOutput.FIELD.length - 3] = '=';
    BibTeXOutput.FIELD[BibTeXOutput.FIELD.length - 1] = '{';
  }

  /** the article */
  private static final String ARTICLE = "@article"; //$NON-NLS-1$
  /** the book */
  private static final String BOOK = "@book"; //$NON-NLS-1$
  /** the misc */
  private static final String MISC = "@misc"; //$NON-NLS-1$
  /** the technical report */
  private static final String TECHREPORT = "@techreport"; //$NON-NLS-1$
  /** the incollection */
  private static final String INCOLLECTION = "@incollection"; //$NON-NLS-1$
  /** the in-proceedings */
  private static final String INPROCEEDINGS = "@inproceedings"; //$NON-NLS-1$
  /** the proceedings */
  private static final String PROCEEDINGS = "@proceedings"; //$NON-NLS-1$
  /** the mastersthesis */
  private static final String MASTERSTHESIS = "@mastersthesis"; //$NON-NLS-1$
  /** the phdthesis */
  private static final String PHDTHESIS = "@phdthesis"; //$NON-NLS-1$
  /** the title */
  private static final String TITLE = "title"; //$NON-NLS-1$
  /** the book title */
  private static final String BOOKTITLE = "booktitle"; //$NON-NLS-1$
  /** the authors */
  private static final String AUTHORS = "author"; //$NON-NLS-1$
  /** the editors */
  private static final String EDITORS = "editor"; //$NON-NLS-1$
  /** the and */
  private static final String AND = "and"; //$NON-NLS-1$
  /** the year */
  private static final String YEAR = "year"; //$NON-NLS-1$
  /** the month */
  private static final String MONTH = "month"; //$NON-NLS-1$
  /** the volume */
  private static final String VOLUME = "volume"; //$NON-NLS-1$
  /** the number */
  private static final String NUMBER = "number"; //$NON-NLS-1$
  /** the pages */
  private static final String PAGES = "pages"; //$NON-NLS-1$
  /** the journal */
  private static final String JOURNAL = "journal"; //$NON-NLS-1$
  /** the url */
  private static final String URL = "url"; //$NON-NLS-1$
  /** the doi */
  private static final String DOI = "doi"; //$NON-NLS-1$
  /** the publisher */
  private static final String PUBLISHER = "publisher"; //$NON-NLS-1$
  /** the address */
  private static final String ADDRESS = "address"; //$NON-NLS-1$
  /** the series */
  private static final String SERIES = "series"; //$NON-NLS-1$
  /** the edition */
  private static final String EDITION = "edition"; //$NON-NLS-1$
  /** the issn */
  private static final String ISSN = "issn"; //$NON-NLS-1$
  /** the isbn */
  private static final String ISBN = "isbn"; //$NON-NLS-1$
  /** the chapter */
  private static final String CHAPTER = "chapter"; //$NON-NLS-1$
  /** the institution */
  private static final String INSTITUTION = "institution"; //$NON-NLS-1$
  /** the school */
  private static final String SCHOOL = "school"; //$NON-NLS-1$
  /** the type */
  private static final String TYPE = "type"; //$NON-NLS-1$

  /** create */
  BibTeXOutput() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  protected final String getDefaultPlainOutputFileName() {
    return ("bibliography." + //$NON-NLS-1$ 
    ELaTeXFileType.BIB.getDefaultSuffix());
  }

  /**
   * Get the instance of the {@link BibTeXOutput}
   * 
   * @return the instance of the {@link BibTeXOutput}
   */
  public static final BibTeXOutput getInstance() {
    return __BibTeXOutputLoader.INSTANCE;
  }

  /** {@inheritDoc} */
  @Override
  protected final void text(final IOJob job, final Object data,
      final ITextOutput textOut) {
    final ITextOutput enc;
    final char[] buf;

    enc = LaTeXCharTransformer.getInstance().transform(textOut,
        TextUtils.DEFAULT_NORMALIZER_FORM);
    buf = BibTeXOutput.FIELD.clone();

    if (data instanceof Bibliography) {
      BibTeXOutput.__storeBibliography(((Bibliography) data), textOut,
          enc, buf);
      return;
    }

    if (data instanceof BibRecord) {
      BibTeXOutput.__storeRecord(((BibRecord) data), textOut, enc, buf);
      return;
    }

    throw new IllegalArgumentException(//
        "Element '" + data + //$NON-NLS-1$
            "' cannot be processed by BibTeX output driver."); //$NON-NLS-1$
  }

  /**
   * store an in-collection record
   * 
   * @param data
   *          the data
   * @param raw
   *          the raw output
   * @param enc
   *          the encoded output
   * @param buf
   *          the buffer
   */
  private static final void __storeInCollection(
      final BibInCollection data, final ITextOutput raw,
      final ITextOutput enc, final char[] buf) {
    final URI u;
    final BibBook b;

    BibTeXOutput.__recS(BibTeXOutput.INCOLLECTION, data, raw);

    BibTeXOutput.__field(BibTeXOutput.TITLE, data.getTitle(), raw, enc,
        buf);
    BibTeXOutput.__authors(BibTeXOutput.AUTHORS, data.getAuthors(), raw,
        enc, buf);

    b = data.getBook();
    if (b != null) {
      BibTeXOutput.__field(BibTeXOutput.BOOKTITLE, b.getTitle(), raw, enc,
          buf);
      BibTeXOutput.__storeBookInner(b, raw, enc, buf);
      BibTeXOutput.__date(b.getDate(), raw, enc, buf);
    }

    BibTeXOutput.__pages(data.getStartPage(), data.getEndPage(), raw, enc,
        buf);
    BibTeXOutput.__field(BibTeXOutput.CHAPTER, data.getChapter(), raw,
        enc, buf);

    BibTeXOutput.__field(BibTeXOutput.DOI, data.getDOI(), raw, enc, buf);
    u = data.getURL();
    if (u != null) {
      BibTeXOutput.__field(BibTeXOutput.URL, u.toString(), raw, enc, buf);
    }
    BibTeXOutput.__recE(raw);
  }

  /**
   * store a bibliographic record
   * 
   * @param data
   *          the data
   * @param raw
   *          the raw output
   * @param enc
   *          the encoded output
   * @param buf
   *          the buffer
   */
  private static final void __storeRecord(final BibRecord data,
      final ITextOutput raw, final ITextOutput enc, final char[] buf) {

    if (data instanceof BibArticle) {
      BibTeXOutput.__storeArticle(((BibArticle) data), raw, enc, buf);
      return;
    }
    if (data instanceof BibInProceedings) {
      BibTeXOutput.__storeInProceedings(((BibInProceedings) data), raw,
          enc, buf);
      return;
    }
    if (data instanceof BibProceedings) {
      BibTeXOutput.__storeProceedings(((BibProceedings) data), raw, enc,
          buf);
      return;
    }
    if (data instanceof BibInCollection) {
      BibTeXOutput.__storeInCollection(((BibInCollection) data), raw, enc,
          buf);
      return;
    }
    if (data instanceof BibThesis) {
      BibTeXOutput.__storeThesis(((BibThesis) data), raw, enc, buf);
      return;
    }
    if (data instanceof BibBook) {
      BibTeXOutput.__storeBook(((BibBook) data), raw, enc, buf);
      return;
    }
    if (data instanceof BibWebsite) {
      BibTeXOutput.__storeWebsite(((BibWebsite) data), raw, enc, buf);
      return;
    }
    if (data instanceof BibTechReport) {
      BibTeXOutput
          .__storeTechReport(((BibTechReport) data), raw, enc, buf);
      return;
    }

    throw new IllegalArgumentException(//
        "Element '" + data + //$NON-NLS-1$
            "' is of unknown type cannot be processed by BibTeX output driver."); //$NON-NLS-1$
  }

  /**
   * put a string into a buffer
   * 
   * @param name
   *          the string
   * @param buf
   *          the buffer
   */
  private static final void __put(final String name, final char[] buf) {
    int l;
    l = name.length();
    name.getChars(0, l, buf, 2);
    l += 2;
    System.arraycopy(BibTeXOutput.FIELD, l, buf, l,
        (BibTeXOutput.FIELD.length - l));
  }

  /**
   * write a field value start
   * 
   * @param name
   *          the field name
   * @param buf
   *          the buffer
   * @param raw
   *          the output
   */
  private static final void __fieldS(final String name,
      final ITextOutput raw, final char[] buf) {
    BibTeXOutput.__put(name, buf);
    raw.append(buf);
  }

  /**
   * write a record start
   * 
   * @param name
   *          the field name
   * @param rec
   *          the bibliographic record
   * @param raw
   *          the output
   */
  private static final void __recS(final String name, final BibRecord rec,
      final ITextOutput raw) {
    raw.append(name);
    raw.append('{');
    raw.append(rec.getKey());
    raw.append(',');
    raw.appendLineBreak();
  }

  /**
   * end a field
   * 
   * @param raw
   *          the output
   */
  private static final void __fieldE(final ITextOutput raw) {
    raw.append('}');
    raw.append(',');
    raw.appendLineBreak();
  }

  /**
   * end a record
   * 
   * @param raw
   *          the output
   */
  private static final void __recE(final ITextOutput raw) {
    raw.append('}');
  }

  /**
   * write a simple field
   * 
   * @param name
   *          the field name
   * @param value
   *          the field value
   * @param raw
   *          the raw output
   * @param enc
   *          the encoded output
   * @param buf
   *          the buffer
   */
  private static final void __field(final String name, final String value,
      final ITextOutput raw, final ITextOutput enc, final char[] buf) {
    if ((value != null) && (value.length() > 0)) {
      BibTeXOutput.__fieldS(name, raw, buf);
      enc.append(value);
      BibTeXOutput.__fieldE(raw);
    }
  }

  /**
   * write a pages field
   * 
   * @param start
   *          the start page
   * @param end
   *          the end page
   * @param raw
   *          the raw output
   * @param enc
   *          the encoded output
   * @param buf
   *          the buffer
   */
  private static final void __pages(final String start, final String end,
      final ITextOutput raw, final ITextOutput enc, final char[] buf) {
    if ((start != null) || (end != null)) {
      BibTeXOutput.__fieldS(BibTeXOutput.PAGES, raw, buf);
      if (start != null) {
        enc.append(start);
      }
      if (end != null) {
        if (start != null) {
          raw.append('-');
          raw.append('-');
        }
        enc.append(end);
      }
      BibTeXOutput.__fieldE(raw);
    }
  }

  /**
   * write a organization field
   * 
   * @param name
   *          the field name
   * @param org
   *          the organization
   * @param raw
   *          the raw output
   * @param enc
   *          the encoded output
   * @param buf
   *          the buffer
   */
  private static final void __organization(final String name,
      final BibOrganization org, final ITextOutput raw,
      final ITextOutput enc, final char[] buf) {
    final String o, a;
    if (org != null) {
      o = org.getName();
      a = org.getAddress();
      if ((o != null) || (a != null)) {
        BibTeXOutput.__fieldS(name, raw, buf);
        if (a != null) {
          enc.append(a);
        }
        if (o != null) {
          if (a != null) {
            raw.append(':');
            raw.append(' ');
          }
          enc.append(o);
        }
        BibTeXOutput.__fieldE(raw);
      }
    }
  }

  /**
   * write a date field
   * 
   * @param start
   *          the start date
   * @param end
   *          the end date
   * @param raw
   *          the raw output
   * @param enc
   *          the encoded output
   * @param buf
   *          the buffer
   */
  private static final void __dates(final BibDate start,
      final BibDate end, final ITextOutput raw, final ITextOutput enc,
      final char[] buf) {
    final int y1, y2, d1, d2;
    final EBibMonth m1, m2;
    final EBibQuarter q1, q2;
    boolean z1, z2;

    if (start == null) {
      BibTeXOutput.__date(end, raw, enc, buf);
      return;
    }
    if ((end == null) || (start == end) || (start.equals(end))) {
      BibTeXOutput.__date(start, raw, enc, buf);
      return;
    }

    y1 = start.getYear();
    y2 = end.getYear();

    if (y1 == y2) {
      BibTeXOutput.__fieldS(BibTeXOutput.YEAR, raw, buf);
      raw.append(y1);
      BibTeXOutput.__fieldE(raw);

      m1 = start.getMonth();
      m2 = end.getMonth();
      d1 = start.getDay();
      d2 = end.getDay();

      // do we have two month records?
      if ((m1 != null) && (m2 != null)) {
        //
        BibTeXOutput.__put(BibTeXOutput.MONTH, buf);
        raw.append(buf, 0, buf.length - 1);

        if (m1 == m2) {
          // both months are the same in the same year
          raw.append(m1.getShortName());
          if ((d1 > 0) && (d2 > 0)) {
            raw.append(" # {~"); //$NON-NLS-1$
            raw.append(d1);
            if (d2 > d1) {
              raw.append('-');
              raw.append('-');
              raw.append(d2);
            }
            raw.append(", }"); //$NON-NLS-1$
          }
        } else {
          // months are different, years are the same

          // first month/day
          raw.append(m1.getShortName());

          if (d1 > 0) {
            raw.append(" # {~"); //$NON-NLS-1$
            raw.append(d1);
            raw.append("--}"); //$NON-NLS-1$
          } else {
            raw.append(" # {--}"); //$NON-NLS-1$
          }
          raw.append(" # "); //$NON-NLS-1$

          // second month/day
          raw.append(m2.getShortName());

          if (d2 > 0) {
            raw.append(" # {~"); //$NON-NLS-1$
            raw.append(d2);
            raw.append(", }"); //$NON-NLS-1$
          } else {
            raw.append(" # { }"); //$NON-NLS-1$
          }
        }

      } else {
        // ok, one month is null

        z1 = false;
        // deal with first date
        if (m1 != null) {
          raw.append(m1.getShortName());
          raw.append(" # {"); //$NON-NLS-1$
          if (d1 > 0) {
            raw.append('~');
            raw.append(d1);
          }
          z1 = true;
        } else {
          q1 = start.getQuarter();
          if (q1 != null) {
            raw.append('{');
            raw.append(q1.getLongName());
            z1 = true;
          }
        }

        // deal with second date

        z2 = false;
        if (m2 != null) {
          if (z1) {
            raw.append("--} # "); //$NON-NLS-1$
          }
          raw.append(m1.getShortName());
          raw.append(" # {"); //$NON-NLS-1$

          if (d2 > 0) {
            raw.append('~');
            raw.append(d2);
          }
          z2 = true;
        } else {
          q2 = end.getQuarter();
          if (q2 != null) {
            if (z1) {
              raw.append("--} # "); //$NON-NLS-1$
            }
            raw.append('{');
            raw.append(q2.getLongName());
          }
        }

        // close up
        if (z1 || z2) {
          raw.append(", }"); //$NON-NLS-1$
        }

      }

      raw.append(',');
      raw.appendLineBreak();
      return;
    }

    // two full dates
    BibTeXOutput.__put(BibTeXOutput.YEAR, buf);
    raw.append(buf, 0, (buf.length - 1));
    BibTeXOutput.__fullDate(start, raw);
    raw.append(" # {--} # "); //$NON-NLS-1$
    BibTeXOutput.__fullDate(end, raw);
    raw.append(',');
    raw.appendLineBreak();
  }

  /**
   * write a full date
   * 
   * @param date
   *          the date
   * @param raw
   *          the raw output
   */
  private static final void __fullDate(final BibDate date,
      final ITextOutput raw) {
    EBibMonth m;
    EBibQuarter q;
    int day;

    m = date.getMonth();
    if (m != null) {
      raw.append(m.getShortName());
      day = date.getDay();
      if (day > 0) {
        raw.append(" # {~"); //$NON-NLS-1$
        raw.append(day);
        raw.append(", } "); //$NON-NLS-1$
      } else {
        raw.append(" # {~} "); //$NON-NLS-1$
      }
      raw.append('#');
      raw.append(' ');
    } else {
      q = date.getQuarter();
      if (q != null) {
        raw.append('{');
        raw.append(q.getLongName());
        raw.append("~} # "); //$NON-NLS-1$
      }
    }
    raw.append(date.getYear());
  }

  /**
   * write a date field
   * 
   * @param value
   *          the field value
   * @param raw
   *          the raw output
   * @param enc
   *          the encoded output
   * @param buf
   *          the buffer
   */
  private static final void __date(final BibDate value,
      final ITextOutput raw, final ITextOutput enc, final char[] buf) {
    int z;
    EBibQuarter q;
    EBibMonth m;

    if (value != null) {
      z = value.getYear();
      if (z > 0) {
        BibTeXOutput.__fieldS(BibTeXOutput.YEAR, raw, buf);
        raw.append(z);
        BibTeXOutput.__fieldE(raw);
      }

      m = value.getMonth();
      if (m != null) {
        BibTeXOutput.__put(BibTeXOutput.MONTH, buf);
        raw.append(buf, 0, buf.length - 1);
        raw.append(m.getShortName());
        z = value.getDay();
        if (z > 0) {
          raw.append(" # {~"); //$NON-NLS-1$
          raw.append(z);
          raw.append(", }"); //$NON-NLS-1$
        }
        raw.append(',');
        raw.appendLineBreak();
      } else {
        q = value.getQuarter();
        if (q != null) {
          BibTeXOutput.__fieldS(BibTeXOutput.MONTH, raw, buf);
          raw.append(q.getLongName());
          BibTeXOutput.__fieldE(raw);
        }
      }
    }
  }

  /**
   * write an author field
   * 
   * @param name
   *          the field name
   * @param value
   *          the field value
   * @param raw
   *          the raw output
   * @param enc
   *          the encoded output
   * @param buf
   *          the buffer
   */
  private static final void __authors(final String name,
      final BibAuthors value, final ITextOutput raw,
      final ITextOutput enc, final char[] buf) {
    boolean b, s;
    String p, f;

    if ((value != null) && (value.size() > 0)) {
      BibTeXOutput.__fieldS(name, raw, buf);
      b = false;
      for (final BibAuthor a : value) {
        p = a.getPersonalName();
        f = a.getFamilyName();
        if ((p != null) || (f != null)) {
          if (b) {
            raw.append(' ');
            raw.append(BibTeXOutput.AND);
            raw.append(' ');
          } else {
            b = true;
          }
          if (p != null) {
            enc.append(p);
          }
          if (f != null) {
            if (p != null) {
              raw.append(' ');
            }

            s = (f.indexOf(' ') >= 0);
            if (s) {
              raw.append('{');
            }
            enc.append(f);
            if (s) {
              raw.append('}');
            }
          }
        }
      }
      BibTeXOutput.__fieldE(raw);
    }
  }

  /**
   * store an article record
   * 
   * @param data
   *          the data
   * @param raw
   *          the raw output
   * @param enc
   *          the encoded output
   * @param buf
   *          the buffer
   */
  private static final void __storeArticle(final BibArticle data,
      final ITextOutput raw, final ITextOutput enc, final char[] buf) {
    final URI u;

    BibTeXOutput.__recS(BibTeXOutput.ARTICLE, data, raw);

    BibTeXOutput.__field(BibTeXOutput.TITLE, data.getTitle(), raw, enc,
        buf);
    BibTeXOutput.__authors(BibTeXOutput.AUTHORS, data.getAuthors(), raw,
        enc, buf);
    BibTeXOutput.__field(BibTeXOutput.JOURNAL, data.getJournal(), raw,
        enc, buf);
    BibTeXOutput.__field(BibTeXOutput.VOLUME, data.getVolume(), raw, enc,
        buf);
    BibTeXOutput.__field(BibTeXOutput.NUMBER, data.getNumber(), raw, enc,
        buf);
    BibTeXOutput.__pages(data.getStartPage(), data.getEndPage(), raw, enc,
        buf);
    BibTeXOutput.__date(data.getDate(), raw, enc, buf);
    BibTeXOutput.__organization(BibTeXOutput.PUBLISHER,
        data.getPublisher(), raw, enc, buf);
    BibTeXOutput.__field(BibTeXOutput.ISSN, data.getISSN(), raw, enc, buf);
    BibTeXOutput.__field(BibTeXOutput.DOI, data.getDOI(), raw, enc, buf);

    u = data.getURL();
    if (u != null) {
      BibTeXOutput.__field(BibTeXOutput.URL, u.toString(), raw, enc, buf);
    }
    BibTeXOutput.__recE(raw);
  }

  /**
   * store a book record
   * 
   * @param data
   *          the data
   * @param raw
   *          the raw output
   * @param enc
   *          the encoded output
   * @param buf
   *          the buffer
   */
  private static final void __storeBook(final BibBook data,
      final ITextOutput raw, final ITextOutput enc, final char[] buf) {
    final URI u;

    BibTeXOutput.__recS(BibTeXOutput.BOOK, data, raw);

    BibTeXOutput.__field(BibTeXOutput.TITLE, data.getTitle(), raw, enc,
        buf);
    BibTeXOutput.__authors(BibTeXOutput.AUTHORS, data.getAuthors(), raw,
        enc, buf);

    BibTeXOutput.__storeBookInner(data, raw, enc, buf);
    BibTeXOutput.__date(data.getDate(), raw, enc, buf);
    BibTeXOutput.__field(BibTeXOutput.DOI, data.getDOI(), raw, enc, buf);
    u = data.getURL();
    if (u != null) {
      BibTeXOutput.__field(BibTeXOutput.URL, u.toString(), raw, enc, buf);
    }
    BibTeXOutput.__recE(raw);
  }

  /**
   * store a thesis record
   * 
   * @param data
   *          the data
   * @param raw
   *          the raw output
   * @param enc
   *          the encoded output
   * @param buf
   *          the buffer
   */
  private static final void __storeThesis(final BibThesis data,
      final ITextOutput raw, final ITextOutput enc, final char[] buf) {
    final URI u;
    final EThesisType type;

    type = data.getType();
    BibTeXOutput.__recS(
        (type == EThesisType.PHD_THESIS) ? BibTeXOutput.PHDTHESIS
            : BibTeXOutput.MASTERSTHESIS, data, raw);

    BibTeXOutput.__field(BibTeXOutput.TITLE, data.getTitle(), raw, enc,
        buf);

    if ((type != null) && (type != EThesisType.PHD_THESIS)) {
      BibTeXOutput.__field(BibTeXOutput.TYPE, type.getName(), raw, enc,
          buf);
    }

    BibTeXOutput.__authors(BibTeXOutput.AUTHORS, data.getAuthors(), raw,
        enc, buf);
    BibTeXOutput.__organization(BibTeXOutput.SCHOOL, data.getSchool(),
        raw, enc, buf);
    BibTeXOutput.__date(data.getDate(), raw, enc, buf);
    BibTeXOutput.__storeBookInner(data, raw, enc, buf);
    BibTeXOutput.__field(BibTeXOutput.DOI, data.getDOI(), raw, enc, buf);
    u = data.getURL();
    if (u != null) {
      BibTeXOutput.__field(BibTeXOutput.URL, u.toString(), raw, enc, buf);
    }
    BibTeXOutput.__recE(raw);
  }

  /**
   * store a book inner record
   * 
   * @param data
   *          the data
   * @param raw
   *          the raw output
   * @param enc
   *          the encoded output
   * @param buf
   *          the buffer
   */
  private static final void __storeBookInner(final BibBookRecord data,
      final ITextOutput raw, final ITextOutput enc, final char[] buf) {

    BibTeXOutput.__authors(BibTeXOutput.EDITORS, data.getEditors(), raw,
        enc, buf);
    BibTeXOutput.__field(BibTeXOutput.EDITION, data.getEdition(), raw,
        enc, buf);
    BibTeXOutput.__field(BibTeXOutput.SERIES, data.getSeries(), raw, enc,
        buf);
    BibTeXOutput.__field(BibTeXOutput.VOLUME, data.getVolume(), raw, enc,
        buf);
    BibTeXOutput.__field(BibTeXOutput.ISSN, data.getISSN(), raw, enc, buf);
    BibTeXOutput.__organization(BibTeXOutput.PUBLISHER,
        data.getPublisher(), raw, enc, buf);
    BibTeXOutput.__field(BibTeXOutput.ISBN, data.getISBN(), raw, enc, buf);
  }

  /**
   * store a proceedings inner record
   * 
   * @param data
   *          the data
   * @param raw
   *          the raw output
   * @param enc
   *          the encoded output
   * @param buf
   *          the buffer
   */
  private static final void __storeProceedingsInner(
      final BibProceedings data, final ITextOutput raw,
      final ITextOutput enc, final char[] buf) {

    BibTeXOutput.__storeBookInner(data, raw, enc, buf);
    BibTeXOutput.__dates(data.getStartDate(), data.getEndDate(), raw, enc,
        buf);
    BibTeXOutput.__organization(BibTeXOutput.ADDRESS, data.getLocation(),
        raw, enc, buf);
  }

  /**
   * store a proceedings record
   * 
   * @param data
   *          the data
   * @param raw
   *          the raw output
   * @param enc
   *          the encoded output
   * @param buf
   *          the buffer
   */
  private static final void __storeProceedings(final BibProceedings data,
      final ITextOutput raw, final ITextOutput enc, final char[] buf) {
    final URI u;

    BibTeXOutput.__recS(BibTeXOutput.PROCEEDINGS, data, raw);

    BibTeXOutput.__field(BibTeXOutput.TITLE, data.getTitle(), raw, enc,
        buf);
    BibTeXOutput.__authors(BibTeXOutput.AUTHORS, data.getAuthors(), raw,
        enc, buf);

    BibTeXOutput.__storeProceedingsInner(data, raw, enc, buf);
    BibTeXOutput.__field(BibTeXOutput.DOI, data.getDOI(), raw, enc, buf);
    u = data.getURL();
    if (u != null) {
      BibTeXOutput.__field(BibTeXOutput.URL, u.toString(), raw, enc, buf);
    }
    BibTeXOutput.__recE(raw);
  }

  /**
   * store an in-proceedings record
   * 
   * @param data
   *          the data
   * @param raw
   *          the raw output
   * @param enc
   *          the encoded output
   * @param buf
   *          the buffer
   */
  private static final void __storeInProceedings(
      final BibInProceedings data, final ITextOutput raw,
      final ITextOutput enc, final char[] buf) {
    final URI u;
    final BibProceedings b;

    BibTeXOutput.__recS(BibTeXOutput.INPROCEEDINGS, data, raw);

    BibTeXOutput.__field(BibTeXOutput.TITLE, data.getTitle(), raw, enc,
        buf);
    BibTeXOutput.__authors(BibTeXOutput.AUTHORS, data.getAuthors(), raw,
        enc, buf);

    b = data.getBook();
    if (b != null) {
      BibTeXOutput.__field(BibTeXOutput.BOOKTITLE, b.getTitle(), raw, enc,
          buf);
      BibTeXOutput.__storeProceedingsInner(b, raw, enc, buf);
    }

    BibTeXOutput.__pages(data.getStartPage(), data.getEndPage(), raw, enc,
        buf);
    BibTeXOutput.__field(BibTeXOutput.CHAPTER, data.getChapter(), raw,
        enc, buf);

    BibTeXOutput.__field(BibTeXOutput.DOI, data.getDOI(), raw, enc, buf);
    u = data.getURL();
    if (u != null) {
      BibTeXOutput.__field(BibTeXOutput.URL, u.toString(), raw, enc, buf);
    }
    BibTeXOutput.__recE(raw);
  }

  /**
   * store a website record
   * 
   * @param data
   *          the data
   * @param raw
   *          the raw output
   * @param enc
   *          the encoded output
   * @param buf
   *          the buffer
   */
  private static final void __storeWebsite(final BibWebsite data,
      final ITextOutput raw, final ITextOutput enc, final char[] buf) {
    final URI u;

    BibTeXOutput.__recS(BibTeXOutput.MISC, data, raw);

    BibTeXOutput.__field(BibTeXOutput.TITLE, data.getTitle(), raw, enc,
        buf);
    BibTeXOutput.__authors(BibTeXOutput.AUTHORS, data.getAuthors(), raw,
        enc, buf);
    BibTeXOutput.__date(data.getDate(), raw, enc, buf);
    BibTeXOutput.__organization(BibTeXOutput.PUBLISHER,
        data.getPublisher(), raw, enc, buf);
    BibTeXOutput.__field(BibTeXOutput.DOI, data.getDOI(), raw, enc, buf);

    u = data.getURL();
    if (u != null) {
      BibTeXOutput.__field(BibTeXOutput.URL, u.toString(), raw, enc, buf);
    }
    BibTeXOutput.__recE(raw);
  }

  /**
   * store a technical report record
   * 
   * @param data
   *          the data
   * @param raw
   *          the raw output
   * @param enc
   *          the encoded output
   * @param buf
   *          the buffer
   */
  private static final void __storeTechReport(final BibTechReport data,
      final ITextOutput raw, final ITextOutput enc, final char[] buf) {
    final URI u;

    BibTeXOutput.__recS(BibTeXOutput.TECHREPORT, data, raw);

    BibTeXOutput.__field(BibTeXOutput.TITLE, data.getTitle(), raw, enc,
        buf);
    BibTeXOutput.__authors(BibTeXOutput.AUTHORS, data.getAuthors(), raw,
        enc, buf);
    BibTeXOutput.__date(data.getDate(), raw, enc, buf);
    BibTeXOutput.__organization(BibTeXOutput.INSTITUTION,
        data.getPublisher(), raw, enc, buf);
    BibTeXOutput.__field(BibTeXOutput.TYPE, data.getSeries(), raw, enc,
        buf);
    BibTeXOutput.__field(BibTeXOutput.ISSN, data.getISSN(), raw, enc, buf);
    BibTeXOutput.__field(BibTeXOutput.NUMBER, data.getNumber(), raw, enc,
        buf);
    BibTeXOutput.__field(BibTeXOutput.DOI, data.getDOI(), raw, enc, buf);

    u = data.getURL();
    if (u != null) {
      BibTeXOutput.__field(BibTeXOutput.URL, u.toString(), raw, enc, buf);
    }
    BibTeXOutput.__recE(raw);
  }

  /**
   * store a bibliography
   * 
   * @param data
   *          the data
   * @param raw
   *          the raw output
   * @param enc
   *          the encoded output
   * @param buf
   *          the buffer
   */
  private static final void __storeBibliography(final Bibliography data,
      final ITextOutput raw, final ITextOutput enc, final char[] buf) {
    boolean b;

    b = false;
    for (final BibRecord rec : data) {
      if (b) {
        raw.appendLineBreak();
        raw.appendLineBreak();
      } else {
        b = true;
      }
      BibTeXOutput.__storeRecord(rec, raw, enc, buf);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return "Bibliography BibTeX Output"; //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  protected void file(final IOJob job, final Object data, final Path file,
      final StreamEncoding<?, ?> encoding) throws Throwable {
    super.file(job, data, file, encoding);
    if (Files.exists(file)) {
      this.addFile(job, file, ELaTeXFileType.BIB);
    }
  }

  /** the loader for lazy initialization */
  private static final class __BibTeXOutputLoader {

    /** the BibTeX driver */
    static final BibTeXOutput INSTANCE = new BibTeXOutput();
  }
}
