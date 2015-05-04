package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.Itemization;
import org.optimizationBenchmarking.utils.document.impl.abstr.StructuredText;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an itemization in a LaTeX document */
final class _LaTeXItemization extends Itemization {
  /** the begin itemize */
  private static final char[] ITEMIZE_BEGIN = { '\\', 'b', 'e', 'g', 'i',
      'n', '{', 'i', 't', 'e', 'm', 'i', 'z', 'e', '}' };
  /** the end itemize */
  private static final char[] ITEMIZE_END = { '\\', 'e', 'n', 'd', '{',
      'i', 't', 'e', 'm', 'i', 'z', 'e', '}' };

  /**
   * Create a new itemization
   *
   * @param owner
   *          the owning text
   */
  _LaTeXItemization(final StructuredText owner) {
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
    LaTeXDriver._endLine(out);
    out.append(_LaTeXItemization.ITEMIZE_BEGIN);
    LaTeXDriver._endLine(out);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    final ITextOutput out;

    out = this.getTextOutput();
    out.append(_LaTeXItemization.ITEMIZE_END);
    LaTeXDriver._endLine(out);
    LaTeXDriver._endLine(out);

    super.onClose();
  }
}
