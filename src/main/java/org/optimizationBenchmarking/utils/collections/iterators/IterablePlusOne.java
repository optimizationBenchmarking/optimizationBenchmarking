package org.optimizationBenchmarking.utils.collections.iterators;

/**
 * An iterable which you can use a single time only and which will provide
 * one more element than in its original iterable.
 * 
 * @param <T>
 *          the type of the elements to be returned in the iteration
 */
public final class IterablePlusOne<T> implements Iterable<T> {
  /** the first iterable */
  private Iterable<? extends T> m_iterable1;
  /** the additional element */
  private T m_additional;

  /**
   * Create a iterable with one additional element
   * 
   * @param iterable1
   *          the first iterable
   * @param additional
   *          the additional element
   */
  public IterablePlusOne(final Iterable<? extends T> iterable1,
      T additional) {
    super();
    if (iterable1 == null) {
      throw new IllegalArgumentException("First iterable cannot be null."); //$NON-NLS-1$
    }
    this.m_iterable1 = iterable1;
    this.m_additional = additional;
  }

  /** {@inheritDoc} */
  @Override
  public final IteratorPlusOne<T> iterator() {
    return new IteratorPlusOne<>(this.m_iterable1.iterator(),
        this.m_additional);
  }

}
