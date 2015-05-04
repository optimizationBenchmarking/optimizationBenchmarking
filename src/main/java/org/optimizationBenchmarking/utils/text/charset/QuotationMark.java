package org.optimizationBenchmarking.utils.text.charset;

/**
 * the internal base class for enclosures
 */
public class QuotationMark extends
    _EnclosureEnd<QuotationMarks, QuotationMark> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * create
   *
   * @param chr
   *          the character
   * @param isOpening
   *          Can this enclosure end open the enclosure?
   * @param isClosing
   *          Can this enclosure end close the enclosure?
   */
  QuotationMark(final int chr, final boolean isOpening,
      final boolean isClosing) {
    super(chr, isOpening, isClosing);
  }

  /**
   * Get the dash count of this quotation mark
   *
   * @return the dash count of this quotation mark
   */
  public final int getDashCount() {
    return this.m_owner.m_dashCount;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean canEndWith(final Char other) {
    final QuotationMark b;
    if (this.m_isOpening) {
      if (other instanceof QuotationMark) {
        b = ((QuotationMark) other);
        if (b.m_isClosing) {
          return ((b.m_otherEnd == this) || (this.m_otherEnd == b)
              || (this.m_owner == b.m_owner) || (this.m_owner.m_dashCount == b.m_owner.m_dashCount));
        }
      }
    }

    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean canStartWith(final Char other) {
    final QuotationMark b;
    if (this.m_isClosing) {
      if (other instanceof QuotationMark) {
        b = ((QuotationMark) other);
        if (b.m_isOpening) {
          return ((b.m_otherEnd == this) || (this.m_otherEnd == b)
              || (this.m_owner == b.m_owner) || (this.m_owner.m_dashCount == b.m_owner.m_dashCount));
        }
      }
    }

    return false;
  }
}
