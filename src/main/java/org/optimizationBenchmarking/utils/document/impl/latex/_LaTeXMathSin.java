package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathSin;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical sine function in a LaTeX document */
final class _LaTeXMathSin extends MathSin {
  /** the begin sine */
  private static final char[] SIN_BEGIN = { '{', '\\', 's', 'i', 'n', '{', };

  /**
   * Create a new mathematical function
   * 
   * @param owner
   *          the owning text
   */
  _LaTeXMathSin(final BasicMath owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void render(final ITextOutput out, final char[][] data,
      final int size) {

    out.append(_LaTeXMathSin.SIN_BEGIN);
    out.append(data[0]);
    out.append('}');
    out.append('}');
  }
}
