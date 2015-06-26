package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathNumber;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical number element of a section in a LaTeX document */
final class _LaTeXMathNumber extends MathNumber {
  /** the begin number name */
  private static final char[] NUMBER_BEGIN = { '{', '\\', 't', 'e', 'x',
      't', 'n', 'o', 'r', 'm', 'a', 'l', '{', };

  /**
   * create the mathematical in-braces element
   *
   * @param owner
   *          the owner
   */
  _LaTeXMathNumber(final BasicMath owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    super.onOpen();
    this.getTextOutput().append(_LaTeXMathNumber.NUMBER_BEGIN);
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
