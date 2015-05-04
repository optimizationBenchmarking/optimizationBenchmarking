package org.optimizationBenchmarking.utils.document.impl.abstr;

/**
 * the caption of a sub-figure
 */
public class SubFigureCaption extends BasicFigureCaption {
  /**
   * create the caption
   *
   * @param owner
   *          the owner
   */
  protected SubFigureCaption(final SubFigure owner) {
    super(owner);
  }

  /**
   * Get the owning figure
   *
   * @return the owning figure
   */
  @Override
  protected SubFigure getOwner() {
    return ((SubFigure) (super.getOwner()));
  }
}
