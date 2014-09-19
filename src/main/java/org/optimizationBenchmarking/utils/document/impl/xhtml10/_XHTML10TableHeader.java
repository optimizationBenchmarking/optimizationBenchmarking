package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.TableHeader;

/** a header of a table in a XHTML document */
final class _XHTML10TableHeader extends TableHeader {
  /**
   * Create a header of a table
   * 
   * @param owner
   *          the owning table
   */
  _XHTML10TableHeader(final _XHTML10Table owner) {
    super(owner);
    this.open();
  }
}
