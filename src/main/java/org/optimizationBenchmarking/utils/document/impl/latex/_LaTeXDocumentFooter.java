package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentFooter;

/** the LaTeX document footer */
final class _LaTeXDocumentFooter extends DocumentFooter {
  /**
   * Create a document footer
   * 
   * @param owner
   *          the owning document
   */
  _LaTeXDocumentFooter(final _LaTeXDocument owner) {
    super(owner);
    this.open();
  }
}
