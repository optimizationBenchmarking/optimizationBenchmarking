package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathNAryFunction;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical n-ary function in a LaTeX document */
final class _LaTeXMathNAryFunction extends MathNAryFunction {
  /** the begin n-ary */
  private static final char[] N_ARY_BEGIN = { '{', '\\', 'm', 'a', 't',
      'h', 'r', 'm', '{', };

  /**
   * Create a new mathematical function
   *
   * @param owner
   *          the owning text
   * @param name
   *          the function name
   * @param minArity
   *          the minimum number of arguments
   * @param maxArity
   *          the maximum number of arguments
   */
  _LaTeXMathNAryFunction(final BasicMath owner, final String name,
      final int minArity, final int maxArity) {
    super(owner, name.replace(' ', '~'), minArity, maxArity);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void render(final ITextOutput out, final char[][] data,
      final int size) {
    final boolean needsBraces;
    final int braceIndex;
    boolean first;

    out.append(_LaTeXMathNAryFunction.N_ARY_BEGIN);
    out.append(this.getName());
    out.append('}');

    needsBraces = this.needsBraces();
    if (needsBraces) {
      braceIndex = this.getBraceIndex();
      out.append(_LaTeXMathInBraces.BRACE_BEGIN[braceIndex
          % _LaTeXMathInBraces.BRACE_BEGIN.length]);
    } else {
      out.append('\\');
      out.append(',');
      braceIndex = 0;
    }

    first = true;
    for (final char[] param : data) {
      if (first) {
        first = false;
      } else {
        out.append(',');
      }
      out.append(param);
    }

    if (needsBraces) {
      out.append(_LaTeXMathInBraces.BRACE_END[braceIndex
          % _LaTeXMathInBraces.BRACE_END.length]);
    }

    out.append('}');
  }
}
