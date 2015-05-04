package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.TableBody;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** a body of a table in a LaTeX document */
final class _LaTeXTableBody extends TableBody {
  /**
   * Create a body of a table
   *
   * @param owner
   *          the owning table
   */
  _LaTeXTableBody(final _LaTeXTable owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    final ITextOutput out;

    if (this.getRowCount() > 0) {
      out = this.getTextOutput();
      out.append(_LaTeXTable.HLINE);
      LaTeXDriver._endLine(out);
    }
    super.onClose();
  }
}
