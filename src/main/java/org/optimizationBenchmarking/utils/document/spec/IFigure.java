package org.optimizationBenchmarking.utils.document.spec;

/**
 * The interface to create and draw on figures.
 */
public interface IFigure extends IDocumentPart {

  /**
   * write the figure caption
   * 
   * @return the complex text to write the figure caption
   */
  public abstract IComplexText caption();

  /**
   * The figure body to paint on
   * 
   * @return the figure body to paint on
   */
  public abstract Graphic body();
}
