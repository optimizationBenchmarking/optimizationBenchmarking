package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.Equation;
import org.optimizationBenchmarking.utils.document.spec.ILabel;

/** an equation in an export document */
final class _ExportEquation extends Equation {

  /**
   * Create a new equation
   *
   * @param owner
   *          the owning section body
   * @param index
   *          the table index in the owning section
   * @param useLabel
   *          the label to use
   */
  _ExportEquation(final _ExportSectionBody owner, final ILabel useLabel,
      final int index) {
    super(owner, useLabel, index);
    this.open();
  }
}
