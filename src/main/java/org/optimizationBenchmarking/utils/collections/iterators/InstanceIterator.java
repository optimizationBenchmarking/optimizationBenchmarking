package org.optimizationBenchmarking.utils.collections.iterators;

import java.util.NoSuchElementException;

/**
 * an iterator returning a single object instance
 *
 * @param <T>
 *          the type
 */
public final class InstanceIterator<T> extends BasicIterator<T> {

  /** the instance */
  private final T m_instance;
  /** was the has not yet been returned */
  private boolean m_has;

  /**
   * instantiate
   *
   * @param inst
   *          the instance
   */
  public InstanceIterator(final T inst) {
    super();
    this.m_instance = inst;
    this.m_has = true;
  }

  /** {@inheritDoc} */
  @Override
  public final boolean hasNext() {
    return this.m_has;
  }

  /** {@inheritDoc} */
  @Override
  public final T next() throws NoSuchElementException {
    if (this.m_has) {
      this.m_has = false;
      return this.m_instance;
    }
    throw new NoSuchElementException(//
        "The only element in this iterator has already been seen."); //$NON-NLS-1$
  }
}
