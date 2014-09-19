package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.SectionTitle;

/** a section title of a section in a XHTML document */
final class _XHTML10SectionTitle extends SectionTitle {
  /**
   * create the section title
   * 
   * @param owner
   *          the owner
   */
  _XHTML10SectionTitle(final _XHTML10Section owner) {
    super(owner);
    this.open();
  }
}
