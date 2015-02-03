package org.optimizationBenchmarking.utils.graphics.style.stroke;

import java.awt.Graphics2D;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.graphics.style.Palette;
import org.optimizationBenchmarking.utils.hash.HashUtils;

/**
 * A stroke palette.
 */
public class StrokePalette extends Palette<StrokeStyle> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the default stroke style */
  private final StrokeStyle m_default;

  /** the thin stroke style */
  private final StrokeStyle m_thin;

  /** the thick stroke style */
  private final StrokeStyle m_thick;

  /**
   * create the palette
   * 
   * @param def
   *          the default stroke style
   * @param thin
   *          the thin stroke style
   * @param thick
   *          the thick stroke style
   * @param data
   *          the palette data
   */
  StrokePalette(final StrokeStyle def, final StrokeStyle thin,
      final StrokeStyle thick, final StrokeStyle[] data) {
    super(data);
    StrokePalette._checkStrokes(thin, false, def, false, thick, false);

    this.m_default = def;
    def.m_palette = this;
    this.m_thin = thin;
    thin.m_palette = this;
    this.m_thick = thick;
    thick.m_palette = this;

    for (final StrokeStyle s : data) {
      s.m_palette = this;
    }
  }

  /**
   * Check the strokes
   * 
   * @param thin
   *          the thin stroke
   * @param thinCanBeNull
   *          can the thin stroke be null?
   * @param def
   *          the default stroke
   * @param defCanBeNull
   *          can the default stroke be null?
   * @param thick
   *          the thick stroke
   * @param thickCanBeNull
   *          can the thick stroke be null?
   */
  static final void _checkStrokes(final StrokeStyle thin,
      final boolean thinCanBeNull, final StrokeStyle def,
      final boolean defCanBeNull, final StrokeStyle thick,
      final boolean thickCanBeNull) {

    float a, b;

    if (def == null) {
      if (!defCanBeNull) {
        throw new IllegalArgumentException(//
            "Default stroke must not be null."); //$NON-NLS-1$
      }
    } else {
      a = def.getLineWidth();

      if (thin != null) {
        b = thin.getLineWidth();
        if (a < b) {
          throw new IllegalArgumentException(//
              "Line width of thin stroke (" + b + //$NON-NLS-1$
                  ") cannot be bigger than line width of default stroke (" //$NON-NLS-1$
                  + a + ") but it is.");//$NON-NLS-1$
        }
      }

      if (thick != null) {
        b = thick.getLineWidth();
        if (a > b) {
          throw new IllegalArgumentException(//
              "Line width of thick stroke (" + b + //$NON-NLS-1$
                  ") cannot be smaler than line width of default stroke (" //$NON-NLS-1$
                  + a + ") but it is.");//$NON-NLS-1$
        }
      }
    }

    if (thin == null) {
      if (!thinCanBeNull) {
        throw new IllegalArgumentException(//
            "Thin stroke must not be null."); //$NON-NLS-1$
      }
    } else {
      a = thin.getLineWidth();
      if (thick != null) {
        b = thick.getLineWidth();
        if (a > b) {
          throw new IllegalArgumentException(//
              "Line width of thick stroke (" + b + //$NON-NLS-1$
                  ") cannot be smaler than line width of thin stroke (" //$NON-NLS-1$
                  + a + ") but it is.");//$NON-NLS-1$
        }

      }
    }

    if (thick == null) {
      if (!thickCanBeNull) {
        throw new IllegalArgumentException(//
            "Thick stroke must not be null."); //$NON-NLS-1$
      }
    }
  }

  /**
   * Get the default stroke
   * 
   * @return the default stroke
   */
  public final StrokeStyle getDefaultStroke() {
    return this.m_default;
  }

  /**
   * Get the thin stroke
   * 
   * @return the thin stroke
   */
  public final StrokeStyle getThinStroke() {
    return this.m_thin;
  }

  /**
   * Get the thick stroke
   * 
   * @return the thick stroke
   */
  public final StrokeStyle getThickStroke() {
    return this.m_thick;
  }

  /** {@inheritDoc} */
  @Override
  public final void initialize(final Graphics2D graphics) {
    graphics.setStroke(this.m_default);
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return HashUtils.combineHashes(HashUtils.combineHashes(
        super.hashCode(), HashUtils.hashCode(this.m_default)), HashUtils
        .combineHashes(HashUtils.hashCode(this.m_thick),
            HashUtils.hashCode(this.m_thin)));
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    final StrokePalette other;

    if (o == this) {
      return true;
    }
    if (o instanceof StrokePalette) {
      other = ((StrokePalette) o);
      return (EComparison.equals(this.m_default, other.m_default) && //
          EComparison.equals(this.m_thick, other.m_thick) && //
          EComparison.equals(this.m_thin, other.m_thin) && super.equals(o));
    }
    return false;
  }
}
