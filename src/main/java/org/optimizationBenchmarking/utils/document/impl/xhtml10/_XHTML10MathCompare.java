package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathCompare;
import org.optimizationBenchmarking.utils.document.spec.EMathComparison;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/** an mathematical compare function in a XHTML document */
final class _XHTML10MathCompare extends MathCompare {

  /** the signs */
  private static final char[][] SIGNS;

  static {
    final MemoryTextOutput out;
    int ord;

    SIGNS = new char[EMathComparison.INSTANCES.size()][];

    _XHTML10MathCompare.SIGNS[EMathComparison.LESS.ordinal()] = new char[] {
        '&', 'l', 't', ';' };
    _XHTML10MathCompare.SIGNS[EMathComparison.LESS_OR_EQUAL.ordinal()] = new char[] {
        '&', 'l', 'e', ';' };
    _XHTML10MathCompare.SIGNS[EMathComparison.GREATER_OR_EQUAL.ordinal()] = new char[] {
        '&', 'g', 'e', ';' };
    _XHTML10MathCompare.SIGNS[EMathComparison.GREATER.ordinal()] = new char[] {
        '&', 'g', 't', ';' };
    _XHTML10MathCompare.SIGNS[EMathComparison.EQUAL.ordinal()] = new char[] { '=' };
    _XHTML10MathCompare.SIGNS[EMathComparison.EQUIVALENT.ordinal()] = new char[] {
        '&', 'e', 'q', 'u', 'i', 'v', ';' };
    _XHTML10MathCompare.SIGNS[EMathComparison.APPROXIMATELY.ordinal()] = new char[] {
        '&', 'a', 's', 'y', 'm', 'p', ';' };
    _XHTML10MathCompare.SIGNS[EMathComparison.PROPROTIONAL_TO.ordinal()] = new char[] {
        '&', 'p', 'r', 'o', 'p', ';' };
    _XHTML10MathCompare.SIGNS[EMathComparison.NOT_EQUAL.ordinal()] = new char[] {
        '&', 'n', 'e', ';' };
    _XHTML10MathCompare.SIGNS[EMathComparison.ELEMENT_OF.ordinal()] = new char[] {
        '&', 'i', 's', 'i', 'n', ';' };
    _XHTML10MathCompare.SIGNS[EMathComparison.NOT_ELEMENT_OF.ordinal()] = new char[] {
        '&', 'n', 'o', 't', 'i', 'n', ';' };
    _XHTML10MathCompare.SIGNS[EMathComparison.SUBSET_OF.ordinal()] = new char[] {
        '&', 's', 'u', 'b', ';' };
    _XHTML10MathCompare.SIGNS[EMathComparison.NOT_SUBSET_OF.ordinal()] = new char[] {
        '&', 'n', 's', 'u', 'b', ';' };
    _XHTML10MathCompare.SIGNS[EMathComparison.SUBSET_OF_OR_EQUAL.ordinal()] = new char[] {
        '&', 's', 'u', 'b', 'e', ';' };
    _XHTML10MathCompare.SIGNS[EMathComparison.NOT_SUBSET_OF_OR_EQUAL
        .ordinal()] = new char[] { '&', 'n', 's', 'u', 'b', 'e', ';' };

    out = new MemoryTextOutput();
    for (final EMathComparison comp : EMathComparison.INSTANCES) {
      ord = comp.ordinal();
      if (_XHTML10MathCompare.SIGNS[ord] == null) {
        out.append('&');
        out.append('#');
        out.append((int) comp.getOperatorChar());
        out.append(';');
        _XHTML10MathCompare.SIGNS[ord] = out.toChars();
        out.clear();
      }
    }
  }

  /**
   * Create a new mathematical function
   *
   * @param owner
   *          the owning text
   * @param cmp
   *          the comparator
   */
  _XHTML10MathCompare(final BasicMath owner, final EMathComparison cmp) {
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
