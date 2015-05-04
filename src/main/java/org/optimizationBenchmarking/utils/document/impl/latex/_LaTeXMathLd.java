package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathLd;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical ld function in a LaTeX document */
final class _LaTeXMathLd extends MathLd {
  /** the begin binary logarithm */
  private static final char[] LD_BEGIN = { '{', '\\', 'l', 'o', 'g', '_',
      '2', '{', };

  /**
   * Create a new mathematical function
   *
   * @param owner
   *          the owning text
   */
  _LaTeXMathLd(final BasicMath owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void render(final ITextOutput out, final char[][] data,
      final int size) {

    out.append(_LaTeXMathLd.LD_BEGIN);
    out.append(data[0]);
    out.append('}');
    out.append('}');
  }
}
