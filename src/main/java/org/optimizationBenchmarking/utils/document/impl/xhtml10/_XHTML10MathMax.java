package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathMax;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical max function in a XHTML document */
final class _XHTML10MathMax extends MathMax {
  /** the operator */
  private final static char[] OP = new char[] { 'm', 'a', 'x', '{' };

  /**
   * Create a new mathematical function
   *
   * @param owner
   *          the owning text
   */
  _XHTML10MathMax(final BasicMath owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void render(final ITextOutput out, final char[][] data,
      final int size) {
    boolean first;

    out.append(_XHTML10InlineMath.MO_TAB);
    out.append(_XHTML10InlineMath.MO_TR);

    out.append(_XHTML10InlineMath.MO_TD);
    out.append(_XHTML10MathMax.OP);
    out.append(_XHTML10Table.TD_END);

    first = true;
    for (final char[] operand : data) {
      out.append(_XHTML10InlineMath.MO_TD);
      if (first) {
        first = false;
      } else {
        out.append(_XHTML10InlineMath.MO_TD);
        out.append(',');
        out.append(_XHTML10Table.TD_END);
      }
      out.append(operand);
      out.append(_XHTML10Table.TD_END);
    }

    out.append(_XHTML10InlineMath.MO_TD);
    out.append('}');
    out.append(_XHTML10Table.TD_END);

    out.append(_XHTML10Table.TR_END);
    out.append(_XHTML10Table.TABLE_END);
  }
}
