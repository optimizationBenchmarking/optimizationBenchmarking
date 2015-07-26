package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathCompare;
import org.optimizationBenchmarking.utils.document.spec.EMathComparison;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical compare function in an export document */
final class _ExportMathCompare extends MathCompare {

  /**
   * Create a new mathematical function
   *
   * @param owner
   *          the owning text
   * @param cmp
   *          the comparator
   */
  _ExportMathCompare(final BasicMath owner, final EMathComparison cmp) {
    super(owner, cmp);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void render(final ITextOutput out, final char[][] data,
      final int size) {

    out.append('(');
    out.append(data[0]);
    out.append(')');

    out.append(TextUtils.toLowerCase(this.getComparison().toString()));

    out.append('(');
    out.append(data[1]);
    out.append(')');
  }
}
