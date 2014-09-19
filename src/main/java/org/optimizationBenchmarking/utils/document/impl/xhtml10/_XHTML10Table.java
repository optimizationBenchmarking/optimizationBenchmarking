package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.Table;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.document.spec.TableCellDef;

/** a table in a XHTML document */
final class _XHTML10Table extends Table {
  /**
   * Create a table
   * 
   * @param owner
   *          the owning section body
   * @param index
   *          the table index in the owning section
   * @param useLabel
   *          the label to use
   * @param spansAllColumns
   *          does the table span all columns
   * @param definition
   *          the table cell definition
   */
  _XHTML10Table(final _XHTML10SectionBody owner, final ILabel useLabel,
      final boolean spansAllColumns, final int index,
      final TableCellDef[] definition) {
    super(owner, useLabel, spansAllColumns, index, definition);
    this.open();
  }
}
