package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.Subscript;
import org.optimizationBenchmarking.utils.document.impl.abstr.Text;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an subscript element of a section in a LaTeX document */
final class _LaTeXSubscript extends Subscript {
  /** the subscript command in text */
  private static final char[] TEXT_SUBSCRIPT = { '{', '\\', 't', 'e', 'x',
      't', 's', 'u', 'b', 's', 'c', 'r', 'i', 'p', 't', '{', };

  /**
   * create the subscript element
   *
   * @param owner
   *          the owner
   */
  _LaTeXSubscript(final Text owner) {
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
    this.getTextOutput().append(_LaTeXSubscript.TEXT_SUBSCRIPT);
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
