package org.optimizationBenchmarking.utils.graphics;

import java.awt.Font;

/**
 * An enumeration of font families.
 */
public enum EFontFamily {

  /** the sans-serif family of fonts */
  SANS_SERIF(Font.SANS_SERIF),

  /** the serif family of fonts */
  SERIF(Font.SERIF),

  /** the monospaced family of fonts */
  MONOSPACED(Font.MONOSPACED),

  /** the dialog family of fonts */
  DIALOG(Font.DIALOG),

  /** the dialog input family of fonts */
  DIALOG_INPUT(Font.DIALOG_INPUT);

  /** the font family used in the class {@link java.awt.Font} */
  private final String m_fontFam;

  /**
   * Create
   *
   * @param fam
   *          the font family name
   */
  private EFontFamily(final String fam) {
    this.m_fontFam = fam;
  }

  /**
   * Get the name of the font family, as used in {@link java.awt.Font}
   *
   * @return the name of the font family, as used in {@link java.awt.Font}
   */
  public final String getFontFamilyName() {
    return this.m_fontFam;
  }
}
