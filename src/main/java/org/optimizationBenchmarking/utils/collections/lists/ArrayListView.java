package org.optimizationBenchmarking.utils.collections.lists;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.optimizationBenchmarking.utils.IImmutable;
import org.optimizationBenchmarking.utils.collections.iterators.ArrayIterator;
import org.optimizationBenchmarking.utils.collections.iterators.BasicIterator;
import org.optimizationBenchmarking.utils.collections.visitors.IVisitor;
import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.predicates.IPredicate;
import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * An immutable {@link java.util.List list} view on an array.
 *
 * @param <DT>
 *          the type
 */
public class ArrayListView<DT> extends BasicList<DT> implements IImmutable {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the data */
  protected final DT[] m_data;

  /** the hash code */
  private int m_hashCode;

  /**
   * instantiate
   *
   * @param data
   *          the data of the set - will not be copied or cloned, but used
   *          directly
   */
  public ArrayListView(final DT[] data) {
    super();

    if (data == null) {
      throw new IllegalArgumentException(//
          "Data (element array) passed to the constructor of " + //$NON-NLS-1$
              TextUtils.className(this.getClass()) + //
              " must not be null."); //$NON-NLS-1$
    }

    this.m_data = data;
  }

  /**
   * copy another list view
   *
   * @param copy
   *          the other list view
   */
  protected ArrayListView(final ArrayListView<? extends DT> copy) {
    this(copy.m_data);
  }

  /** {@inheritDoc} */
  @Override
  public final int size() {
    return this.m_data.length;
  }

