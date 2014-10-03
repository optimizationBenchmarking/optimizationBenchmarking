package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.CodeCaption;

/** the caption of code object in a XHTML document */
final class _XHTML10CodeCaption extends CodeCaption {
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
}
