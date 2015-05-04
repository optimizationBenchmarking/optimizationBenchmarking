package org.optimizationBenchmarking.utils.text.charset;

/**
 * the internal base class for enclosures
 *
 * @param <OT>
 *          the owning enumeration
 * @param <T>
 *          the other end type
 */
abstract class _EnclosureEnd<OT extends _Enclosure<T>, T extends _EnclosureEnd<OT, T>>
    extends EnclosureEnd {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the other end of the enclosure */
  transient T m_otherEnd;

  /** the owning enclosure set */
  transient OT m_owner;

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
  _EnclosureEnd(final int chr, final boolean isOpening,
      final boolean isClosing) {
    super(chr, isOpening, isClosing);
  }

  /** {@inheritDoc} */
  @Override
  public final T getOtherEnd() {
    return this.m_otherEnd;
  }

  /** {@inheritDoc} */
  @Override
  public final OT getOwner() {
    return this.m_owner;
  }
}
