package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.Equation;
import org.optimizationBenchmarking.utils.document.spec.ILabel;

/** an equation in a XHTML document */
final class _XHTML10Equation extends Equation {
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
  _XHTML10Equation(final _XHTML10SectionBody owner, final ILabel useLabel,
      final int index) {
    super(owner, useLabel, index);
    this.open();
  }
}
