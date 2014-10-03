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

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    final ITextOutput out;
    final IStyle style;

    super.onOpen();

    style = this.getStyle();
    out = this.getTextOutput();

    if (style instanceof FontStyle) {
      out.append(XHTML10Driver.SPAN_CLASS_BEGIN);
      out.append(((FontStyle) style).getID());
    } else {
      if (style instanceof ColorStyle) {
        out.append(_XHTML10StyledText.SPAN_COLOR_BEGIN);
        out.append(Integer.toHexString(((ColorStyle) style).getRGB() & 0xffffff));
      } else {
        return;
      }
    }

    out.append(XHTML10Driver.ATTRIB_TAG_BEGIN_END);
    this.styleUsed(style);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    final IStyle style;

    style = this.getStyle();

    if ((style instanceof FontStyle) || (style instanceof ColorStyle)) {
      this.getTextOutput().append(XHTML10Driver.SPAN_END);
    }
    super.onClose();
  }
}
