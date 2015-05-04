package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathPow;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical pow function in a XHTML document */
final class _XHTML10MathPow extends MathPow {
  /** the overlined cell */
  private final static char[] EXP_TD = { '<', 't', 'd', ' ', 'c', 'l',
      'a', 's', 's', '=', '"', 'm', 'a', 't', 'h', 'E', 'x', 'p', '"',
      ' ', 'r', 'o', 'w', 's', 'p', 'a', 'n', '=', '"', '2', '"', '>' };
  /** the power tr */
  private final static char[] POWR_TR = { '<', 't', 'r', ' ', 'c', 'l',
      'a', 's', 's', '=', '"', 'm', 'a', 't', 'h', 'P', 'o', 'w', 'T',
      'R', '"', '>' };

  /**
   * Create a new mathematical function
   *
   * @param owner
   *          the owning text
   */
  _XHTML10MathPow(final BasicMath owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void render(final ITextOutput out, final char[][] data,
      final int size) {

    out.append(_XHTML10InlineMath.MO_TAB);
    out.append(_XHTML10MathPow.POWR_TR);
    out.append(_XHTML10InlineMath.MO_TD);
    out.append(_XHTML10Table.TD_END);

    out.append(_XHTML10MathPow.EXP_TD);
    out.append(data[1]);
    out.append(_XHTML10Table.TD_END);
    out.append(_XHTML10Table.TR_END);

    out.append(_XHTML10InlineMath.MO_TR);
    out.append(_XHTML10InlineMath.MO_TD);
    out.append(data[0]);
    out.append(_XHTML10Table.TD_END);
    out.append(_XHTML10Table.TR_END);

    out.append(_XHTML10Table.TABLE_END);
  }
}
