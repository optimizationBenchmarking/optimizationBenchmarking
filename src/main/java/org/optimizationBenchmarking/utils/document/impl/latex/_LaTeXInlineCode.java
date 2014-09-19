package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.ComplexText;
import org.optimizationBenchmarking.utils.document.impl.abstr.InlineCode;

/** an inline code element of a section in a LaTeX document */
final class _LaTeXInlineCode extends InlineCode {
  /**
   * create the inline code element
   * 
   * @param owner
   *          the owner
   */
  _LaTeXInlineCode(final ComplexText owner) {
    super(owner);
    this.open();
  }
}
