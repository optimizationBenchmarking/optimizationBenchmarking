package org.optimizationBenchmarking.utils.io.files;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.optimizationBenchmarking.utils.tasks.Task;

/** UnzipToFolder a zip file to a folder.<@javaAutorVersion/> */
public final class UnzipToFolder extends Task<Void> {

  /** the zip file */
  private final InputStream m_input;

  /** the destination directory */
  private final File m_destDir;

  /**
   * Create the find file unzip action
   * 
   * @param input
   *          the input stream
   * @param destDir
   *          the destination dir
   */
  public UnzipToFolder(final InputStream input, final File destDir) {
    super();
    this.m_input = input;
    this.m_destDir = destDir;
  }

  /** {@inheritDoc} */
  @Override
  public final Void call() throws IOException {
    final File res;
    File out;
    ZipEntry entry;
    byte[] buffer;
    int bytesRead;

    try (final InputStream input = this.m_input) {
      try (final ZipInputStream zis = new ZipInputStream(input)) {
        res = new CanonicalizeFile(this.m_destDir).call();
        buffer = null;

        while ((entry = zis.getNextEntry()) != null) {
          try {

            out = new CanonicalizeFile(new File(res, entry.getName()))
                .call();

            if (entry.isDirectory()) {
              out.mkdirs();
              continue;
            }
            out.getParentFile().mkdirs();

            try (final FileOutputStream fos = new FileOutputStream(out)) {
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
