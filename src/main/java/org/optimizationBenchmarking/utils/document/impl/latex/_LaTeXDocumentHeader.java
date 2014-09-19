package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentHeader;

/** the LaTeX document header */
final class _LaTeXDocumentHeader extends DocumentHeader {
  /**
   * Create a document header.
   * 
   * @param owner
   *          the owning document
   */
  _LaTeXDocumentHeader(final _LaTeXDocument owner) {
    super(owner);
    this.open();
  }
}
