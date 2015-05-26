package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.Enumeration;
import org.optimizationBenchmarking.utils.document.impl.abstr.StructuredText;

/** an enumeration in an export document */
final class _ExportEnumeration extends Enumeration {

  /**
   * Create a new enumeration
   *
   * @param owner
   *          the owning text
   */
  _ExportEnumeration(final StructuredText owner) {
    super(owner);
    this.open();
  }
}
