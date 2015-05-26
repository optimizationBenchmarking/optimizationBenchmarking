package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.ComplexText;
import org.optimizationBenchmarking.utils.document.impl.abstr.InlineMath;

/** an inline math element of a section in an export document */
final class _ExportInlineMath extends InlineMath {

  /**
   * create the inline math element
   *
   * @param owner
   *          the owner
   */
  _ExportInlineMath(final ComplexText owner) {
    super(owner);
    this.open();
  }
}
