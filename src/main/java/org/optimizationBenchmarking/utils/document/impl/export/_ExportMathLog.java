package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathLog;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical log function in an export document */
final class _ExportMathLog extends MathLog {
  /**
   * Create a new mathematical function
   *
   * @param owner
   *          the owning text
   */
  _ExportMathLog(final BasicMath owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void render(final ITextOutput out, final char[][] data,
      final int size) {
    out.append("log("); //$NON-NLS-1$
    out.append(data[0]);
    out.append(',');
    out.append(data[1]);
    out.append(')');
  }
}
