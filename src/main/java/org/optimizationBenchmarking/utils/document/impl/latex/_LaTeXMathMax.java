package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathMax;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical max function in a LaTeX document */
final class _LaTeXMathMax extends MathMax {
  /** the begin max */
  private static final char[] MAX_BEGIN = { '{', '\\', 'm', 'a', 'x', '{',
      '\\', 'l', 'e', 'f', 't', '\\', };
  /** the end max */
  static final char[] MAX_END = { '\\', 'r', 'i', 'g', 'h', 't', '\\',
      '}', '}', '}' };

  /**
   * Create a new mathematical function
   *
   * @param owner
   *          the owning text
   */
  _LaTeXMathMax(final BasicMath owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void render(final ITextOutput out, final char[][] data,
      final int size) {
    char next;

    out.append(_LaTeXMathMax.MAX_BEGIN);
    next = '{';
    for (final char[] operand : data) {
      out.append(next);
      next = ',';
      out.append(operand);
    }
    out.append(_LaTeXMathMax.MAX_END);
  }
}
