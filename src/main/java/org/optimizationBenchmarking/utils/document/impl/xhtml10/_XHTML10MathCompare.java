package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathCompare;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical compare function in a XHTML document */
final class _XHTML10MathCompare extends MathCompare {

  /** the signs */
  private static final char[][] SIGNS;

  static {
    SIGNS = new char[EComparison.NOT_EQUAL.ordinal() + 1][];

    _XHTML10MathCompare.SIGNS[EComparison.LESS.ordinal()] = new char[] {
        '&', 'l', 't', ';' };
    _XHTML10MathCompare.SIGNS[EComparison.LESS_OR_EQUAL.ordinal()] = //
    _XHTML10MathCompare.SIGNS[EComparison.LESS_OR_SAME.ordinal()] = new char[] {
        '&', '#', '8', '8', '0', '4', ';' };
    _XHTML10MathCompare.SIGNS[EComparison.EQUAL.ordinal()] = _XHTML10MathCompare.SIGNS[EComparison.SAME
        .ordinal()] = new char[] { '=' };
    _XHTML10MathCompare.SIGNS[EComparison.GREATER_OR_SAME.ordinal()] = //
    _XHTML10MathCompare.SIGNS[EComparison.GREATER_OR_EQUAL.ordinal()] = new char[] {
        '&', '#', '8', '8', '0', '5', ';' };
    _XHTML10MathCompare.SIGNS[EComparison.GREATER.ordinal()] = new char[] {
        '&', 'g', 't', ';' };
    _XHTML10MathCompare.SIGNS[EComparison.NOT_EQUAL.ordinal()] = //
    _XHTML10MathCompare.SIGNS[EComparison.NOT_SAME.ordinal()] = new char[] {
        '&', '#', '8', '8', '0', '0', ';' };

  }

  /**
   * Create a new mathematical function
   * 
   * @param owner
   *          the owning text
   * @param cmp
   *          the comparator
   */
  _XHTML10MathCompare(final BasicMath owner, final EComparison cmp) {
    super(owner, cmp);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void render(final ITextOutput out, final char[][] data,
      final int size) {
    out.append(_XHTML10InlineMath.MO_TAB);
    out.append(_XHTML10InlineMath.MO_TR);

    out.append(_XHTML10InlineMath.MO_TD);
    out.append(data[0]);
    out.append(_XHTML10Table.TD_END);

    out.append(_XHTML10InlineMath.MO_TD);
    out.append(_XHTML10MathCompare.SIGNS[this.getComparison().ordinal()]);
    out.append(_XHTML10Table.TD_END);

    out.append(_XHTML10InlineMath.MO_TD);
    out.append(data[1]);
    out.append(_XHTML10Table.TD_END);

    out.append(_XHTML10Table.TR_END);
    out.append(_XHTML10Table.TABLE_END);
  }
}
