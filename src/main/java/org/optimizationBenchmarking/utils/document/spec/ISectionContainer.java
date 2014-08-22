package org.optimizationBenchmarking.utils.document.spec;

/** a section container */
public interface ISectionContainer extends IDocumentPart {
  /**
   * Create a new section.
   * 
   * @param useLabel
   *          a label to use for the table,
   *          {@link org.optimizationBenchmarking.utils.document.spec.ELabelType#AUTO}
   *          if a label should be created, or {@code null} if this
   *          component should not be labeled
   * @return the new section
   */
  public abstract ISection section(final ILabel useLabel);
}
