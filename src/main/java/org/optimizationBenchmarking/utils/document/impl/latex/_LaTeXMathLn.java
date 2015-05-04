package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathLn;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical ln function in a LaTeX document */
final class _LaTeXMathLn extends MathLn {
  /** the begin natural logarithm */
  private static final char[] LN_BEGIN = { '{', '\\', 'l', 'n', '{', };

  /**
   * Create a new mathematical function
   *
   * @param owner
   *          the owning text
   */
  _LaTeXMathLn(final BasicMath owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void render(final ITextOutput out, final char[][] data,
      final int size) {

    out.append(_LaTeXMathLn.LN_BEGIN);
    out.append(data[0]);
    out.append('}');
    out.append('}');
  }
}
