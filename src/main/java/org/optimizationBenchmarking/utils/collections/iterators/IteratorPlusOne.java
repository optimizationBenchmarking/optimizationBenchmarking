package org.optimizationBenchmarking.utils.collections.iterators;

import java.util.Iterator;

/**
 * An iterator which returns one more element than in the iterator it
 * wraps.
 *
 * @param <T>
 *          the type of the elements to be returned in the iteration
 */
public final class IteratorPlusOne<T> extends BasicIterator<T> {

  /** the first iterator */
  private Iterator<? extends T> m_iterator1;
  /** the additional element */
  private T m_additional;

  /** the state */
  private int m_state;

  /**
   * Create the iterator with one additional element
   * 
   * @param iterator1
   *          the first iterator
   * @param additional
   *          the additional element
   */
  public IteratorPlusOne(final Iterator<? extends T> iterator1,
      T additional) {
    super();
    if (iterator1 == null) {
      throw new IllegalArgumentException("First iterator cannot be null."); //$NON-NLS-1$
    }
    this.m_iterator1 = iterator1;
    this.m_additional = additional;
  }

  /** {@inheritDoc} */
  @SuppressWarnings("fallthrough")
  @Override
  public final boolean hasNext() {
    switch (this.m_state) {
      case 0: {
        if (this.m_iterator1.hasNext()) {
          this.m_state = 2;
          return true;
        }
        this.m_state = 3;
      }
      case 2:
      case 3: {
        return true;
      }
      default: {
        return false;
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public final T next() {
    switch (this.m_state) {
      case 0: {
        if (this.m_iterator1.hasNext()) {
          return this.m_iterator1.next();
        }
        this.m_state = 4;
        return this.m_additional;
      }
      case 2: {
        this.m_state = 0;
        return this.m_iterator1.next();
      }
      case 3: {
        this.m_state = 4;
        return this.m_additional;
      }
      default: {
        return super.next();
      }
    }
  }
}
