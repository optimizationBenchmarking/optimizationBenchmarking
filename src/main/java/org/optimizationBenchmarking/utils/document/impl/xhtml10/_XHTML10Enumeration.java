package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.Enumeration;
import org.optimizationBenchmarking.utils.document.impl.abstr.StructuredText;

/** an enumeration in a XHTML document */
final class _XHTML10Enumeration extends Enumeration {

  /** the start of ol */
  private static final char[] OL_BEGIN = { '<', 'o', 'l', '>' };
  /** the end of ol */
  private static final char[] OL_END = { '<', '/', 'o', 'l', '>' };

  /**
   * Create a new enumeration
   *
   * @param owner
   *          the owning text
   */
  _XHTML10Enumeration(final StructuredText owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    super.onOpen();
    this.getTextOutput().append(_XHTML10Enumeration.OL_BEGIN);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    this.getTextOutput().append(_XHTML10Enumeration.OL_END);
    super.onClose();
  }
}
