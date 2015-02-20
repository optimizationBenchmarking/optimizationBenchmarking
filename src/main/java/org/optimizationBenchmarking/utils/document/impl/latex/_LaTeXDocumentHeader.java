package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.bibliography.data.BibAuthor;
import org.optimizationBenchmarking.utils.bibliography.data.BibAuthors;
import org.optimizationBenchmarking.utils.bibliography.data.BibDate;
import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentHeader;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** the LaTeX document header */
final class _LaTeXDocumentHeader extends DocumentHeader {

  /** start the document */
  private static final char[] DOCUMENT_BEGIN = { '\\', 'b', 'e', 'g', 'i',
      'n', '{', 'd', 'o', 'c', 'u', 'm', 'e', 'n', 't', '}' };

  /**
   * Create a document header.
   * 
   * @param owner
   *          the owning document
   */
  _LaTeXDocumentHeader(final _LaTeXDocument owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    final ITextOutput out;

    super.onOpen();

    out = this.getTextOutput();
    LaTeXDriver._endLine(out);
    out.append(_LaTeXDocumentHeader.DOCUMENT_BEGIN);
    LaTeXDriver._endLine(out);
  }

  /** {@inheritDoc} */
  @Override
  protected final void doAuthors(final BibAuthors authors) {
    final ITextOutput out, encoded;
    final LaTeXDocumentClass clazz;
    final int size;
    BibAuthor author;
    int i;
    String s;
    boolean needsSpace;

    super.doAuthors(authors);

    if ((authors == null) || ((size = authors.size()) <= 0)) {
      return;
    }

    out = this.getTextOutput();
    LaTeXDriver._endLine(out);

    clazz = ((_LaTeXDocument) (this.getDocument())).m_class;

    s = clazz.getAuthorsBegin();
    if ((s != null) && (!(s.isEmpty()))) {
      out.append(s);
    }

    encoded = ((LaTeXDriver) (this.getDriver())).encode(out);
    for (i = 0; i < size; i++) {
      if (i > 0) {
        s = clazz.getAuthorSeparator();
        if ((s != null) && (!(s.isEmpty()))) {
          out.append(s);
        }
      }

      s = clazz.getAuthorBegin();
      if ((s != null) && (!(s.isEmpty()))) {
        out.append(s);
      }

      author = authors.get(i);

      needsSpace = false;
      s = author.getPersonalName();
      if ((s != null) & (!(s.isEmpty()))) {
        encoded.append(s);
        needsSpace = true;
      }
      s = author.getFamilyName();
      if ((s != null) & (!(s.isEmpty()))) {
        if (needsSpace) {
          encoded.append(' ');
        }
        encoded.append(s);
      }
      s = clazz.getAuthorEnd();
      if ((s != null) && (!(s.isEmpty()))) {
        out.append(s);
      }

    }

    s = clazz.getAuthorsEnd();
    if ((s != null) && (!(s.isEmpty()))) {
      out.append(s);
    }

    LaTeXDriver._endLine(out);
  }

  /** {@inheritDoc} */
  @Override
  protected final void doDate(final BibDate date) {
    super.doDate(date);
    final LaTeXDocumentClass clazz;
    final ITextOutput out;
    String s;

    if (date == null) {
      return;
    }

    out = this.getTextOutput();
    LaTeXDriver._endLine(out);

    clazz = ((_LaTeXDocument) (this.getDocument())).m_class;

    s = clazz.getDateBegin();
    if ((s != null) && (!(s.isEmpty()))) {
      out.append(s);
    }
    date.toText(out);
    s = clazz.getDateEnd();
    if ((s != null) && (!(s.isEmpty()))) {
      out.append(s);
    }
    LaTeXDriver._endLine(out);

  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    final ITextOutput out;
    final String s;

    out = this.getTextOutput();

    LaTeXDriver._endLine(out);

    s = ((_LaTeXDocument) (this.getDocument())).m_class.getMakeTitle();
    if ((s != null) && (!(s.isEmpty()))) {
      out.append(s);
      LaTeXDriver._endLine(out);
    }

    super.onClose();
  }
}
