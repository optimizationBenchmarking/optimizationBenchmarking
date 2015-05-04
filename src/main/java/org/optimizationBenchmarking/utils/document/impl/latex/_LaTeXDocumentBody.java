package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentBody;

/** the LaTeX document body */
final class _LaTeXDocumentBody extends DocumentBody {
  /**
   * Create a document body
   *
   * @param owner
   *          the owning document
   */
  _LaTeXDocumentBody(final LaTeXDocument owner) {
    super(owner);
    this.open();
  }
}
