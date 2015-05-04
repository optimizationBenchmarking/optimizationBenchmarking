package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.FigureSeries;
import org.optimizationBenchmarking.utils.document.spec.EFigureSize;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** a figure series in a LaTeX document */
final class _LaTeXFigureSeries extends FigureSeries {

  /** begin figure series */
  private static final char[] FIGURE_SERIES_BEGIN = { '\\', 'f', 'i', 'g',
      'u', 'r', 'e', 'S', 'e', 'r', 'i', 'e', 's', 'F', 'l', 'o', 'a',
      't', '{' };

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
  public _LaTeXFigureSeries(final _LaTeXSectionBody owner,
      final ILabel useLabel, final EFigureSize size, final String path,
      final int index) {
    super(owner, useLabel, size, path, index);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    final ITextOutput out;

    super.onOpen();

    ((LaTeXDocument) (this.getDocument()))._registerFigureSeries();

    out = this.getTextOutput();
    LaTeXDriver._endLine(out);
    LaTeXDriver._endLine(out);
    out.append(_LaTeXFigureSeries.FIGURE_SERIES_BEGIN);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    final ITextOutput out;

    out = this.getTextOutput();
    LaTeXDriver._endCommandLine(out);
    LaTeXDriver._endCommandLine(out);
    LaTeXDriver._endLine(out);

    super.onClose();
  }
}
