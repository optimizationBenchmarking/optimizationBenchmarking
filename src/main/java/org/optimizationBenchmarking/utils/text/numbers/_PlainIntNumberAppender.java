package org.optimizationBenchmarking.utils.text.numbers;

import org.optimizationBenchmarking.utils.text.ETextCase;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * An internal number appender which, well, directly appends the integer
 * numbers to the output.
 */
abstract class _PlainIntNumberAppender extends NumberAppender {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** create */
  _PlainIntNumberAppender() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final void appendTo(final long v, final ETextCase textCase,
      final ITextOutput textOut) {
    textOut.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public final String toString(final long v, final ETextCase textCase) {
    return Long.toString(v);
  }

  /** {@inheritDoc} */
  @Override
  public final void appendTo(final int v, final ETextCase textCase,
      final ITextOutput textOut) {
    textOut.append(v);
  }

  /** {@inheritDoc} */
  @Override
  public final String toString(final int v, final ETextCase textCase) {
    return Integer.toString(v);
  }

}
