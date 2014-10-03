package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.ComplexText;
import org.optimizationBenchmarking.utils.document.impl.abstr.Superscript;

/** an superscript element of a section in a XHTML document */
final class _XHTML10Superscript extends Superscript {
  /**
   * create the superscript element
   * 
   * @param owner
   *          the owner
   */
  _XHTML10Superscript(final ComplexText owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void appendLineBreak() {
    this.assertNoChildren();
    this.getTextOutput().append(' ');
  }
}
