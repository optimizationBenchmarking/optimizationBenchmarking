package org.optimizationBenchmarking.utils.text.charset;

/**
 * the internal base class for enclosures
 */
public final class Brace extends _EnclosureEnd<Braces, Brace> {

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
  Brace(final int chr, final boolean isOpening, final boolean isClosing) {
    super(chr, isOpening, isClosing);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean canEndWith(final Char other) {
    final Brace b;
    if (this.m_isOpening) {
      if (other instanceof Brace) {
        b = ((Brace) other);
        if (b.m_isClosing) {
          return ((b.m_otherEnd == this) || (this.m_otherEnd == b) || (this.m_owner == b.m_owner));
        }
      }
    }

    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean canStartWith(final Char other) {
    final Brace b;
    if (this.m_isClosing) {
      if (other instanceof Brace) {
        b = ((Brace) other);
        if (b.m_isOpening) {
          return ((b.m_otherEnd == this) || (this.m_otherEnd == b) || (this.m_owner == b.m_owner));
        }
      }
    }

    return false;
  }
}
