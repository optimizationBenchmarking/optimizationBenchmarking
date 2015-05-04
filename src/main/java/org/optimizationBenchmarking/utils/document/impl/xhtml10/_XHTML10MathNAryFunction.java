package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathNAryFunction;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical n-ary function in a XHTML document */
final class _XHTML10MathNAryFunction extends MathNAryFunction {

  /**
   * Create a new mathematical function
   *
   * @param owner
   *          the owning text
   * @param name
   *          the name of the function
   * @param minArity
   *          the minimum number of arguments
   * @param maxArity
   *          the maximum number of arguments
   */
  _XHTML10MathNAryFunction(final BasicMath owner, final String name,
      final int minArity, final int maxArity) {
    super(owner, name, minArity, maxArity);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void render(final ITextOutput out, final char[][] data,
      final int size) {
    final boolean needsBraces;
    final int braceIndex;
    boolean first;

    out.append(_XHTML10InlineMath.MO_TAB);
    out.append(_XHTML10InlineMath.MO_TR);

    out.append(_XHTML10InlineMath.MO_TD);
    out.append(this.getName());
    out.append(_XHTML10Table.TD_END);

    needsBraces = this.needsBraces();
    if (needsBraces) {
      braceIndex = this.getBraceIndex();
      out.append(_XHTML10InlineMath.MO_TD);
      out.append(((braceIndex & 1) == 0) ? '(' : '[');
      out.append(_XHTML10Table.TD_END);
    } else {
      out.append(XHTML10Driver.NBSP);
      braceIndex = 0;
    }

    first = true;
    for (final char[] param : data) {
      if (first) {
        first = false;
      } else {
        out.append(',');
        out.append(XHTML10Driver.NBSP);
        out.append(_XHTML10Table.TD_END);
      }

      out.append(_XHTML10InlineMath.MO_TD);
      out.append(param);
    }
    out.append(_XHTML10Table.TD_END);

    if (needsBraces) {
      out.append(_XHTML10InlineMath.MO_TD);
      out.append(((braceIndex & 1) == 0) ? ')' : ']');
      out.append(_XHTML10Table.TD_END);
    }

    out.append(_XHTML10Table.TR_END);
    out.append(_XHTML10Table.TABLE_END);
  }
}
