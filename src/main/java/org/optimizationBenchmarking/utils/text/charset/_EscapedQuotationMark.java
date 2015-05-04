package org.optimizationBenchmarking.utils.text.charset;

/**
 * the internal base class for escaped quotation marks
 */
public final class _EscapedQuotationMark extends QuotationMark {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the escape sequence */
  transient final char m_afterBackslash;

  /**
   * create
   *
   * @param chr
   *          the character
   * @param isOpening
   *          Can this enclosure end open the enclosure?
   * @param isClosing
   *          Can this enclosure end close the enclosure?
   * @param afterBackslash
   *          the character after the afterBackslash
   */
  _EscapedQuotationMark(final int chr, final boolean isOpening,
      final boolean isClosing, final char afterBackslash) {
    super(chr, isOpening, isClosing);
    this.m_afterBackslash = afterBackslash;
  }

  /** {@inheritDoc} */
  @Override
  public final String getEscapeSequence() {
    return ("\\" + this.m_afterBackslash); //$NON-NLS-1$
  }
}
