package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import java.io.File;
import java.nio.file.Path;
import java.util.Map;

import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.document.impl.abstr.Figure;
import org.optimizationBenchmarking.utils.document.impl.abstr.FigureCaption;
import org.optimizationBenchmarking.utils.document.spec.EFigureSize;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.graphics.PhysicalDimension;
import org.optimizationBenchmarking.utils.graphics.graphic.EGraphicFormat;
import org.optimizationBenchmarking.utils.hierarchy.HierarchicalText;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/** a figure in a XHTML document */
final class _XHTML10Figure extends Figure {

  /** the start of the float */
  static final char[] FIGURE_DIV_BEGIN = { '<', 'd', 'i', 'v', ' ', 'c',
    'l', 'a', 's', 's', '=', '"', 'f', 'i', 'g', 'u', 'r', 'e', '"',
    '>', };

  /** the start of the float */
  static final char[] FIGURE_TABLE_BEGIN = { '<', 't', 'a', 'b', 'l', 'e',
    ' ', 'c', 'l', 'a', 's', 's', '=', '"', 'f', 'i', 'g', 'u', 'r',
    'e', '"', '>' };

  /** the start of the float tr caption */
  static final char[] FIGURE_TR_CAPTION_BEGIN = { '<', 't', 'r', ' ', 'c',
    'l', 'a', 's', 's', '=', '"', 'f', 'i', 'g', 'u', 'r', 'e', 'C',
    'a', 'p', 't', 'i', 'o', 'n', '"', '>' };
  /** the start of the float tr body */
  static final char[] FIGURE_TR_BODY_BEGIN = { '<', 't', 'r', ' ', 'c',
    'l', 'a', 's', 's', '=', '"', 'f', 'i', 'g', 'u', 'r', 'e', 'B',
    'o', 'd', 'y', '"', '>' };
  /** the start of the float td */
  static final char[] FIGURE_TD_CAPTION_SPAN_BEGIN = { '<', 't', 'd', ' ',
    'c', 'l', 'a', 's', 's', '=', '"', 'f', 'i', 'g', 'u', 'r', 'e',
    'C', 'a', 'p', 't', 'i', 'o', 'n', '"', '>', '<', 's', 'p', 'a',
    'n', ' ', 'c', 'l', 'a', 's', 's', '=', '"', 'c', 'a', 'p', 't',
    'i', 'o', 'n', 'T', 'i', 't', 'l', 'e', '"', '>', 'F', 'i', 'g',
    '.', '&', 'n', 'b', 's', 'p', ';' };
  /** the start of the float body td */
  static final char[] FIGURE_TD_BODY_BEGIN = { '<', 't', 'd', ' ', 'c',
    'l', 'a', 's', 's', '=', '"', 'f', 'i', 'g', 'u', 'r', 'e', 'B',
    'o', 'd', 'y', '"', '>' };

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

  /**
   * Create the {@code img}-tag
   *
   * @param out
   *          the output
   * @param path
   *          the path
   * @param docFolder
   *          the document base folder
   * @param size
   *          the image size
   * @param caption
   *          the image caption
   */
  static final void _img(final ITextOutput out, final Path path,
      final Path docFolder, final PhysicalDimension size,
      final char[] caption) {
    String s;
    int i, j;

    out.append(_XHTML10Figure.FIGURE_IMG_URI);

    s = docFolder.relativize(path).toString();
    if (File.separatorChar == '\\') {// just in case...
      s = s.replace('\\', '/');
    }
    out.append(s);

    out.append(_XHTML10Figure.FIGURE_IMG_WIDTH);
    out.append(size.getWidth());
    out.append(_XHTML10Figure.FIGURE_IMG_HEIGHT);
    out.append(size.getHeight());
    if ((caption != null) && (caption.length > 0)) {
      out.append(_XHTML10Figure.FIGURE_IMG_ALT);

      j = 0;
      for (i = j; i < caption.length; i++) {
        if (caption[i] == '<') {
          out.append(caption, j, i);
        }
        inner: for (j = i; (++j) < caption.length;) {
          if (caption[j] == '>') {
            j++;
            break inner;
          }
        }
        i = j;
      }
      if (j < caption.length) {
        out.append(caption, j, caption.length);
      }
    } else {
      out.append(_XHTML10Figure.FIGURE_IMG_ALT, 0, 3);
    }
    out.append(XHTML10Driver.EMPTY_ATTRIB_TAG_END);
  }

  /** {@inheritDoc} */
  @Override
  protected final void onFigureClose(final PhysicalDimension size,
      final ArrayListView<Map.Entry<Path, EGraphicFormat>> files) {
    final ITextOutput out;

    out = this.getTextOutput();

    out.append(_XHTML10Figure.FIGURE_TR_BODY_BEGIN);
    out.append(_XHTML10Figure.FIGURE_TD_BODY_BEGIN);

    if (files.isEmpty()) {
      throw new IllegalStateException("No figure file."); //$NON-NLS-1$
    }

    _XHTML10Figure._img(out, files.get(0).getKey(), this.getDocument()
        .getDocumentFolder(), size, this.m_caption);

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
