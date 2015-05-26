package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.ComplexText;
import org.optimizationBenchmarking.utils.document.impl.abstr.InlineCode;

/** an inline code element of a section in an export document */
final class _ExportInlineCode extends InlineCode {

  /**
   * create the inline code element
   *
   * @param owner
   *          the owner
   */
  _ExportInlineCode(final ComplexText owner) {
    super(owner);
    this.open();
  }
}
