package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.SectionBody;

/** a section body of a section in a LaTeX document */
final class _LaTeXSectionBody extends SectionBody {
  /**
   * create the section body
   *
   * @param owner
   *          the owner
   */
  _LaTeXSectionBody(final _LaTeXSection owner) {
    super(owner);
    this.open();
  }
}
