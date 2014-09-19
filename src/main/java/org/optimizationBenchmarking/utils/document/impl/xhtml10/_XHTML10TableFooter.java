package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.TableFooter;

/** a footer of a table in a XHTML document */
final class _XHTML10TableFooter extends TableFooter {
  /**
   * Create a footer of a table
   * 
   * @param owner
   *          the owning table
   */
  _XHTML10TableFooter(final _XHTML10Table owner) {
    super(owner);
    this.open();
  }
}
