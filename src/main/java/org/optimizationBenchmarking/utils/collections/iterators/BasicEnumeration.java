package org.optimizationBenchmarking.utils.collections.iterators;

import java.util.Enumeration;
import java.util.NoSuchElementException;

/**
 * A base class implementing the {@link java.util.Enumeration} interface.
 *
 * @param <T>
 *          the type to iterate about
 */
public class BasicEnumeration<T> implements Enumeration<T> {

  /** the empty enumeration */
  public static final BasicEnumeration<Object> EMPTY_ENUMERATION = new BasicEnumeration<>();

  /** Instantiate the enumeration base */
  protected BasicEnumeration() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public boolean hasMoreElements() {
    return false;
  }

  /** {@inheritDoc} */
  @Override
  public T nextElement() {
    throw new NoSuchElementException();
  }

}
