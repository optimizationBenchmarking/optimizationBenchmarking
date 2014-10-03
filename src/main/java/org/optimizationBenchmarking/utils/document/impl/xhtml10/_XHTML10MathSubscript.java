package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathSubscript;

/** an mathematical subscript element of a section in a XHTML document */
final class _XHTML10MathSubscript extends MathSubscript {
  /**
   * create the mathematical subscript element
   * 
   * @param owner
   *          the owner
   */
  _XHTML10MathSubscript(final BasicMath owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void appendLineBreak() {
    // nothing
  }
}
