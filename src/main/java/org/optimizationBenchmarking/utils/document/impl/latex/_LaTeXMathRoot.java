package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathRoot;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical root function in a LaTeX document */
final class _LaTeXMathRoot extends MathRoot {
  /** the root */
  private static final char[] ROOT = { '{', '\\', 's', 'q', 'r', 't', '[',
      '{', };

  /**
   * Create a new mathematical function
   *
   * @param owner
   *          the owning text
   */
  _LaTeXMathRoot(final BasicMath owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void render(final ITextOutput out, final char[][] data,
      final int size) {
    out.append(_LaTeXMathRoot.ROOT);
    out.append(data[0]);
    out.append('}');
    out.append(']');
    out.append('{');
    out.append(data[1]);
    out.append('}');
    out.append('}');
  }
}
