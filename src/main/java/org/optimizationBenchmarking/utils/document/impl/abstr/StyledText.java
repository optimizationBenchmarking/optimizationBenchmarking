package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.graphics.style.IStyle;
import org.optimizationBenchmarking.utils.graphics.style.StyleSet;
import org.optimizationBenchmarking.utils.graphics.style.color.ColorStyle;
import org.optimizationBenchmarking.utils.graphics.style.font.FontStyle;
import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.TextUtils;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * A text class which allows creating text with more sophisticated styles
 * in it
 */
public class StyledText extends ComplexText {

  /** the color mode */
  private static final int MODE_COLOR = 0;
  /** the black color mode */
  private static final int MODE_BLACK = (StyledText.MODE_COLOR + 1);
  /** the white color mode */
  private static final int MODE_WHITE = (StyledText.MODE_BLACK + 1);
  /** the font mode */
  private static final int MODE_FONT = (StyledText.MODE_WHITE + 1);
  /** the code mode */
  private static final int MODE_CODE = (StyledText.MODE_FONT + 1);
  /** the emph mode */
  private static final int MODE_EMPH = (StyledText.MODE_CODE + 1);
  /** the normal mode */
  private static final int MODE_NORMAL_FONT = (StyledText.MODE_EMPH + 1);
  /** the odd mode */
  private static final int MODE_ODD = (StyledText.MODE_NORMAL_FONT + 1);

  /** the style */
  private final IStyle m_style;

  /** the mode */
  private final int m_mode;

  /**
   * Create a text.
   * 
   * @param owner
   *          the owning FSM
   * @param style
   *          the style to use
   */
  protected StyledText(final ComplexText owner, final IStyle style) {
    super(owner);

    final StyleSet styles;

    this.m_driver.checkStyleForText(style);
    this.m_style = style;

    styles = this.m_doc.m_styles;

    if (style instanceof FontStyle) {
      if (style.equals(styles.getCodeFont())) {
        this.m_mode = StyledText.MODE_CODE;
      } else {
        if (style.equals(styles.getDefaultFont())) {
          this.m_mode = StyledText.MODE_NORMAL_FONT;
        } else {
          if (style.equals(styles.getEmphFont())) {
            this.m_mode = StyledText.MODE_EMPH;
          } else {
            this.m_mode = StyledText.MODE_FONT;
          }
        }
      }
    } else {
      if (style instanceof ColorStyle) {
        if (style.equals(styles.getBlack())) {
          this.m_mode = StyledText.MODE_BLACK;
        } else {
          if (style.equals(styles.getWhite())) {
            this.m_mode = StyledText.MODE_WHITE;
          } else {
            this.m_mode = StyledText.MODE_COLOR;
          }
        }
      } else {
        this.m_mode = StyledText.MODE_ODD;
      }
    }
  }

  /**
   * Get the style of this text
   * 
   * @return the style of this text
   */
  protected final IStyle getStyle() {
    return this.m_style;
  }

  /**
   * begin a color style
   * 
   * @param color
   *          the color style
   * @param out
   *          the text output
   */
  protected void beginColor(final ColorStyle color, final ITextOutput out) {
    //
  }

  /**
   * end a color style
   * 
   * @param color
   *          the color style
   * @param out
   *          the text output
   */
  protected void endColor(final ColorStyle color, final ITextOutput out) {
    //
  }

  /**
   * begin white
   * 
   * @param color
   *          the white color style
   * @param out
   *          the text output
   */
  protected void beginWhite(final ColorStyle color, final ITextOutput out) {
    StyledText._forbid(color, false);
  }

  /**
   * end white
   * 
   * @param color
   *          the white color style
   * @param out
   *          the text output
   */
  protected void endWhite(final ColorStyle color, final ITextOutput out) {
    StyledText._forbid(color, false);
  }

  /**
   * begin black
   * 
   * @param color
   *          the black color style
   * @param out
   *          the text output
   */
  protected void beginBlack(final ColorStyle color, final ITextOutput out) {
    //
  }

  /**
   * end black
   * 
   * @param color
   *          the black color style
   * @param out
   *          the text output
   */
  protected void endBlack(final ColorStyle color, final ITextOutput out) {
    //
  }

  /**
   * begin a font style
   * 
   * @param font
   *          the font style
   * @param out
   *          the text output
   */
  protected void beginFont(final FontStyle font, final ITextOutput out) {
    //
  }

  /**
   * end a font style
   * 
   * @param font
   *          the font style
   * @param out
   *          the text output
   */
  protected void endFont(final FontStyle font, final ITextOutput out) {
    //
  }

