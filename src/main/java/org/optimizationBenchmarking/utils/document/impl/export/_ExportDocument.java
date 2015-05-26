package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.Document;

/** the export document */
final class _ExportDocument extends Document {

  /**
   * Create a document.
   *
   * @param builder
   *          the document builder
   */
  _ExportDocument(final ExportDocumentBuilder builder) {
    super(ExportDriver.getInstance(), builder);
    this.open();
  }
}
