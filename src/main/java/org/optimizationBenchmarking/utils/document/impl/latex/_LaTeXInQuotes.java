package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.InQuotes;
import org.optimizationBenchmarking.utils.document.impl.abstr.PlainText;

/** an in-quotes element of a section in a LaTeX document */
final class _LaTeXInQuotes extends InQuotes {
  /**
   * create the in-quotes element
   * 
   * @param owner
   *          the owner
   */
  _LaTeXInQuotes(final PlainText owner) {
    super(owner);
    this.open();
  }
}
