package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.SubFigureCaption;

/** the caption of a sub-figure in a XHTML document */
final class _XHTML10SubFigureCaption extends SubFigureCaption {
  /**
   * Create a new sub-figure caption
   *
   * @param owner
   *          the owner
   */
  _XHTML10SubFigureCaption(final _XHTML10SubFigure owner) {
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
