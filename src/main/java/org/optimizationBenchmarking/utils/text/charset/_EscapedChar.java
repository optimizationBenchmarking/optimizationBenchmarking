package org.optimizationBenchmarking.utils.text.charset;

/**
 * The base class for characters
 */
final class _EscapedChar extends Char {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the escape sequence */
  transient final char m_afterBackslash;

  /**
   * create
   *
   * @param chr
   *          the character
   * @param afterBackslash
   *          the character after the afterBackslash
   */
  _EscapedChar(final int chr, final char afterBackslash) {
    super(chr);
    this.m_afterBackslash = afterBackslash;
  }

  /** {@inheritDoc} */
  @Override
  public final String getEscapeSequence() {
    return ("\\" + this.m_afterBackslash); //$NON-NLS-1$
  }
}
