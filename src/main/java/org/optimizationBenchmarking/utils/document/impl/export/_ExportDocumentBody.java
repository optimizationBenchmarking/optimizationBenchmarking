package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentBody;

/** the export document body */
final class _ExportDocumentBody extends DocumentBody {

  /**
   * Create a document body
   *
   * @param owner
   *          the owning document
   */
  _ExportDocumentBody(final _ExportDocument owner) {
    super(owner);
    this.open();
  }
}
