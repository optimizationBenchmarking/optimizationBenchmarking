package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathText;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical text element of a section in a XHTML document */
final class _XHTML10MathText extends MathText {
  /**
   * create the mathematical in-braces element
   *
   * @param owner
   *          the owner
   */
  _XHTML10MathText(final BasicMath owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    final ITextOutput out;

    super.onOpen();

    out = this.getTextOutput();
    out.append(XHTML10Driver.SPAN_CLASS_BEGIN);
    out.append(this.getDocument().getStyles().getDefaultFont().getID());
    out.append(XHTML10Driver.ATTRIB_TAG_BEGIN_END);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    this.getTextOutput().append(XHTML10Driver.SPAN_END);
    super.onClose();
  }

}
