package org.optimizationBenchmarking.utils.collections;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import org.optimizationBenchmarking.utils.EmptyUtils;
import org.optimizationBenchmarking.utils.collections.iterators.BasicIterator;
import org.optimizationBenchmarking.utils.collections.lists.ArrayListView;
import org.optimizationBenchmarking.utils.collections.lists.ArraySetView;
import org.optimizationBenchmarking.utils.collections.visitors.IVisitor;
import org.optimizationBenchmarking.utils.comparison.EComparison;
import org.optimizationBenchmarking.utils.predicates.IPredicate;
import org.optimizationBenchmarking.utils.text.ITextable;
import org.optimizationBenchmarking.utils.text.textOutput.ITextOutput;
import org.optimizationBenchmarking.utils.text.textOutput.MemoryTextOutput;

/**
 * The base class for all collections.
 *
 * @param <ET>
 *          the element type
 */
public class BasicCompound<ET> implements Serializable, Iterable<ET>,
    ITextable {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** instantiate! */
  protected BasicCompound() {
    super();
  }

  /**
   * Visit the elements in this list
   *
   * @param visitor
   *          the visitor
   * @return {@code false} if the visitors {@code visit} method returned
   *         {@code false} at some point, {@code true} otherwise
   */
  public boolean visit(final IVisitor<? super ET> visitor) {
    final BasicIterator<ET> it;

    it = this.iterator();
    if (it != null) {
      while (it.hasNext()) {
        if (visitor.visit(it.next())) {
          continue;
        }
        return false;
      }
    }
    return true;
  }

  /** {@inheritDoc} */
  @Override
  @SuppressWarnings("unchecked")
  public boolean equals(final Object o) {
    final Iterator<ET> ita;
    final Iterator<Object> itb;

    if (o == null) {
      return false;
    }

    if (o == this) {
      return true;
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

  /** {@inheritDoc} */
  @Override
  public int hashCode() {
    int hashCode;

    hashCode = 1;
    for (final ET e : this) {
      hashCode = ((31 * hashCode) + ((e == null) ? 0 : e.hashCode()));
    }
    return hashCode;
  }

  /** {@inheritDoc} */
  @SuppressWarnings("unchecked")
  @Override
  public BasicIterator<ET> iterator() {
    return ((BasicIterator<ET>) (BasicIterator.EMPTY_ITERATOR));
  }

  /**
   * Get the number of elements of this compound data structure
   *
   * @return the number of elements of this compound data structure
   */
  public int size() {
    return 0;
  }

  /**
   * Check if this compound data structure is empty or not.
   *
   * @return {@code true} if the data structure is empty, {@code false}
   *         otherwise
   */
  public final boolean isEmpty() {
    return (this.size() <= 0);
  }

  /**
   * Does this compound data structure contain a specific element?
   *
   * @param o
   *          the object to find
   * @return {@code true} if the object is contained, {@code false}
   *         otherwise
   * @throws ClassCastException
   *           if necessary
   * @throws NullPointerException
   *           if necessary
   */
  public boolean contains(final Object o) {

    if (o == null) {
      for (final ET e : this) {
        if (e == null) {
          return true;
        }
      }
    } else {
      for (final ET e : this) {
        if (o.equals(e)) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * transform the contents of this compound to an array
   *
   * @return the array
   */
  public Object[] toArray() {
    final Object[] r;
    int i;

    i = this.size();
    if (i <= 0) {
      return EmptyUtils.EMPTY_OBJECTS;
    }

    r = new Object[i];
    i = 0;
    for (final ET e : this) {
      r[i++] = e;
    }
    return r;
  }

  /**
   * transform the contents of this compound to a specific array
   *
   * @param a
   *          the array
   * @return the array
   * @param <T>
   *          the array type
   * @throws ArrayStoreException
   *           if the class does not fit
   * @throws NullPointerException
   *           on error
   */
  @SuppressWarnings("unchecked")
  public <T> T[] toArray(final T[] a) {
    final T[] r;
    int i;

    i = this.size();
    r = ((a.length >= i) ? a : ((T[]) (Array.newInstance(a.getClass()
        .getComponentType(), i))));

    i = 0;
    for (final ET e : this) {
      r[i++] = ((T) e);
    }
    if (i < r.length) {
      r[i] = null;
    }
    return r;
  }

  /**
   * Check if all the elements in a collection are contained
   *
   * @param c
   *          the collection
   * @return {@code true} if all the collection's elements are contained,
   *         {@code false} otherwise
   * @throws ClassCastException
   *           if a corresponding error happens
   * @throws NullPointerException
   *           if a corresponding error happens
   * @see #contains(Object)
   */
  public boolean containsAll(final Collection<?> c) {
    if (c != null) {
      for (final Object e : c) {
        if (this.contains(e)) {
          continue;
        }
        return false;
      }
    }
    return true;
  }

  /**
   * Clear this compound data structure
   *
   * @throws UnsupportedOperationException
   *           if not supported
   */
  public void clear() {
    final Iterator<ET> it;

    it = this.iterator();
    while (it.hasNext()) {
      it.next();
      it.remove();
    }
  }

  /** {@inheritDoc} */
  @Override
  public void toText(final ITextOutput textOut) {
    boolean b;

    textOut.append('[');
    b = false;

    for (final ET e : this) {
      if (b) {
        textOut.append(',');
        textOut.append(' ');
      } else {
        b = true;
      }
      if (e == this) {
        textOut.append("(this Collection)"); //$NON-NLS-1$
      } else {
        textOut.append(e);
      }
    }
    textOut.append(']');
  }

  /** {@inheritDoc} */
  @Override
  public final String toString() {
    final MemoryTextOutput sb;

    if (this.isEmpty()) {
      return "[]"; //$NON-NLS-1$
    }

    sb = new MemoryTextOutput();
    this.toText(sb);

    return sb.toString();
  }

  /**
   * Select all elements that meet the predicate. This method will not
   * modify the current compound, but it may return the compound itself. If
   * some of the elements do not meet the condition, a new compound may be
   * returned.
   *
   * @param predicate
   *          the predicate
   * @return a compound with the elements in this compound that meet the
   *         predicate
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  public BasicCompound<ET> select(final IPredicate<? super ET> predicate) {
    final Object[] l;
    int i;

    i = this.size();
    if (i <= 0) {
      return ((ArrayListView) (ArraySetView.EMPTY_SET_VIEW));
    }

    l = new Object[i];
    i = 0;

    for (final ET x : this) {
      if (predicate.check(x)) {
        l[i++] = x;
      }
    }
    if (i >= l.length) {
      return this;
    }

    if (i <= 0) {
      return ((ArrayListView) (ArraySetView.EMPTY_SET_VIEW));
    }

    return new ArrayListView((i > 0) ? ((i < l.length) ? Arrays.copyOf(l,
        i) : l) : EmptyUtils.EMPTY_OBJECTS);
  }

  /**
   * Check whether there is any element that meets the condition
   *
   * @param predicate
   *          the predicate
   * @return {@code true} if there is any element that meets the condition,
   *         {@code false} otherwise
   */
  public boolean hasAny(final IPredicate<? super ET> predicate) {
    for (final ET e : this) {
      if (predicate.check(e)) {
        return true;
      }
    }
    return false;
  }

}
