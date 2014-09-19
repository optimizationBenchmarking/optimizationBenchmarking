package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.CodeInBraces;

/** a code in braces object in a XHTML document */
final class _XHTML10CodeInBraces extends CodeInBraces {
  /**
   * create the code in braces
   * 
   * @param owner
   *          the owning code body
   */
  _XHTML10CodeInBraces(final _XHTML10CodeBody owner) {
    super(owner);
    this.open();
  }
}
