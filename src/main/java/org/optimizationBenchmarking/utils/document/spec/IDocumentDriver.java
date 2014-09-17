package org.optimizationBenchmarking.utils.document.spec;

import java.nio.file.Path;

import org.optimizationBenchmarking.utils.document.IObjectListener;

/**
 * The entry interface to the document API: a document driver can create
 * documents.
 */
public interface IDocumentDriver {
  /**
   * Create a document at the given destination
   * 
   * @param folder
   *          the path to the destination folder
   * @param nameSuggestion
   *          the suggestion for the document file
   * @param listener
   *          the document listener
   * @return the document
   */
  public abstract IDocument createDocument(final Path folder,
      final String nameSuggestion, final IObjectListener listener);
}
