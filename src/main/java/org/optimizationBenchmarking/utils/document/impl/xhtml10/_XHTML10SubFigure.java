package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.SubFigure;
import org.optimizationBenchmarking.utils.document.spec.ILabel;

/** a sub-figure in a XHTML document */
final class _XHTML10SubFigure extends SubFigure {
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
  public _XHTML10SubFigure(final _XHTML10FigureSeries owner,
      final ILabel useLabel, final String path) {
    super(owner, useLabel, path);
    this.open();
  }
}
