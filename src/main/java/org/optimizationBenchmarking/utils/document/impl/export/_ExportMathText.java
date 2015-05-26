package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathText;

/** an mathematical text element of a section in an export document */
final class _ExportMathText extends MathText {
  /**
   * create the mathematical in-braces element
   *
   * @param owner
   *          the owner
   */
  _ExportMathText(final BasicMath owner) {
    super(owner);
    this.open();
  }
}
