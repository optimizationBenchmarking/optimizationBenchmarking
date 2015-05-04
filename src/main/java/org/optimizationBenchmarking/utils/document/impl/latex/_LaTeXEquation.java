package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.Equation;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an equation in a LaTeX document */
final class _LaTeXEquation extends Equation {

  /** begin equation */
  private static final char[] EQUATION_BEGIN = { '\\', 'b', 'e', 'g', 'i',
      'n', '{', 'e', 'q', 'u', 'a', 't', 'i', 'o', 'n', '}' };
  /** end equation */
  private static final char[] EQUATION_END = { '\\', 'e', 'n', 'd', '{',
      'e', 'q', 'u', 'a', 't', 'i', 'o', 'n', '}' };

  /**
   * Create a new equation
   *
   * @param owner
   *          the owning section body
   * @param index
   *          the table index in the owning section
   * @param useLabel
   *          the label to use
   */
  _LaTeXEquation(final _LaTeXSectionBody owner, final ILabel useLabel,
      final int index) {
    super(owner, useLabel, index);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    final ITextOutput out;

    super.onOpen();

    out = this.getTextOutput();
    LaTeXDriver._endLine(out);
    LaTeXDriver._endLine(out);
    out.append(_LaTeXEquation.EQUATION_BEGIN);
    LaTeXDriver._endLine(out);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    final ITextOutput out;

    out = this.getTextOutput();

    LaTeXDriver._endLine(out);
    LaTeXDriver._label(this.getLabel(), out);

    out.append(_LaTeXEquation.EQUATION_END);
    LaTeXDriver._endLine(out);
    LaTeXDriver._endLine(out);

    super.onClose();
  }
}
