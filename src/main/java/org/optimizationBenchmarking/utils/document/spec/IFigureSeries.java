package org.optimizationBenchmarking.utils.document.spec;

/**
 * The interface to create and draw a series of figures.
 */
public interface IFigureSeries extends IDocumentElement, ILabeledObject {

  /**
   * write the figure series caption
   *
   * @return the complex text to write the figure series caption
   */
  public abstract IComplexText caption();

  /**
   * Create the next figure in the series
   *
   * @param useLabel
   *          a label to use for the figure,
   *          {@link org.optimizationBenchmarking.utils.document.spec.ELabelType#AUTO}
   *          if a label should be created, or {@code null} if this
   *          component should not be labeled
   * @param path
   *          a relative path (using '/' as separators) suggestion which
   *          may be used for creating an output path to store the document
   *          under, can also be {@code null} in which case a default path
   *          may be used
   * @return the figure object
   */
  public abstract IFigure figure(final ILabel useLabel, final String path);
}
