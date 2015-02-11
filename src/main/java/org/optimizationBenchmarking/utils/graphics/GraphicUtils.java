package org.optimizationBenchmarking.utils.graphics;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;

import org.optimizationBenchmarking.utils.error.ErrorUtils;

/** a utility class for graphics stuff */
public final class GraphicUtils {

  /** the default renHashMap<K, V>hints */
  private static final RenderingHintHolder[] DEFAULT_RENDERING_HINTS;

  static {
    DEFAULT_RENDERING_HINTS = new RenderingHintHolder[10];

    GraphicUtils.DEFAULT_RENDERING_HINTS[0] = new RenderingHintHolder(
        RenderingHints.KEY_FRACTIONALMETRICS,
        RenderingHints.VALUE_FRACTIONALMETRICS_ON);

    GraphicUtils.DEFAULT_RENDERING_HINTS[1] = new RenderingHintHolder(
        RenderingHints.KEY_ALPHA_INTERPOLATION,
        RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);

    GraphicUtils.DEFAULT_RENDERING_HINTS[2] = new RenderingHintHolder(
        RenderingHints.KEY_COLOR_RENDERING,
        RenderingHints.VALUE_COLOR_RENDER_QUALITY);

    GraphicUtils.DEFAULT_RENDERING_HINTS[3] = new RenderingHintHolder(
        RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

    GraphicUtils.DEFAULT_RENDERING_HINTS[4] = new RenderingHintHolder(
        RenderingHints.KEY_STROKE_CONTROL,
        RenderingHints.VALUE_STROKE_NORMALIZE);

    GraphicUtils.DEFAULT_RENDERING_HINTS[5] = new RenderingHintHolder(
        RenderingHints.KEY_ALPHA_INTERPOLATION,
        RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);

    GraphicUtils.DEFAULT_RENDERING_HINTS[6] = new RenderingHintHolder(
        RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    GraphicUtils.DEFAULT_RENDERING_HINTS[7] = new RenderingHintHolder(
        RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);

    GraphicUtils.DEFAULT_RENDERING_HINTS[8] = new RenderingHintHolder(
        RenderingHints.KEY_INTERPOLATION,
        RenderingHints.VALUE_INTERPOLATION_BICUBIC);

    GraphicUtils.DEFAULT_RENDERING_HINTS[9] = new RenderingHintHolder(
        RenderingHints.KEY_TEXT_ANTIALIASING,
        RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);

  }

  /** the forbidden constructor */
  private GraphicUtils() {
    ErrorUtils.doNotCall();
  }

  /**
   * Set the default rendering hints
   * 
   * @param g
   *          the graphic to initialize
   */
  public static final void setDefaultRenderingHints(final Graphics2D g) {
    for (final RenderingHintHolder hint : GraphicUtils.DEFAULT_RENDERING_HINTS) {
      g.setRenderingHint(hint.m_key, hint.m_value);
    }
  }

  /**
   * Create the default rendering hints
   * 
   * @return a default set of rendering hints
   */
  public static final RenderingHints createDefaultRenderingHints() {
    RenderingHintHolder hint;
    RenderingHints h;
    int i;

    i = GraphicUtils.DEFAULT_RENDERING_HINTS.length;
    hint = GraphicUtils.DEFAULT_RENDERING_HINTS[--i];
    h = new RenderingHints(hint.m_key, hint.m_value);
    for (; (--i) >= 0;) {
      hint = GraphicUtils.DEFAULT_RENDERING_HINTS[i];
      h.put(hint.m_key, hint.m_value);
    }

    return h;
  }

  /** the internal rendering hint holder */
  private static final class RenderingHintHolder {
    /** the key */
    final Key m_key;
    /** the value */
    final Object m_value;

    /**
     * create the hint holder
     * 
     * @param key
     *          the key
     * @param value
     *          the value
     */
    RenderingHintHolder(final Key key, final Object value) {
      super();
      this.m_key = key;
      this.m_value = value;
    }
  }
}
