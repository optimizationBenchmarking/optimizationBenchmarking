package org.optimizationBenchmarking.utils.io.files;

import java.io.File;
import java.io.IOException;
import java.security.AccessController;

import org.optimizationBenchmarking.utils.tasks.Task;

/** a task which canonicalizes a file */
public final class CanonicalizeFile extends Task<File> {

  /** the file to canonicalize */
  private final File m_file;

  /**
   * Create a task which can fully resolve (canonicalize) a file name.
   * 
   * @param file
   *          the file to canonicalize
   */
  public CanonicalizeFile(final File file) {
    super();
    this.m_file = file;
  }

  /**
   * Create a task which can fully resolve (canonicalize) a file name.
   * 
   * @param name
   *          the name of the file to canonicalize
   */
  public CanonicalizeFile(final String name) {
    super();
    this.m_file = new File(name);
  }

  /** {@inheritDoc} */
  @Override
  public final File call() throws IOException, SecurityException {
    if (this.m_file instanceof _CanonicalFile) {
      return this.m_file;
    }

    try {
      return new _CanonicalFile(this.m_file.getCanonicalPath());
    } catch (IOException | SecurityException error) {
      try {
        return AccessController.doPrivileged(new _Canonicalizer(
            this.m_file));
      } catch (final Throwable tt) {
        //
      }
      throw error;
    }
  }
}
