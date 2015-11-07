package org.optimizationBenchmarking.utils.io.paths.predicates;

import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

import org.optimizationBenchmarking.utils.predicates.IPredicate;

/**
 * A predicate working on file attributes.
 */
public abstract class BasicFileAttributesPredicate implements
    IPredicate<BasicFileAttributes> {

  /** create */
  protected BasicFileAttributesPredicate() {
    super();
  }

  /**
   * Check the predicate by obtaining the attributes from a path and
   * passing them to
   * {@link org.optimizationBenchmarking.utils.predicates.IPredicate#check(Object)}
   * .
   *
   * @param path
   *          the path
   * @return {@code true} if the attributes of the path could successfully
   *         be obtained and
   *         {@link org.optimizationBenchmarking.utils.predicates.IPredicate#check(Object)}
   *         returns {@code true}, {@code false} otherwise
   */
  @SuppressWarnings("unused")
  public final boolean check(final Path path) {
    final BasicFileAttributes bfa;

    if (path == null) {
      return false;
    }

    try {
      bfa = java.nio.file.Files.readAttributes(path,
          BasicFileAttributes.class);
      return this.check(bfa);
    } catch (final Throwable error) {
      return false;
    }
  }
}
