package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.ComplexText;
import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentElement;

/** a complex text in an export document */
final class _ExportComplexText extends ComplexText {
  /**
   * create the complex text
   *
   * @param owner
   *          the owning element
   */
  _ExportComplexText(final DocumentElement owner) {
    super(owner);
    this.open();
  }
}
