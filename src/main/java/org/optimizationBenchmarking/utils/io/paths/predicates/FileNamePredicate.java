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
   * ignore (remove) the file extension (if any) before the name comparison
   */
  private final boolean m_ignoreExtension;

  /**
   * Create the file name comparison predicate
   * 
   * @param ignoreExtension
   *          ignore (remove) the file extension (if any) before the name
   *          comparison
   * @param list
   *          the string list
   */
  public FileNamePredicate(final boolean ignoreExtension,
      final String[] list) {
    super(list);
    this.m_ignoreExtension = ignoreExtension;
  }

  /**
   * Create the file name comparison predicate
   * 
   * @param ignoreExtension
   *          ignore (remove) the file extension (if any) before the name
   *          comparison
   * @param list
   *          the string list
   */
  public FileNamePredicate(final boolean ignoreExtension,
      final Collection<String> list) {
    super(list);
    this.m_ignoreExtension = ignoreExtension;
  }

  /** {@inheritDoc} */
  @Override
  protected final String getString(final Path file) {
    String s;
    int idx;

    s = PathUtils.getName(file);
    if (s != null) {
      if (this.m_ignoreExtension) {
        idx = s.lastIndexOf('.');
        if (idx > 0) {
          s = s.substring(0, idx);
        }
      }
      return s.toLowerCase();
    }

    return null;
  }
}
