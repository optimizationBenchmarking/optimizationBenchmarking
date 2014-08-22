package org.optimizationBenchmarking.utils.io.files.predicates;

import java.io.File;
import java.util.Collection;

import org.optimizationBenchmarking.utils.text.predicates.StringInListIgnoreCase;

/**
 * This predicate is {@code true} if the file name matches a given
 * template.
 */
public final class FileNamePredicate extends StringInListIgnoreCase<File> {

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
  protected final String getString(final File file) {
    return file.getName().toLowerCase();
  }
}
