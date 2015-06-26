package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathCompare;
import org.optimizationBenchmarking.utils.document.spec.EMathComparison;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical compare function in a LaTeX document */
final class _LaTeXMathCompare extends MathCompare {
  /** the signs */
  private static final char[][] SIGNS;

  /** the needs-ams-symb tags */
  private static final boolean[] NEEDS_AMSSYMB;

  static {
    SIGNS = new char[EMathComparison.INSTANCES.size()][];

    _LaTeXMathCompare.SIGNS[EMathComparison.VERY_MUCH_LESS.ordinal()] = new char[] {
        ' ', '\\', 'l', 'l', 'l', ' ', };
    _LaTeXMathCompare.SIGNS[EMathComparison.MUCH_LESS.ordinal()] = new char[] {
        ' ', '\\', 'l', 'l', ' ', };
    _LaTeXMathCompare.SIGNS[EMathComparison.LESS.ordinal()] = new char[] {
        ' ', '<', ' ' };
    _LaTeXMathCompare.SIGNS[EMathComparison.LESS_OR_EQUAL.ordinal()] = new char[] {
        ' ', '\\', 'l', 'e', 'q', ' ', };
    _LaTeXMathCompare.SIGNS[EMathComparison.GREATER_OR_EQUAL.ordinal()] = new char[] {
        ' ', '\\', 'g', 'e', 'q', ' ', };
    _LaTeXMathCompare.SIGNS[EMathComparison.GREATER.ordinal()] = new char[] {
        ' ', '>', ' ' };
    _LaTeXMathCompare.SIGNS[EMathComparison.MUCH_GREATER.ordinal()] = new char[] {
        ' ', '\\', 'g', 'g', ' ', };
    _LaTeXMathCompare.SIGNS[EMathComparison.VERY_MUCH_GREATER.ordinal()] = new char[] {
        ' ', '\\', 'g', 'g', 'g', ' ', };
    _LaTeXMathCompare.SIGNS[EMathComparison.EQUAL.ordinal()] = new char[] {
        ' ', '=', ' ' };
    _LaTeXMathCompare.SIGNS[EMathComparison.EQUIVALENT.ordinal()] = new char[] {
        ' ', '\\', 'e', 'q', 'u', 'i', 'v', ' ', };
    _LaTeXMathCompare.SIGNS[EMathComparison.APPROXIMATELY.ordinal()] = new char[] {
        ' ', '\\', 'a', 'p', 'p', 'r', 'o', 'x', ' ', };
    _LaTeXMathCompare.SIGNS[EMathComparison.APPROXIMATELY_EQUAL.ordinal()] = new char[] {
        ' ', '\\', 'a', 'p', 'p', 'r', 'o', 'x', 'e', 'q', ' ', };
    _LaTeXMathCompare.SIGNS[EMathComparison.PROPROTIONAL_TO.ordinal()] = new char[] {
        ' ', '\\', 'p', 'r', 'o', 'p', 't', 'o', ' ', };
    _LaTeXMathCompare.SIGNS[EMathComparison.NOT_EQUAL.ordinal()] = new char[] {
        ' ', '\\', 'n', 'e', 'q', ' ', };
    _LaTeXMathCompare.SIGNS[EMathComparison.NOT_EQUIVALENT.ordinal()] = new char[] {
        ' ', '\\', 'n', 'o', 't', '\\', 'e', 'q', 'u', 'i', 'v', ' ' };
    _LaTeXMathCompare.SIGNS[EMathComparison.NOT_APPROXIMATELY.ordinal()] = new char[] {
        ' ', '\\', 'n', 'o', 't', '\\', 'a', 'p', 'p', 'r', 'o', 'x', ' ', };
    _LaTeXMathCompare.SIGNS[EMathComparison.NOT_APPROXIMATELY_EQUAL
        .ordinal()] = new char[] { ' ', '\\', 'n', 'o', 't', '\\', 'a',
        'p', 'p', 'r', 'o', 'x', 'e', 'q', ' ', };
    _LaTeXMathCompare.SIGNS[EMathComparison.ELEMENT_OF.ordinal()] = new char[] {
        ' ', '\\', 'i', 'n', ' ', };
    _LaTeXMathCompare.SIGNS[EMathComparison.NOT_ELEMENT_OF.ordinal()] = new char[] {
        ' ', '\\', 'n', 'o', 't', 'i', 'n', ' ', };
    _LaTeXMathCompare.SIGNS[EMathComparison.SUBSET_OF.ordinal()] = new char[] {
        ' ', '\\', 's', 'u', 'b', 's', 'e', 't', ' ', };
    _LaTeXMathCompare.SIGNS[EMathComparison.NOT_SUBSET_OF.ordinal()] = new char[] {
        ' ', '\\', 'n', 'o', 't', '\\', 's', 'u', 'b', 's', 'e', 't', ' ', };
    _LaTeXMathCompare.SIGNS[EMathComparison.SUBSET_OF_OR_EQUAL.ordinal()] = new char[] {
        ' ', '\\', 's', 'u', 'b', 's', 'e', 't', 'e', 'q', ' ', };
    _LaTeXMathCompare.SIGNS[EMathComparison.NOT_SUBSET_OF_OR_EQUAL
        .ordinal()] = new char[] { ' ', '\\', 'n', 's', 'u', 'b', 's',
        'e', 't', 'e', 'q', ' ', };
    _LaTeXMathCompare.SIGNS[EMathComparison.DEFINED_AS.ordinal()] = new char[] {
        ' ', '\\', 't', 'r', 'i', 'a', 'n', 'g', 'l', 'e', 'q', ' ', };
    _LaTeXMathCompare.SIGNS[EMathComparison.APPROXIMATED_AS.ordinal()] = new char[] {
        ' ', '\\', 's', 't', 'a', 'c', 'k', 'r', 'e', 'l', '{', '\\', 'l',
        'a', 'n', 'd', '}', '{', '=', '}', ' ', };
    _LaTeXMathCompare.SIGNS[EMathComparison.PRECEDES.ordinal()] = new char[] {
        ' ', '\\', 'p', 'r', 'e', 'c', ' ', };
    _LaTeXMathCompare.SIGNS[EMathComparison.NOT_PRECEDES.ordinal()] = new char[] {
        ' ', '\\', 'n', 'p', 'r', 'e', 'c', ' ', };
    _LaTeXMathCompare.SIGNS[EMathComparison.PRECEDES_OR_EQUAL.ordinal()] = new char[] {
        ' ', '\\', 'p', 'r', 'e', 'c', 'e', 'q', ' ', };
    _LaTeXMathCompare.SIGNS[EMathComparison.NOT_PRECEDES_OR_EQUAL
        .ordinal()] = new char[] { ' ', '\\', 'n', 'p', 'r', 'e', 'c',
        'e', 'q', ' ', };
    _LaTeXMathCompare.SIGNS[EMathComparison.SUCCEEDS.ordinal()] = new char[] {
        ' ', '\\', 's', 'u', 'c', 'c', ' ', };
    _LaTeXMathCompare.SIGNS[EMathComparison.NOT_SUCCEEDS.ordinal()] = new char[] {
        ' ', '\\', 'n', 's', 'u', 'c', 'c', ' ', };
    _LaTeXMathCompare.SIGNS[EMathComparison.SUCCEEDS_OR_EQUAL.ordinal()] = new char[] {
        ' ', '\\', 's', 'u', 'c', 'c', 'e', 'q', ' ', };
    _LaTeXMathCompare.SIGNS[EMathComparison.NOT_SUCCEEDS_OR_EQUAL
        .ordinal()] = new char[] { ' ', '\\', 'n', 's', 'u', 'c', 'c',
        'e', 'q', ' ', };
    _LaTeXMathCompare.SIGNS[EMathComparison.SIMILAR.ordinal()] = new char[] {
        ' ', '\\', 's', 'i', 'm', ' ', };
    _LaTeXMathCompare.SIGNS[EMathComparison.NOT_SIMILAR.ordinal()] = new char[] {
        ' ', '\\', 'n', 's', 'i', 'm', ' ', };

    NEEDS_AMSSYMB = new boolean[EMathComparison.INSTANCES.size()];

    _LaTeXMathCompare.NEEDS_AMSSYMB[EMathComparison.VERY_MUCH_LESS
        .ordinal()] = true;
    _LaTeXMathCompare.NEEDS_AMSSYMB[EMathComparison.VERY_MUCH_GREATER
        .ordinal()] = true;
    _LaTeXMathCompare.NEEDS_AMSSYMB[EMathComparison.APPROXIMATELY_EQUAL
        .ordinal()] = true;
    _LaTeXMathCompare.NEEDS_AMSSYMB[EMathComparison.NOT_APPROXIMATELY_EQUAL
        .ordinal()] = true;
    _LaTeXMathCompare.NEEDS_AMSSYMB[EMathComparison.NOT_SUBSET_OF_OR_EQUAL
        .ordinal()] = true;
    _LaTeXMathCompare.NEEDS_AMSSYMB[EMathComparison.DEFINED_AS.ordinal()] = true;
    _LaTeXMathCompare.NEEDS_AMSSYMB[EMathComparison.NOT_PRECEDES.ordinal()] = true;
    _LaTeXMathCompare.NEEDS_AMSSYMB[EMathComparison.NOT_PRECEDES_OR_EQUAL
        .ordinal()] = true;
    _LaTeXMathCompare.NEEDS_AMSSYMB[EMathComparison.NOT_SUCCEEDS.ordinal()] = true;
    _LaTeXMathCompare.NEEDS_AMSSYMB[EMathComparison.NOT_SUCCEEDS_OR_EQUAL
        .ordinal()] = true;
    _LaTeXMathCompare.NEEDS_AMSSYMB[EMathComparison.NOT_SIMILAR.ordinal()] = true;

  }

  /**
   * Create a new mathematical function
   *
   * @param owner
   *          the owning text
   * @param cmp
   *          the comparator
   */
  _LaTeXMathCompare(final BasicMath owner, final EMathComparison cmp) {
    super(owner, cmp);
    this.open();
    if (_LaTeXMathCompare.NEEDS_AMSSYMB[cmp.ordinal()]) {
      ((LaTeXDocument) (this.getDocument()))._registerNeedsAMSSymb();
    }
  }

  /** {@inheritDoc} */
  @Override
  protected final void render(final ITextOutput out, final char[][] data,
      final int size) {
    out.append('{');
    out.append(data[0]);
    out.append(_LaTeXMathCompare.SIGNS[this.getComparison().ordinal()]);
    out.append(data[1]);
    out.append('}');
  }
}
