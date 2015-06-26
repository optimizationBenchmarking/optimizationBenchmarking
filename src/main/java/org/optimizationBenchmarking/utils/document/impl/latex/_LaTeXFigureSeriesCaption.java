package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.FigureSeriesCaption;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** the caption of a figure series in a LaTeX document */
final class _LaTeXFigureSeriesCaption extends FigureSeriesCaption {
  /**
   * Create a new figure series caption
   *
   * @param owner
   *          the owner
   */
  _LaTeXFigureSeriesCaption(final _LaTeXFigureSeries owner) {
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
