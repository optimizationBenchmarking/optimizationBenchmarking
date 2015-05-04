package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathCompare;
import org.optimizationBenchmarking.utils.document.spec.EMathComparison;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical compare function in a XHTML document */
final class _XHTML10MathCompare extends MathCompare {

  /** the signs */
  private static final char[][] SIGNS;

  static {
    SIGNS = new char[EMathComparison.INSTANCES.size()][];
    _XHTML10MathCompare.SIGNS[EMathComparison.VERY_MUCH_LESS.ordinal()] = new char[] {
        '&', '#', 'x', '2', '2', 'd', '8', ';' };
    _XHTML10MathCompare.SIGNS[EMathComparison.MUCH_LESS.ordinal()] = new char[] {
        '&', '#', 'x', '2', '2', '6', 'a', ';' };
    _XHTML10MathCompare.SIGNS[EMathComparison.LESS.ordinal()] = new char[] {
        '&', 'l', 't', ';' };
    _XHTML10MathCompare.SIGNS[EMathComparison.LESS_OR_EQUAL.ordinal()] = new char[] {
        '&', 'l', 'e', ';' };
    _XHTML10MathCompare.SIGNS[EMathComparison.GREATER_OR_EQUAL.ordinal()] = new char[] {
        '&', 'g', 'e', ';' };
    _XHTML10MathCompare.SIGNS[EMathComparison.GREATER.ordinal()] = new char[] {
        '&', 'g', 't', ';' };
    _XHTML10MathCompare.SIGNS[EMathComparison.MUCH_GREATER.ordinal()] = new char[] {
        '&', '#', 'x', '2', '2', '6', 'b', ';' };
    _XHTML10MathCompare.SIGNS[EMathComparison.VERY_MUCH_GREATER.ordinal()] = new char[] {
        '&', '#', 'x', '2', '2', 'd', '9', ';' };
    _XHTML10MathCompare.SIGNS[EMathComparison.EQUAL.ordinal()] = new char[] { '=' };
    _XHTML10MathCompare.SIGNS[EMathComparison.EQUIVALENT.ordinal()] = new char[] {
        '&', 'e', 'q', 'u', 'i', 'v', ';' };
    _XHTML10MathCompare.SIGNS[EMathComparison.APPROXIMATELY.ordinal()] = new char[] {
        '&', 'a', 's', 'y', 'm', 'p', ';' };
    _XHTML10MathCompare.SIGNS[EMathComparison.APPROXIMATELY_EQUAL
        .ordinal()] = new char[] { '&', '#', 'x', '2', '2', '4', 'a', ';' };
    _XHTML10MathCompare.SIGNS[EMathComparison.PROPROTIONAL_TO.ordinal()] = new char[] {
        '&', 'p', 'r', 'o', 'p', ';' };
    _XHTML10MathCompare.SIGNS[EMathComparison.NOT_EQUAL.ordinal()] = new char[] {
        '&', 'n', 'e', ';' };
    _XHTML10MathCompare.SIGNS[EMathComparison.NOT_EQUIVALENT.ordinal()] = new char[] {
        '&', '#', 'x', '2', '2', '6', '2', ';' };
    _XHTML10MathCompare.SIGNS[EMathComparison.NOT_APPROXIMATELY.ordinal()] = new char[] {
        '&', '#', 'x', '2', '2', '4', '9', ';' };
    _XHTML10MathCompare.SIGNS[EMathComparison.NOT_APPROXIMATELY_EQUAL
        .ordinal()] = new char[] { '&', '#', 'x', '2', '2', '4', '7', ';' };
    _XHTML10MathCompare.SIGNS[EMathComparison.NOT_PROPROTIONAL_TO
        .ordinal()] = new char[] { '&', 'n', 'o', 't', ';', '&', 'p', 'r',
        'o', 'p', ';' };
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
    _XHTML10MathCompare.SIGNS[EMathComparison.DEFINED_AS.ordinal()] = new char[] {
        '&', '#', 'x', '2', '2', '5', 'c', ';' };
    _XHTML10MathCompare.SIGNS[EMathComparison.APPROXIMATED_AS.ordinal()] = new char[] {
        '&', '#', 'x', '2', '2', '5', '9', ';' };
    _XHTML10MathCompare.SIGNS[EMathComparison.PRECEDES.ordinal()] = new char[] {
        '&', '#', 'x', '2', '2', '7', 'a', ';' };
    _XHTML10MathCompare.SIGNS[EMathComparison.NOT_PRECEDES.ordinal()] = new char[] {
        '&', '#', 'x', '2', '2', '8', '0', ';' };
    _XHTML10MathCompare.SIGNS[EMathComparison.PRECEDES_OR_EQUAL.ordinal()] = new char[] {
        '&', '#', 'x', '2', '2', '7', 'c', ';' };
    _XHTML10MathCompare.SIGNS[EMathComparison.NOT_PRECEDES_OR_EQUAL
        .ordinal()] = new char[] { '&', '#', 'x', '2', '2', 'e', '0', ';' };
    _XHTML10MathCompare.SIGNS[EMathComparison.SUCCEEDS.ordinal()] = new char[] {
        '&', '#', 'x', '2', '2', '7', 'b', ';' };
    _XHTML10MathCompare.SIGNS[EMathComparison.NOT_SUCCEEDS.ordinal()] = new char[] {
        '&', '#', 'x', '2', '2', '8', '1', ';' };
    _XHTML10MathCompare.SIGNS[EMathComparison.SUCCEEDS_OR_EQUAL.ordinal()] = new char[] {
        '&', '#', 'x', '2', '2', '7', 'd', ';' };
    _XHTML10MathCompare.SIGNS[EMathComparison.NOT_SUCCEEDS_OR_EQUAL
        .ordinal()] = new char[] { '&', '#', 'x', '2', '2', 'e', '1', ';' };
    _XHTML10MathCompare.SIGNS[EMathComparison.SIMILAR.ordinal()] = new char[] {
        '&', '#', 'x', '2', '2', '3', 'c', ';' };
    _XHTML10MathCompare.SIGNS[EMathComparison.NOT_SIMILAR.ordinal()] = new char[] {
        '&', '#', 'x', '2', '2', '4', '1', ';' };

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
