package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.ComplexText;
import org.optimizationBenchmarking.utils.document.impl.abstr.StyledText;
import org.optimizationBenchmarking.utils.graphics.style.IStyle;
import org.optimizationBenchmarking.utils.graphics.style.color.ColorStyle;
import org.optimizationBenchmarking.utils.graphics.style.font.FontStyle;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** a styled text in a XHTML document */
final class _XHTML10StyledText extends StyledText {
  /** the span color begin */
  private static final char[] SPAN_COLOR_BEGIN = { '<', 's', 'p', 'a',
    'n', ' ', 's', 't', 'y', 'l', 'e', '=', '"', 'c', 'o', 'l', 'o',
    'r', ':', '#' };
  /** black color span begin */
  private static final char[] SPAN_BLACK = { '<', 's', 'p', 'a', 'n', ' ',
    's', 't', 'y', 'l', 'e', '=', '"', 'c', 'o', 'l', 'o', 'r', ':',
    'b', 'l', 'a', 'c', 'k', '"', '>' };
  /** the code begin */
  static final char[] CODE_BEGIN = { '<', 'c', 'o', 'd', 'e', '>' };
  /** the code end */
  static final char[] CODE_END = { '<', '/', 'c', 'o', 'd', 'e', '>' };
  /** the em begin */
  static final char[] EM_BEGIN = { '<', 'e', 'm', '>' };
  /** the em end */
  static final char[] EM_END = { '<', '/', 'e', 'm', '>' };

  /**
   * create the styled text
   *
   * @param owner
   *          the owning element
   * @param style
   *          the style
   */
  _XHTML10StyledText(final ComplexText owner, final IStyle style) {
    super(owner, style);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void appendLineBreak() {
    this.assertNoChildren();
    this.getTextOutput().append(XHTML10Driver.BR);
  }

  /** {inheritDoc} */
  @Override
  protected final void beginColor(final ColorStyle color,
      final ITextOutput out) {
    out.append(_XHTML10StyledText.SPAN_COLOR_BEGIN);
    out.append(Integer.toHexString(color.getRGB() & 0xffffff));
    out.append(XHTML10Driver.ATTRIB_TAG_BEGIN_END);
  }

  /** {inheritDoc} */
  @Override
  protected final void endColor(final ColorStyle color,
      final ITextOutput out) {
    out.append(XHTML10Driver.SPAN_END);
  }

  /** {inheritDoc} */
  @Override
  protected final void beginBlack(final ColorStyle color,
      final ITextOutput out) {
    out.append(_XHTML10StyledText.SPAN_BLACK);
    out.append(XHTML10Driver.ATTRIB_TAG_BEGIN_END);
  }

  /** {inheritDoc} */
  @Override
  protected final void endBlack(final ColorStyle color,
      final ITextOutput out) {
    out.append(XHTML10Driver.SPAN_END);
  }

  /** {inheritDoc} */
  @Override
  protected final void beginFont(final FontStyle font,
      final ITextOutput out) {
    out.append(XHTML10Driver.SPAN_CLASS_BEGIN);
    out.append(font.getID());
    out.append(XHTML10Driver.ATTRIB_TAG_BEGIN_END);
  }

  /** {inheritDoc} */
  @Override
  protected final void endFont(final FontStyle font, final ITextOutput out) {
    out.append(XHTML10Driver.SPAN_END);
  }

  /** {inheritDoc} */
  @Override
  protected final void beginCode(final FontStyle font,
      final ITextOutput out) {
    out.append(_XHTML10StyledText.CODE_BEGIN);
  }

  /** {inheritDoc} */
  @Override
  protected final void endCode(final FontStyle font, final ITextOutput out) {
    out.append(_XHTML10StyledText.CODE_END);
  }

  /** {inheritDoc} */
  @Override
  protected final void beginEmph(final FontStyle font,
      final ITextOutput out) {
    out.append(_XHTML10StyledText.EM_BEGIN);
  }

  /** {inheritDoc} */
  @Override
  protected final void endEmph(final FontStyle font, final ITextOutput out) {
    out.append(_XHTML10StyledText.EM_END);
  }

  /** {inheritDoc} */
  @Override
  protected final void beginNormalText(final FontStyle font,
      final ITextOutput out) {
    this.beginFont(font, out);
  }

  /** {inheritDoc} */
  @Override
  protected final void endNormalText(final FontStyle font,
      final ITextOutput out) {
    this.endFont(font, out);
  }

}
