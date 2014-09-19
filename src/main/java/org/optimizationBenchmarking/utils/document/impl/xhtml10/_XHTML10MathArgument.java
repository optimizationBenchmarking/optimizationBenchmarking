package org.optimizationBenchmarking.utils.document.impl.xhtml10;

import org.optimizationBenchmarking.utils.document.impl.abstr.MathArgument;

/** an mathematical function argument in a XHTML document */
final class _XHTML10MathArgument extends MathArgument {
  /**
   * Create a new mathematical function argument
   * 
   * @param owner
   *          the owning text
   */
  _XHTML10MathArgument(final _XHTML10MathFunction owner) {
    super(owner);
    this.open();
  }
}
