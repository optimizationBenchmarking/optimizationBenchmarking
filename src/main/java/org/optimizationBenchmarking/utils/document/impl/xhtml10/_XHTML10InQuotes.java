package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.InQuotes;
import org.optimizationBenchmarking.utils.document.impl.abstr.PlainText;

/** an in-quotes element of a section in a XHTML document */
final class _XHTML10InQuotes extends InQuotes {
  /**
   * create the in-quotes element
   *
   * @param owner
   *          the owner
   */
  _XHTML10InQuotes(final PlainText owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void appendLineBreak() {
    this.assertNoChildren();
    this.getTextOutput().append(XHTML10Driver.BR);
  }
}
