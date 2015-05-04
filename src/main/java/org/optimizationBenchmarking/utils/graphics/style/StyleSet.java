package org.optimizationBenchmarking.utils.graphics.style;

import java.awt.Color;
import java.awt.Graphics2D;

import org.optimizationBenchmarking.utils.collections.maps.StringMap;
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

  /** the owning style set */
  private final StyleSet m_owner;

  /** the style set definition */
  private final _StyleSetDefinition m_def;

  /** the used fonts */
  private int m_usedFonts;
  /** the used colors */
  private int m_usedColors;
  /** the used strokes */
  private int m_usedStrokes;
  /** the named colors */
  private StringMap<ColorStyle> m_namedColors;
  /** the named strokes */
  private StringMap<StrokeStyle> m_namedStrokes;
  /** the named fonts */
  private StringMap<FontStyle> m_namedFonts;

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
    this(fonts, colors, strokes, null);
  }

  /**
   * Create the style set
   *
   * @param fonts
   *          the font palette
   * @param strokes
   *          the stroke palette
   * @param colors
   *          the color palette
   * @param owner
   *          the owning style set
   */
  private StyleSet(final FontPalette fonts, final ColorPalette colors,
      final StrokePalette strokes, final StyleSet owner) {
    super();

    if (owner == null) {
      this.m_def = new _StyleSetDefinition(fonts, colors, strokes);
    } else {
      this.m_def = owner.m_def;
    }

    this.m_owner = owner;
  }

  /**
   * Create a style set from an owning style set
   *
   * @param owner
   *          the owning style set
   */
  public StyleSet(final StyleSet owner) {
    this(null, null, null, owner);
    this.m_usedColors = owner.m_usedColors;
    this.m_usedFonts = owner.m_usedFonts;
    this.m_usedStrokes = owner.m_usedStrokes;
  }

  /**
   * Allocate a fonts. Best effort is made to return fonts different from
   * those allocated before.
   *
   * @return the font
   */
  public synchronized final FontStyle allocateFont() {
    final int index;
    index = this.m_usedFonts;
    this.m_usedFonts = ((index + 1) % this.m_def.m_fontsSize);
    return this.m_def.m_fonts.get(index);
  }

  /**
   * Allocate a colors. Best effort is made to return colors different from
   * those allocated before.
   *
   * @return the color
   */
  public synchronized final ColorStyle allocateColor() {
    final int index;

    index = this.m_usedColors;
    this.m_usedColors = ((index + 1) % this.m_def.m_colorsSize);
    return this.m_def.m_colors.get(index);
  }

  /**
   * Allocate a strokes. Best effort is made to return strokes different
   * from those allocated before.
   *
   * @return the stroke
   */
  public synchronized final StrokeStyle allocateStroke() {
    final int index;
    index = this.m_usedStrokes;
    this.m_usedStrokes = ((index + 1) % this.m_def.m_strokesSize);
    return this.m_def.m_strokes.get(index);
  }

  /**
   * Obtain the color of a given name. If {@code allocateIfUndefined} is
   * {@code true} and no color is defined for the given name yet, then one
   * will be allocated. If {@code allocateIfUndefined} is {@code false} and
   * no color is defined for the given name yet, {@code null} will be
   * returned.
   *
   * @param name
   *          the name
   * @param allocateIfUndefined
   *          should the color be allocated if it is still undefined?
   * @return the color for the given name, or {@code null} if none was
   *         allocated for the name and {@code allocateIfUndefined} is
   *         {@code false}
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public synchronized final ColorStyle getColor(final String name,
      final boolean allocateIfUndefined) {
    ColorStyle color;
    StyleSet owner;

    if (this.m_namedColors == null) {
      findMapToCopy: {
      for (owner = this.m_owner; (owner != null); owner = owner.m_owner) {
        synchronized (owner) {
          if (owner.m_namedColors != null) {
            if (allocateIfUndefined) {
              this.m_namedColors = ((StringMap) (//
                  owner.m_namedColors.clone()));
              break findMapToCopy;
            }

            return owner.m_namedColors.get(name);
          }
        }
      }
      if (!allocateIfUndefined) {
        return null;
      }
      this.m_namedColors = new StringMap<>();
    }
    }

    color = this.m_namedColors.get(name);
    if (color != null) {
      return color;
    }

    if (allocateIfUndefined) {
      color = this.allocateColor();
      this.m_namedColors.put(name, color);
      return color;
    }

    return null;
  }

  /**
   * Obtain the font of a given name. If {@code allocateIfUndefined} is
   * {@code true} and no font is defined for the given name yet, then one
   * will be allocated. If {@code allocateIfUndefined} is {@code false} and
   * no font is defined for the given name yet, {@code null} will be
   * returned.
   *
   * @param name
   *          the name
   * @param allocateIfUndefined
   *          should the font be allocated if it is still undefined?
   * @return the font for the given name, or {@code null} if none was
   *         allocated for the name and {@code allocateIfUndefined} is
   *         {@code false}
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public synchronized final FontStyle getFont(final String name,
      final boolean allocateIfUndefined) {
    FontStyle font;
    StyleSet owner;

    if (this.m_namedFonts == null) {
      findMapToCopy: {
      for (owner = this.m_owner; (owner != null); owner = owner.m_owner) {
        synchronized (owner) {
          if (owner.m_namedFonts != null) {
            if (allocateIfUndefined) {
              this.m_namedFonts = ((StringMap) (//
                  owner.m_namedFonts.clone()));
              break findMapToCopy;
            }

            return owner.m_namedFonts.get(name);
          }
        }
      }
      if (!allocateIfUndefined) {
        return null;
      }
      this.m_namedFonts = new StringMap<>();
    }
    }

    font = this.m_namedFonts.get(name);
    if (font != null) {
      return font;
    }

    if (allocateIfUndefined) {
      font = this.allocateFont();
      this.m_namedFonts.put(name, font);
      return font;
    }

    return null;
  }

  /**
   * Obtain the stroke of a given name. If {@code allocateIfUndefined} is
   * {@code true} and no stroke is defined for the given name yet, then one
   * will be allocated. If {@code allocateIfUndefined} is {@code false} and
   * no stroke is defined for the given name yet, {@code null} will be
   * returned.
   *
   * @param name
   *          the name
   * @param allocateIfUndefined
   *          should the stroke be allocated if it is still undefined?
   * @return the stroke for the given name, or {@code null} if none was
   *         allocated for the name and {@code allocateIfUndefined} is
   *         {@code false}
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public synchronized final StrokeStyle getStroke(final String name,
      final boolean allocateIfUndefined) {
    StrokeStyle stroke;
    StyleSet owner;

    if (this.m_namedStrokes == null) {
      findMapToCopy: {
      for (owner = this.m_owner; (owner != null); owner = owner.m_owner) {
        synchronized (owner) {
          if (owner.m_namedStrokes != null) {
            if (allocateIfUndefined) {
              this.m_namedStrokes = ((StringMap) (//
                  owner.m_namedStrokes.clone()));
              break findMapToCopy;
            }

            return owner.m_namedStrokes.get(name);
          }
        }
      }
      if (!allocateIfUndefined) {
        return null;
      }
      this.m_namedStrokes = new StringMap<>();
    }
    }

    stroke = this.m_namedStrokes.get(name);
    if (stroke != null) {
      return stroke;
    }

    if (allocateIfUndefined) {
      stroke = this.allocateStroke();
      this.m_namedStrokes.put(name, stroke);
      return stroke;
    }

    return null;
  }

  /**
   * Get the default stroke
   *
   * @return the default stroke
   */
  public final StrokeStyle getDefaultStroke() {
    return this.m_def.m_strokes.getDefaultStroke();
  }

  /**
   * Get the thin stroke
   *
   * @return the thin stroke
   */
  public final StrokeStyle getThinStroke() {
    return this.m_def.m_strokes.getThinStroke();
  }

  /**
   * Get the thick stroke
   *
   * @return the thick stroke
   */
  public final StrokeStyle getThickStroke() {
    return this.m_def.m_strokes.getThickStroke();
  }

  /**
   * Get the default font style
   *
   * @return the default font style
   */
  public final FontStyle getDefaultFont() {
    return this.m_def.m_fonts.getDefaultFont();
  }

  /**
   * Get the emphasize font style
   *
   * @return the emphasize font style
   */
  public final FontStyle getEmphFont() {
    return this.m_def.m_fonts.getEmphFont();
  }

  /**
   * Get the code font style
   *
   * @return the code font style
   */
  public final FontStyle getCodeFont() {
    return this.m_def.m_fonts.getCodeFont();
  }

  /**
   * Get the black color
   *
   * @return the black color
   */
  public final ColorStyle getBlack() {
    return this.m_def.m_colors.getBlack();
  }

  /**
   * Get the white color
   *
   * @return the white color
   */
  public final ColorStyle getWhite() {
    return this.m_def.m_colors.getWhite();
  }

  /**
   * Initialize a graphics context with the default font, color, and stroke
   * settings
   *
   * @param graphics
   *          the graphics context
   */
  public final void initialize(final Graphics2D graphics) {
    this.m_def.m_fonts.initialize(graphics);
    this.m_def.m_colors.initialize(graphics);
    this.m_def.m_strokes.initialize(graphics);
  }

  /**
   * Get the color most similar to a given RGB value
   *
   * @param rgb
   *          the rgb value of the color
   * @return the color most similar to the RGB value
   */
  public final ColorStyle getMostSimilarColor(final int rgb) {
    return this.m_def.m_colors.getMostSimilarColor(rgb);
  }

  /**
   * Get the color most similar to another color
   *
   * @param color
   *          the other color
   * @return the color most similar to a given color
   */
  public final ColorStyle getMostSimilarColor(final Color color) {
    return this.m_def.m_colors.getMostSimilarColor(color);
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
    return this.m_def.m_fonts.getMostSimilarFont(family, bold, italic,
        underlined, size);
  }
}
