package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.SubFigureCaption;

/** the caption of a sub-figure in a LaTeX document */
final class _LaTeXSubFigureCaption extends SubFigureCaption {
  /**
   * Create a new sub-figure caption
   * 
   * @param owner
   *          the owner
   */
  _LaTeXSubFigureCaption(final _LaTeXSubFigure owner) {
    super(owner);
    this.open();
  }
}
