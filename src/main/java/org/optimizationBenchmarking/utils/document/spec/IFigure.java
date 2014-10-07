package org.optimizationBenchmarking.utils.document.spec;

import org.optimizationBenchmarking.utils.graphics.graphic.Graphic;

/**
 * The interface to create and draw on figures.
 */
public interface IFigure extends IDocumentElement, ILabeledElement {

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
