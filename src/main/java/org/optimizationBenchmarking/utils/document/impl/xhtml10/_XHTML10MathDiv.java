package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathDiv;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical div function in a XHTML document */
final class _XHTML10MathDiv extends MathDiv {
  /** the before */
  private final static char[] FRAC_TD = { '<', 't', 'd', ' ', 'c', 'l',
      'a', 's', 's', '=', '"', 'm', 'a', 't', 'h', 'U', 'L', '"', '>' };

  /**
   * Create a new mathematical function
   *
   * @param owner
   *          the owning text
   */
  _XHTML10MathDiv(final BasicMath owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void render(final ITextOutput out, final char[][] data,
      final int size) {

    out.append(_XHTML10InlineMath.MO_TAB);
    out.append(_XHTML10InlineMath.MO_TR);
    out.append(_XHTML10MathDiv.FRAC_TD);

    out.append(data[0]);

    out.append(_XHTML10Table.TD_END);
    out.append(_XHTML10Table.TR_END);

    out.append(_XHTML10InlineMath.MO_TR);
    out.append(_XHTML10InlineMath.MO_TD);

    out.append(data[1]);

    out.append(_XHTML10Table.TD_END);
    out.append(_XHTML10Table.TR_END);

    out.append(_XHTML10Table.TABLE_END);
  }
}
