package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.Code;
import org.optimizationBenchmarking.utils.document.spec.ILabel;

/** a code object in an export document */
final class _ExportCode extends Code {

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
  _ExportCode(final _ExportSectionBody owner, final ILabel useLabel,
      final boolean spansAllColumns, final int index) {
    super(owner, useLabel, spansAllColumns, index);
    this.open();
  }
}
