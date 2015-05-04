package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.Itemization;
import org.optimizationBenchmarking.utils.document.impl.abstr.StructuredText;

/** an itemization in a XHTML document */
final class _XHTML10Itemization extends Itemization {
  /** the start of ul */
  private static final char[] UL_BEGIN = { '<', 'u', 'l', '>' };
  /** the end of ul */
  private static final char[] UL_END = { '<', '/', 'u', 'l', '>' };

  /**
   * Create a new itemization
   *
   * @param owner
   *          the owning text
   */
  _XHTML10Itemization(final StructuredText owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    super.onOpen();
    this.getTextOutput().append(_XHTML10Itemization.UL_BEGIN);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    this.getTextOutput().append(_XHTML10Itemization.UL_END);
    super.onClose();
  }
}
