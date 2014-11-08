package org.optimizationBenchmarking.utils.text;

import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;

/** a character sequence based on a char array */
public final class CharArrayCharSequence implements CharSequence,
    ITextable {

  /** the data */
  private final char[] m_data;

  /** the offset */
  private final int m_start;

  /** the length */
  private final int m_length;

  /**
   * Instantiate
   * 
   * @param data
   *          the data
   * @param start
   *          the start
   * @param length
   *          the length
   */
  public CharArrayCharSequence(final char[] data, final int start,
      final int length) {
    super();
    this.m_data = data;
    if ((start >= data.length) || (start < 0)) {
      throw new IllegalArgumentException("Start index " + start + //$NON-NLS-1$
          " is out of the valid range [0," + //$NON-NLS-1$
          data.length + ")."); //$NON-NLS-1$
    }
    if ((length < 0) || ((data.length - start) < length)) {
      throw new IllegalArgumentException("Length " + length + //$NON-NLS-1$
          " is out of the valid range [0," + //$NON-NLS-1$
          (data.length - start) + "]."); //$NON-NLS-1$
    }
    this.m_start = start;
    this.m_length = length;
  }

  /**
   * Instantiate
   * 
   * @param data
   *          the data
   */
  public CharArrayCharSequence(final char[] data) {
    super();
    this.m_data = data;
    this.m_start = 0;
    this.m_length = data.length;
  }

  /** {@inheritDoc} */
  @Override
  public final int length() {
    return this.m_length;
  }

  /** {@inheritDoc} */
  @Override
  public final char charAt(final int index) {
    return this.m_data[this.m_start + index];
  }

  /** {@inheritDoc} */
  @Override
  public final CharSequence subSequence(final int start, final int end) {
    return new CharArrayCharSequence(this.m_data, (this.m_start + start),
        (end - start));
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return String.valueOf(this.m_data, this.m_start, this.m_length);
  }

  /** {@inheritDoc} */
  @Override
  public final void toText(final ITextOutput textOut) {
    final int len;
    len = this.m_length;
    if (len > 0) {
      textOut.append(this.m_data, this.m_start, (this.m_start + len));
    }
  }
}
