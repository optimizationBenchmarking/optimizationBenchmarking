package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathLg;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical lg function in a LaTeX document */
final class _LaTeXMathLg extends MathLg {
  /** the begin decadic logarithm */
  private static final char[] LG_BEGIN = { '{', '\\', 'l', 'o', 'g', '_',
      '{', '1', '0', '}', '{', };

  /**
   * Create a new mathematical function
   *
   * @param owner
   *          the owning text
   */
  _LaTeXMathLg(final BasicMath owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void render(final ITextOutput out, final char[][] data,
      final int size) {

    out.append(_LaTeXMathLg.LG_BEGIN);
    out.append(data[0]);
    out.append('}');
    out.append('}');
  }
}
