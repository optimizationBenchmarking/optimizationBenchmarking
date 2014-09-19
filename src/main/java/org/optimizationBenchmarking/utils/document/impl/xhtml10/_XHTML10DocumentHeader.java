package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentHeader;

/** the XHTML document header */
final class _XHTML10DocumentHeader extends DocumentHeader {
  /**
   * Create a document header.
   * 
   * @param owner
   *          the owning document
   */
  _XHTML10DocumentHeader(final _XHTML10Document owner) {
    super(owner);
    this.open();
  }
}
