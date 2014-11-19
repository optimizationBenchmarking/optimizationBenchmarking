package org.optimizationBenchmarking.utils.document.impl.latex;

import java.nio.file.Path;
import java.util.logging.Logger;

import org.optimizationBenchmarking.utils.document.impl.abstr.Document;
import org.optimizationBenchmarking.utils.tools.spec.IFileProducerListener;

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
   * @param logger
   *          the logger
   */
  _LaTeXDocument(final LaTeXDriver driver, final Path docPath,
      final Logger logger, final IFileProducerListener listener) {
    super(driver, docPath, logger, listener);
    this.open();
  }
}
