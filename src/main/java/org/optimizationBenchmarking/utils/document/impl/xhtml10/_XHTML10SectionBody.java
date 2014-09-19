package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.SectionBody;

/** a section body of a section in a XHTML document */
final class _XHTML10SectionBody extends SectionBody {
  /**
   * create the section body
   * 
   * @param owner
   *          the owner
   */
  _XHTML10SectionBody(final _XHTML10Section owner) {
    super(owner);
    this.open();
  }
}
