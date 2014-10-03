package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.TableCaption;

/** the caption of a table in a XHTML document */
final class _XHTML10TableCaption extends TableCaption {
  /**
   * Create the caption of a table
   * 
   * @param owner
   *          the owning table
   */
  _XHTML10TableCaption(final _XHTML10Table owner) {
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
