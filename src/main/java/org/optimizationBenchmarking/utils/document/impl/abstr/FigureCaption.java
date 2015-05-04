package org.optimizationBenchmarking.utils.document.impl.abstr;

/**
 * the caption of a figure
 */
public class FigureCaption extends BasicFigureCaption {
  /**
   * create the caption
   *
   * @param owner
   *          the owner
   */
  protected FigureCaption(final Figure owner) {
    super(owner);
  }

  /**
   * Get the owning figure
   *
   * @return the owning figure
   */
  @Override
  protected Figure getOwner() {
    return ((Figure) (super.getOwner()));
  }
}
