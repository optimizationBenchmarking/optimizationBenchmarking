package org.optimizationBenchmarking.utils.text.textOutput;

/**
 * A version of the text output wrapped around an
 * {@link java.lang.Appendable}.
 */
final class _AppendableWrappedTextOutput extends
    _AppendableWrappedTextOutputBase<Appendable> {

  /**
   * Create a wrapped text output
   * 
   * @param out
   *          the output
   */
  _AppendableWrappedTextOutput(final Appendable out) {
    super(out);
  }
}
