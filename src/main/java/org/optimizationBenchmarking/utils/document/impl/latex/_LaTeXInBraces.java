package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.InBraces;
import org.optimizationBenchmarking.utils.document.impl.abstr.PlainText;

/** an in-braces element of a section in a LaTeX document */
final class _LaTeXInBraces extends InBraces {
  /**
   * create the in-braces element
   * 
   * @param owner
   *          the owner
   */
  _LaTeXInBraces(final PlainText owner) {
    super(owner);
    this.open();
  }
}
