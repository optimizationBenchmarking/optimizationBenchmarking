package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.ComplexText;
import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentElement;

/** a complex text in a LaTeX document */
final class _LaTeXComplexText extends ComplexText {
  /**
   * create the complex text
   * 
   * @param owner
   *          the owning element
   */
  _LaTeXComplexText(final DocumentElement owner) {
    super(owner);
    this.open();
  }
}
