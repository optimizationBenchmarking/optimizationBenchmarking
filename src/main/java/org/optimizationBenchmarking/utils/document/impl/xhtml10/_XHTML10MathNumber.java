package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathNumber;

/** an mathematical number element of a section in a XHTML document */
final class _XHTML10MathNumber extends MathNumber {
  /**
   * create the mathematical in-braces element
   *
   * @param owner
   *          the owner
   */
  _XHTML10MathNumber(final BasicMath owner) {
    super(owner);
    this.open();
  }

}
