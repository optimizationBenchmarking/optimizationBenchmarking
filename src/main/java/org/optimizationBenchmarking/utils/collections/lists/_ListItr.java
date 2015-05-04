package org.optimizationBenchmarking.utils.collections.lists;

import java.util.ConcurrentModificationException;
import java.util.ListIterator;
import java.util.NoSuchElementException;

import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * the list iterator
 *
 * @param <ET>
 *          the element type
 */
final class _ListItr<ET> extends _Itr<ET> implements ListIterator<ET> {

  /**
   * create
   *
   * @param list
   *          the list
   * @param index
   *          the index
   */
  _ListItr(final BasicList<ET> list, final int index) {
    super(list);
    this.m_cursor = index;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean hasPrevious() {
    return (this.m_cursor != 0);
  }

  /** {@inheritDoc} */
  @Override
  public final ET previous() {
    int i;
    ET previous;

    try {
      i = (this.m_cursor - 1);
      previous = this.m_list.get(i);
      this.m_lastRet = (this.m_cursor = i);
      return previous;
    } catch (final IndexOutOfBoundsException e) {
      throw new NoSuchElementException(TextUtils.throwableToString(e));
    }
  }

  /** {@inheritDoc} */
  @Override
  public final int nextIndex() {
    return this.m_cursor;
  }

  /** {@inheritDoc} */
  @Override
  public final int previousIndex() {
    return (this.m_cursor - 1);
  }

  /** {@inheritDoc} */
  @Override
  public final void set(final ET e) {
    if (this.m_lastRet < 0) {
      throw new IllegalStateException(//
          "Last returned must not be before list start."); //$NON-NLS-1$
    }

    try {
      this.m_list.set(this.m_lastRet, e);
    } catch (final IndexOutOfBoundsException ex) {
      throw new ConcurrentModificationException(ex);
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void add(final ET e) {
    int i;

    try {
      i = this.m_cursor;
      this.m_list.add(i, e);
      this.m_lastRet = -1;
      this.m_cursor = (i + 1);
    } catch (final IndexOutOfBoundsException ex) {
      throw new ConcurrentModificationException(ex);
    }
  }
}