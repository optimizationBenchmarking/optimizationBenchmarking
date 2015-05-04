package org.optimizationBenchmarking.utils.collections.lists;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

import org.optimizationBenchmarking.utils.collections.iterators.BasicIterator;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * an internal iterator class
 *
 * @param <ET>
 *          the element type
 */
class _Itr<ET> extends BasicIterator<ET> {

  /** the list */
  final BasicList<ET> m_list;

  /**
   * Index of element to be returned by subsequent call to next.
   */
  int m_cursor;

  /**
   * Index of element returned by most recent call to next or previous.
   * Reset to -1 if this element is deleted by a call to remove.
   */
  int m_lastRet;

  /**
   * Create
   *
   * @param list
   *          the list
   */
  _Itr(final BasicList<ET> list) {
    super();
    this.m_list = list;
    this.m_cursor = 0;
    this.m_lastRet = (-1);
  }

  /** {@inheritDoc} */
  @Override
  public final boolean hasNext() {
    return (this.m_cursor != (this.m_list.size()));
  }

  /** {@inheritDoc} */
  @Override
  public final ET next() {
    int i;

    try {
      i = this.m_cursor;
      final ET next = this.m_list.get(i);
      this.m_lastRet = i;
      this.m_cursor = (i + 1);
      return next;
    } catch (final IndexOutOfBoundsException e) {
      throw new NoSuchElementException(TextUtils.throwableToString(e));
    }
  }

  /** {@inheritDoc} */
  @Override
  public final void remove() {
    if (this.m_lastRet < 0) {
      throw new IllegalStateException();
    }

    try {
      this.m_list.remove(this.m_lastRet);
      if (this.m_lastRet < this.m_cursor) {
        this.m_cursor--;
      }
      this.m_lastRet = (-1);
    } catch (final IndexOutOfBoundsException e) {
      throw new ConcurrentModificationException(e);
    }
  }

}
