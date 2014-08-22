package org.optimizationBenchmarking.utils.document.spec;

/** The footer of a document. */
public interface IDocumentFooter extends IStyleContext, IDocumentPart {

  /**
   * Create a new appendix section.
   * 
   * @param useLabel
   *          a label to use for the table,
   *          {@link org.optimizationBenchmarking.utils.document.spec.ELabelType#AUTO}
   *          if a label should be created, or {@code null} if this
   *          component should not be labeled
   * @return the new section
   */
  public abstract ISection appendix(final ILabel useLabel);
}
