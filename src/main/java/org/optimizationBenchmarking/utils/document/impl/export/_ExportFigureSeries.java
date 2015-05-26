package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.FigureSeries;
import org.optimizationBenchmarking.utils.document.spec.EFigureSize;
import org.optimizationBenchmarking.utils.document.spec.ILabel;

/** a figure series in an export document */
final class _ExportFigureSeries extends FigureSeries {

  /**
   * Create a new figure series
   *
   * @param owner
   *          the owning section body
   * @param index
   *          the figure index in the owning section
   * @param useLabel
   *          the label to use
   * @param size
   *          the figure size
   * @param path
   *          the path suggestion
   */
  public _ExportFigureSeries(final _ExportSectionBody owner,
      final ILabel useLabel, final EFigureSize size, final String path,
      final int index) {
    super(owner, useLabel, size, path, index);
    this.open();
  }

}
