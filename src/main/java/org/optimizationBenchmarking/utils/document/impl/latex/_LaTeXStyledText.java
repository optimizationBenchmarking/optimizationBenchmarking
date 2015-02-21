package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.ComplexText;
import org.optimizationBenchmarking.utils.document.impl.abstr.StyledText;
import org.optimizationBenchmarking.utils.graphics.style.IStyle;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** a styled text in a LaTeX document */
final class _LaTeXStyledText extends StyledText {
  /**
   * create the styled text
   * 
   * @param owner
   *          the owning element
   * @param style
   *          the style
   */
  _LaTeXStyledText(final ComplexText owner, final IStyle style) {
    super(owner, style);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void appendLineBreak() {
    final ITextOutput textOut;

    this.assertNoChildren();
    textOut = this.getTextOutput();
    textOut.appendLineBreak();
    textOut.appendLineBreak();
  }
}
