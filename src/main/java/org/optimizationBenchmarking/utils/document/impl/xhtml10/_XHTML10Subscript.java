package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.ComplexText;
import org.optimizationBenchmarking.utils.document.impl.abstr.Subscript;

/** an subscript element of a section in a XHTML document */
final class _XHTML10Subscript extends Subscript {
  /**
   * create the subscript element
   * 
   * @param owner
   *          the owner
   */
  _XHTML10Subscript(final ComplexText owner) {
    super(owner);
    this.open();
  }
}
