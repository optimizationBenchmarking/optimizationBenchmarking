package org.optimizationBenchmarking.utils.collections.iterators;

import java.util.Enumeration;

/**
 * An {@link java.util.Iterator} wrapping an {@link java.util.Enumeration}.
 *
 * @param <T>
 *          the type parameter
 */
public final class EnumerationIterator<T> extends IterableIterator<T> {

  /** the enumeration */
  private final Enumeration<? extends T> m_enum;

  /**
   * Create the enumeration-based iterator
   *
   * @param enume
   *          the enumeration to wrap
   */
  public EnumerationIterator(final Enumeration<? extends T> enume) {
    super();
    if (enume == null) {
      throw new IllegalArgumentException(//
          "Enumeration to wrap with EnumerationIterator cannot be null."); //$NON-NLS-1$
    }
    this.m_enum = enume;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean hasNext() {
    return this.m_enum.hasMoreElements();
  }

  /** {@inheritDoc} */
  @Override
  public final T next() {
    return this.m_enum.nextElement();
  }
}