  /** {@inheritDoc} */
  @Override
  public final DT get(final int index) {
    return this.m_data[index];
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("unchecked")
  public final BasicIterator<DT> iterator() {
    return ((this.m_data.length <= 0) ? ((BasicIterator<DT>) (BasicIterator.EMPTY_ITERATOR))
        : new ArrayIterator<>(this.m_data));
  }

  /** {@inheritDoc} */
  @Override
  public final boolean contains(final Object o) {
    return (this.indexOf(o) >= 0);
  }

  /** {@inheritDoc} */
  @Override
  public final Object[] toArray() {
    final Object[] data;
    data = new Object[this.m_data.length];
    System.arraycopy(this.m_data, 0, data, 0, data.length);
    return data;
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("unchecked")
  public final <T> T[] toArray(final T[] a) {
    final T[] out;
    final int len;

    len = this.m_data.length;

    out = ((a.length >= len) ? a : //
        ((T[]) (java.lang.reflect.Array.newInstance(//
            a.getClass().getComponentType(), len))));
    System.arraycopy(this.m_data, 0, out, 0, len);
    if (len < out.length) {
      out[len] = null;
    }
    return out;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean containsAll(final Collection<?> c) {
    for (final Object x : c) {
      if (this.indexOf(x) < 0) {
        return false;
      }
    }
    return true;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean addAll(final Collection<? extends DT> c) {
    if (c.isEmpty()) {
      return false;
    }
    throw new UnsupportedOperationException(//
        "Cannot add elements " + c + //$NON-NLS-1$
            " to this list."//$NON-NLS-1$
    );
  }

  /** {@inheritDoc} */
  @Override
  public final boolean addAll(final int index,
      final Collection<? extends DT> c) {
    if (c.isEmpty()) {
      return false;
    }
    throw new UnsupportedOperationException(//
        "Cannot add elements " + c + //$NON-NLS-1$
            "at index " + index//$NON-NLS-1$
    );
  }

  /** {@inheritDoc} */
  @Override
  public final boolean removeAll(final Collection<?> c) {
    for (final Object o : c) {
      if (this.contains(o)) {
        throw new UnsupportedOperationException(//
            "Cannot remove elements " + c //$NON-NLS-1$
        );
      }
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean retainAll(final Collection<?> c) {
    for (final Object o : this.m_data) {
      if (!(c.contains(o))) {
        throw new UnsupportedOperationException(//
            "Cannot retain the elements " + c //$NON-NLS-1$
        );
      }
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final void clear() {
    if (this.m_data.length > 0) {
      throw new UnsupportedOperationException(//
          "Cannot clear this list."//$NON-NLS-1$
      );
    }
  }

  /** {@inheritDoc} */
  @Override
  public final DT set(final int index, final DT element) {
    throw new UnsupportedOperationException(//
        "Cannot set element " + element + //$NON-NLS-1$
            " at index " + index//$NON-NLS-1$
    );
  }

  /** {@inheritDoc} */
  @Override
  public final void add(final int index, final DT element) {
    throw new UnsupportedOperationException(//
        "Cannot add element " + element + //$NON-NLS-1$
            " at index " + index//$NON-NLS-1$
    );
  }

  /** {@inheritDoc} */
  @Override
  public final boolean add(final DT element) {
    throw new UnsupportedOperationException(//
        "Cannot add element " + element//$NON-NLS-1$
    );
  }

  /** {@inheritDoc} */
  @Override
  public boolean remove(final Object o) {
    final int i;

    i = this.indexOf(o);
    if (i >= 0) {
      throw new UnsupportedOperationException(//
          "Cannot remove element " + o + //$NON-NLS-1$
              ", which is at index " + i//$NON-NLS-1$
      );
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public final DT remove(final int index) {
    throw new UnsupportedOperationException(//
        "Cannot remove the element at index " + index//$NON-NLS-1$
    );
  }

  /** {@inheritDoc} */
  @Override
  public int indexOf(final Object o) {
    final DT[] data;
    int idx;

    data = this.m_data;
    if (o == null) {
      for (idx = 0; idx < data.length; idx++) {
        if (data[idx] == null) {
          return idx;
        }
      }
      return (-1);
    }
    for (idx = 0; idx < data.length; idx++) {
      if (o.equals(data[idx])) {
        return idx;
      }
    }
    return (-1);
  }

  /** {@inheritDoc} */
  @Override
  public int lastIndexOf(final Object o) {
    final DT[] data;
    int idx;

    data = this.m_data;
    if (o == null) {
      for (idx = data.length; (--idx) >= 0;) {
        if (data[idx] == null) {
          return idx;
        }
      }
      return (-1);
    }
    for (idx = data.length; (--idx) >= 0;) {
      if (o.equals(data[idx])) {
        return idx;
      }
    }
    return (-1);
  }

  /** {@inheritDoc} */
  @Override
  public final ArrayIterator<DT> listIterator() {
    return new ArrayIterator<>(this.m_data);
  }

  /** {@inheritDoc} */
  @Override
  public final ArrayIterator<DT> listIterator(final int index) {
    return new ArrayIterator<>(this.m_data, this.m_data.length, index);
  }

  /** {@inheritDoc} */
  @Override
  public final void removeRange(final int fromIndex, final int toIndex) {
    if (toIndex > fromIndex) {
      throw new UnsupportedOperationException();
    }
  }

  /** {@inheritDoc} */
  @Override
  public final boolean visit(final IVisitor<? super DT> visitor) {
    for (final DT t : this.m_data) {
      if (!(visitor.visit(t))) {
        return false;
      }
    }
    return true;
  }

  /** {@inheritDoc} */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public ArrayListView<DT> select(final IPredicate<? super DT> condition) {
    DT[] data;
    DT x;
    int i, s, len;

    data = this.m_data;
    len = s = data.length;

    if (s <= 0) {
      return ((ArrayListView) (ArraySetView.EMPTY_SET_VIEW));
    }

    check: {
      for (i = 0; i < len; i++) {
        if (condition.check(data[i])) {
          continue;
        }
        break check;
      }
      return this;
    }

    s = i;
    for (; (++i) < len;) {
      x = data[i];
      if (condition.check(x)) {
        if (data == this.m_data) {
          data = data.clone();
        }
        data[s++] = x;
      }
    }

    if (s <= 0) {
      return ((ArrayListView) (ArraySetView.EMPTY_SET_VIEW));
    }

    return new ArrayListView<>(Arrays.copyOf(data, s));
  }

  /** {@inheritDoc} */
  @Override
  public final boolean hasAny(final IPredicate<? super DT> predicate) {
    return (this.indexOf(predicate) >= 0);
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public boolean equals(final Object o) {
    final Iterator<DT> ita;
    final Iterator<Object> itb;

    if (o == null) {
      return false;
    }

    if (o == this) {
      return true;
    }

    if (o instanceof ArrayListView) {
      return Arrays.equals(this.m_data, ((ArrayListView) o).m_data);
    }

    if (o instanceof Iterable) {
      itb = ((Iterable<Object>) o).iterator();
      if (itb == null) {
        return false;
      }
      ita = this.iterator();

      while (ita.hasNext() && itb.hasNext()) {
        if (!(EComparison.equals(ita.next(), itb.next()))) {
          return false;
        }
      }

      return (ita.hasNext() == itb.hasNext());
    }

    return false;
  }

  /**
   * Calculate the hash code
   *
   * @return the hash code
   */
  protected int calcHashCode() {
    int hashCode;

    hashCode = 1;
    for (final DT e : this.m_data) {
      hashCode = ((31 * hashCode) + ((e == null) ? 0 : e.hashCode()));
    }
    return hashCode;
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    if (this.m_hashCode == 0) {
      this.m_hashCode = this.calcHashCode();
    }
    return this.m_hashCode;
  }

  /**
   * Take the data from a given collection to fill an array list view. If
   * the collection is {@code null} or empty,
   * {@link org.optimizationBenchmarking.utils.collections.lists.ArraySetView#EMPTY_SET_VIEW}
   * will be returned.
   *
   * @param collection
   *          the collection
   * @return the list view
   * @param <T>
   *          the data type
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public static final <T> ArrayListView<T> collectionToView(
      final Collection<? extends T> collection) {
    final int s;
    final ArrayListView<T> l;

    if ((collection == null) || ((s = collection.size()) <= 0)) {
      return ((ArrayListView) (ArraySetView.EMPTY_SET_VIEW));
    }

    if (collection instanceof ArrayListView) {
      return ((ArrayListView) collection);
    }

    l = new ArrayListView(collection.toArray(new Object[s]));
    return l;
  }

}
