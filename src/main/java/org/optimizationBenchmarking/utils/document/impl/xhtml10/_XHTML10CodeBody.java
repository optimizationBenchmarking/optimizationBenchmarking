package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.CodeBody;

/** the body of code object in a XHTML document */
final class _XHTML10CodeBody extends CodeBody {
  /** the start of the code tr body */
  private static final char[] CODE_TR_TD_BODY_BEGIN = { '<', 't', 'r',
    ' ', 'c', 'l', 'a', 's', 's', '=', '"', 'c', 'o', 'd', 'e', '"',
    '>', '<', 't', 'd', ' ', 'c', 'l', 'a', 's', 's', '=', '"', 'c',
    'o', 'd', 'e', 'B', 'o', 'd', 'y', '"', '>', '<', 'p', 'r', 'e', '>' };
  /** the start of the code body td */
  private static final char[] CODE_BODY_END = { '<', '/', 'p', 'r', 'e',
    '>', '<', '/', 't', 'd', '>', '<', '/', 't', 'r', '>' };

  /**
   * create the code body
   *
   * @param owner
   *          the owning FSM
   */
  _XHTML10CodeBody(final _XHTML10Code owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void appendLineBreak() {
    this.getTextOutput().append(XHTML10Driver.BR, 0, 5);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    super.onOpen();
    this.getTextOutput().append(_XHTML10CodeBody.CODE_TR_TD_BODY_BEGIN);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    this.getTextOutput().append(_XHTML10CodeBody.CODE_BODY_END);
    super.onClose();
  }
}
