package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.Code;
import org.optimizationBenchmarking.utils.document.spec.ILabel;

/** a code object in a LaTeX document */
final class _LaTeXCode extends Code {
  /**
   * create the code
   * 
   * @param owner
   *          the owning section body
   * @param index
   *          the table index in the owning section
   * @param useLabel
   *          the label to use
   * @param spansAllColumns
   *          does the table span all columns
   */
  _LaTeXCode(final _LaTeXSectionBody owner, final ILabel useLabel,
      final boolean spansAllColumns, final int index) {
    super(owner, useLabel, spansAllColumns, index);
    this.open();
  }
}
