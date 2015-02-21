package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathSqrt;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical sqrt function in a LaTeX document */
final class _LaTeXMathSqrt extends MathSqrt {
  /** the root */
  private static final char[] ROOT = { '{', '\\', 's', 'q', 'r', 't', '{', };

  /**
   * Create a new mathematical function
   * 
   * @param owner
   *          the owning text
   */
  _LaTeXMathSqrt(final BasicMath owner) {
    super(owner);
  }

  /** {@inheritDoc} */
  @Override
  protected final void render(final ITextOutput out, final char[][] data,
      final int size) {
    out.append(_LaTeXMathSqrt.ROOT);
    out.append(data[0]);
    out.append('}');
    out.append('}');
  }
}
