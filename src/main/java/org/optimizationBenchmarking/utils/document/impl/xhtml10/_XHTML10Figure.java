package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import java.io.File;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.document.impl.abstr.Figure;
import org.optimizationBenchmarking.utils.document.impl.abstr.FigureCaption;
import org.optimizationBenchmarking.utils.document.impl.object.PathEntry;
import org.optimizationBenchmarking.utils.document.spec.EFigureSize;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalText;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/** a figure in a XHTML document */
final class _XHTML10Figure extends Figure {

  /** the start of the float */
  private static final char[] FIGURE_DIV_BEGIN = { '<', 'd', 'i', 'v',
      ' ', 'c', 'l', 'a', 's', 's', '=', '"', 'f', 'i', 'g', 'u', 'r',
      'e', '"', '>', };

  /** the start of the float */
  private static final char[] FIGURE_TABLE_BEGIN = { '<', 't', 'a', 'b',
      'l', 'e', ' ', 'c', 'l', 'a', 's', 's', '=', '"', 'f', 'i', 'g',
      'u', 'r', 'e', '"', '>' };

  /** the start of the float tr caption */
  private static final char[] FIGURE_TR_CAPTION_BEGIN = { '<', 't', 'r',
      ' ', 'c', 'l', 'a', 's', 's', '=', '"', 'f', 'i', 'g', 'u', 'r',
      'e', 'C', 'a', 'p', 't', 'i', 'o', 'n', '"', '>' };
  /** the start of the float tr body */
  private static final char[] FIGURE_TR_BODY_BEGIN = { '<', 't', 'r', ' ',
      'c', 'l', 'a', 's', 's', '=', '"', 'f', 'i', 'g', 'u', 'r', 'e',
      'B', 'o', 'd', 'y', '"', '>' };
  /** the start of the float td */
  private static final char[] FIGURE_TD_CAPTION_SPAN_BEGIN = { '<', 't',
      'd', ' ', 'c', 'l', 'a', 's', 's', '=', '"', 'f', 'i', 'g', 'u',
      'r', 'e', 'C', 'a', 'p', 't', 'i', 'o', 'n', '"', '>', '<', 's',
      'p', 'a', 'n', ' ', 'c', 'l', 'a', 's', 's', '=', '"', 'c', 'a',
      'p', 't', 'i', 'o', 'n', 'T', 'i', 't', 'l', 'e', '"', '>', 'F',
      'i', 'g', '.', '&', 'n', 'b', 's', 'p', ';' };
  /** the start of the float body td */
  private static final char[] FIGURE_TD_BODY_BEGIN = { '<', 't', 'd', ' ',
      'c', 'l', 'a', 's', 's', '=', '"', 'f', 'i', 'g', 'u', 'r', 'e',
      'B', 'o', 'd', 'y', '"', '>' };

  /** the start figure image: uri follows */
  private static final char[] FIGURE_IMG_URI = { '<', 'i', 'm', 'g', ' ',
      'c', 'l', 'a', 's', 's', '=', '"', 'f', 'i', 'g', 'u', 'r', 'e',
      '"', ' ', 's', 'r', 'c', '=', '"' };
  /** the start figure image width: width follows */
  private static final char[] FIGURE_IMG_WIDTH = { '"', ' ', 's', 't',
      'y', 'l', 'e', '=', '"', 'w', 'i', 'd', 't', 'h', ':', };
  /** the start figure image height: height will follow */
  private static final char[] FIGURE_IMG_HEIGHT = { 'p', 't', ';', 'h',
      'e', 'i', 'g', 'h', 't', ':', };
  /** the start figure image: alt will follow */
  private static final char[] FIGURE_IMG_ALT = { 'p', 't', '"', ' ', 'a',
      'l', 't', '=', '"' };

  /** the caption */
  private char[] m_caption;

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
  public _XHTML10Figure(final _XHTML10SectionBody owner,
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

    out = this.getTextOutput();
    out.append(_XHTML10Figure.FIGURE_DIV_BEGIN);
    out.append(_XHTML10Figure.FIGURE_TABLE_BEGIN);
  }

  /** {@inheritDoc} */
  @Override
  protected final boolean mustChildBeBuffered(final HierarchicalText child) {
    return (child instanceof FigureCaption);
  }

  /** {@inheritDoc} */
  @Override
  protected final void processBufferedOutputFromChild(
      final HierarchicalText child, final MemoryTextOutput out) {
    if (child instanceof FigureCaption) {
      this.m_caption = out.toChars();
    } else {
      super.processBufferedOutputFromChild(child, out);
    }
  }

  /** {@inheritDoc} */
  @Override
  protected void onFigureClose(final PhysicalDimension size,
      final ArrayListView<PathEntry> files) {
    final ITextOutput out;
    String s;

    out = this.getTextOutput();

    out.append(_XHTML10Figure.FIGURE_TR_BODY_BEGIN);
    out.append(_XHTML10Figure.FIGURE_TD_BODY_BEGIN);

    out.append(_XHTML10Figure.FIGURE_IMG_URI);
    if (files.isEmpty()) {
      throw new IllegalStateException("No figure file."); //$NON-NLS-1$
    }

    s = this.getDocument().getDocumentFolder()
        .relativize(files.get(0).getValue()).toString();
    if (File.separatorChar == '\\') {// just in case...
      s = s.replace('\\', '/');
    }
    out.append(s);

    out.append(_XHTML10Figure.FIGURE_IMG_WIDTH);
    out.append(size.getWidth());
    out.append(_XHTML10Figure.FIGURE_IMG_HEIGHT);
    out.append(size.getHeight());
    out.append(_XHTML10Figure.FIGURE_IMG_ALT);
    out.append(this.m_caption);
    out.append(XHTML10Driver.EMPTY_ATTRIB_TAG_END);

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

    super.onFigureClose(size, files);
  }
}
