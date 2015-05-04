package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.FigureCaption;

/** the caption of a figure in a XHTML document */
final class _XHTML10FigureCaption extends FigureCaption {
  /**
   * Create a new figure caption
   *
   * @param owner
   *          the owner
   */
  _XHTML10FigureCaption(final _XHTML10Figure owner) {
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
