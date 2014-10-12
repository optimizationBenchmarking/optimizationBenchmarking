package org.optimizationBenchmarking.utils.io.files;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.optimizationBenchmarking.utils.io.path.PathUtils;
import org.optimizationBenchmarking.utils.tasks.Task;

/** UnzipToFolder a zip file to a folder.<@javaAutorVersion/> */
public final class UnzipToFolder extends Task<Void> {

  /** the zip file */
  private final InputStream m_input;

  /** the destination directory */
  private final Path m_destDir;

  /**
   * Create the find file unzip action
   * 
   * @param input
   *          the input stream
   * @param destDir
   *          the destination dir
   */
  public UnzipToFolder(final InputStream input, final Path destDir) {
    super();
    this.m_input = input;
    this.m_destDir = destDir;
  }

  /** {@inheritDoc} */
  @Override
  public final Void call() throws IOException {
    final Path res;
    Path out;
    ZipEntry entry;
    byte[] buffer;
    int bytesRead;

    try (final InputStream input = this.m_input) {
      try (final ZipInputStream zis = new ZipInputStream(input)) {
        res = PathUtils.normalize(this.m_destDir);
        buffer = null;

        while ((entry = zis.getNextEntry()) != null) {
          try {

            out = PathUtils.createPathInside(res, entry.getName());

            if (entry.isDirectory()) {
              Files.createDirectories(out);
              continue;
            }
            Files.createDirectories(out.getParent());

            try (final OutputStream fos = PathUtils.openOutputStream(out)) {
              if (buffer == null) {
                buffer = new byte[4096];
              }
              bytesRead = 0;

              while ((bytesRead = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, bytesRead);
              }
            }

          } finally {
            zis.closeEntry();
          }
        }
      }
    }
    return null;
  }
}
