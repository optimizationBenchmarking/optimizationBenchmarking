package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathInBraces;

/** an mathematical in-braces element of a section in a LaTeX document */
final class _LaTeXMathInBraces extends MathInBraces {

  /** the begin brace */
  static final char[][] BRACE_BEGIN = {//
  { '{', '\\', 'l', 'e', 'f', 't', '(', '{', },//
      { '{', '\\', 'l', 'e', 'f', 't', '[', '{', }, };

  /** the end brace */
  static final char[][] BRACE_END = {//
  { '}', '\\', 'r', 'i', 'g', 'h', 't', ')', '}' },//
      { '}', '\\', 'r', 'i', 'g', 'h', 't', ']', '}' },//
  };

  /**
   * create the mathematical in-braces element
   *
   * @param owner
   *          the owner
   */
  _LaTeXMathInBraces(final BasicMath owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    super.onOpen();
    this.getTextOutput().append(
        _LaTeXMathInBraces.BRACE_BEGIN[this.getBraceIndex()
            % _LaTeXMathInBraces.BRACE_BEGIN.length]);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    this.getTextOutput().append(
        _LaTeXMathInBraces.BRACE_END[this.getBraceIndex()
            % _LaTeXMathInBraces.BRACE_END.length]);
    super.onClose();
  }
}
