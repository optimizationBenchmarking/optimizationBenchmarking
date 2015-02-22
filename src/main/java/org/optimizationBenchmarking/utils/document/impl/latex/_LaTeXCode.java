package org.optimizationBenchmarking.utils.document.impl.latex;

import org.optimizationBenchmarking.utils.document.impl.abstr.Code;
import org.optimizationBenchmarking.utils.document.impl.abstr.Label;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** a code object in a LaTeX document */
final class _LaTeXCode extends Code {

  /** the listing begin */
  private static final char[] BEGIN_LISTING = { '\\', 'b', 'e', 'g', 'i',
      'n', '{', 'l', 's', 't', 'l', 'i', 's', 't', 'i', 'n', 'g', '}',
      '[', 'f', 'l', 'o', 'a', 't', '=', };

  /** the float placement */
  private static final char[] FLOAT_LISTING = { '*', 't', 'b', 'h', 'p', };

  /** the float label */
  private static final char[] FLOAT_LABEL = { ',', 'l', 'a', 'b', 'e',
      'l', '=' };

  /** the listing end */
  private static final char[] END_LISTING = { '\\', 'e', 'n', 'd', '{',
      'l', 's', 't', 'l', 'i', 's', 't', 'i', 'n', 'g', '}', };

  /**
   * create the code
   * 
   * @param owner
   *          the owning section body
   * @param index
   *          the table index in the owning section
   * @param useLabel
   *          the label to use
   * @param spansAllColumns
   *          does the table span all columns
   */
  _LaTeXCode(final _LaTeXSectionBody owner, final ILabel useLabel,
      final boolean spansAllColumns, final int index) {
    super(owner, useLabel, spansAllColumns, index);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    final ITextOutput out;
    final Label label;

    super.onOpen();

    out = this.getTextOutput();

    LaTeXDriver._endLine(out);
    LaTeXDriver._endLine(out);

    out.append(_LaTeXCode.BEGIN_LISTING);
    if (this.spansAllColumns()) {
      out.append(_LaTeXCode.FLOAT_LISTING);
    } else {
      out.append(_LaTeXCode.FLOAT_LISTING, 1,
          _LaTeXCode.FLOAT_LISTING.length);
    }

    label = this.getLabel();
    if (label != null) {
      out.append(_LaTeXCode.FLOAT_LABEL);
      out.append(label.getLabelMark());
    }

    ((_LaTeXDocument) (this.getDocument()))._registerCode();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    final ITextOutput out;

    out = this.getTextOutput();
    out.append(_LaTeXCode.END_LISTING);
    LaTeXDriver._endLine(out);
    LaTeXDriver._endLine(out);

    super.onClose();
  }
}
