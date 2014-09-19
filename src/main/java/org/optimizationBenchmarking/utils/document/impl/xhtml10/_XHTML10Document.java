package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import java.nio.file.Path;

import org.optimizationBenchmarking.utils.document.IObjectListener;
import org.optimizationBenchmarking.utils.document.impl.abstr.Document;

/** the XHTML document */
final class _XHTML10Document extends Document {
  /**
   * Create a document.
   * 
   * @param driver
   *          the document driver
   * @param docPath
   *          the path to the document
   * @param listener
   *          the object listener the object listener
   */
  _XHTML10Document(final XHTML10Driver driver, final Path docPath,
      final IObjectListener listener) {
    super(driver, docPath, listener);
    this.open();
  }
}
