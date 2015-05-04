package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.TableHeader;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** a header of a table in a LaTeX document */
final class _LaTeXTableHeader extends TableHeader {

  /** begin the tabular */
  private static final char[] TABULAR_BEGIN = { '\\', 'b', 'e', 'g', 'i',
      'n', '{', 't', 'a', 'b', 'u', 'l', 'a', 'r', '}', '{' };

  /**
   * Create a header of a table
   *
   * @param owner
   *          the owning table
   */
  _LaTeXTableHeader(final _LaTeXTable owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    final ITextOutput out;

    super.onOpen();

    out = this.getTextOutput();

    LaTeXDriver._endLine(out);
    out.append(_LaTeXTableHeader.TABULAR_BEGIN);

    _LaTeXTable._appendCellDef(this.getOwner(), out);
    LaTeXDriver._endCommandLine(out);

    out.append(_LaTeXTable.HLINE);
    LaTeXDriver._endLine(out);
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
