package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathMin;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical min function in a LaTeX document */
final class _LaTeXMathMin extends MathMin {
  /** the begin min */
  private static final char[] MIN_BEGIN = { '{', '\\', 'm', 'i', 'n', '{',
      '\\', 'l', 'e', 'f', 't', '\\', };
  /** the end min */
  private static final char[] MIN_END = _LaTeXMathMax.MAX_END;

  /**
   * Create a new mathematical function
   *
   * @param owner
   *          the owning text
   */
  _LaTeXMathMin(final BasicMath owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void render(final ITextOutput out, final char[][] data,
      final int size) {
    char next;

    out.append(_LaTeXMathMin.MIN_BEGIN);
    next = '{';
    for (final char[] operand : data) {
      out.append(next);
      next = ',';
      out.append(operand);
    }
    out.append(_LaTeXMathMin.MIN_END);
  }
}
