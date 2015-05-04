package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathText;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical text element of a section in a LaTeX document */
final class _LaTeXMathText extends MathText {
  /** the begin math name */
  private static final char[] TEXT_BEGIN = _LaTeXStyledText.TEXT_NORMAL_BEGIN;

  /**
   * create the mathematical in-braces element
   *
   * @param owner
   *          the owner
   */
  _LaTeXMathText(final BasicMath owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {

    super.onOpen();
    this.getTextOutput().append(_LaTeXMathText.TEXT_BEGIN);
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
