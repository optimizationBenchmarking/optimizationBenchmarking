package org.optimizationBenchmarking.utils.collections;

import java.util.Collection;
import java.util.Iterator;

import org.optimizationBenchmarking.utils.comparison.EComparison;

/**
 * the base class for all sfc collections
 *
 * @param <ET>
 *          the element type
 */
public class BasicCollection<ET> extends BasicCompound<ET> implements
    Collection<ET> {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** an empty collection */
  public static final BasicCollection<Object> EMPTY_COLLECTION = new BasicCollection<>();

  /** instantiate! */
  protected BasicCollection() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public boolean add(final ET e) {
    throw new UnsupportedOperationException(//
        "Cannot add element " + e); //$NON-NLS-1$
  }

  /** {@inheritDoc} */
  @Override
  public boolean remove(final Object o) {
    final Iterator<ET> it;

    it = this.iterator();
    while (it.hasNext()) {
      if (EComparison.equals(o, it.next())) {
        it.remove();
        return true;
      }
    }
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public boolean addAll(final Collection<? extends ET> c) {
    boolean modified;

    modified = false;
    if (c != null) {
      for (final ET e : c) {
        if (this.add(e)) {
          modified = true;
        }
      }
    }

    return modified;
  }

  /** {@inheritDoc} */
  @Override
  public boolean removeAll(final Collection<?> c) {
    boolean modified;
    final Iterator<?> it;

    modified = false;
    if (c != null) {
      it = this.iterator();
      while (it.hasNext()) {
        if (c.contains(it.next())) {
          it.remove();
          modified = true;
        }
      }
    }
    return modified;
  }

  /** {@inheritDoc} */
  @Override
  public boolean retainAll(final Collection<?> c) {
    boolean modified;
    final Iterator<?> it;

    if (c != null) {
      modified = false;
      it = this.iterator();
      while (it.hasNext()) {
        if (!(c.contains(it.next()))) {
          it.remove();
          modified = true;
        }
      }
    } else {
      modified = (!(this.isEmpty()));
      this.clear();
    }
    return modified;
  }

}
