package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentPart;
import org.optimizationBenchmarking.utils.document.impl.abstr.Section;
import org.optimizationBenchmarking.utils.document.spec.ILabel;

/** a section in an export document */
final class _ExportSection extends Section {
  /**
   * Create a new section
   *
   * @param owner
   *          the owning text
   * @param useLabel
   *          the label to use
   * @param index
   *          the section index
   */
  _ExportSection(final DocumentPart owner, final ILabel useLabel,
      final int index) {
    super(owner, useLabel, index);
    this.open();
  }
}
