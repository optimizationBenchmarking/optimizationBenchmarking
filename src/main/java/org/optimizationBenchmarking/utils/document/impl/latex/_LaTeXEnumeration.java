package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.Enumeration;
import org.optimizationBenchmarking.utils.document.impl.abstr.StructuredText;

/** an enumeration in a LaTeX document */
final class _LaTeXEnumeration extends Enumeration {
  /**
   * Create a new enumeration
   * 
   * @param owner
   *          the owning text
   */
  _LaTeXEnumeration(final StructuredText owner) {
    super(owner);
    this.open();
  }
}
