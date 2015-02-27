package org.optimizationBenchmarking.utils.graphics.style;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.graphics.EFontFamily;
import org.optimizationBenchmarking.utils.graphics.style.color.ColorPalette;
import org.optimizationBenchmarking.utils.graphics.style.color.ColorStyle;
import org.optimizationBenchmarking.utils.graphics.style.font.FontPalette;
import org.optimizationBenchmarking.utils.graphics.style.font.FontStyle;
import org.optimizationBenchmarking.utils.graphics.style.stroke.StrokePalette;
import org.optimizationBenchmarking.utils.graphics.style.stroke.StrokeStyle;

/**
 * A set of styles.
 */
public final class StyleSet {

  /** the font palette */
  private final FontPalette m_fonts;

  /** the used fonts */
  private int m_usedFonts;

  /** the color palette */
  private final ColorPalette m_colors;

  /** the used colors */
  private int m_usedColors;

  /** the stroke palette */
  private final StrokePalette m_strokes;

  /** the used strokes */
  private int m_usedStrokes;

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
  public StyleSet(final FontPalette fonts, final ColorPalette colors,
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
  }

  /**
   * Create a style set from an owning style set
   * 
   * @param owner
   *          the owning style set
   */
  public StyleSet(final StyleSet owner) {
    this(owner.m_fonts, owner.m_colors, owner.m_strokes);
    this.m_usedColors = owner.m_usedColors;
    this.m_usedFonts = owner.m_usedFonts;
    this.m_usedStrokes = owner.m_usedStrokes;
  }

  /**
   * check the styles
   * 
   * @param n
   *          the number of elements
   */
  private static void __checkN(final int n) {
    if (n < 0) {
      throw new IllegalArgumentException(//
          "Cannot allocate a negative number of styles, such as " + n); //$NON-NLS-1$
    }
  }

  /**
   * allocate a set of fonts
   * 
   * @param n
   *          the number of fonts to allocate
   * @return the list of fonts, or {@code null} if less than {@code n}
   *         fonts can be allocated
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public synchronized final List<FontStyle> allocateFonts(final int n) {
    int a, b;

    StyleSet.__checkN(n);
    if (n <= 0) {
      return ((List) (ArraySetView.EMPTY_SET_VIEW));
    }
    a = this.m_usedFonts;
    b = (a + n);
    if (b <= this.m_fonts.size()) {
      this.m_usedFonts = b;
      return this.m_fonts.subList(a, b);
    }
    return null;
  }

  /**
   * allocate a set of colors
   * 
   * @param n
   *          the number of colors to allocate
   * @return the list of colors, or {@code null} if less than {@code n}
   *         colors can be allocated
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public synchronized final List<ColorStyle> allocateColors(final int n) {
    int a, b;

    StyleSet.__checkN(n);
    if (n <= 0) {
      return ((List) (ArraySetView.EMPTY_SET_VIEW));
    }
    a = this.m_usedColors;
    b = (a + n);
    if (b <= this.m_colors.size()) {
      this.m_usedColors = b;
      return this.m_colors.subList(a, b);
    }
    return null;
  }

  /**
   * allocate a set of strokes
   * 
   * @param n
   *          the number of strokes to allocate
   * @return the list of strokes, or {@code null} if less than {@code n}
   *         strokes can be allocated
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public synchronized final List<StrokeStyle> allocateStrokes(final int n) {
    int a, b;

    StyleSet.__checkN(n);
    if (n <= 0) {
      return ((List) (ArraySetView.EMPTY_SET_VIEW));
    }
    a = this.m_usedStrokes;
    b = (a + n);
    if (b <= this.m_strokes.size()) {
      this.m_usedStrokes = b;
      return this.m_strokes.subList(a, b);
    }
    return null;
  }

  /**
   * Get the default stroke
   * 
   * @return the default stroke
   */
  public final StrokeStyle getDefaultStroke() {
    return this.m_strokes.getDefaultStroke();
  }

  /**
   * Get the thin stroke
   * 
   * @return the thin stroke
   */
  public final StrokeStyle getThinStroke() {
    return this.m_strokes.getThinStroke();
  }

  /**
   * Get the thick stroke
   * 
   * @return the thick stroke
   */
  public final StrokeStyle getThickStroke() {
    return this.m_strokes.getThickStroke();
  }

  /**
   * Get the default font style
   * 
   * @return the default font style
   */
  public final FontStyle getDefaultFont() {
    return this.m_fonts.getDefaultFont();
  }

  /**
   * Get the emphasize font style
   * 
   * @return the emphasize font style
   */
  public final FontStyle getEmphFont() {
    return this.m_fonts.getEmphFont();
  }

  /**
   * Get the code font style
   * 
   * @return the code font style
   */
  public final FontStyle getCodeFont() {
    return this.m_fonts.getCodeFont();
  }

  /**
   * Get the black color
   * 
   * @return the black color
   */
  public final ColorStyle getBlack() {
    return this.m_colors.getBlack();
  }

  /**
   * Get the white color
   * 
   * @return the white color
   */
  public final ColorStyle getWhite() {
    return this.m_colors.getWhite();
  }

  /**
   * Initialize a graphics context with the default font, color, and stroke
   * settings
   * 
   * @param graphics
   *          the graphics context
   */
  public final void initialize(final Graphics2D graphics) {
    this.m_fonts.initialize(graphics);
    this.m_colors.initialize(graphics);
    this.m_strokes.initialize(graphics);
  }

  /**
   * Get the color most similar to a given RGB value
   * 
   * @param rgb
   *          the rgb value of the color
   * @return the color most similar to the RGB value
   */
  public final ColorStyle getMostSimilarColor(final int rgb) {
    return this.m_colors.getMostSimilarColor(rgb);
  }

  /**
   * Get the color most similar to another color
   * 
   * @param color
   *          the other color
   * @return the color most similar to a given color
   */
  public final ColorStyle getMostSimilarColor(final Color color) {
    return this.m_colors.getMostSimilarColor(color);
  }

  /**
   * Find the font style most similar to a given setup
   * 
   * @param family
   *          the font family
   * @param bold
   *          is the font bold?
   * @param italic
   *          is the font italic?
   * @param underlined
   *          is the font underlined?
   * @param size
   *          the size of the font
   * @return the font style
   */
  public final FontStyle getMostSimilarFont(final EFontFamily family,
      final boolean bold, final boolean italic, final boolean underlined,
      final float size) {
    return this.m_fonts.getMostSimilarFont(family, bold, italic,
        underlined, size);
  }
}
