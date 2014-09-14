package org.optimizationBenchmarking.utils.document.impl.abstr;

import org.optimizationBenchmarking.utils.graphics.style.IStyle;

/**
 * A text class which allows creating text with more sophisticated styles
 * in it
 */
public class StyledText extends ComplexText {

  /** the style */
  private final IStyle m_style;

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
    this.m_driver.checkStyleForText(style);
    this.m_style = style;
  }

  /**
   * Get the style of this text
   * 
   * @return the style of this text
   */
  protected final IStyle getStyle() {
    return this.m_style;
  }

}
