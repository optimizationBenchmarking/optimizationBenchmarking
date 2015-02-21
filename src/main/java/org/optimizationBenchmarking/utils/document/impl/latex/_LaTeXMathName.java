package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathName;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical name element in a LaTeX document */
final class _LaTeXMathName extends MathName {
  /** the begin math name */
  private static final char[] NAME_BEGIN = { '{', '\\', 't', 'e', 'x',
      't', 'i', 't', '{', };

  /**
   * create the mathematical in-braces element
   * 
   * @param owner
   *          the owner
   */
  _LaTeXMathName(final BasicMath owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {

    super.onOpen();
    this.getTextOutput().append(_LaTeXMathName.NAME_BEGIN);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    final ITextOutput out;

    out = this.getTextOutput();
    out.append('}');
    out.append('}');
    super.onClose();
  }
}
