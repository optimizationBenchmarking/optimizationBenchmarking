package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.SectionBody;

/** a section body of a section in a XHTML document */
final class _XHTML10SectionBody extends SectionBody {
  /** the start of the section body div */
  private static final char[] SECTION_BODY_DIV_BEGIN = { '<', 'd', 'i',
      'v', ' ', 'c', 'l', 'a', 's', 's', '=', '"', 's', 'e', 'c', 't',
      'i', 'o', 'n', 'B', 'o', 'd', 'y', '"', '>' };

  /**
   * create the section body
   *
   * @param owner
   *          the owner
   */
  _XHTML10SectionBody(final _XHTML10Section owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    super.onOpen();
    this.getTextOutput()
        .append(_XHTML10SectionBody.SECTION_BODY_DIV_BEGIN);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    this.getTextOutput().append(XHTML10Driver.DIV_END);
    super.onClose();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void appendLineBreak() {
    this.assertNoChildren();
    this.getTextOutput().append(XHTML10Driver.BR);
  }
}
