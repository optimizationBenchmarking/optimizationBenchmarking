package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathNAryFunction;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical n-ary function in an export document */
final class _ExportMathNAryFunction extends MathNAryFunction {

  /**
   * Create a new mathematical function
   *
   * @param owner
   *          the owning text
   * @param name
   *          the name of the function
   * @param minArity
   *          the minimum number of arguments
   * @param maxArity
   *          the maximum number of arguments
   */
  _ExportMathNAryFunction(final BasicMath owner, final String name,
      final int minArity, final int maxArity) {
    super(owner, name, minArity, maxArity);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void render(final ITextOutput out, final char[][] data,
      final int size) {
    char next;

    out.append(this.getName());
    next = '(';
    for (final char[] dta : data) {
      out.append(next);
      next = ',';
      out.append(dta);
    }
    out.append(')');
  }
}
