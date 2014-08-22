package org.optimizationBenchmarking.utils.io.files;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/** A temporary directory */
public class TempDir implements Closeable {

  /** the directory */
  private volatile File m_dir;

  /**
   * create the temporary directory
   * 
   * @throws IOException
   *           if io fails
   */
  public TempDir() throws IOException {
    super();

    this.m_dir = new CanonicalizeFile(Files.createTempDirectory(null)
        .toFile()).call();
    this.m_dir.mkdirs();
    this.m_dir.deleteOnExit();
  }

  /**
   * Get the temporary directory
   * 
   * @return the directory
   */
  public final File getDir() {
    return this.m_dir;
  }

  /** {@inheritDoc} */
  @Override
  public final void close() throws IOException {
    final File f;
    synchronized (this) {
      f = this.m_dir;
      this.m_dir = null;
    }
    if (f != null) {
      new DeleteFile(f).call();
    }
  }
}
