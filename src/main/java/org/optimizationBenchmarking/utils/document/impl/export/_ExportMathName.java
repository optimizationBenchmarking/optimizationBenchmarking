package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathName;

/** an mathematical name element of a section in an export document */
final class _ExportMathName extends MathName {
  /**
   * create the mathematical name element
   *
   * @param owner
   *          the owner
   */
  _ExportMathName(final BasicMath owner) {
    super(owner);
    this.open();
  }
}
