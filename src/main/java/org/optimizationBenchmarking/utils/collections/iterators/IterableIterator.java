package org.optimizationBenchmarking.utils.collections.iterators;

/**
 * An iterator which is also iterable.
 * 
 * @param <T>
 *          the element type
 */
public abstract class IterableIterator<T> extends BasicIterator<T>
    implements Iterable<T> {

  /** create */
  protected IterableIterator() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final IterableIterator<T> iterator() {
    return this;
  }

}
