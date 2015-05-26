package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathLn;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical ln function in an export document */
final class _ExportMathLn extends MathLn {

  /**
   * Create a new mathematical function
   *
   * @param owner
   *          the owning text
   */
  _ExportMathLn(final BasicMath owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void render(final ITextOutput out, final char[][] data,
      final int size) {
    out.append("ln("); //$NON-NLS-1$
    out.append(data[0]);
    out.append(')');
  }
}
