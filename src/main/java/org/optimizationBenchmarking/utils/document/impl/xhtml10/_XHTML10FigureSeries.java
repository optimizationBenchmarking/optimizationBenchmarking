package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import java.nio.file.Path;

import org.optimizationBenchmarking.utils.document.impl.abstr.FigureSeries;
import org.optimizationBenchmarking.utils.document.impl.abstr.FigureSeriesCaption;
import org.optimizationBenchmarking.utils.document.spec.EFigureSize;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalText;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/** a figure series in a XHTML document */
final class _XHTML10FigureSeries extends FigureSeries {
  /** the start of the float */
  private static final char[] SUBFIGURE_TABLE_BEGIN = { '<', 't', 'a',
      'b', 'l', 'e', ' ', 'c', 'l', 'a', 's', 's', '=', '"', 's', 'u',
      'b', 'f', 'i', 'g', 'u', 'r', 'e', '"', '>' };

  /** the start of the float tr caption */
  private static final char[] SUBFIGURE_TR_CAPTION_BEGIN = { '<', 't',
      'r', ' ', 'c', 'l', 'a', 's', 's', '=', '"', 's', 'u', 'b', 'f',
      'i', 'g', 'u', 'r', 'e', 'C', 'a', 'p', 't', 'i', 'o', 'n', '"', '>' };
  /** the start of the float tr body */
  private static final char[] SUBFIGURE_TR_BODY_BEGIN = { '<', 't', 'r',
      ' ', 'c', 'l', 'a', 's', 's', '=', '"', 's', 'u', 'b', 'f', 'i',
      'g', 'u', 'r', 'e', 'B', 'o', 'd', 'y', '"', '>' };
  /** the start of the float td */
  private static final char[] SUBFIGURE_TD_CAPTION_SPAN_BEGIN = { '<',
      't', 'd', ' ', 'c', 'l', 'a', 's', 's', '=', '"', 's', 'u', 'b',
      'f', 'i', 'g', 'u', 'r', 'e', 'C', 'a', 'p', 't', 'i', 'o', 'n',
      '"', '>', '<', 's', 'p', 'a', 'n', ' ', 'c', 'l', 'a', 's', 's',
      '=', '"', 'c', 'a', 'p', 't', 'i', 'o', 'n', 'T', 'i', 't', 'l',
      'e', '"', '>', 'F', 'i', 'g', '.', '&', 'n', 'b', 's', 'p', ';' };
  /** the start of the float td */
  private static final char[] SUBFIGURE_TD_CAPTION_EMPTY = { '<', 't',
      'd', ' ', 'c', 'l', 'a', 's', 's', '=', '"', 's', 'u', 'b', 'f',
      'i', 'g', 'u', 'r', 'e', 'C', 'a', 'p', 't', 'i', 'o', 'n', '"',
      '/', '>' };
  /** the start of the float body td */
  private static final char[] SUBFIGURE_TD_BODY_BEGIN = { '<', 't', 'd',
      ' ', 'c', 'l', 'a', 's', 's', '=', '"', 's', 'u', 'b', 'f', 'i',
      'g', 'u', 'r', 'e', 'B', 'o', 'd', 'y', '"', '>' };
  /** the start of the float body td */
  private static final char[] SUBFIGURE_TD_BODY_EMPTY = { '<', 't', 'd',
      ' ', 'c', 'l', 'a', 's', 's', '=', '"', 's', 'u', 'b', 'f', 'i',
      'g', 'u', 'r', 'e', 'B', 'o', 'd', 'y', '"', '/', '>' };

  /** the sub-figures */
  private final _SubFigureDesc[] m_subFigs;

  /** the sub-figure count */
  private int m_count;

  /** the caption */
  private char[] m_caption;

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
  public _XHTML10FigureSeries(final _XHTML10SectionBody owner,
      final ILabel useLabel, final EFigureSize size, final String path,
      final int index) {
    super(owner, useLabel, size, path, index);

    final int nx;

    nx = size.getNX();
    this.m_subFigs = new _SubFigureDesc[nx];
    this.open();
  }

