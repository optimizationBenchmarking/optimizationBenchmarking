package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.Itemization;
import org.optimizationBenchmarking.utils.document.impl.abstr.StructuredText;

/** an itemization in an export document */
final class _ExportItemization extends Itemization {

  /**
   * Create a new itemization
   *
   * @param owner
   *          the owning text
   */
  _ExportItemization(final StructuredText owner) {
    super(owner);
    this.open();
  }
}
