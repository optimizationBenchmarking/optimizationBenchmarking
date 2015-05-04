package org.optimizationBenchmarking.utils.document.impl.latex;

import java.nio.file.Path;
import java.util.Map;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.document.impl.abstr.SubFigure;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** a sub-figure in a LaTeX document */
final class _LaTeXSubFigure extends SubFigure {

  /** begin a figure series row */
  private static final char[] FIGURE_ROW_BEGIN = { '\\', 'f', 'i', 'g',
      'u', 'r', 'e', 'S', 'e', 'r', 'i', 'e', 's', 'R', 'o', 'w', '{', };

  /** begin a figure series cell */
  private static final char[] FIGURE_CELL_BEGIN = { '\\', 'f', 'i', 'g',
      'u', 'r', 'e', 'S', 'e', 'r', 'i', 'e', 's', 'E', 'l', 'e', 'm',
      'e', 'n', 't', '{', };

  /**
   * Create a new sub figure
   *
   * @param owner
   *          the owning section body
   * @param useLabel
   *          the label to use
   * @param path
   *          the path suggestion
   */
  public _LaTeXSubFigure(final _LaTeXFigureSeries owner,
      final ILabel useLabel, final String path) {
    super(owner, useLabel, path);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    final ITextOutput out;
    final int index;

    super.onOpen();

    out = this.getTextOutput();
    LaTeXDriver._endLine(out);

    index = this.getIndex();
    if ((((index - 1) % this.getOwner().getFiguresPerRow()) == 0)) {
      if (index > 1) {
        LaTeXDriver._endCommandLine(out);
      }
      out.append(_LaTeXSubFigure.FIGURE_ROW_BEGIN);
    }

    LaTeXDriver._endLine(out);
    out.append(_LaTeXSubFigure.FIGURE_CELL_BEGIN);
  }

  /** {@inheritDoc} */
  @Override
  protected final void onFigureClose(final PhysicalDimension size,
      final ArrayListView<Map.Entry<Path, EGraphicFormat>> files) {
    final ITextOutput out;

    out = this.getTextOutput();
    ((LaTeXDocument) (this.getDocument()))._includeGraphics(size, files,
        out);
    LaTeXDriver._endCommandLine(out);

    super.onFigureClose(size, files);
  }
}
