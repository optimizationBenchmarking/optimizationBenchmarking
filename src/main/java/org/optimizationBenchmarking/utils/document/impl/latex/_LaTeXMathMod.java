package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathMod;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical mod function in a LaTeX document */
final class _LaTeXMathMod extends MathMod {
  /** the modulus */
  private static final char[] MOD = { '}', '\\', 'b', 'm', 'o', 'd', '{', };

  /**
   * Create a new mathematical function
   *
   * @param owner
   *          the owning text
   */
  _LaTeXMathMod(final BasicMath owner) {
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
    out.append(_LaTeXMathMod.MOD);
    out.append(data[1]);
    out.append('}');
    out.append('}');
  }
}
