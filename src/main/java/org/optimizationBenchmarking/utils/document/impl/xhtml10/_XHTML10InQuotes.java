package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.InQuotes;
import org.optimizationBenchmarking.utils.document.impl.abstr.Text;

/** an in-quotes element of a section in a XHTML document */
final class _XHTML10InQuotes extends InQuotes {
  /**
   * create the in-quotes element
   * 
   * @param owner
   *          the owner
   */
  _XHTML10InQuotes(final Text owner) {
    super(owner);
    this.open();
  }
}
