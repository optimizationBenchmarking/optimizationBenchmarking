package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentFooter;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** the XHTML document footer */
final class _XHTML10DocumentFooter extends DocumentFooter {

  /** the html end */
  private static final char[] BODY_HTML_END = { '<', '/', 'b', 'o', 'd',
      'y', '>', '<', '/', 'h', 't', 'm', 'l', '>' };

  /**
   * Create a document footer
   * 
   * @param owner
   *          the owning document
   */
  _XHTML10DocumentFooter(final _XHTML10Document owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    final ITextOutput out;

    out = this.getTextOutput();

    out.append(XHTML10Driver.DIV_END);
    out.append(XHTML10Driver.DIV_END);
    out.append(BODY_HTML_END);
    super.onClose();
  }
}
