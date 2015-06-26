package org.optimizationBenchmarking.utils.document.spec;

import java.nio.file.Path;

import org.optimizationBenchmarking.utils.graphics.style.IStyleProvider;
import org.optimizationBenchmarking.utils.tools.spec.IToolJob;

/**
 * The root interface for the document output API.
 */
public interface IDocument extends IDocumentElement, IStyleProvider,
    IToolJob, ILabelBuilder {

  /**
   * create the document header
   *
   * @return the document header
   */
  public abstract IDocumentHeader header();

  /**
   * create the document body
   *
   * @return the document body
   */
  public abstract IDocumentBody body();

  /**
   * create the document footer
   *
   * @return the document footer
   */
  public abstract IDocumentBody footer();

  /**
   * Get the path to the document's main file
   *
   * @return the path to the document's main file
   */
  public abstract Path getDocumentPath();
}
