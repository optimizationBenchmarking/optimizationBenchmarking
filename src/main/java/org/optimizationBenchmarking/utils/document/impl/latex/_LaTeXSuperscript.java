package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.Superscript;
import org.optimizationBenchmarking.utils.document.impl.abstr.Text;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an superscript element of a section in a LaTeX document */
final class _LaTeXSuperscript extends Superscript {
  /** the superscript command in text */
  private static final char[] TEXT_SUPERSCRIPT = { '{', '\\', 't', 'e',
      'x', 't', 's', 'u', 'p', 'e', 'r', 's', 'c', 'r', 'i', 'p', 't',
      '{', };

  /**
   * create the superscript element
   *
   * @param owner
   *          the owner
   */
  _LaTeXSuperscript(final Text owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void appendLineBreak() {
    this.assertNoChildren();
    this.getTextOutput().append(' ');
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    super.onOpen();

    ((LaTeXDocument) (this.getDocument()))._registerTextSubOrSuperScript();
    this.getTextOutput().append(_LaTeXSuperscript.TEXT_SUPERSCRIPT);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    final ITextOutput out;

    out = this.getTextOutput();
    out.append('}');
    out.append('}');

    super.onClose();
  }
}
