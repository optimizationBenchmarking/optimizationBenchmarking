package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentSummary;

/** the summary of a LaTeX document header */
final class _LaTeXDocumentSummary extends DocumentSummary {
  /**
   * Create a document summary.
   * 
   * @param owner
   *          the owning document header
   */
  _LaTeXDocumentSummary(final _LaTeXDocumentHeader owner) {
    super(owner);
    this.open();
  }
}
