package org.optimizationBenchmarking.utils.document.impl.export;

import org.optimizationBenchmarking.utils.document.impl.abstr.BasicMath;
import org.optimizationBenchmarking.utils.document.impl.abstr.MathInBraces;

/** an mathematical in-braces element of a section in an export document */
final class _ExportMathInBraces extends MathInBraces {
  /**
   * create the mathematical in-braces element
   *
   * @param owner
   *          the owner
   */
  _ExportMathInBraces(final BasicMath owner) {
    super(owner);
    this.open();
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onOpen() {
    super.onOpen();
    this.getTextOutput().append('(');
  }

  /** {@inheritDoc} */
  @Override
  protected synchronized final void onClose() {
    this.getTextOutput().append(')');
    super.onClose();
  }
}
