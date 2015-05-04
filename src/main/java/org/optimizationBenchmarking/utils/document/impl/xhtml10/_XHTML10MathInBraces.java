package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathInBraces;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical in-braces element of a section in a XHTML document */
final class _XHTML10MathInBraces extends MathInBraces {
  /**
   * create the mathematical in-braces element
   *
   * @param owner
   *          the owner
   */
  _XHTML10MathInBraces(final BasicMath owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    final ITextOutput out;

    super.onOpen();

    out = this.getTextOutput();

    out.append(_XHTML10InlineMath.MO_TAB);
    out.append(_XHTML10InlineMath.MO_TR);

    out.append(_XHTML10InlineMath.MO_TD);
    out.append(((this.getBraceIndex() & 1) == 0) ? '(' : '[');
    out.append(_XHTML10Table.TD_END);

    out.append(_XHTML10InlineMath.MO_TD);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    final ITextOutput out;

    out = this.getTextOutput();

    out.append(_XHTML10Table.TD_END);

    out.append(_XHTML10InlineMath.MO_TD);
    out.append(((this.getBraceIndex() & 1) == 0) ? ')' : ']');
    out.append(_XHTML10Table.TD_END);

    out.append(_XHTML10Table.TR_END);
    out.append(_XHTML10Table.TABLE_END);

    super.onClose();
  }

}
