package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentPart;
import org.optimizationBenchmarking.utils.document.impl.abstr.Section;
import org.optimizationBenchmarking.utils.document.spec.ILabel;

/** a section in a XHTML document */
final class _XHTML10Section extends Section {
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
  _XHTML10Section(final DocumentPart owner, final ILabel useLabel,
      final int index) {
    super(owner, useLabel, index);
    this.open();
  }
}
