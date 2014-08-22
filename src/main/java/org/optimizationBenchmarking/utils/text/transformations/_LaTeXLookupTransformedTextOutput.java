package org.optimizationBenchmarking.utils.text.transformations;

import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A lookup text output for LaTeX.
 */
class _LaTeXLookupTransformedTextOutput extends
    _LookupTransformedTextOutput {

  /**
   * create the transformed text output
   * 
   * @param out
   *          the wrapped output
   * @param data
   *          the lookup data
   * @param state
   *          the character state
   */
  _LaTeXLookupTransformedTextOutput(final ITextOutput out,
      final char[][] data, final byte[] state) {
    super(out, data, state);
  }

  /** {@inheritDoc} */
  @Override
  public final void appendNonBreakingSpace() {
    this.m_out.append('~');
  }
}
