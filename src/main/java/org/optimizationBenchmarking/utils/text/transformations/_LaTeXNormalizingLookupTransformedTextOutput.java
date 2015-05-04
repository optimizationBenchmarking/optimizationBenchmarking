package org.optimizationBenchmarking.utils.text.transformations;

import java.text.Normalizer;

import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * A normalizing lookup transformator for LaTeX.
 *
 * @author Thomas Weise
 */
class _LaTeXNormalizingLookupTransformedTextOutput extends
_NormalizingLookupTransformedTextOutput {

  /**
   * create the transformed text output
   *
   * @param out
   *          the wrapped output
   * @param data
   *          the lookup data
   * @param state
   *          the character state
   * @param form
   *          the normalizer form
   */
  _LaTeXNormalizingLookupTransformedTextOutput(final ITextOutput out,
      final char[][] data, final byte[] state, final Normalizer.Form form) {
    super(out, data, state, form);
  }

  /** {@inheritDoc} */
  @Override
  public final void appendNonBreakingSpace() {
    this.m_out.append('~');
  }

}