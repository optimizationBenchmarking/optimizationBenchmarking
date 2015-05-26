package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.SectionBody;

/** a section body of a section in an export document */
final class _ExportSectionBody extends SectionBody {
  /**
   * create the section body
   *
   * @param owner
   *          the owner
   */
  _ExportSectionBody(final _ExportSection owner) {
    super(owner);
    this.open();
  }
}
