package org.optimizationBenchmarking.utils.document.spec;

import org.optimizationBenchmarking.utils.bibliography.data.BibAuthorsBuilder;
import org.optimizationBenchmarking.utils.bibliography.data.BibDateBuilder;

/** The header of a document. */
public interface IDocumentHeader extends IDocumentPart, IStyleContext {

  /**
   * Define a mathematical macro into this document header, if it has not
   * already been defined (and do nothing otherwise)
   * 
   * @param macro
   *          the macro to be defined
   */
  public abstract void defineMathMacro(final MathMacro macro);

  /**
   * Define a text macro into this document header, if it has not already
   * been defined (and do nothing otherwise)
   * 
   * @param macro
   *          the macro to be defined
   */
  public abstract void defineTextMacro(final TextMacro macro);

  /**
   * Write the document's title
   * 
   * @return the document's title
   */
  public abstract IText title();

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
  public abstract IText summary();
}