  /**
   * begin a code style
   * 
   * @param font
   *          the code style
   * @param out
   *          the text output
   */
  protected void beginCode(final FontStyle font, final ITextOutput out) {
    //
  }

  /**
   * end a code style
   * 
   * @param font
   *          the code style
   * @param out
   *          the text output
   */
  protected void endCode(final FontStyle font, final ITextOutput out) {
    //
  }

  /**
   * begin a emph style
   * 
   * @param font
   *          the emph style
   * @param out
   *          the text output
   */
  protected void beginEmph(final FontStyle font, final ITextOutput out) {
    //
  }

  /**
   * end a emph style
   * 
   * @param font
   *          the emph style
   * @param out
   *          the text output
   */
  protected void endEmph(final FontStyle font, final ITextOutput out) {
    //
  }

  /**
   * begin a normal text style
   * 
   * @param font
   *          the normal text style
   * @param out
   *          the text output
   */
  protected void beginNormalText(final FontStyle font,
      final ITextOutput out) {
    //
  }

  /**
   * end a normal text style
   * 
   * @param font
   *          the normal text style
   * @param out
   *          the text output
   */
  protected void endNormalText(final FontStyle font, final ITextOutput out) {
    //
  }

  /**
   * begin an odd style
   * 
   * @param odd
   *          the odd style
   * @param out
   *          the text output
   */
  protected void beginOdd(final IStyle odd, final ITextOutput out) {
    StyledText._forbid(odd, false);
  }

  /**
   * end a odd style
   * 
   * @param odd
   *          the odd style
   * @param out
   *          the text output
   */
  protected void endOdd(final IStyle odd, final ITextOutput out) {
    StyledText._forbid(odd, false);
  }

  /**
   * forbid a given style
   * 
   * @param style
   *          the style
   * @param throwIllegal
   *          throw an illegal state exception or an unsupported operation
   *          exception?
   */
  static final void _forbid(final IStyle style, final boolean throwIllegal) {
    MemoryTextOutput o;
    String s;

    o = new MemoryTextOutput();
    o.append("Cannot apply style '"); //$NON-NLS-1$
    o.append(style);
    o.append(' ');
    o.append('(');
    style.appendDescription(ETextCase.IN_SENTENCE, o, false);
    o.append(") to a text since it is a "); //$NON-NLS-1$
    o.append(TextUtils.className(style.getClass()));
    o.append(" but only font and color styles (except white) are allowed."); //$NON-NLS-1$

    s = o.toString();
    o = null;
    if (throwIllegal) {
      throw new IllegalArgumentException(s);
    }
    throw new UnsupportedOperationException(s);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    final ITextOutput out;
    final IStyle style;

    super.onOpen();

    out = this.getTextOutput();

    style = this.m_style;
    this.styleUsed(style);

    switch (this.m_mode) {
      case MODE_COLOR: {
        this.beginColor(((ColorStyle) (style)), out);
        break;
      }
      case MODE_BLACK: {
        this.beginBlack(((ColorStyle) (style)), out);
        break;
      }
      case MODE_WHITE: {
        this.beginWhite(((ColorStyle) (style)), out);
        break;
      }
      case MODE_FONT: {
        this.beginFont(((FontStyle) (style)), out);
        break;
      }
      case MODE_CODE: {
        this.beginCode(((FontStyle) (style)), out);
        break;
      }
      case MODE_EMPH: {
        this.beginEmph(((FontStyle) (style)), out);
        break;
      }
      case MODE_NORMAL_FONT: {
        this.beginNormalText(((FontStyle) (style)), out);
        break;
      }
      default: {
        this.beginOdd(style, out);
        break;
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    final ITextOutput out;

    out = this.getTextOutput();

    switch (this.m_mode) {
      case MODE_COLOR: {
        this.endColor(((ColorStyle) (this.m_style)), out);
        break;
      }
      case MODE_BLACK: {
        this.endBlack(((ColorStyle) (this.m_style)), out);
        break;
      }
      case MODE_WHITE: {
        this.endWhite(((ColorStyle) (this.m_style)), out);
        break;
      }
      case MODE_FONT: {
        this.endFont(((FontStyle) (this.m_style)), out);
        break;
      }
      case MODE_CODE: {
        this.endCode(((FontStyle) (this.m_style)), out);
        break;
      }
      case MODE_EMPH: {
        this.endEmph(((FontStyle) (this.m_style)), out);
        break;
      }
      case MODE_NORMAL_FONT: {
        this.endNormalText(((FontStyle) (this.m_style)), out);
        break;
      }
      default: {
        this.endOdd(this.m_style, out);
        break;
      }
    }
    super.onClose();
  }
}
