package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathTan;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical tangent function in a LaTeX document */
final class _LaTeXMathTan extends MathTan {
  /** the begin tangent */
  private static final char[] TAN_BEGIN = { '{', '\\', 't', 'a', 'n', '{', };

  /**
   * Create a new mathematical function
   * 
   * @param owner
   *          the owning text
   */
  _LaTeXMathTan(final BasicMath owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void render(final ITextOutput out, final char[][] data,
      final int size) {

    out.append(_LaTeXMathTan.TAN_BEGIN);
    out.append(data[0]);
    out.append('}');
    out.append('}');
  }

}
