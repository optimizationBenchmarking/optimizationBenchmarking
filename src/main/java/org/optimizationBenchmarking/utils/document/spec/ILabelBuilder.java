package org.optimizationBenchmarking.utils.document.spec;

/**
 * This interface is common to document elements which can create labels.
 */
public interface ILabelBuilder {
  /**
   * Create a new label to mark a table or figure or section with that is
   * going to be written in the future.
   *
   * @param type
   *          the label type
   * @return the label to be used in forward references
   */
  public abstract ILabel createLabel(final ELabelType type);
}
