package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathFactorial;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical factorial function in an export document */
final class _ExportMathFactorial extends MathFactorial {

  /**
   * Create a new mathematical function
   *
   * @param owner
   *          the owning text
   */
  _ExportMathFactorial(final BasicMath owner) {
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
    out.append('!');
  }
}
