package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentSummary;

/** the summary of a XHTML document header */
final class _XHTML10DocumentSummary extends DocumentSummary {
  /**
   * Create a document summary.
   *
   * @param owner
   *          the owning document header
   */
  _XHTML10DocumentSummary(final _XHTML10DocumentHeader owner) {
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
