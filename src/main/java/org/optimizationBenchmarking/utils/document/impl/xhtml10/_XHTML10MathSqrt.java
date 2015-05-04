package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathSqrt;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical sqrt function in a XHTML document */
final class _XHTML10MathSqrt extends MathSqrt {

  /** the before */
  final static char[] ROOT = { '&', '#', '8', '7', '3', '0', ';' };

  /** the overlined cell */
  final static char[] OL_TD = { '<', 't', 'd', ' ', 'c', 'l', 'a', 's',
      's', '=', '"', 'm', 'a', 't', 'h', 'T', 'L', '"', '>' };

  /**
   * Create a new mathematical function
   *
   * @param owner
   *          the owning text
   */
  _XHTML10MathSqrt(final BasicMath owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void render(final ITextOutput out, final char[][] data,
      final int size) {
    out.append(_XHTML10InlineMath.MO_TAB);
    out.append(_XHTML10InlineMath.MO_TR);
    out.append(_XHTML10InlineMath.MO_TD);
    out.append(_XHTML10MathSqrt.ROOT);
    out.append(_XHTML10Table.TD_END);

    out.append(_XHTML10MathSqrt.OL_TD);

    out.append(data[0]);

    out.append(_XHTML10Table.TD_END);
    out.append(_XHTML10Table.TR_END);
    out.append(_XHTML10Table.TABLE_END);
  }
}
