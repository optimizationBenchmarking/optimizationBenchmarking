package org.optimizationBenchmarking.utils.predicates;

import java.io.Serializable;

/**
 * A predicate that returns the same value for each {@code null} object.
 */
public final class NotNullPredicate extends Object implements
    IPredicate<Object>, Serializable {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * return {@code true} for each {@code null} object and {@code false} for
   * each non-{@code null} object
   */
  public static final NotNullPredicate INSTANCE = new NotNullPredicate();

  /** create */
  private NotNullPredicate() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final boolean check(final Object param) {
    return (param != null);
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return this.getClass().hashCode();
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    return (o instanceof NotNullPredicate);
  }

  /**
   * write replace
   * 
   * @return the replacement
   */
  private final Object writeReplace() {
    return NotNullPredicate.INSTANCE;
  }

  /**
   * read resolve
   * 
   * @return the replacement
   */
  private final Object readResolve() {
    return NotNullPredicate.INSTANCE;
  }

}
