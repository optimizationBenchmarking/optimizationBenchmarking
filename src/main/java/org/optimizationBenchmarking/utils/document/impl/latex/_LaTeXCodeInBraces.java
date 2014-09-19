package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.CodeInBraces;

/** a code in braces object in a LaTeX document */
final class _LaTeXCodeInBraces extends CodeInBraces {
  /**
   * create the code in braces
   * 
   * @param owner
   *          the owning code body
   */
  _LaTeXCodeInBraces(final _LaTeXCodeBody owner) {
    super(owner);
    this.open();
  }
}
