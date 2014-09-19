package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentTitle;

/** the title of a LaTeX document header */
final class _LaTeXDocumentTitle extends DocumentTitle {
  /**
   * Create a document title.
   * 
   * @param owner
   *          the owning document header
   */
  _LaTeXDocumentTitle(final _LaTeXDocumentHeader owner) {
    super(owner);
    this.open();
  }
}
