package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentFooter;

/** the export document footer */
final class _ExportDocumentFooter extends DocumentFooter {
  /**
   * Create a document footer
   *
   * @param owner
   *          the owning document
   */
  _ExportDocumentFooter(final _ExportDocument owner) {
    super(owner);
    this.open();
  }
}
