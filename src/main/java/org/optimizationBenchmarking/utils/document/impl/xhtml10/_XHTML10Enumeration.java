package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.Enumeration;
import org.optimizationBenchmarking.utils.document.impl.abstr.StructuredText;

/** an enumeration in a XHTML document */
final class _XHTML10Enumeration extends Enumeration {
  /**
   * Create a new enumeration
   * 
   * @param owner
   *          the owning text
   */
  _XHTML10Enumeration(final StructuredText owner) {
    super(owner);
    this.open();
  }
}
