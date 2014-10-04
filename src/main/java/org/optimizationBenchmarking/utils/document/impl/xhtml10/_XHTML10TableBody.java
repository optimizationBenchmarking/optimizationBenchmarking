package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.TableBody;

/** a body of a table in a XHTML document */
final class _XHTML10TableBody extends TableBody {
  /** the start of table body */
  private static final char[] TAB_BODY_BEGIN = { '<', 't', 'b', 'o', 'd',
      'y', ' ', 'c', 'l', 'a', 's', 's', '=', '"', 't', 'a', 'b', '"', '>' };
  /** the end of the table body */
  private static final char[] TAB_BODY_END = { '<', '/', 't', 'b', 'o',
      'd', 'y', '>' };

  /**
   * Create a body of a table
   * 
   * @param owner
   *          the owning table
   */
  _XHTML10TableBody(final _XHTML10Table owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    super.onOpen();
    this.getTextOutput().append(_XHTML10TableBody.TAB_BODY_BEGIN);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    this.getTextOutput().append(_XHTML10TableBody.TAB_BODY_END);
    super.onClose();
  }
}
