package org.optimizationBenchmarking.utils.text.transformations;

import org.optimizationBenchmarking.utils.text.textOutput.AbstractTextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/**
 * An implementation of the text output which forwards all text to an
 * underlying output while applying a specific text transformation.
 *
 * @author Thomas Weise
 */
class _TransformedTextOutput extends AbstractTextOutput {

  /** the text output we are actually writing to */
  final ITextOutput m_out;

  /**
   * create the transformed text output
   *
   * @param out
   *          the wrapped output
   */
  _TransformedTextOutput(final ITextOutput out) {
    super();
    this.m_out = out;
  }

  /** {@inheritDoc} */
  @Override
  public AbstractTextOutput append(final CharSequence csq,
      final int start, final int end) {
    this.m_out.append(csq, start, end);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public AbstractTextOutput append(final char c) {
    this.m_out.append(c);
    return this;
  }

  /** {@inheritDoc} */
  @Override
  public void append(final String s, final int start, final int end) {
    this.m_out.append(s, start, end);
  }

  /** {@inheritDoc} */
  @Override
  public void append(final char[] chars, final int start, final int end) {
    this.m_out.append(chars, start, end);
  }

  /** {@inheritDoc} */
  @Override
  public final void append(final Object o) {
    this.append(String.valueOf(o));
  }

  /**
   * This method is called when an unknown character is encountered. By
   * default it throws an {@link java.lang.UnsupportedOperationException}.
   *
   * @param ch
   *          the character
   * @throws UnsupportedOperationException
   *           by default
   */
  final void _onUnknown(final char ch) {
    throw new UnsupportedOperationException(//
        "No mapping known for character '" + //$NON-NLS-1$
            String.valueOf(ch) + "' (0x" + //$NON-NLS-1$
            Integer.toHexString(ch) + ")."); //$NON-NLS-1$

  }
}
