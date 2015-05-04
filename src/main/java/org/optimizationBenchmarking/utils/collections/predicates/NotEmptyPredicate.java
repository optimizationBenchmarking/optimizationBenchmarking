package org.optimizationBenchmarking.utils.collections.predicates;

import java.io.Serializable;
import java.util.Collection;

import org.optimizationBenchmarking.utils.predicates.IPredicate;

/**
 * A condition checking whether a collection is empty.
 */
public final class NotEmptyPredicate implements IPredicate<Collection<?>>,
    Serializable {
  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the not-empty condition */
  public static final NotEmptyPredicate INSTANCE = new NotEmptyPredicate();

  /** create */
  private NotEmptyPredicate() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final boolean check(final Collection<?> param) {
    return ((param != null) && (!(param.isEmpty())));
  }

  /** {@inheritDoc} */
  @Override
  public final boolean equals(final Object o) {
    return (o instanceof NotEmptyPredicate);
  }

  /** {@inheritDoc} */
  @Override
  public final int hashCode() {
    return NotEmptyPredicate.class.hashCode();
  }

  /**
   * write replace
   *
   * @return the replacement
   */
  private final Object writeReplace() {
    return NotEmptyPredicate.INSTANCE;
  }

  /**
   * read resolve
   *
   * @return the replacement
   */
  private final Object readResolve() {
    return NotEmptyPredicate.INSTANCE;
  }
}
