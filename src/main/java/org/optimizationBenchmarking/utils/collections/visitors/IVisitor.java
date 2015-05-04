package org.optimizationBenchmarking.utils.collections.visitors;

/**
 * A visitor that gets shown elements of type {@code T}.
 *
 * @param <T>
 *          the element type
 */
public interface IVisitor<T> {

  /**
   * Visit the given element
   *
   * @param object
   *          the object to visit
   * @return {@code true} if visiting should continue, {@code false}
   *         otherwise
   */
  public abstract boolean visit(final T object);

}
