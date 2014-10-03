package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.ComplexText;
import org.optimizationBenchmarking.utils.document.impl.abstr.InlineMath;

/** an inline math element of a section in a XHTML document */
final class _XHTML10InlineMath extends InlineMath {
  /**
   * create the inline math element
   * 
   * @param owner
   *          the owner
   */
  _XHTML10InlineMath(final ComplexText owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void appendLineBreak() {
    // nothing
  }
}
