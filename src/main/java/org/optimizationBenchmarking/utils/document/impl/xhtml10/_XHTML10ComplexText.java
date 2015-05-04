package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.ComplexText;
import org.optimizationBenchmarking.utils.document.impl.abstr.DocumentElement;

/** a complex text in a XHTML document */
final class _XHTML10ComplexText extends ComplexText {
  /**
   * create the complex text
   *
   * @param owner
   *          the owning element
   */
  _XHTML10ComplexText(final DocumentElement owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  public synchronized final void appendLineBreak() {
    this.getTextOutput().append(XHTML10Driver.BR);
  }
}
