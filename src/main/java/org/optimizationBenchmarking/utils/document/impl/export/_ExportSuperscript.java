package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.Superscript;
import org.optimizationBenchmarking.utils.document.impl.abstr.Text;

/** an superscript element of a section in an export document */
final class _ExportSuperscript extends Superscript {
  /**
   * create the superscript element
   *
   * @param owner
   *          the owner
   */
  _ExportSuperscript(final Text owner) {
    super(owner);
    this.open();
  }
}
