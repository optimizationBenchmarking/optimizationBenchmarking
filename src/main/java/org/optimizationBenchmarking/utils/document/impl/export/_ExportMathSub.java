package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathSub;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical sub function in an export document */
final class _ExportMathSub extends MathSub {
  /**
   * Create a new mathematical function
   *
   * @param owner
   *          the owning text
   */
  _ExportMathSub(final BasicMath owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected final void render(final ITextOutput out, final char[][] data,
      final int size) {
    boolean done;

    done = false;
    for (final char[] chrs : data) {
      if (done) {
        out.append('-');
      } else {
        done = true;
      }
      out.append('(');
      out.append(chrs);
      out.append(')');
    }
  }
}
