package org.optimizationBenchmarking.utils.document.impl.abstr;

/**
 * the caption of a figure series
 */
public class FigureSeriesCaption extends ComplexText {
  /**
   * create the caption
   *
   * @param owner
   *          the owner
   */
  protected FigureSeriesCaption(final FigureSeries owner) {
    super(owner);
  }

  /**
   * Get the owning figure series
   *
   * @return the owning figure series
   */
  @Override
  protected FigureSeries getOwner() {
    return ((FigureSeries) (super.getOwner()));
  }
}
