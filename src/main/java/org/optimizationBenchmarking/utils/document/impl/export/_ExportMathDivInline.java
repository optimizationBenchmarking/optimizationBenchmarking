package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathDivInline;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical div inline function in an export document */
final class _ExportMathDivInline extends MathDivInline {

  /**
   * Create a new mathematical function
   *
   * @param owner
   *          the owning text
   */
  _ExportMathDivInline(final BasicMath owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void render(final ITextOutput out, final char[][] data,
      final int size) {
    out.append('(');
    out.append(data[0]);
    out.append(')');
    out.append('/');
    out.append('(');
    out.append(data[1]);
    out.append(')');
  }
}
