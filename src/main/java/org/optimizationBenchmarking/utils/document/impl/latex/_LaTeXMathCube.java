package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathCube;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical cube function in a LaTeX document */
final class _LaTeXMathCube extends MathCube {

  /**
   * Create a new mathematical function
   *
   * @param owner
   *          the owning text
   */
  _LaTeXMathCube(final BasicMath owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void render(final ITextOutput out, final char[][] data,
      final int size) {
    out.append('{');
    out.append('{');
    out.append(data[0]);
    out.append('}');
    out.append('^');
    out.append('3');
    out.append('}');
  }
}
