package org.optimizationBenchmarking.utils.document.spec;

import org.optimizationBenchmarking.utils.bibliography.data.BibAuthorsBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibDateBuilder;

/** The header of a document. */
public interface IDocumentHeader extends IDocumentElement {

  /**
   * Write the document's title
   *
   * @return the document's title
   */
  public abstract IPlainText title();

  /**
   * Build the authors of this document
   *
   * @return the authors of this document
   */
  public abstract BibAuthorsBuilder authors();

  /**
   * Set the document's data
   *
   * @return the document's data
   */
  public abstract BibDateBuilder date();

  /**
   * Write the document's summary
   *
   * @return the document's summary
   */
  public abstract IPlainText summary();
}
