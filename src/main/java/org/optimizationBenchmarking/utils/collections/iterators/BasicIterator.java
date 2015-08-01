package org.optimizationBenchmarking.utils.collections.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * The base class for iterators that unites the behavior of both, the
 * {@link java.util.Iterator} and {@link java.util.Enumeration} interface.
 *
 * @param <T>
 *          the type to iterate about
 */
public class BasicIterator<T> extends BasicEnumeration<T> implements
    Iterator<T> {

  /** an iterator iterating over nothing */
  public static final BasicIterator<Object> EMPTY_ITERATOR = new BasicIterator<>();

  /** Instantiate the iterator base */
  protected BasicIterator() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final boolean hasMoreElements() {
    return this.hasNext();
  }

  /** {@inheritDoc} */
  @Override
  public final T nextElement() {
    return this.next();
  }

  /** {@inheritDoc} */
  @Override
  public boolean hasNext() {
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public T next() {
    throw new NoSuchElementException(
        "The iterator of type " + TextUtils.className(this.getClass()) + //$NON-NLS-1$
            " does not contain another element. You should have checked hasNext() before invoking next()."//$NON-NLS-1$
    );
  }

  /** {@inheritDoc} */
  @Override
  public void remove() {
    throw new UnsupportedOperationException(
        "The iterator of type " + TextUtils.className(this.getClass()) + //$NON-NLS-1$
            " does not allow removing the current element (or maybe you did not call next() before remove())."//$NON-NLS-1$
    );
  }

}
