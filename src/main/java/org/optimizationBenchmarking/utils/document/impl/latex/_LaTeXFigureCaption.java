package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.FigureCaption;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** the caption of a figure in a LaTeX document */
final class _LaTeXFigureCaption extends FigureCaption {
  /**
   * Create a new figure caption
   *
   * @param owner
   *          the owner
   */
  _LaTeXFigureCaption(final _LaTeXFigure owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    super.onOpen();
    this.getTextOutput().append(LaTeXDriver.CAPTION_BEGIN);
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void appendLineBreak() {
    this.assertNoChildren();
    this.getTextOutput().append(' ');
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    final ITextOutput out;

    out = this.getTextOutput();
    LaTeXDriver._label(this.getOwner().getLabel(), out);
    LaTeXDriver._endCommandLine(out);

    super.onClose();
  }
}
