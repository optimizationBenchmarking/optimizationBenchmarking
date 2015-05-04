package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.TableHeader;

/** a header of a table in a XHTML document */
final class _XHTML10TableHeader extends TableHeader {
  /** the start of the table head */
  private static final char[] TAB_HEAD_BEGIN = { '<', 't', 'h', 'e', 'a',
    'd', ' ', 'c', 'l', 'a', 's', 's', '=', '"', 't', 'a', 'b', '"', '>' };
  /** the end of the table head */
  private static final char[] TAB_HEAD_END = { '<', '/', 't', 'h', 'e',
    'a', 'd', '>' };

  /**
   * Create a header of a table
   *
   * @param owner
   *          the owning table
   */
  _XHTML10TableHeader(final _XHTML10Table owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    super.onOpen();
    this.getTextOutput().append(_XHTML10TableHeader.TAB_HEAD_BEGIN);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    this.getTextOutput().append(_XHTML10TableHeader.TAB_HEAD_END);
    super.onClose();
  }
}