  /**
   * a sub-figure image has been created
   * 
   * @param desc
   *          the sub-figure description
   */
  synchronized final void _subFigure(final _SubFigureDesc desc) {
    final int i;

    i = this.m_count;
    this.m_subFigs[i] = desc;
    if ((this.m_count = (i + 1)) >= this.m_subFigs.length) {
      this.__flushSubFigs();
    }
  }

  /** flush the sub-figures */
  private final void __flushSubFigs() {
    final int c, e;
    final Path df;
    _SubFigureDesc d;
    int i;
    char[] cap;

    final ITextOutput out;

    if ((c = this.m_count) <= 0) {
      return;
    }
    this.m_count = 0;

    out = this.getTextOutput();
    out.append(_XHTML10FigureSeries.SUBFIGURE_TR_BODY_BEGIN);

    df = this.getDocument().getDocumentFolder();
    for (i = 0; i < c; i++) {
      d = this.m_subFigs[i];
      out.append(_XHTML10FigureSeries.SUBFIGURE_TD_BODY_BEGIN);
      XHTML10Driver._label(d.m_label, out);
      _XHTML10Figure._img(out, d.m_path, df, d.m_size, d.m_caption);
      out.append(_XHTML10Table.TD_END);
    }

    e = this.getSize().getNX();
    for (; i < e; i++) {
      out.append(_XHTML10FigureSeries.SUBFIGURE_TD_BODY_EMPTY);
    }
    out.append(_XHTML10Table.TR_END);

    out.append(_XHTML10FigureSeries.SUBFIGURE_TR_CAPTION_BEGIN);
    for (i = 0; i < c; i++) {
      d = this.m_subFigs[i];
      cap = d.m_caption;
      if (((d.m_id != null) && (d.m_id.length() > 0))
          && ((cap != null) && (cap.length > 0))) {
        out.append(_XHTML10FigureSeries.SUBFIGURE_TD_CAPTION_SPAN_BEGIN);
        out.append(d.m_id);
        out.append(XHTML10Driver.SPAN_END_NBSP);
        out.append(cap);
        out.append(_XHTML10Table.TD_END);
      } else {
        out.append(_XHTML10FigureSeries.SUBFIGURE_TD_CAPTION_EMPTY);
      }
      this.m_subFigs[i] = null;
    }

    for (; i < e; i++) {
      out.append(_XHTML10FigureSeries.SUBFIGURE_TD_CAPTION_EMPTY);
    }
    out.append(_XHTML10Table.TR_END);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    final ITextOutput out;

    super.onOpen();

    out = this.getTextOutput();
    out.append(_XHTML10Figure.FIGURE_DIV_BEGIN);
    out.append(_XHTML10Figure.FIGURE_TABLE_BEGIN);
    out.append(_XHTML10Figure.FIGURE_TR_BODY_BEGIN);
    out.append(_XHTML10Figure.FIGURE_TD_BODY_BEGIN);
    out.append(_XHTML10FigureSeries.SUBFIGURE_TABLE_BEGIN);
  }

  /** {@inheritDoc} */
  @Override
  protected final boolean mustChildBeBuffered(final HierarchicalText child) {
    return (child instanceof FigureSeriesCaption);
  }

  /** {@inheritDoc} */
  @Override
  protected final void processBufferedOutputFromChild(
      final HierarchicalText child, final MemoryTextOutput out) {
    if (child instanceof FigureSeriesCaption) {
      this.m_caption = out.toChars();
    } else {
      super.processBufferedOutputFromChild(child, out);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    final ITextOutput out;

    this.__flushSubFigs();
    out = this.getTextOutput();
    out.append(_XHTML10Table.TABLE_END);
    out.append(_XHTML10Table.TD_END);
    out.append(_XHTML10Table.TR_END);
    out.append(_XHTML10Figure.FIGURE_TR_CAPTION_BEGIN);
    out.append(_XHTML10Figure.FIGURE_TD_CAPTION_SPAN_BEGIN);

    out.append(this.getGlobalID());

    out.append(XHTML10Driver.SPAN_END_NBSP);

    XHTML10Driver._label(this.getLabel(), out);

    out.append(this.m_caption);
    this.m_caption = null;

    out.append(_XHTML10Table.TD_END);
    out.append(_XHTML10Table.TR_END);

    out.append(_XHTML10Table.TABLE_END);
    out.append(XHTML10Driver.DIV_END);
    super.onClose();
  }
}
