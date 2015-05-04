package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.Code;
import org.optimizationBenchmarking.utils.document.impl.abstr.CodeCaption;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** the caption of code object in a XHTML document */
final class _XHTML10CodeCaption extends CodeCaption {

  /** the start of the code tr caption */
  private static final char[] CODE_TR_TD_CAPTION_BEGIN = { '<', 't', 'r',
    ' ', 'c', 'l', 'a', 's', 's', '=', '"', 'c', 'o', 'd', 'e', '"',
    '>', '<', 't', 'd', ' ', 'c', 'l', 'a', 's', 's', '=', '"', 'c',
    'o', 'd', 'e', 'C', 'a', 'p', 't', 'i', 'o', 'n', '"', '>', '<',
    's', 'p', 'a', 'n', ' ', 'c', 'l', 'a', 's', 's', '=', '"', 'c',
    'a', 'p', 't', 'i', 'o', 'n', 'T', 'i', 't', 'l', 'e', '"', '>',
    'L', 'i', 's', 't', 'i', 'n', 'g', '&', 'n', 'b', 's', 'p', ';' };

  /**
   * create the code caption
   *
   * @param owner
   *          the owning FSM
   */
  _XHTML10CodeCaption(final _XHTML10Code owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void appendLineBreak() {
    this.getTextOutput().append(' ');
  }

  /** {@inheritDoc} */
  @SuppressWarnings("resource")
  @Override
  protected synchronized final void onOpen() {
    final ITextOutput out;
    final Code code;

    super.onOpen();

    out = this.getTextOutput();
    out.append(_XHTML10CodeCaption.CODE_TR_TD_CAPTION_BEGIN);
    code = this.getOwner();
    out.append(code.getGlobalID());
    out.append(XHTML10Driver.SPAN_END_NBSP);
    XHTML10Driver._label(code.getLabel(), out);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    final ITextOutput out;

    out = this.getTextOutput();
    out.append(_XHTML10Table.TD_END);
    out.append(_XHTML10Table.TR_END);

    super.onClose();
  }
}
