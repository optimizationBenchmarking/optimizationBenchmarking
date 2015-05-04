package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathName;

/** an mathematical name element of a section in a XHTML document */
final class _XHTML10MathName extends MathName {
  /**
   * create the mathematical name element
   *
   * @param owner
   *          the owner
   */
  _XHTML10MathName(final BasicMath owner) {
    super(owner);
    this.open();
  }

}
