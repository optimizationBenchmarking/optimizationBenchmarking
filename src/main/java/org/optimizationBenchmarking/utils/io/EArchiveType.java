package org.optimizationBenchmarking.utils.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.optimizationBenchmarking.utils.io.paths.PathUtils;

/**
 * The archive types supported by the structured I/O API. In the future,
 * the Boolean "isZipCompressed" parameters of many of the output and input
 * job builders will be redesigned to take an argument of this type here
 * instead.
 */
public enum EArchiveType implements IFileType {

  /** the ZIP archive type */
  ZIP() {
    /** {@inheritDoc} */
    @Override
    public final String getDefaultSuffix() {
      return "zip"; //$NON-NLS-1$
    }

    /** {@inheritDoc} */
    @Override
    public final String getMIMEType() {
      return "application/zip";//$NON-NLS-1$
    }

    /** {@inheritDoc} */
    @Override
    public final String getName() {
      return "ZIP Archive"; //$NON-NLS-1$
    }

    /** {@inheritDoc} */
    @Override
    public final void compressPathToStream(final Path sourceFolder,
        final OutputStream destStream) throws IOException {
      final Path res;

      res = PathUtils.normalize(sourceFolder);
      try (final ZipOutputStream zipStream = new ZipOutputStream(
          destStream)) {
        zipStream.setMethod(ZipOutputStream.DEFLATED);
        zipStream.setLevel(Deflater.BEST_COMPRESSION);
        Files.walkFileTree(res, new __Zipper(res, zipStream));
      }
    }

    /** {@inheritDoc} */
    @Override
    public final void decompressStreamToFolder(
        final InputStream sourceStream, final Path destFolder)
        throws IOException {

      final Path res;
      Path out;
      ZipEntry entry;
      byte[] buffer;
      int bytesRead;

      res = PathUtils.normalize(destFolder);

      try (final ZipInputStream zis = new ZipInputStream(sourceStream)) {
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
  };

  /**
   * Compress a single file or a whole folder structure to an output
   * stream.
   * 
   * @param sourceFolder
   *          the folder
   * @param destStream
   *          the destination stream
   * @throws IOException
   *           if I/O fails
   */
  public abstract void compressPathToStream(final Path sourceFolder,
      final OutputStream destStream) throws IOException;

  /**
   * De-compress a stream to a folder.
   * 
   * @param sourceStream
   *          the source stream
   * @param destFolder
   *          the destination folder
   * @throws IOException
   *           if I/O fails
   */
  public abstract void decompressStreamToFolder(
      final InputStream sourceStream, final Path destFolder)
      throws IOException;

  /**
   * A visitor for the storing paths into a ZIP archive.
   */
  private static final class __Zipper extends SimpleFileVisitor<Path> {

    /** the root path */
    private final Path m_root;

    /** the output stream */
    private final ZipOutputStream m_zipStream;

    /**
     * create the zipper
     * 
     * @param root
     *          the root path
     * @param zipStream
     *          the stream
     */
    __Zipper(final Path root, final ZipOutputStream zipStream) {
      super();
      this.m_root = root;
      this.m_zipStream = zipStream;
    }

    /** {@inheritDoc} */
    @Override
    public final FileVisitResult preVisitDirectory(final Path dir,
        final BasicFileAttributes attrs) {
      return FileVisitResult.CONTINUE;
    }

    /** {@inheritDoc} */
    @Override
    public final FileVisitResult visitFile(final Path file,
        final BasicFileAttributes attrs) throws IOException {
      String name;

      if (file.equals(this.m_root)) {
        name = PathUtils.getName(file);
      } else {
        name = this.m_root.relativize(file).toString();
      }

      this.m_zipStream.putNextEntry(new ZipEntry(name));
      try {
        Files.copy(file, this.m_zipStream);
      } finally {
        this.m_zipStream.closeEntry();
      }
      return FileVisitResult.CONTINUE;
    }

    /** {@inheritDoc} */
    @Override
    public final FileVisitResult visitFileFailed(final Path file,
        final IOException exc) throws IOException {
      throw exc;
    }

    /** {@inheritDoc} */
    @Override
    public final FileVisitResult postVisitDirectory(final Path dir,
        final IOException exc) throws IOException {
      return FileVisitResult.CONTINUE;
    }
  }
}