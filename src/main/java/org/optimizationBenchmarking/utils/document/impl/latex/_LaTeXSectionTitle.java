package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.SectionTitle;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

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

  /** {@inheritDoc} */
  @SuppressWarnings("resource")
  @Override
  protected synchronized final void onClose() {
    final ITextOutput out;
    final boolean emulated;
    final _LaTeXSection owner;

    out = this.getTextOutput();
    LaTeXDriver._endCommandLine(out);

    owner = ((_LaTeXSection) (this.getOwner()));
    emulated = owner.m_emulated;
    LaTeXDriver._label(owner.getLabel(), out, emulated);
    if (emulated) {
      out.append('~');
    }

    super.onClose();
  }
}
