package org.optimizationBenchmarking.utils.collections.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An iterator for arrays. As a gimmick, this class implements
 * {@link java.lang.Iterable} as well, in order to allow you to use its
 * instances directly in the new {@code for(T x : Iterable<T>)}-style
 * {@code for} loops. Handle with care.
 *
 * @param <T>
 *          the type
 */
public final class ArrayIterator<T> extends BasicListIterator<T>
    implements Iterable<T> {
  /** the array */
  private final T[] m_array;
  /** the position */
  private int m_pos;
  /** the last index */
  private int m_size;

  /**
   * instantiate
   *
   * @param arr
   *          the m_array
   */
  public ArrayIterator(final T[] arr) {
    this(arr, arr.length, 0);
  }

  /**
   * instantiate
   *
   * @param arr
   *          the m_array
   * @param size
   *          the size
   */
  public ArrayIterator(final T[] arr, final int size) {
    this(arr, size, 0);
  }

  /**
   * instantiate
   *
   * @param arr
   *          the m_array
   * @param size
   *          the size
   * @param startIndex
   *          the start index
   */
  public ArrayIterator(final T[] arr, final int size,
      final int startIndex) {
    super();

    if ((size < 0) || (size > arr.length)) {
      throw new IllegalArgumentException("Illegal size: " + size); //$NON-NLS-1$
    }
    if ((startIndex < 0) || (startIndex > size)) {
      throw new IllegalArgumentException("Illegal start index: " + size); //$NON-NLS-1$
    }

    this.m_array = arr;
    this.m_size = size;
    this.m_pos = startIndex;
  }

  /**
   * Get the element at position {@code index}
   *
   * @param index
   *          the index
   * @return the element at that position
   */
  public final T get(final int index) {
    return this.m_array[index];
  }

  /** {@inheritDoc} */
  @Override
  public final boolean hasNext() {
    return (this.m_pos < this.m_size);
  }

  /** {@inheritDoc} */
  @Override
  public final T next() throws NoSuchElementException {
    if (this.m_pos < this.m_size) {
      return this.m_array[this.m_pos++];
    }
    throw new NoSuchElementException(
        "The current position (" + this.m_pos + //$NON-NLS-1$
            ") is already at the end of the valid range (0.." + //$NON-NLS-1$
            this.m_size + ")."); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final boolean hasPrevious() {
    return (this.m_pos > 0);
  }

  /** {@inheritDoc} */
  @Override
  public final T previous() {
    if (this.m_pos > 0) {
      return this.m_array[--this.m_pos];
    }
    throw new NoSuchElementException(//
        "Position is still at the start."); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public final int nextIndex() {
    return this.m_pos;
  }

  /** {@inheritDoc} */
  @Override
  public final int previousIndex() {
    return (this.m_pos - 1);
  }

  /** {@inheritDoc} */
  @Override
  public final Iterator<T> iterator() {
    return this;
  }
}
