package org.optimizationBenchmarking.utils.io.files;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.optimizationBenchmarking.utils.io.path.PathUtils;

/**
 * A temporary directory <strong>This works currently with files under the
 * hood, but should be modified to work with path.
 */
public class TempDir implements Closeable {

  /** the directory */
  private volatile Path m_dir;

  /**
   * create the temporary directory
   * 
   * @throws IOException
   *           if io fails
   */
  public TempDir() throws IOException {
    super();

    this.m_dir = PathUtils.normalize(Files.createTempDirectory(null));
  }

  /**
   * Get the temporary directory
   * 
   * @return the directory
   */
  public final Path getDir() {
    return this.m_dir;
  }

  /** {@inheritDoc} */
  @Override
  public final void close() throws IOException {
    final File f;
    synchronized (this) {
      f = this.m_dir.toFile();// TODO: this must be changed!
      this.m_dir = null;
    }
    if (f != null) {
      new DeleteFile(f).call();
    }
  }
}
