package org.optimizationBenchmarking.utils.graphics;

import java.awt.Font;
import java.awt.font.TextAttribute;

import org.optimizationBenchmarking.utils.ErrorUtils;

/** a utility class for graphics stuff */
public final class GraphicUtils {

  /**
   * Check whether a font is underlined or not
   * 
   * @param font
   *          the font
   * @return {@code true} if the font is underlined, {@code false}
   *         otherwise
   */
  public static final boolean isUnderlined(final Font font) {
    final Number underlineAtt;

    underlineAtt = ((Number) (font.getAttributes()
        .get(TextAttribute.UNDERLINE)));
    return ((underlineAtt != null) && (underlineAtt.intValue() >= 0));
  }

  /** the forbidden constructor */
  private GraphicUtils() {
    ErrorUtils.doNotCall();
  }
}
