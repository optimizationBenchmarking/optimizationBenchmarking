package org.optimizationBenchmarking.utils.io.path;

import java.nio.file.Path;

import org.optimizationBenchmarking.utils.text.TextUtils;

/**
 * A base class for objects which can create files of a specific type.
 */
public abstract class FileTypeDriver {

  /** the file extension */
  private final String m_suffix;

  /**
   * instantiate
   * 
   * @param suffix
   *          the file suffix
   */
  protected FileTypeDriver(final String suffix) {
    super();

    String s;

    s = TextUtils.prepare(suffix);
    if (s != null) {
      if (s.charAt(0) == '.') {
        s = TextUtils.prepare(s.substring(1));
      }
    }

    if (s == null) {
      throw new IllegalArgumentException(//
          "Suffix must not be null or empty, but is '" //$NON-NLS-1$
              + suffix + '\'');
    }
    this.m_suffix = s;
  }

  /**
   * Get the file suffix associated with this driver
   * 
   * @return the file suffix associated with this driver
   */
  public final String getSuffix() {
    return this.m_suffix;
  }

  /**
   * Create a file name for a file in a given folder, append the suffix to
   * the name suggestion if necessary
   * 
   * @param folder
   *          the folder
   * @param nameSuggestion
   *          the name suggestion
   * @return the path
   */
  protected final Path makePath(final Path folder,
      final String nameSuggestion) {
    return PathUtils.normalize(folder.resolve(PathUtils.makeFileName(
        nameSuggestion, this.m_suffix)));
  }
}
