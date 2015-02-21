package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathCompare;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical compare function in a LaTeX document */
final class _LaTeXMathCompare extends MathCompare {
  /** the signs */
  private static final char[][] SIGNS;

  static {
    SIGNS = new char[EComparison.INSTANCES.size()][];

    _LaTeXMathCompare.SIGNS[EComparison.LESS.ordinal()] = new char[] {
        '}', '<', '{' };
    _LaTeXMathCompare.SIGNS[EComparison.LESS_OR_EQUAL.ordinal()] = //
    _LaTeXMathCompare.SIGNS[EComparison.LESS_OR_SAME.ordinal()] = new char[] {
        '}', '{', '\\', 'l', 'e', 'q', '}', '{' };
    _LaTeXMathCompare.SIGNS[EComparison.EQUAL.ordinal()] = _LaTeXMathCompare.SIGNS[EComparison.SAME
        .ordinal()] = new char[] { '=' };
    _LaTeXMathCompare.SIGNS[EComparison.GREATER_OR_SAME.ordinal()] = //
    _LaTeXMathCompare.SIGNS[EComparison.GREATER_OR_EQUAL.ordinal()] = new char[] {
        '}', '{', '\\', 'g', 'e', 'q', '}', '{' };
    _LaTeXMathCompare.SIGNS[EComparison.GREATER.ordinal()] = new char[] {
        '}', '>', '{' };
    _LaTeXMathCompare.SIGNS[EComparison.NOT_EQUAL.ordinal()] = //
    _LaTeXMathCompare.SIGNS[EComparison.NOT_SAME.ordinal()] = new char[] {
        '}', '{', '\\', 'n', 'e', 'q', '}', '{', };

  }

  /**
   * Create a new mathematical function
   * 
   * @param owner
   *          the owning text
   * @param cmp
   *          the comparator
   */
  _LaTeXMathCompare(final BasicMath owner, final EComparison cmp) {
    super(owner, cmp);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void render(final ITextOutput out, final char[][] data,
      final int size) {
    out.append('{');
    out.append('{');
    out.append(data[0]);
    out.append(_LaTeXMathCompare.SIGNS[this.getComparison().ordinal()]);
    out.append(data[1]);
    out.append('}');
    out.append('}');
  }
}
