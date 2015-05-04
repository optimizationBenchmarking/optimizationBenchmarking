package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathCos;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical cosine function in a LaTeX document */
final class _LaTeXMathCos extends MathCos {
  /** the begin cosine */
  private static final char[] COS_BEGIN = { '{', '\\', 'c', 'o', 's', '{', };

  /**
   * Create a new mathematical function
   *
   * @param owner
   *          the owning text
   */
  _LaTeXMathCos(final BasicMath owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void render(final ITextOutput out, final char[][] data,
      final int size) {

    out.append(_LaTeXMathCos.COS_BEGIN);
    out.append(data[0]);
    out.append('}');
    out.append('}');
  }
}
