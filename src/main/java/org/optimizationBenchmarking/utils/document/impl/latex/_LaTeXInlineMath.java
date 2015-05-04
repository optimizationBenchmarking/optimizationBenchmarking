package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.ComplexText;
import org.optimizationBenchmarking.utils.document.impl.abstr.InlineMath;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an inline math element of a section in a LaTeX document */
final class _LaTeXInlineMath extends InlineMath {
  /** begin ensuremath */
  private static final char[] ENSUREMATH_BEGIN = { '{', '\\', 'e', 'n',
      's', 'u', 'r', 'e', 'm', 'a', 't', 'h', '{' };

  /**
   * create the inline math element
   *
   * @param owner
   *          the owner
   */
  _LaTeXInlineMath(final ComplexText owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    super.onOpen();

    this.getTextOutput().append(_LaTeXInlineMath.ENSUREMATH_BEGIN);
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
