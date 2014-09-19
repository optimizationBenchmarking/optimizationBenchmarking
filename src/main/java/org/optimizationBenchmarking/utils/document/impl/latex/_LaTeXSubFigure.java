package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.SubFigure;
import org.optimizationBenchmarking.utils.document.spec.ILabel;

/** a sub-figure in a LaTeX document */
final class _LaTeXSubFigure extends SubFigure {
  /**
   * Create a new sub figure
   * 
   * @param owner
   *          the owning section body
   * @param useLabel
   *          the label to use
   * @param path
   *          the path suggestion
   */
  public _LaTeXSubFigure(final _LaTeXFigureSeries owner,
      final ILabel useLabel, final String path) {
    super(owner, useLabel, path);
    this.open();
  }
}
