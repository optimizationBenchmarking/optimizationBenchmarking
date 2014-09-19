package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.FigureCaption;

/** the caption of a figure in a LaTeX document */
final class _LaTeXFigureCaption extends FigureCaption {
  /**
   * Create a new figure caption
   * 
   * @param owner
   *          the owner
   */
  _LaTeXFigureCaption(final _LaTeXFigure owner) {
    super(owner);
    this.open();
  }
}
