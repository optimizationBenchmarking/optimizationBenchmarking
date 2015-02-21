package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.TableCaption;

/** the caption of a table in a LaTeX document */
final class _LaTeXTableCaption extends TableCaption {
  /**
   * Create the caption of a table
   * 
   * @param owner
   *          the owning table
   */
  _LaTeXTableCaption(final _LaTeXTable owner) {
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
