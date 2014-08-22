package org.optimizationBenchmarking.utils.io.files;

import java.io.File;
import java.io.IOException;

import org.optimizationBenchmarking.utils.tasks.Task;

/** this task deletes a file or directory<@javaAutorVersion/> */
public final class DeleteFile extends Task<Void> {

  /** the file or directory to delete */
  private final File m_file;

  /**
   * should we try to force deletion ({@code true}) or stop any recursive
   * deletion attempt as soon as one fails ({@code false})
   */
  final boolean m_failFast;

  /** the error */
  transient Exception m_error;

  /**
   * Create
   * 
   * @param file
   *          the file or directory to delete
   * @param failFast
   *          should we try to force deletion ({@code true}) or stop any
   *          recursive deletion attempt as soon as one fails ({@code false}
   *          )?
   */
  public DeleteFile(final File file, final boolean failFast) {
    super();
    this.m_file = file;
    this.m_failFast = failFast;
  }

  /**
   * Create
   * 
   * @param file
   *          the file or directory to delete
   */
  public DeleteFile(final File file) {
    this(file, false);
  }

  /** {@inheritDoc} */
  @Override
  public final Void call() throws IOException, SecurityException {
    Exception e;

    this.m_error = null;
    new FileSystemWalker(this.m_file, new _DeleteFile(this), true, true)
        .call();
    e = this.m_error;
    if (e != null) {
      this.m_error = null;
      if (e instanceof IOException) {
        throw ((IOException) e);
      }
      if (e instanceof SecurityException) {
        throw ((SecurityException) e);
      }
      throw new RuntimeException(e);
    }
    return null;
  }

}
