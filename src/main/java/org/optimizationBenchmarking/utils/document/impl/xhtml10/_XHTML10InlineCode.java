package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.ComplexText;
import org.optimizationBenchmarking.utils.document.impl.abstr.InlineCode;

/** an inline code element of a section in a XHTML document */
final class _XHTML10InlineCode extends InlineCode {
  /**
   * create the inline code element
   * 
   * @param owner
   *          the owner
   */
  _XHTML10InlineCode(final ComplexText owner) {
    super(owner);
    this.open();
  }
}
