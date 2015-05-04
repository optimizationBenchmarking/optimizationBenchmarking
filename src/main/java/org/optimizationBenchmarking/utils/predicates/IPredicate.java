package org.optimizationBenchmarking.utils.predicates;

/**
 * A predicate on a given object of type {@code T}.
 *
 * @param <T>
 *          the element type
 */
public interface IPredicate<T> {

  /**
   * Check the predicate on {@code object}
   *
   * @param object
   *          the object to check
   * @return {@code true} if the predicate is {@code true}, {@code false}
   *         otherwise
   */
  public abstract boolean check(final T object);
}
