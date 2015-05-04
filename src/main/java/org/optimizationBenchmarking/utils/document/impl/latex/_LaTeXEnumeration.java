package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.Enumeration;
import org.optimizationBenchmarking.utils.document.impl.abstr.StructuredText;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an enumeration in a LaTeX document */
final class _LaTeXEnumeration extends Enumeration {

  /** the begin enumerate */
  private static final char[] ENUMERATE_BEGIN = { '\\', 'b', 'e', 'g',
      'i', 'n', '{', 'e', 'n', 'u', 'm', 'e', 'r', 'a', 't', 'e', '}' };
  /** the end enumerate */
  private static final char[] ENUMERATE_END = { '\\', 'e', 'n', 'd', '{',
      'e', 'n', 'u', 'm', 'e', 'r', 'a', 't', 'e', '}' };

  /**
   * Create a new enumeration
   *
   * @param owner
   *          the owning text
   */
  _LaTeXEnumeration(final StructuredText owner) {
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
    out.append(_LaTeXEnumeration.ENUMERATE_BEGIN);
    LaTeXDriver._endLine(out);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    final ITextOutput out;

    out = this.getTextOutput();
    out.append(_LaTeXEnumeration.ENUMERATE_END);
    LaTeXDriver._endLine(out);
    LaTeXDriver._endLine(out);

    super.onClose();
  }
}
