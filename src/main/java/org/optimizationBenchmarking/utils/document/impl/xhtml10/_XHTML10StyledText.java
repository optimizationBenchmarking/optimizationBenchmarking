package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.ComplexText;
import org.optimizationBenchmarking.utils.document.impl.abstr.StyledText;
import org.optimizationBenchmarking.utils.graphics.style.IStyle;

/** a styled text in a XHTML document */
final class _XHTML10StyledText extends StyledText {
  /**
   * create the styled text
   * 
   * @param owner
   *          the owning element
   * @param style
   *          the style
   */
  _XHTML10StyledText(final ComplexText owner, final IStyle style) {
    super(owner, style);
    this.open();
  }
}
