package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.CodeBody;

/** the body of code object in a LaTeX document */
final class _LaTeXCodeBody extends CodeBody {
  /**
   * create the code body
   * 
   * @param owner
   *          the owning FSM
   */
  _LaTeXCodeBody(final _LaTeXCode owner) {
    super(owner);
    this.open();
  }
}
