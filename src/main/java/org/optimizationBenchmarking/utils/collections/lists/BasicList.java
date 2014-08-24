package org.optimizationBenchmarking.utils.collections.lists;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;

import org.optimizationBenchmarking.utils.collections.BasicCollection;
import org.optimizationBenchmarking.utils.collections.iterators.BasicIterator;
import org.optimizationBenchmarking.utils.predicates.IPredicate;

/**
 * A basic list.
 *
 * @param <ET>
 *          the element type
 */
public class BasicList<ET> extends BasicCollection<ET> implements
    List<ET>, RandomAccess {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * Instantiate the array list base
   */
  protected BasicList() {
    super();
  }

  /**
   * check an index for accessing the list
   *
   * @param index
   *          the index
   * @param size
   *          the size
   */
  static final void _checkIndexForAccess(final int index, final int size) {
    if ((index < 0) || (index >= size)) {
      throw new IndexOutOfBoundsException("Index " + index + //$NON-NLS-1$
          " is outside the valid range 0.." + (size - 1) + //$NON-NLS-1$
          " for list access."); //$NON-NLS-1$
    }
  }

  /**
   * check an index range for accessing the list
   *
   * @param start
   *          the inclusive start index
   * @param end
   *          the exclusive end index
   * @param size
   *          the size
   */
  static final void _checkIndexRangeForAccess(final int start,
      final int end, final int size) {
    if (start > end) {
      throw new IndexOutOfBoundsException("Index pair [" + start + //$NON-NLS-1$
          ", " + end + //$NON-NLS-1$
          "] is invalid, since start index is bigger than end index."); //$NON-NLS-1$
    }
    if ((start < 0) || (end > size)) {
      throw new IndexOutOfBoundsException("Index pair [" + start + //$NON-NLS-1$
          ", " + end + //$NON-NLS-1$
          " is outside the valid range 0.." + size + //$NON-NLS-1$
          " for list access."); //$NON-NLS-1$
    }
  }

  /**
   * check an index for adding to the list
   *
   * @param index
   *          the index
   * @param size
   *          the size
   */
  static final void _checkIndexForAdd(final int index, final int size) {
    if ((index < 0) || (index > size)) {
      throw new IndexOutOfBoundsException("Index " + index + //$NON-NLS-1$
          " is outside the valid range 0.." + size + //$NON-NLS-1$
          " for adding to the list."); //$NON-NLS-1$
    }
  }

  /** {@inheritDoc} */
  @Override
  public boolean add(final ET e) {
    this.add(this.size(), e);
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public ET get(final int index) {
    throw new UnsupportedOperationException(//
        "Cannot get element at index " + index); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public ET set(final int index, final ET element) {
    throw new UnsupportedOperationException(//
        "Cannot set element " + element + //$NON-NLS-1$
            " at index " + index //$NON-NLS-1$
    );
  }

  /** {@inheritDoc} */
  @Override
  public void add(final int index, final ET element) {
    throw new UnsupportedOperationException(//
        "Cannot add element " + element + //$NON-NLS-1$
            " at index " + index//$NON-NLS-1$
    );
  }

  /** {@inheritDoc} */
  @Override
  public boolean remove(final Object o) {
    final int i;
    i = this.indexOf(o);
    if (i >= 0) {
      this.remove(i);
      return true;
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public ET remove(final int index) {
    throw new UnsupportedOperationException(//
        "Cannot remove element at index " + index //$NON-NLS-1$
    );
  }

  /** {@inheritDoc} */
  @Override
  public int indexOf(final Object o) {
    final int size;
    int i;

    i = 0;
    size = this.size();
    if (o == null) {
      for (i = 0; i < size; i++) {
        if (this.get(i) == null) {
          return i;
        }
      }
    } else {
      for (i = 0; i < size; i++) {
        if (o.equals(this.get(i))) {
          return i;
        }
      }
    }

    return (-1);
  }

  /** {@inheritDoc} */
  @Override
  public int lastIndexOf(final Object o) {
    int i;

    i = this.size();
    if (o == null) {
      for (; (--i) >= 0;) {
        if (this.get(i) == null) {
          return i;
        }
      }
    } else {
      for (; (--i) >= 0;) {
        if (o.equals(this.get(i))) {
          return i;
        }
      }
    }
    return (-1);
  }

  /** {@inheritDoc} */
  @Override
  public void clear() {
    this.removeRange(0, this.size());
  }

  /** {@inheritDoc} */
  @Override
  public boolean addAll(final int index, final Collection<? extends ET> c) {
    final int size;
    int i;

    size = this.size();
    BasicList._checkIndexForAdd(index, size);

    i = index;
    for (final ET e : c) {
      this.add((i++), e);
    }
    return (this.size() > size);
  }

  // Iterators

  /** {@inheritDoc} */
  @Override
  public BasicIterator<ET> iterator() {
    return new _Itr<>(this);
  }

  /** {@inheritDoc} */
  @Override
  public ListIterator<ET> listIterator() {
    return this.listIterator(0);
  }

  /** {@inheritDoc} */
  @Override
  public ListIterator<ET> listIterator(final int index) {
    BasicList._checkIndexForAdd(index, this.size());

    return new _ListItr<>(this, index);
  }

  /** {@inheritDoc} */
  @Override
  public BasicList<ET> subList(final int fromIndex, final int toIndex) {
    final int size;

    size = this.size();
    if ((fromIndex == 0) && (toIndex == size)) {
      return this;
    }
    BasicList._checkIndexRangeForAccess(fromIndex, toIndex, this.size());

    return new _BasicSubList<>(this, fromIndex, (toIndex - fromIndex));
  }

  /**
   * Removes from this list all of the elements whose index is between
   * {@code fromIndex}, inclusive, and {@code toIndex}, exclusive. Shifts
   * any succeeding elements to the left (reduces their index). This call
   * shortens the list by {@code (toIndex - fromIndex)} elements. (If
   * {@code toIndex==fromIndex}, this operation has no effect.)
   *
   * @param fromIndex
   *          index of first element to be removed
   * @param toIndex
   *          index after last element to be removed
   */
  public void removeRange(final int fromIndex, final int toIndex) {
    final ListIterator<ET> it = this.listIterator(fromIndex);
    for (int i = 0, n = (toIndex - fromIndex); i < n; i++) {
      it.next();
      it.remove();
    }
  }

  /** {@inheritDoc} */
  @Override
  public BasicList<ET> select(final IPredicate<? super ET> predicate) {
    return ((BasicList<ET>) (super.select(predicate)));
  }

}
