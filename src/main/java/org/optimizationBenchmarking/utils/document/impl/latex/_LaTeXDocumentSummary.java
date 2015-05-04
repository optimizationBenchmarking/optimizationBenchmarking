package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentSummary;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** the summary of a LaTeX document header */
final class _LaTeXDocumentSummary extends DocumentSummary {

  /**
   * Create a document summary.
   *
   * @param owner
   *          the owning document header
   */
  _LaTeXDocumentSummary(final _LaTeXDocumentHeader owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    final ITextOutput out;
    final String s;

    super.onOpen();

    out = this.getTextOutput();
    LaTeXDriver._endLine(out);

    s = ((LaTeXDocument) (this.getDocument())).m_class.getSummaryBegin();
    if ((s != null) && (!(s.isEmpty()))) {
      out.append(s);
    }
    LaTeXDriver._endLine(out);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void appendLineBreak() {
    this.assertNoChildren();
    this.getTextOutput().append(' ');
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    final ITextOutput out;
    final String s;

    out = this.getTextOutput();
    LaTeXDriver._endLine(out);

    s = ((LaTeXDocument) (this.getDocument())).m_class.getSummaryEnd();
    if ((s != null) && (!(s.isEmpty()))) {
      out.append(s);
    }
    LaTeXDriver._endLine(out);

    super.onClose();
  }
}
