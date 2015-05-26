package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.ComplexText;
import org.optimizationBenchmarking.utils.document.impl.abstr.Emphasize;

/** an emphasize element of a section in an export document */
final class _ExportEmphasize extends Emphasize {

  /**
   * create the emphasize element
   *
   * @param owner
   *          the owner
   */
  _ExportEmphasize(final ComplexText owner) {
    super(owner);
    this.open();
  }
}
