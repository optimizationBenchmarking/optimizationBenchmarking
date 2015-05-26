package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.ComplexText;
import org.optimizationBenchmarking.utils.document.impl.abstr.StyledText;
import org.optimizationBenchmarking.utils.graphics.style.IStyle;

/** a styled text in an export document */
final class _ExportStyledText extends StyledText {
  /**
   * create the styled text
   *
   * @param owner
   *          the owning element
   * @param style
   *          the style
   */
  _ExportStyledText(final ComplexText owner, final IStyle style) {
    super(owner, style);
    this.open();
  }
}
