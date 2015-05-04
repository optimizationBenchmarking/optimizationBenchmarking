package org.optimizationBenchmarking.utils.io.paths.predicates;

import java.io.Serializable;
import java.nio.file.attribute.BasicFileAttributes;

import org.optimizationBenchmarking.utils.predicates.IPredicate;

/**
 * This predicate returns {@code true} only if a path identifies a
 * directory
 */
public final class IsDirectoryPredicate implements
    IPredicate<BasicFileAttributes>, Serializable {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance */
  public static final IsDirectoryPredicate INSTANCE = new IsDirectoryPredicate();

  /** create */
  private IsDirectoryPredicate() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final boolean check(final BasicFileAttributes atts) {
    return ((atts != null) && (atts.isDirectory()));
  }

  /**
   * write replace
   *
   * @return the replacement
   */
  private final Object writeReplace() {
    return CanExecutePredicate.INSTANCE;
  }

  /**
   * read resolve
   *
   * @return the replacement
   */
  private final Object readResolve() {
    return CanExecutePredicate.INSTANCE;
  }
}
