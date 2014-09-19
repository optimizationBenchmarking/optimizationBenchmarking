package org.optimizationBenchmarking.utils.document.impl.latex;

import java.nio.file.Path;

import org.optimizationBenchmarking.utils.document.IObjectListener;
import org.optimizationBenchmarking.utils.document.impl.abstr.Document;

/** the LaTeX document */
final class _LaTeXDocument extends Document {
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
  _LaTeXDocument(final LaTeXDriver driver, final Path docPath,
      final IObjectListener listener) {
    super(driver, docPath, listener);
    this.open();
  }
}
