package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.SectionTitle;

/** a section title of a section in an export document */
final class _ExportSectionTitle extends SectionTitle {
  /**
   * create the section title
   *
   * @param owner
   *          the owner
   */
  _ExportSectionTitle(final _ExportSection owner) {
    super(owner);
    this.open();
  }
}
