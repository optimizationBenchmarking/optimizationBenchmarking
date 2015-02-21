package org.optimizationBenchmarking.utils.document.impl.latex;

import java.nio.file.Path;
import java.util.Map;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.document.impl.abstr.Figure;
import org.optimizationBenchmarking.utils.document.spec.EFigureSize;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** a figure in a LaTeX document */
final class _LaTeXFigure extends Figure {

  /** begin column-wide figure */
  private static final char[] FIGURE_COL_WIDE_BEGIN = { '\\', 'b', 'e',
      'g', 'i', 'n', '{', 'f', 'i', 'g', 'u', 'r', 'e', '}', '[', 't',
      'b', ']' };
  /** end column-wide figure */
  private static final char[] FIGURE_COL_WIDE_END = { '\\', 'e', 'n', 'd',
      '{', 'f', 'i', 'g', 'u', 'r', 'e', '}' };
  /** begin a page-wide figure */
  private static final char[] FIGURE_PAGE_WIDE_BEGIN = { '\\', 'b', 'e',
      'g', 'i', 'n', '{', 'f', 'i', 'g', 'u', 'r', 'e', '*', '}', '[',
      't', 'b', ']' };
  /** end a page-wide figure */
  private static final char[] FIGURE_PAGE_WIDE_END = { '\\', 'e', 'n',
      'd', '{', 'f', 'i', 'g', 'u', 'r', 'e', '*', '}' };

  /** are we using the page wide version? */
  private boolean m_pageWide;

  /**
   * Create a new figure
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
  public _LaTeXFigure(final _LaTeXSectionBody owner,
      final ILabel useLabel, final EFigureSize size, final String path,
      final int index) {
    super(owner, useLabel, size, path, index);
    this.open();
  }

  /** {@inheritDoc} */
  @SuppressWarnings("resource")
  @Override
  protected synchronized final void onOpen() {
    final ITextOutput out;
    final _LaTeXDocument doc;

    super.onOpen();

    doc = ((_LaTeXDocument) (this.getDocument()));
    doc._registerFigure();

    out = this.getTextOutput();
    LaTeXDriver._endLine(out);
    LaTeXDriver._endLine(out);

    this.m_pageWide = (this.getSize().spansAllColumns() && //
    (doc.m_class.getColumnCount() > 1));
    out.append(this.m_pageWide ? _LaTeXFigure.FIGURE_PAGE_WIDE_BEGIN
        : _LaTeXFigure.FIGURE_COL_WIDE_BEGIN);
    LaTeXDriver._endLine(out);

    out.append(LaTeXDriver.CENTER_BEGIN);
    LaTeXDriver._endLine(out);

    LaTeXDriver._endLine(out);
  }

  /** {@inheritDoc} */
  @Override
  protected void onFigureClose(final PhysicalDimension size,
      final ArrayListView<Map.Entry<Path, EGraphicFormat>> files) {
    final ITextOutput out;

    out = this.getTextOutput();

    ((_LaTeXDocument) (this.getDocument()))._includeGraphics(size, files,
        out);

    out.append(LaTeXDriver.CENTER_END);
    LaTeXDriver._endLine(out);
    out.append(this.m_pageWide ? _LaTeXFigure.FIGURE_PAGE_WIDE_END
        : _LaTeXFigure.FIGURE_COL_WIDE_END);
    LaTeXDriver._endLine(out);
    LaTeXDriver._endLine(out);

    super.onFigureClose(size, files);
  }

}
