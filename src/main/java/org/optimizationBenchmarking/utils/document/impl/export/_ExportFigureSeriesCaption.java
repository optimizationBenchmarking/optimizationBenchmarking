package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.FigureSeriesCaption;

/** the caption of a figure series in an export document */
final class _ExportFigureSeriesCaption extends FigureSeriesCaption {
  /**
   * Create a new figure series caption
   *
   * @param owner
   *          the owner
   */
  _ExportFigureSeriesCaption(final _ExportFigureSeries owner) {
    super(owner);
    this.open();
  }
}
