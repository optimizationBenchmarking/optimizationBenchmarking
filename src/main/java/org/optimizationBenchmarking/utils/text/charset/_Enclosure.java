package org.optimizationBenchmarking.utils.text.charset;

/**
 * A collection of enclosures
 *
 * @param <ET>
 *          the end type
 */
abstract class _Enclosure<ET extends _EnclosureEnd<?, ET>> extends
Enclosure implements Comparable<_Enclosure<?>> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the beginning char */
  transient final ET m_begin;

  /** the ending char */
  transient final ET m_end;

  /** the id */
  int m_id;

  /**
   * Create
   *
   * @param begin
   *          the beginning character
   * @param end
   *          the ending character
   */
  @SuppressWarnings("unchecked")
  _Enclosure(final ET begin, final ET end) {
    this.m_begin = begin;

    if (begin.m_owner == null) {
      ((_EnclosureEnd<_Enclosure<ET>, ?>) begin).m_owner = this;
    }
    if (begin.m_otherEnd == null) {
      begin.m_otherEnd = end;
    }

    this.m_end = end;
    if (end.m_owner == null) {
      ((_EnclosureEnd<_Enclosure<ET>, ?>) end).m_owner = this;
    }
    if (end.m_otherEnd == null) {
      end.m_otherEnd = begin;
    }
  }

  /** {@inheritDoc} */
  @Override
  public final ET getBegin() {
    return this.m_begin;
  }

  /** {@inheritDoc} */
  @Override
  public final ET getEnd() {
    return this.m_end;
  }

  /** {@inheritDoc} */
  @Override
  public final char getBeginChar() {
    return this.m_begin.m_char;
  }

  /** {@inheritDoc} */
  @Override
  public final char getEndChar() {
    return this.m_end.m_char;
  }

  /** {@inheritDoc} */
  @Override
  public final int compareTo(final _Enclosure<?> enc) {
    return Integer.compare(this.m_id, enc.m_id);
  }

  /**
   * write replace
   *
   * @return the replacement
   */
  final Object writeReplace() {
    return this.getSet().get(this.m_id);
  }

  /**
   * read resolve
   *
   * @return the replacement
   */
  final Object readResolve() {
    return this.writeReplace();
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    return (this.m_begin.toString() + this.m_end.toString());
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return this.m_id;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    return (o == this);
  }
}
