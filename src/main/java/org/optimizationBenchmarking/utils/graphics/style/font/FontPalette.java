package org.optimizationBenchmarking.utils.graphics.style.font;

import java.awt.Graphics2D;

import org.optimizationBenchmarking.utils.graphics.style.EFontFamily;
import org.optimizationBenchmarking.utils.graphics.style.Palette;

/**
 * A palette of font styles.
 */
public class FontPalette extends Palette<FontStyle> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the default font */
  private final FontStyle m_default;
  /** the emph font */
  private final FontStyle m_emph;
  /** the code font */
  private final FontStyle m_code;

  /**
   * create the palette
   * 
   * @param def
   *          the default font style
   * @param emph
   *          the emphasize font
   * @param code
   *          the code font style
   * @param data
   *          the palette data
   */
  FontPalette(final FontStyle def, final FontStyle emph,
      final FontStyle code, final FontStyle[] data) {
    super(data);

    FontPalette._checkDefaultFont(def);
    FontPalette._checkEmphFont(emph);
    FontPalette._checkCodeFont(code);

    this.m_default = def;
    def.m_palette = this;

    this.m_emph = emph;
    emph.m_palette = this;

    this.m_code = code;
    code.m_palette = this;

    for (final FontStyle f : data) {
      f.m_palette = this;
    }
  }

  /**
   * Check the default font style
   * 
   * @param s
   *          the default font style
   */
  static final void _checkDefaultFont(final FontStyle s) {
    if (s == null) {
      throw new IllegalArgumentException(//
          "The default font style cannot be null."); //$NON-NLS-1$
    }
  }

  /**
   * Check the code font style
   * 
   * @param s
   *          the code font style
   */
  static final void _checkCodeFont(final FontStyle s) {
    final EFontFamily f;

    if (s == null) {
      throw new IllegalArgumentException(//
          "The code font style cannot be null."); //$NON-NLS-1$
    }

    if ((f = s.getFamily()) != EFontFamily.MONOSPACED) {
      throw new IllegalArgumentException(//
          "The code font family must be " + //$NON-NLS-1$
              EFontFamily.MONOSPACED + " but is " + f);//$NON-NLS-1$

    }
  }

  /**
   * Check the emphasize font style
   * 
   * @param s
   *          the code emphasize style
   */
  static final void _checkEmphFont(final FontStyle s) {

    if (s == null) {
      throw new IllegalArgumentException(//
          "The emphasize font style cannot be null."); //$NON-NLS-1$
    }

    if (s.getFamily() == EFontFamily.MONOSPACED) {
      throw new IllegalArgumentException(//
          "The emphasize font family must not be " + //$NON-NLS-1$
              EFontFamily.MONOSPACED + " but is.");//$NON-NLS-1$

    }
  }

  /**
   * Get the default font style
   * 
   * @return the default font style
   */
  public final FontStyle getDefaultFont() {
    return this.m_default;
  }

  /**
   * Get the emphasize font style
   * 
   * @return the emphasize font style
   */
  public final FontStyle getEmphFont() {
    return this.m_emph;
  }

  /**
   * Get the code font style
   * 
   * @return the code font style
   */
  public final FontStyle getCodeFont() {
    return this.m_code;
  }

  /** {@inheritDoc} */
  @Override
  public final void initialize(final Graphics2D graphics) {
    graphics.setFont(this.m_default.getFont());
  }
  //
  // /**
  // * Find a font style which combines the features of the given styles.
  // *
  // * @param styles
  // * a collection of styles
  // * @return the joined font, or {@code null} it no such font is found in
  // * this palette
  // */
  // public final FontStyle join(final Iterable<FontStyle> styles) {
  // Iterator<FontStyle> it;
  // EFontFamily family, newFamily, defaultFamily;
  // boolean italic, bold, underlined;
  // int size, newSize, defaultSize;
  // FontStyle style;
  //
  // style = this.getDefaultFontStyle();
  // defaultFamily = style.getFamily();
  // defaultSize = style.getSize();
  //
  // it = styles.iterator();
  // italic = bold = underlined = false;
  // if (it.hasNext()) {
  // style = it.next();
  // family = style.getFamily();
  //
  // italic = style.isItalic();
  // bold = style.isBold();
  // underlined = style.isUnderlined();
  //
  // size = style.getSize();
  // } else {
  // return null;
  // }
  //
  // while (it.hasNext()) {
  // style = it.next();
  //
  // italic |= style.isItalic();
  // bold |= style.isBold();
  // underlined |= style.isUnderlined();
  //
  // checkSize: {
  // newSize = style.getSize();
  // if (newSize != size) {
  // if (newSize == defaultSize) {
  // break checkSize;
  // }
  // if (size == defaultSize) {
  // size = newSize;
  // break checkSize;
  // }
  // return null;
  // }
  // }
  //
  // checkFont: {
  // newFamily = style.getFamily();
  // if (newFamily != family) {
  // if (newFamily == defaultFamily) {
  // break checkFont;
  // }
  // if (family == defaultFamily) {
  // family = newFamily;
  // break checkFont;
  // }
  // return null;
  // }
  // }
  //
  // }
  //
  // for (FontStyle font : this) {
  // if (font.getFamily() != family) {
  // continue;
  // }
  // if (font.getSize() != size) {
  // continue;
  // }
  // if (font.isBold() != bold) {
  // continue;
  // }
  // if (font.isItalic() != italic) {
  // continue;
  // }
  // if (font.isUnderlined() != underlined) {
  // continue;
  // }
  // return font;
  // }
  //
  // return null;
  // }
}
