package org.optimizationBenchmarking.utils.io.paths.predicates;

import java.nio.file.Path;
import java.util.Collection;

import org.optimizationBenchmarking.utils.io.paths.PathUtils;
import org.optimizationBenchmarking.utils.text.predicates.StringInListIgnoreCase;

/**
 * This predicate is {@code true} if the file name matches a given
 * template.
 */
public final class FileNamePredicate extends StringInListIgnoreCase<Path> {

  /** the serial version uid */
  private static final long serialVersionUID = 1L;

  /**
   * Create the predicate
   * 
   * @param list
   *          the string list
   */
  public FileNamePredicate(final String[] list) {
    super(list);
  }

  /**
   * Create the predicate
   * 
   * @param list
   *          the string list
   */
  public FileNamePredicate(final Collection<String> list) {
    super(list);
  }

  /** {@inheritDoc} */
  @Override
  protected final String getString(final Path file) {
    final String s;

    s = PathUtils.getName(file);
    return ((s != null) ? s.toLowerCase() : null);
  }
}
