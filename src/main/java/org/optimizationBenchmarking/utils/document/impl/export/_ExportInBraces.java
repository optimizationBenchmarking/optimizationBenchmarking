package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.InBraces;
import org.optimizationBenchmarking.utils.document.impl.abstr.PlainText;

/** an in-braces element of a section in an export document */
final class _ExportInBraces extends InBraces {
  /**
   * create the in-braces element
   *
   * @param owner
   *          the owner
   */
  _ExportInBraces(final PlainText owner) {
    super(owner);
    this.open();
  }
}
