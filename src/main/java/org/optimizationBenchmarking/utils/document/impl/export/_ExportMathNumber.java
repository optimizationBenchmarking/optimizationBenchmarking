package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathNumber;

/** an mathematical number element of a section in an export document */
final class _ExportMathNumber extends MathNumber {
  /**
   * create the mathematical in-braces element
   *
   * @param owner
   *          the owner
   */
  _ExportMathNumber(final BasicMath owner) {
    super(owner);
    this.open();
  }
}
