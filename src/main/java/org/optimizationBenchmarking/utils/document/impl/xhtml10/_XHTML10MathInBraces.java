package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathInBraces;

/** an mathematical in-braces element of a section in a XHTML document */
final class _XHTML10MathInBraces extends MathInBraces {
  /**
   * create the mathematical in-braces element
   * 
   * @param owner
   *          the owner
   */
  _XHTML10MathInBraces(final BasicMath owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void appendLineBreak() {
    // nothing
  }
}
