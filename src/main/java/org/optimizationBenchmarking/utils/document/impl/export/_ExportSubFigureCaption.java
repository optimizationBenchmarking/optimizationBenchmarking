package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.SubFigureCaption;

/** the caption of a sub-figure in an export document */
final class _ExportSubFigureCaption extends SubFigureCaption {
  /**
   * Create a new sub-figure caption
   *
   * @param owner
   *          the owner
   */
  _ExportSubFigureCaption(final _ExportSubFigure owner) {
    super(owner);
    this.open();
  }
}
