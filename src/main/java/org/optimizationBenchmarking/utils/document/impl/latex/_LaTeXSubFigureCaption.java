package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.SubFigureCaption;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** the caption of a sub-figure in a LaTeX document */
final class _LaTeXSubFigureCaption extends SubFigureCaption {
  /**
   * Create a new sub-figure caption
   *
   * @param owner
   *          the owner
   */
  _LaTeXSubFigureCaption(final _LaTeXSubFigure owner) {
    super(owner);
    this.open();
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

    if (!(LaTeXDriver._label(this.getOwner().getLabel(), out))) {
      LaTeXDriver._endLine(out);
    }
    out.append('}');
    out.append('{');
    LaTeXDriver._endLine(out);

    super.onClose();
  }
}
