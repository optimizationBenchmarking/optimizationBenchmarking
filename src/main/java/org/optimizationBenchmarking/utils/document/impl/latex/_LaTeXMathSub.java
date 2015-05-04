package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathSub;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical sub function in a LaTeX document */
final class _LaTeXMathSub extends MathSub {

  /**
   * Create a new mathematical function
   *
   * @param owner
   *          the owning text
   */
  _LaTeXMathSub(final BasicMath owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void render(final ITextOutput out, final char[][] data,
      final int size) {
    int i;
    for (i = 0; i < size; i++) {
      if (i > 0) {
        out.append('-');
      }
      out.append(data[i]);
    }
  }
}
