package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentHeader;

/** the export document header */
final class _ExportDocumentHeader extends DocumentHeader {

  /**
   * Create a document header.
   *
   * @param owner
   *          the owning document
   */
  _ExportDocumentHeader(final _ExportDocument owner) {
    super(owner);
    this.open();
  }
}
