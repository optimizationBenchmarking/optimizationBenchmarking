package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathSin;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical sine function in an export document */
final class _ExportMathSin extends MathSin {
  /**
   * Create a new mathematical function
   *
   * @param owner
   *          the owning text
   */
  _ExportMathSin(final BasicMath owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void render(final ITextOutput out, final char[][] data,
      final int size) {
    out.append("sin("); //$NON-NLS-1$
    out.append(data[0]);
    out.append(')');
  }
}
