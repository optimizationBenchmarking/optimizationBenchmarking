package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentPart;
import org.optimizationBenchmarking.utils.document.impl.abstr.Section;
import org.optimizationBenchmarking.utils.document.spec.ILabel;

/** a section in a XHTML document */
final class _XHTML10Section extends Section {
  /** the start of the section div */
  private static final char[] SECTION_DIV_BEGIN = { '<', 'd', 'i', 'v',
    ' ', 'c', 'l', 'a', 's', 's', '=', '"', 's', 'e', 'c', 't', 'i',
    'o', 'n', '"', '>' };

  /**
   * Create a new section
   *
   * @param owner
   *          the owning text
   * @param useLabel
   *          the label to use
   * @param index
   *          the section index
   */
  _XHTML10Section(final DocumentPart owner, final ILabel useLabel,
      final int index) {
    super(owner, useLabel, index);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    super.onOpen();
    this.getTextOutput().append(_XHTML10Section.SECTION_DIV_BEGIN);
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    this.getTextOutput().append(XHTML10Driver.DIV_END);
    super.onClose();
  }
}
