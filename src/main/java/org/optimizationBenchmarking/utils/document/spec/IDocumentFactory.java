package org.optimizationBenchmarking.utils.document.spec;

import java.nio.file.Path;

/**
 * A document factory.
 */
public interface IDocumentFactory {

  /**
   * Create a document writing its output to the folder identified by the
   * path {@code destination}.
   *
   * @param destination
   *          a path identifying a destination folder
   * @return the document
   */
  public abstract IDocument createDocument(final Path destination);

}
