package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathExp;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical exp function in a LaTeX document */
final class _LaTeXMathExp extends MathExp {
  /** the begin exp */
  private static final char[] EXP_BEGIN = { '{', '\\', 'e', 'x', 'p', '{', };

  /**
   * Create a new mathematical function
   *
   * @param owner
   *          the owning text
   */
  _LaTeXMathExp(final BasicMath owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void render(final ITextOutput out, final char[][] data,
      final int size) {

    out.append(_LaTeXMathExp.EXP_BEGIN);
    out.append(data[0]);
    out.append('}');
    out.append('}');
  }
}
