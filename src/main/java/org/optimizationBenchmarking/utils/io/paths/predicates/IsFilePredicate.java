package org.optimizationBenchmarking.utils.io.paths.predicates;

import java.io.Serializable;
import java.nio.file.attribute.BasicFileAttributes;

/** This predicate returns {@code true} only if a path identifies a file */
public final class IsFilePredicate extends BasicFileAttributesPredicate
    implements Serializable {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /** the globally shared instance */
  public static final IsFilePredicate INSTANCE = new IsFilePredicate();

  /** create */
  private IsFilePredicate() {
    super();
  }

  /** {@inheritDoc} */
  @Override
  public final boolean check(final BasicFileAttributes atts) {
    return ((atts != null) && (atts.isRegularFile()));
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
