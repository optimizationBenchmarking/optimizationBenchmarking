package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentSummary;

/** the summary of an export document header */
final class _ExportDocumentSummary extends DocumentSummary {
  /**
   * Create a document summary.
   *
   * @param owner
   *          the owning document header
   */
  _ExportDocumentSummary(final _ExportDocumentHeader owner) {
    super(owner);
    this.open();
  }
}
