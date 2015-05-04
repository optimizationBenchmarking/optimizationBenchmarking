package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.FigureSeriesCaption;

/** the caption of a figure series in a XHTML document */
final class _XHTML10FigureSeriesCaption extends FigureSeriesCaption {
  /**
   * Create a new figure series caption
   *
   * @param owner
   *          the owner
   */
  _XHTML10FigureSeriesCaption(final _XHTML10FigureSeries owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void appendLineBreak() {
    this.assertNoChildren();
    this.getTextOutput().append(' ');
  }
}
