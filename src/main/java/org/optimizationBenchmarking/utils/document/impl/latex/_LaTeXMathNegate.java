package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathNegate;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical negate function in a LaTeX document */
final class _LaTeXMathNegate extends MathNegate {

  /**
   * Create a new mathematical function
   *
   * @param owner
   *          the owning text
   */
  _LaTeXMathNegate(final BasicMath owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void render(final ITextOutput out, final char[][] data,
      final int size) {

    out.append('{');
    out.append('-');
    out.append('{');
    out.append(data[0]);
    out.append('}');
    out.append('}');
  }
}
