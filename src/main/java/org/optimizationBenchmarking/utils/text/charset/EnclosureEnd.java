package org.optimizationBenchmarking.utils.text.charset;

/**
 * the internal base class for enclosures
 */
public abstract class EnclosureEnd extends Char {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** Can this enclosure end open the enclosure? */
  final transient boolean m_isOpening;

  /** Can this enclosure end close the enclosure? */
  final transient boolean m_isClosing;

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
  EnclosureEnd(final int chr, final boolean isOpening,
      final boolean isClosing) {
    super(chr);
    this.m_isOpening = isOpening;
    this.m_isClosing = isClosing;
  }

  /**
   * Can this enclosure end open the enclosure?
   *
   * @return {@code true} if this enclosure end can be at the beginning of
   *         the enclosure, {@code false} otherwise
   */
  public final boolean isOpening() {
    return this.m_isOpening;
  }

  /**
   * Can this enclosure end close the enclosure?
   *
   * @return {@code true} if this enclosure end can be at the end of the
   *         enclosure, {@code false} otherwise
   */
  public final boolean isClosing() {
    return this.m_isClosing;
  }

  /**
   * Get the (default) other end of this enclosure
   *
   * @return the (default) other end of this enclosure
   */
  public abstract EnclosureEnd getOtherEnd();

  /**
   * Get the owner of this enclosure
   *
   * @return the owner of this enclosure
   */
  public abstract Enclosure getOwner();

  /**
   * Check if the other enclosure end can end an enclosure starting with
   * this enclosure end
   *
   * @param other
   *          the other character
   * @return {@code true} if and only if the other enclosure end can end an
   *         enclosure starting with this enclosure end, {@code false}
   *         otherwise.
   */
  public abstract boolean canEndWith(final Char other);

  /**
   * Check if the other enclosure end can start an enclosure ending with
   * this enclosure end
   *
   * @param other
   *          the other character
   * @return {@code true} if and only if the other enclosure end can start
   *         an enclosure ending with this enclosure end, {@code false}
   *         otherwise.
   */
  public abstract boolean canStartWith(final Char other);
}
