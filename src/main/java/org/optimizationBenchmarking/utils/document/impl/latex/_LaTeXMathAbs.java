package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathAbs;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical absolute function in a LaTeX document */
final class _LaTeXMathAbs extends MathAbs {
  /** the begin abs */
  private static final char[] ABS_BEGIN = { '{', '\\', 'l', 'e', 'f', 't',
      '|', '{', };
  /** the end abs */
  private static final char[] ABS_END = { '}', '\\', 'r', 'i', 'g', 'h',
      't', '|', '}' };

  /**
   * Create a new mathematical function
   *
   * @param owner
   *          the owning text
   */
  _LaTeXMathAbs(final BasicMath owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void render(final ITextOutput out, final char[][] data,
      final int size) {
    out.append(_LaTeXMathAbs.ABS_BEGIN);
    out.append(data[0]);
    out.append(_LaTeXMathAbs.ABS_END);
  }
}
