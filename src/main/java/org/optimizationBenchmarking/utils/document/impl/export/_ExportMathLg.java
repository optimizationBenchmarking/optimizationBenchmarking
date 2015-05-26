package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathLg;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical lg function in an export document */
final class _ExportMathLg extends MathLg {
  /**
   * Create a new mathematical function
   *
   * @param owner
   *          the owning text
   */
  _ExportMathLg(final BasicMath owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void render(final ITextOutput out, final char[][] data,
      final int size) {
    out.append("lg("); //$NON-NLS-1$
    out.append(data[0]);
    out.append(')');
  }
}
