package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentTitle;

/** the title of an export document header */
final class _ExportDocumentTitle extends DocumentTitle {
  /**
   * Create a document title.
   *
   * @param owner
   *          the owning document header
   */
  _ExportDocumentTitle(final _ExportDocumentHeader owner) {
    super(owner);
    this.open();
  }
}
