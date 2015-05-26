package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathAdd;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** an mathematical add function in an export document */
final class _ExportMathAdd extends MathAdd {

  /**
   * Create a new mathematical function
   *
   * @param owner
   *          the owning text
   */
  _ExportMathAdd(final BasicMath owner) {
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
        out.append('+');
      } else {
        done = true;
      }
      out.append('(');
      out.append(chrs);
      out.append(')');
    }
  }
}
