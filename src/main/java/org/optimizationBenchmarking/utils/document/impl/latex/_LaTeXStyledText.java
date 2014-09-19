package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.ComplexText;
import org.optimizationBenchmarking.utils.document.impl.abstr.StyledText;
import org.optimizationBenchmarking.utils.graphics.style.IStyle;

/** a styled text in a LaTeX document */
final class _LaTeXStyledText extends StyledText {
  /**
   * create the styled text
   * 
   * @param owner
   *          the owning element
   * @param style
   *          the style
   */
  _LaTeXStyledText(final ComplexText owner, final IStyle style) {
    super(owner, style);
    this.open();
  }
}
