package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.SectionTitle;

/** a section title of a section in a LaTeX document */
final class _LaTeXSectionTitle extends SectionTitle {
  /**
   * create the section title
   * 
   * @param owner
   *          the owner
   */
  _LaTeXSectionTitle(final _LaTeXSection owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void appendLineBreak() {
    this.assertNoChildren();
    this.getTextOutput().append(' ');
  }
}
