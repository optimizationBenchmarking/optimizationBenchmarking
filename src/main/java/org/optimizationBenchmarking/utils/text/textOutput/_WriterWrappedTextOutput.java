package org.optimizationBenchmarking.utils.text.textOutput;

import java.io.Writer;

/** A version of the text output wrapped around an {@link java.io.Writer}. */
final class _WriterWrappedTextOutput extends
    _WriterWrappedTextOutputBase<Writer> {

  /**
   * Create a wrapped text output
   *
   * @param out
   *          the output
   */
  _WriterWrappedTextOutput(final Writer out) {
    super(out);
  }
}
