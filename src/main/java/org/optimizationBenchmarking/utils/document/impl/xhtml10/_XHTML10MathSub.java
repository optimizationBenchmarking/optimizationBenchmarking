package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathSub;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical sub function in a XHTML document */
final class _XHTML10MathSub extends MathSub {

  /**
   * Create a new mathematical function
   *
   * @param owner
   *          the owning text
   */
  _XHTML10MathSub(final BasicMath owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void render(final ITextOutput out, final char[][] data,
      final int size) {
    int i;

    out.append(_XHTML10InlineMath.MO_TAB);
    out.append(_XHTML10InlineMath.MO_TR);

    for (i = 0; i < size; i++) {
      if (i > 0) {
        out.append(_XHTML10InlineMath.MO_TD);
        out.append('-');
        out.append(_XHTML10Table.TD_END);
      }
      out.append(_XHTML10InlineMath.MO_TD);
      out.append(data[i]);
      out.append(_XHTML10Table.TD_END);
    }

    out.append(_XHTML10Table.TR_END);
    out.append(_XHTML10Table.TABLE_END);
  }
}
