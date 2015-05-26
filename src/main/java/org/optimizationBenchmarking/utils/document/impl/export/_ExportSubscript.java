package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.Subscript;
import org.optimizationBenchmarking.utils.document.impl.abstr.Text;

/** an subscript element of a section in an export document */
final class _ExportSubscript extends Subscript {
  /**
   * create the subscript element
   *
   * @param owner
   *          the owner
   */
  _ExportSubscript(final Text owner) {
    super(owner);
    this.open();
  }
}
