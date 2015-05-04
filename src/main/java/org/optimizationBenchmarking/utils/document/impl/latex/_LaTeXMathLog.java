package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathLog;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical log function in a LaTeX document */
final class _LaTeXMathLog extends MathLog {
  /** the begin logarithm */
  private static final char[] LOG_BEGIN = { '{', '\\', 'l', 'o', 'g', '_',
      '{', };

  /**
   * Create a new mathematical function
   *
   * @param owner
   *          the owning text
   */
  _LaTeXMathLog(final BasicMath owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void render(final ITextOutput out, final char[][] data,
      final int size) {

    out.append(_LaTeXMathLog.LOG_BEGIN);
    out.append(data[0]);
    out.append('}');
    out.append('{');
    out.append(data[1]);
    out.append('}');
    out.append('}');
  }
}
