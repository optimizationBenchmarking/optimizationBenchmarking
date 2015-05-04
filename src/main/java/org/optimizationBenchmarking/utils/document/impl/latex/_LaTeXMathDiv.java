package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathDiv;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical div function in a LaTeX document */
final class _LaTeXMathDiv extends MathDiv {
  /** the begin div */
  private static final char[] DIV_BEGIN = { '{', '\\', 'f', 'r', 'a', 'c',
      '{', };

  /**
   * Create a new mathematical function
   *
   * @param owner
   *          the owning text
   */
  _LaTeXMathDiv(final BasicMath owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void render(final ITextOutput out, final char[][] data,
      final int size) {

    out.append(_LaTeXMathDiv.DIV_BEGIN);
    out.append(data[0]);
    out.append('}');
    out.append('{');
    out.append(data[1]);
    out.append('}');
    out.append('}');
  }
}
