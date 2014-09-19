package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.TableBody;

/** a body of a table in a XHTML document */
final class _XHTML10TableBody extends TableBody {
  /**
   * Create a body of a table
   * 
   * @param owner
   *          the owning table
   */
  _XHTML10TableBody(final _XHTML10Table owner) {
    super(owner);
    this.open();
  }
}
