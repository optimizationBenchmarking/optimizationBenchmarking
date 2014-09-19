package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentPart;
import org.optimizationBenchmarking.utils.document.impl.abstr.Section;
import org.optimizationBenchmarking.utils.document.spec.ILabel;

/** a section in a LaTeX document */
final class _LaTeXSection extends Section {
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
  _LaTeXSection(final DocumentPart owner, final ILabel useLabel,
      final int index) {
    super(owner, useLabel, index);
    this.open();
  }
}
