package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.FigureCaption;

/** the caption of a figure in an export document */
final class _ExportFigureCaption extends FigureCaption {
  /**
   * Create a new figure caption
   *
   * @param owner
   *          the owner
   */
  _ExportFigureCaption(final _ExportFigure owner) {
    super(owner);
    this.open();
  }
}
