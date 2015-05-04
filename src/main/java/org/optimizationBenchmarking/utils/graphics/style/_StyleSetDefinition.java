package org.optimizationBenchmarking.utils.graphics.style;

import org.optimizationBenchmarking.utils.graphics.style.color.ColorPalette;
import org.optimizationBenchmarking.utils.graphics.style.font.FontPalette;
import org.optimizationBenchmarking.utils.graphics.style.stroke.StrokePalette;

/** an internal definition for style sets */
final class _StyleSetDefinition {

  /** the font palette */
  final FontPalette m_fonts;

  /** the number of available fonts */
  final int m_fontsSize;

  /** the color palette */
  final ColorPalette m_colors;

  /** the number of available colors */
  final int m_colorsSize;

  /** the stroke palette */
  final StrokePalette m_strokes;

  /** the number of available strokes */
  final int m_strokesSize;

  /**
   * Create the style set
   *
   * @param fonts
   *          the font palette
   * @param strokes
   *          the stroke palette
   * @param colors
   *          the color palette
   */
  _StyleSetDefinition(final FontPalette fonts, final ColorPalette colors,
      final StrokePalette strokes) {
    super();

    if (fonts == null) {
      throw new IllegalArgumentException(//
          "The font palette cannot be null."); //$NON-NLS-1$
    }
    if (colors == null) {
      throw new IllegalArgumentException(//
          "The color palette cannot be null."); //$NON-NLS-1$
    }
    if (strokes == null) {
      throw new IllegalArgumentException(//
          "The stroke palette cannot be null."); //$NON-NLS-1$
    }

    this.m_fonts = fonts;
    this.m_colors = colors;
    this.m_strokes = strokes;

    // TODO: make these sizes co-prime to extend possible cycles to the
    // maximum length
    this.m_fontsSize = fonts.size();
    this.m_colorsSize = colors.size();
    this.m_strokesSize = strokes.size();
  }
}
