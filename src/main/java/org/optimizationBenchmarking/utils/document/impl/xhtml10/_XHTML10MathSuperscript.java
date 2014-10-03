package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathSuperscript;

/** an mathematical superscript element of a section in a XHTML document */
final class _XHTML10MathSuperscript extends MathSuperscript {
  /**
   * create the mathematical superscript element
   * 
   * @param owner
   *          the owner
   */
  _XHTML10MathSuperscript(final BasicMath owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void appendLineBreak() {
    // nothing
  }
}
