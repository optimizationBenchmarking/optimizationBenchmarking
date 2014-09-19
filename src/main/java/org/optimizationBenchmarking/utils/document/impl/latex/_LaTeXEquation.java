package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.Equation;
import org.optimizationBenchmarking.utils.document.spec.ILabel;

/** an equation in a LaTeX document */
final class _LaTeXEquation extends Equation {
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
  _LaTeXEquation(final _LaTeXSectionBody owner, final ILabel useLabel,
      final int index) {
    super(owner, useLabel, index);
    this.open();
  }
}
