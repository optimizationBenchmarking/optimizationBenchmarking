package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentTitle;

/** the title of a XHTML document header */
final class _XHTML10DocumentTitle extends DocumentTitle {
  /**
   * Create a document title.
   *
   * @param owner
   *          the owning document header
   */
  _XHTML10DocumentTitle(final _XHTML10DocumentHeader owner) {
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
