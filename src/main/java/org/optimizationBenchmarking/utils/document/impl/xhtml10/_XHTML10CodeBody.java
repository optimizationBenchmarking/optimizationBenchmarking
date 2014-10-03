package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.CodeBody;

/** the body of code object in a XHTML document */
final class _XHTML10CodeBody extends CodeBody {
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
}
