package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.Section;
import org.optimizationBenchmarking.utils.document.impl.abstr.SectionTitle;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** a section title of a section in a XHTML document */
final class _XHTML10SectionTitle extends SectionTitle {
  /** the start of the section head div */
  private static final char[] SECTION_HEAD_DIV_BEGIN = { '<', 'd', 'i',
      'v', ' ', 'c', 'l', 'a', 's', 's', '=', '"', 's', 'e', 'c', 't',
      'i', 'o', 'n', 'H', 'e', 'a', 'd', '"', '>' };

  /**
   * create the section title
   * 
   * @param owner
   *          the owner
   */
  _XHTML10SectionTitle(final _XHTML10Section owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @SuppressWarnings("resource")
  @Override
  protected synchronized final void onOpen() {
    final int i;
    final ITextOutput out;
    final Section o;

    super.onOpen();

    out = this.getTextOutput();
    out.append(_XHTML10SectionTitle.SECTION_HEAD_DIV_BEGIN);

    o = this.getOwner();
    i = Math.min(o.getDepth(), (XHTML10Driver.HEADLINE_BEGIN.length - 1));
    out.append(XHTML10Driver.HEADLINE_BEGIN[i]);
    out.append(o.getGlobalID());

    out.append(XHTML10Driver.NBSP);

    XHTML10Driver._label(o.getLabel(), out);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    final int i;
    final ITextOutput out;

    out = this.getTextOutput();

    i = Math.min(this.getOwner().getDepth(),
        (XHTML10Driver.HEADLINE_END.length - 1));
    out.append(XHTML10Driver.HEADLINE_END[i]);
    out.append(XHTML10Driver.DIV_END);
    super.onClose();
  }
}
