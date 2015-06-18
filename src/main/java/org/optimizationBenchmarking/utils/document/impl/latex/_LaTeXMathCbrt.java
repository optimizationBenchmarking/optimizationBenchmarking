package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathCbrt;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical cbrt function in a LaTeX document */
final class _LaTeXMathCbrt extends MathCbrt {

  /**
   * Create a new mathematical function
   *
   * @param owner
   *          the owning text
   */
  _LaTeXMathCbrt(final BasicMath owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void render(final ITextOutput out, final char[][] data,
      final int size) {
    out.append(_LaTeXMathRoot.ROOT, 0, (_LaTeXMathRoot.ROOT.length - 1));
    out.append('3');
    out.append(']');
    out.append('{');
    out.append(data[0]);
    out.append('}');
    out.append('}');
  }
}
