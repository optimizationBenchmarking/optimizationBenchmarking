package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.Code;
import org.optimizationBenchmarking.utils.document.spec.ILabel;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** a code object in a XHTML document */
final class _XHTML10Code extends Code {

  /** the start of the code */
  private static final char[] CODE_DIV_BEGIN = { '<', 'd', 'i', 'v', ' ',
      'c', 'l', 'a', 's', 's', '=', '"', 'c', 'o', 'd', 'e', '"', '>', };

  /** the start of the code */
  private static final char[] CODE_TABLE_BEGIN = { '<', 't', 'a', 'b',
      'l', 'e', ' ', 'c', 'l', 'a', 's', 's', '=', '"', 'c', 'o', 'd',
      'e', '"', '>' };

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
  _XHTML10Code(final _XHTML10SectionBody owner, final ILabel useLabel,
      final boolean spansAllColumns, final int index) {
    super(owner, useLabel, spansAllColumns, index);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    final ITextOutput out;

    super.onOpen();

    out = this.getTextOutput();
    out.append(_XHTML10Code.CODE_DIV_BEGIN);
    out.append(_XHTML10Code.CODE_TABLE_BEGIN);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    final ITextOutput out;

    out = this.getTextOutput();
    out.append(_XHTML10Table.TABLE_END);
    out.append(XHTML10Driver.DIV_END);

    super.onClose();
  }
}
