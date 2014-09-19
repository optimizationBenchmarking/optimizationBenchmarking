package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.CodeCaption;

/** the caption of code object in a LaTeX document */
final class _LaTeXCodeCaption extends CodeCaption {
  /**
   * create the code caption
   * 
   * @param owner
   *          the owning FSM
   */
  _LaTeXCodeCaption(final _LaTeXCode owner) {
    super(owner);
    this.open();
  }
}
