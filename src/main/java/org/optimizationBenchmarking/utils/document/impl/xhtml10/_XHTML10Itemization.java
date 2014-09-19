package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.Itemization;
import org.optimizationBenchmarking.utils.document.impl.abstr.StructuredText;

/** an itemization in a XHTML document */
final class _XHTML10Itemization extends Itemization {
  /**
   * Create a new itemization
   * 
   * @param owner
   *          the owning text
   */
  _XHTML10Itemization(final StructuredText owner) {
    super(owner);
    this.open();
  }
}
