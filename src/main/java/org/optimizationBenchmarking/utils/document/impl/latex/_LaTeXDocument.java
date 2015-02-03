package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.Document;

/** the LaTeX document */
final class _LaTeXDocument extends Document {
  /**
   * Create a document.
   * 
   * @param builder
   *          the document builder
   */
  _LaTeXDocument(final LaTeXDocumentBuilder builder) {
    super(LaTeXDriver.getInstance(), builder);
    this.open();
  }
}
