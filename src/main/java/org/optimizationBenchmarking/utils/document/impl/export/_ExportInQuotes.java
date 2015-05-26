package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.InQuotes;
import org.optimizationBenchmarking.utils.document.impl.abstr.PlainText;

/** an in-quotes element of a section in an export document */
final class _ExportInQuotes extends InQuotes {
  /**
   * create the in-quotes element
   *
   * @param owner
   *          the owner
   */
  _ExportInQuotes(final PlainText owner) {
    super(owner);
    this.open();
  }
}
