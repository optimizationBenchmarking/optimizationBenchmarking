package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.FigureSeriesCaption;

/** the caption of a figure series in a LaTeX document */
final class _LaTeXFigureSeriesCaption extends FigureSeriesCaption {
  /**
   * Create a new figure series caption
   * 
   * @param owner
   *          the owner
   */
  _LaTeXFigureSeriesCaption(final _LaTeXFigureSeries owner) {
    super(owner);
    this.open();
  }
}
