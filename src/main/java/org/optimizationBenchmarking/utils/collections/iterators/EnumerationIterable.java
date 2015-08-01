package org.optimizationBenchmarking.utils.collections.iterators;

import java.util.Enumeration;

/**
 * An {@link java.lang.Iterable} producing an {@link java.util.Iterator}
 * wrapping an {@link java.util.Enumeration}.
 *
 * @param <T>
 *          the type parameter
 */
public final class EnumerationIterable<T> implements Iterable<T> {

  /** the enumeration */
  private Enumeration<? extends T> m_enum;

  /**
   * Create the enumeration-based iterable
   *
   * @param enume
   *          the enumeration to wrap
   */
  public EnumerationIterable(final Enumeration<? extends T> enume) {
    super();
    if (enume == null) {
      throw new IllegalArgumentException(//
          "Enumeration to wrap with EnumerationIterator cannot be null."); //$NON-NLS-1$
    }
    this.m_enum = enume;
  }

  /** {@inheritDoc} */
  @Override
  public final EnumerationIterator<T> iterator() {
    final Enumeration<? extends T> en;
    en = this.m_enum;
    this.m_enum = null;

    if (en != null) {
      return new EnumerationIterator<>(en);
    }
    throw new IllegalStateException(//
        "Can iterate only once over an enumeration.");//$NON-NLS-1$
  }

}
