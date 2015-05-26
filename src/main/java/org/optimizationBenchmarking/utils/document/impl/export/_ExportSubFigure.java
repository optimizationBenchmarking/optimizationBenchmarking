package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.SubFigure;
import org.optimizationBenchmarking.utils.document.spec.ILabel;

/** a sub-figure in an export document */
final class _ExportSubFigure extends SubFigure {
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
  public _ExportSubFigure(final _ExportFigureSeries owner,
      final ILabel useLabel, final String path) {
    super(owner, useLabel, path);
    this.open();
  }
}
